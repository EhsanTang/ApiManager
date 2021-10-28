package cn.crap.controller.user;

import cn.crap.ability.ProjectAbility;
import cn.crap.adapter.DebugAdapter;
import cn.crap.adapter.InterfaceAdapter;
import cn.crap.dto.DebugDto;
import cn.crap.dto.DebugInterfaceParamDto;
import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.PostwomanResDTO;
import cn.crap.enu.MyError;
import cn.crap.enu.ProjectStatus;
import cn.crap.enu.ProjectType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.InterfaceWithBLOBs;
import cn.crap.model.ModulePO;
import cn.crap.model.ProjectPO;
import cn.crap.model.ProjectUserPO;
import cn.crap.query.InterfaceQuery;
import cn.crap.query.ModuleQuery;
import cn.crap.service.InterfaceService;
import cn.crap.service.ModuleService;
import cn.crap.service.ProjectService;
import cn.crap.service.ProjectUserService;
import cn.crap.utils.LoginUserHelper;
import cn.crap.utils.MD5;
import cn.crap.utils.MyString;
import cn.crap.utils.VipUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO 待解决问题：路径参数问题
 */
@Controller
@RequestMapping("/user/crapDebug/v1")
public class CrapDebugV1Controller extends BaseController {
    protected Logger log = Logger.getLogger(getClass());

    @Autowired
    private InterfaceService interfaceService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private ProjectAbility projectAbility;
    @Autowired
    private ProjectUserService projectUserService;

