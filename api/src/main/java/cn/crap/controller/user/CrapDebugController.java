package cn.crap.controller.user;

import cn.crap.adapter.DebugAdapter;
import cn.crap.adapter.InterfaceAdapter;
import cn.crap.dto.DebugDto;
import cn.crap.dto.DebugInterfaceParamDto;
import cn.crap.dto.LoginInfoDto;
import cn.crap.enu.MyError;
import cn.crap.enu.ProjectStatus;
import cn.crap.enu.ProjectType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.InterfaceWithBLOBs;
import cn.crap.model.Module;
import cn.crap.model.Project;
import cn.crap.query.InterfaceQuery;
import cn.crap.query.ModuleQuery;
import cn.crap.service.InterfaceService;
import cn.crap.service.ModuleService;
import cn.crap.service.ProjectService;
import cn.crap.utils.LoginUserHelper;
import cn.crap.utils.MD5;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;
import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * TODO 待解决问题：路径参数问题
 */
@Controller
@RequestMapping("/user/crapDebug")
public class CrapDebugController extends BaseController {
    protected Logger log = Logger.getLogger(getClass());

    @Autowired
    private InterfaceService interfaceService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ModuleService moduleService;

    @RequestMapping("/synch.do")
    @ResponseBody
    @AuthPassport
    @Transactional
    public JsonResult synch(@RequestBody String body) throws Exception {
        List<DebugInterfaceParamDto> list = JSON.parseArray(body, DebugInterfaceParamDto.class);
        LoginInfoDto user = LoginUserHelper.getUser();
        String userId = user.getId();

        /**
         * 1. 处理项目
         * TODO 后续要支持多项目切换：如果项目ID存在，且用户为当前用户则可直接使用，否者新建项目（使用当前项目名称）
         * 调试项目ID唯一，根据用户ID生成，不在CrapApi网站显示
         */
        String projectId = generateProjectId(user);
        Project project = projectService.getById(projectId);
        if (project == null) {
            project = buildProject(user, projectId);
            projectService.insert(project);
        }

        /**
         * 2.处理模块+接口
         */
        long moduleSequence = System.currentTimeMillis();
        for (DebugInterfaceParamDto moduleDTO : list) {

            /**
             * 2.1 模块处理
             */
            String moduleId = moduleDTO.getModuleId();
            if (moduleDTO == null || MyString.isEmpty(moduleId)) {
                log.error("sync moduleId is null:" + userId + ",moduleName:" + moduleDTO.getModuleName());
                continue;
            }

            // 兼容历史数据，ID中没有携带用户信息
            Module module = moduleService.getById(moduleId);
            if  (module == null || !module.getUserId().equals(userId)){
                moduleId = Tools.addUserInfoForId(user, moduleId);
                moduleDTO.setModuleId(moduleId);
                module = moduleService.getById(moduleId);
            }

            log.error("sync moduleId:" + moduleId);
            // 处理模块：删除、更新、添加，处理异常
            handelModule(user, project, module, moduleSequence, moduleDTO);

            moduleSequence = moduleSequence - 1;

            // 先删除需要删除的接口
            deleteDebug(user, moduleDTO);

            // 每个用户的最大接口数量不能超过100
            int totalNum = interfaceService.count(new InterfaceQuery().setProjectId(projectId));
            if (totalNum > 100) {
                return new JsonResult(MyError.E000058);
            }

            // 更新接口
            addDebug(projectId, module, user, moduleDTO, totalNum);
        }


        // 组装返回数据
        List<Module> modules = moduleService.query(new ModuleQuery().setProjectId(projectId).setPageSize(100));
        List<String> moduleIds = new ArrayList<>();
        for (Module m : modules) {
            moduleIds.add(m.getId());
        }

        List<InterfaceWithBLOBs> debugs = interfaceService.queryAll(new InterfaceQuery().setProjectId(projectId));
        Map<String, List<DebugDto>> mapDebugs = new HashMap<>();
        for (InterfaceWithBLOBs d : debugs) {
            List<DebugDto> moduleDebugs = mapDebugs.get(d.getModuleId());
            if (moduleDebugs == null) {
                moduleDebugs = new ArrayList<>();
                mapDebugs.put(d.getModuleId(), moduleDebugs);
            }
            moduleDebugs.add(DebugAdapter.getDtoFromInterface(d));
        }

        List<DebugInterfaceParamDto> returnlist = new ArrayList<DebugInterfaceParamDto>();
        for (Module m : modules) {
            try {
                DebugInterfaceParamDto debugDto = new DebugInterfaceParamDto();
                debugDto.setModuleId(m.getId());
                debugDto.setModuleName(m.getName());
                debugDto.setVersion(m.getVersion());
                debugDto.setStatus(m.getStatus());
                debugDto.setDebugs(mapDebugs.get(m.getId()) == null ? new ArrayList<>() : mapDebugs.get(m.getId()));
                returnlist.add(debugDto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new JsonResult(1, returnlist);
    }

    private String generateProjectId(LoginInfoDto user) {
        return MD5.encrytMD5(user.getId(), "").substring(0, 20) + "-debug";
    }

    private void addDebug(String projectId, Module module, LoginInfoDto user, DebugInterfaceParamDto moduleDTO, int totalNum) {
        if (moduleDTO.getStatus() == -1) {
            return;
        }

        long debugSequence = System.currentTimeMillis();
        for (DebugDto debug : moduleDTO.getDebugs()) {
            debugSequence = debugSequence - 1;
            debug.setSequence(debugSequence);
            String moduleId = moduleDTO.getModuleId();
            try {
                if (MyString.isEmpty(debug.getId())) {
                    log.error("addDebug debugId is null, moduleName:" + moduleDTO.getModuleName());
                    continue;
                }

                if (debug.getStatus() == -1) {
                    continue;
                }

                // 更新接口，兼容历史数据，部分接口没有用户MD5信息
                log.error("addDebug name:" + debug.getName() + ",id" + debug.getId());
                InterfaceWithBLOBs old = interfaceService.getById(debug.getId());
                if (old == null || !old.getModuleId().equals(moduleId)){
                    debug.setId(Tools.addUserInfoForId(user, debug.getId()));
                    old = interfaceService.getById(debug.getId());
                }

                if (old != null){
                    if (old.getVersionNum() >= debug.getVersion()){
                        log.error("addDebug ignore error name:" + debug.getName());
                        continue;
                    }
                    debug.setStatus(old.getStatus());
                    debug.setUid(user.getId());
                    interfaceService.update(DebugAdapter.getInterfaceByDebug(module, old, debug));
                    continue;
                }
                debug.setUid(user.getId());
                debug.setCreateTime(new Date());

                InterfaceWithBLOBs interfaceWithBLOBs = InterfaceAdapter.getInit();
                interfaceWithBLOBs.setProjectId(projectId);
                interfaceWithBLOBs.setModuleId(moduleId);
                interfaceService.insert(DebugAdapter.getInterfaceByDebug(module, interfaceWithBLOBs, debug));
                totalNum = totalNum + 1;
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }


    private void deleteDebug(LoginInfoDto user, DebugInterfaceParamDto moduleDTO) {
        if (moduleDTO.getStatus() == -1) {
            return;
        }

        for (DebugDto debug : moduleDTO.getDebugs()) {
            try {
                if (MyString.isEmpty(debug.getId())) {
                    log.error("deleteDebug error debugId is null:" + debug.getName());
                    continue;
                }

                if (debug.getStatus() == -1) {
                    log.error("deleteDebug debugName:" + debug.getName());
                    // 兼容历史数据，历史数据中不包含用户信息
                    interfaceService.deleteByModuleId(moduleDTO.getModuleId(), debug.getId());
                    interfaceService.deleteByModuleId(moduleDTO.getModuleId(), Tools.addUserInfoForId(user, debug.getId()));
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    private void handelModule(LoginInfoDto user, Project project, Module module, long moduleSequence, DebugInterfaceParamDto moduleDTO) throws Exception{
        String moduleId = moduleDTO.getModuleId();

        // 新增模块
        if (module == null && moduleDTO.getStatus() != -1) {
            module = buildModule(user, project, moduleSequence, moduleDTO);
            moduleService.insert(module);
        }

        // 删除模块
        else if (moduleDTO.getStatus() == -1 && module != null) {
            try {
                interfaceService.deleteByModuleId(moduleId);
                moduleService.delete(moduleId);
            } catch (MyException e){
                log.error("crapDebugController delete module fail:" + e.getErrorCode());
            }
        }

        // 更新模块
        else if (module != null && (moduleDTO.getVersion() == null || module.getVersion() <= moduleDTO.getVersion())) {
            module.setVersion(moduleDTO.getVersion() == null ? 0 : moduleDTO.getVersion());
            module.setName(moduleDTO.getModuleName());
            module.setSequence(moduleSequence);
            moduleService.update(module);
        }
    }

    private Module buildModule(LoginInfoDto user, Project project, long moduleSequence, DebugInterfaceParamDto d) {
        Module module = new Module();
        module.setId(d.getModuleId());
        module.setName(d.getModuleName());
        module.setCreateTime(new Date());
        module.setSequence(moduleSequence);
        module.setProjectId(project.getId());
        module.setUserId(user.getId());
        module.setRemark("");
        module.setUrl("");
        module.setVersion(d.getVersion() == null ? 0 : d.getVersion());
        return module;
    }

    private Project buildProject(LoginInfoDto user, String projectId) {
        Project project;
        project = new Project();
        project.setId(projectId);
        project.setCover("/resources/images/logo_new.png");
        project.setLuceneSearch(Byte.valueOf("0"));
        project.setName("默认调试项目");
        project.setStatus(ProjectStatus.COMMON.getStatus());
        project.setSequence(System.currentTimeMillis());
        project.setType(ProjectType.PRIVATE.getByteType());
        project.setUserId(user.getId());
        project.setCreateTime(new Date());
        project.setRemark("该项目是系统自动创建的PostWoman/ApiDebug插件项目，请勿删除！！！！");
        return project;
    }

}