    @RequestMapping("/synch.do")
    @ResponseBody
    @AuthPassport
    @Transactional
    public JsonResult synch(@RequestBody String body, String projectUniKey, String defProjectName) throws Exception {
        List<DebugInterfaceParamDto> list = JSON.parseArray(body, DebugInterfaceParamDto.class);
        LoginInfoDto user = LoginUserHelper.getUser();
        log.error("sync projectUniKey:" + projectUniKey);

        /**
         * 1. 处理项目
         * 项目根据用户ID生成，所以一定是该用户的
         * TODO 后续要支持多项目切换：如果项目ID存在，且用户为当前用户则可直接使用，否者新建项目（使用当前项目名称）
         * 调试项目ID唯一，根据用户ID生成，不在CrapApi网站显示
         */
        ProjectPO project = null;
        if (MyString.isNotEmptyOrNUll(projectUniKey)){
            ProjectUserPO projectUserPO = projectUserService.getByProjectUniKey(user.getId(), projectUniKey);
            if (projectUserPO != null){
                project = projectService.get(projectUserPO.getProjectId());
            }
        }

        if (project == null){
            project = projectService.get(generateProjectId(user));
        }

        if (project == null) {
            project = buildProject(user, generateProjectId(user));
            project.setName(MyString.isNotEmptyOrNUll(defProjectName) ? defProjectName : project.getName());
            projectAbility.addProject(project, user);
        }

        String projectId = project.getId();
        projectUniKey = project.getUniKey();
        String projectName = project.getName();
        String userId = project.getUserId();

        /**
         * 2.处理模块+接口
         */
        long moduleSequence = System.currentTimeMillis();
        Map<String, ModulePO> moduleUniKeyMap = moduleService.select(new ModuleQuery().setProjectId(projectId).setQueryAll(true)).stream().collect(Collectors.toMap(ModulePO::getUniKey, a -> a,(k1, k2)->k1));

        for (DebugInterfaceParamDto debutModuleDTO : list) {

            /**
             * 2.1 模块处理
             */
            String moduleUniKey = debutModuleDTO.getModuleUniKey();
            if (debutModuleDTO == null || MyString.isEmpty(moduleUniKey)) {
                log.error("sync moduleUniKey is null:" + userId + ",moduleName:" + debutModuleDTO.getModuleName());
                continue;
            }

            ModulePO modulePO = moduleUniKeyMap.get(moduleUniKey);

            // 处理模块：删除、更新、添加，处理异常
            modulePO = handelModule(user, project, modulePO, moduleSequence, debutModuleDTO);
            moduleSequence = moduleSequence - 1;
            if (modulePO == null){
                continue;
            }

            moduleUniKeyMap.put(moduleUniKey, modulePO);

            // 先删除需要删除的接口
            deleteDebug(modulePO, debutModuleDTO);
        }

        // 每个用户的最大接口数量不能超过100
        int totalNum = interfaceService.count(new InterfaceQuery().setProjectId(projectId));

        for (DebugInterfaceParamDto debutModuleDTO : list) {
            String moduleUniKey = debutModuleDTO.getModuleUniKey();

            // 更新接口
            totalNum = addDebug(projectId, moduleUniKeyMap.get(moduleUniKey), user, debutModuleDTO, totalNum);
            int maxInterNum = VipUtil.getPostWomanPlugInterNum(settingCache, user);
            if (totalNum > maxInterNum) {
                log.error("sync addDebug error, totalNum:" + maxInterNum + ",userId:" + userId);
                return new JsonResult(0, null , MyError.E000058.name(), "最多允许同步" + maxInterNum + "个接口，请删除部分接口再试，或联系管理员修改数量！");
            }
        }

        /**
         *  组装返回数据
         *  id 全部使用uniKey替代
         */
        List<ModulePO> modules = moduleService.select(new ModuleQuery().setProjectId(projectId).setPageSize(100));
        Map<String, ModulePO> moduleMap = modules.stream().collect(Collectors.toMap(ModulePO::getId, a -> a,(k1, k2)->k1));

        List<InterfaceWithBLOBs> debugs = interfaceService.queryAll(new InterfaceQuery().setProjectId(projectId));
        Map<String, List<DebugDto>> mapDebugs = new HashMap<>();
        for (InterfaceWithBLOBs d : debugs) {
            try {
                String moduleId = d.getModuleId();
                List<DebugDto> moduleDebugs = mapDebugs.get(moduleId);
                if (moduleDebugs == null) {
                    moduleDebugs = new ArrayList<>();
                    mapDebugs.put(moduleId, moduleDebugs);
                }
                DebugDto dtoFromInterface = DebugAdapter.getDtoFromInterface(project, moduleMap, d);
                if (dtoFromInterface == null) {
                    continue;
                }
                moduleDebugs.add(dtoFromInterface);
            } catch (Throwable e){
                e.printStackTrace();
                try {
                    log.error("getDtoFromInterface error:" + JSON.toJSONString(d));
                } catch (Throwable e2){
                    e2.printStackTrace();
                }
            }
        }

        List<DebugInterfaceParamDto> returnList = new ArrayList<>();
        for (ModulePO m : modules) {
            try {
                DebugInterfaceParamDto debugDto = new DebugInterfaceParamDto();
                debugDto.setModuleId(m.getUniKey());
                debugDto.setModuleUniKey(m.getUniKey());
                debugDto.setModuleName(m.getName());
                debugDto.setVersion(m.getVersionNum());
                debugDto.setStatus(m.getStatus());
                debugDto.setDebugs(mapDebugs.get(m.getId()) == null ? new ArrayList<>() : mapDebugs.get(m.getId()));
                debugDto.setProjectUniKey(projectUniKey);
                returnList.add(debugDto);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    log.error("returnList error:" + JSON.toJSONString(m));
                } catch (Throwable e2){
                    e2.printStackTrace();
                }
            }
        }
        PostwomanResDTO postwomanResDTO = new PostwomanResDTO();
        postwomanResDTO.setProjectName(projectName);
        postwomanResDTO.setProjectUniKey(projectUniKey);
        postwomanResDTO.setProjectCover(project.getCover());

        postwomanResDTO.setModuleList(returnList);
        return new JsonResult(1, postwomanResDTO);
    }

    private String generateProjectId(LoginInfoDto user) {
        return MD5.encrytMD5(user.getId(), "").substring(0, 20) + "-debug";
    }

    private int addDebug(String projectId, ModulePO module, LoginInfoDto user, DebugInterfaceParamDto moduleDTO, int totalNum) throws MyException {
        if (module == null){
            return totalNum;
        }

        if (moduleDTO.getStatus() == -1) {
            return totalNum;
        }

        String moduleId = module.getId();
        long debugSequence = System.currentTimeMillis();

        Map<String, InterfaceWithBLOBs> interfaceMap = interfaceService.queryAll(new InterfaceQuery().setModuleId(moduleId))
                .stream().collect(Collectors.toMap(InterfaceWithBLOBs::getUniKey, a -> a, (k1, k2) -> k1));

        for (DebugDto debug : moduleDTO.getDebugs()) {
            debugSequence = debugSequence - 1;
            debug.setSequence(debugSequence);
            try {
                if (MyString.isEmpty(debug.getId())) {
                    log.error("addDebug debugId is null, moduleId:" + module.getId());
                    continue;
                }

                if (debug.getStatus() == -1) {
                    continue;
                }

                String uniKey = debug.getUniKey() == null ? debug.getId() : debug.getUniKey();
                InterfaceWithBLOBs old = interfaceMap.get(uniKey);
                if (old != null){
                    if (old.getVersionNum() >= debug.getVersion()){
                        log.error("addDebug ignore name:" + debug.getId());
                        continue;
                    }

                    debug.setStatus(old.getStatus());
                    debug.setUid(user.getId());
                    log.error("updateDebug id:" + debug.getId() + ",uniKey:" + uniKey);
                    interfaceService.update(DebugAdapter.getInterfaceByDebug(module, old, debug));
                    continue;
                }
                old = InterfaceAdapter.getInit();
                old.setProjectId(projectId);
                old.setModuleId(moduleId);
                old.setUniKey(uniKey);

                log.error("addDebug id:" + debug.getId() + ",uniKey:" + uniKey);
                interfaceService.insert(DebugAdapter.getInterfaceByDebug(module, old, debug));
                totalNum = totalNum + 1;
            } catch (Throwable e) {
                try {
                    log.error("addDebug error:" + JSON.toJSONString(debug));
                } catch (Throwable e2){
                    e2.printStackTrace();
                }
                e.printStackTrace();
                continue;
            }
        }
        return totalNum;
    }


    private void deleteDebug(ModulePO module, DebugInterfaceParamDto moduleDTO) throws Exception{
        Assert.notNull(module, "deleteDebug module is null");
        if (moduleDTO.getStatus() == -1) {
            return;
        }

        String moduleId = module.getId();
        List<String> uniKeyList = Lists.newArrayList();
        for (DebugDto debug : moduleDTO.getDebugs()) {
            if (MyString.isEmpty(debug.getUniKey())) {
                log.error("deleteDebug error debugId is null:" + debug.getName());
                continue;
            }

            if (debug.getStatus() == -1) {
                uniKeyList.add(debug.getUniKey());
            }
        }
        interfaceService.deleteByModuleId(moduleId, uniKeyList);
    }

    private ModulePO handelModule(LoginInfoDto user, ProjectPO project, ModulePO module, long moduleSequence, DebugInterfaceParamDto moduleDTO) throws Exception{

        // 新增模块
        if (module == null && moduleDTO.getStatus() != -1) {
            module = buildModule(project, moduleSequence, moduleDTO);
            moduleService.insert(module);
        }

        // 删除模块
        else if (moduleDTO.getStatus() == -1 && module != null) {
            try {
                interfaceService.deleteByModuleId(module.getId());
                moduleService.delete(module.getId());
            } catch (MyException e){
                log.error("crapDebugController delete module fail:" + module.getId() + "," + e.getErrorCode());
            }
        }

        // 更新模块
        else if (module != null && (moduleDTO.getVersion() == null || module.getVersionNum() <= moduleDTO.getVersion())) {
            module.setVersionNum(moduleDTO.getVersion() == null ? 0 : moduleDTO.getVersion());
            module.setName(moduleDTO.getModuleName());
            module.setSequence(moduleSequence);
            moduleService.update(module);
        }

        return module;
    }

    private ModulePO buildModule( ProjectPO project, long moduleSequence, DebugInterfaceParamDto d) {
        ModulePO module = new ModulePO();
        module.setName(d.getModuleName());
        module.setCreateTime(new Date());
        module.setSequence(moduleSequence);
        module.setProjectId(project.getId());
        module.setUserId(project.getUserId());
        module.setRemark("");
        module.setUrl("");
        module.setCategory("");
        module.setUniKey(d.getModuleUniKey() == null ? d.getModuleId() : d.getModuleUniKey());
        module.setVersionNum(d.getVersion() == null ? 0 : d.getVersion());
        return module;
    }

    private ProjectPO buildProject(LoginInfoDto user, String projectId) {
        ProjectPO project;
        project = new ProjectPO();
        project.setId(projectId);
        project.setCover("/resources/images/postwoman_logo.png");
        project.setLuceneSearch(Byte.valueOf("0"));
        project.setName("默认调试项目");
        project.setStatus(ProjectStatus.PLUG.getStatus());
        project.setSequence(System.currentTimeMillis());
        project.setType(ProjectType.PRIVATE.getByteType());
        project.setUserId(user.getId());
        project.setCreateTime(new Date());
        project.setRemark("Project auto create for postwoman「该项目是系统自动创建的PostWoman/ApiDebug插件项目」");
        return project;
    }

}
