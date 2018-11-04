package cn.crap.controller.user;

import cn.crap.adapter.DebugAdapter;
import cn.crap.dto.DebugDto;
import cn.crap.dto.DebugInterfaceParamDto;
import cn.crap.dto.LoginInfoDto;
import cn.crap.enu.MyError;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.Debug;
import cn.crap.model.Module;
import cn.crap.model.Project;
import cn.crap.query.DebugQuery;
import cn.crap.query.ModuleQuery;
import cn.crap.service.DebugService;
import cn.crap.service.ModuleService;
import cn.crap.service.ProjectService;
import cn.crap.utils.LoginUserHelper;
import cn.crap.utils.MD5;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/user/crapDebug")
public class CrapDebugController extends BaseController {
    @Autowired
    private DebugService debugService;
    @Autowired
    private DebugService customDebugService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ModuleService moduleService;

    @RequestMapping("/synch.do")
    @ResponseBody
    @AuthPassport
    public JsonResult synch(@RequestBody String body) throws MyException {
        List<DebugInterfaceParamDto> list = JSON.parseArray(body, DebugInterfaceParamDto.class);
        LoginInfoDto user = LoginUserHelper.getUser();

        // 调试项目ID唯一，根据用户ID生成，不在CrapApi网站显示
        String projectId = MD5.encrytMD5(user.getId(), "").substring(0, 20) + "-debug";
        Project project = projectService.getById(projectId);
        if (project == null) {
            project = buildProject(user, projectId);
            projectService.insert(project);
        }

        int moduleSequence = 0;
        for (DebugInterfaceParamDto d : list) {
            String moduleId = d.getModuleId();
            if (d == null || MyString.isEmpty(moduleId)) {
                continue;
            }

            try {
                // id = id + 用户ID MD5
                moduleId = Tools.handleId(user, moduleId);
                // 处理模块：删除、更新、添加，处理异常
                handelModule(user, project, moduleSequence, d, moduleId);
                moduleSequence = moduleSequence + 1;

                // 处理模块ID、用户ID，避免多个用户混乱问题
                handelModuleIdAndDubugId(user, d, moduleId);
                // 先删除需要删除的接口
                deleteDebug(user, d, moduleId);

                // 每个用户的最大接口数量不能超过100
                int totalNum = debugService.count(new DebugQuery().setUserId(user.getId()));
                if (totalNum > 100) {
                    return new JsonResult(MyError.E000058);
                }

                // 更新接口
                addDebug(user, d, totalNum);
            }catch (Exception e){
                e.printStackTrace();
                continue;
            }
        }


        // 组装返回数据
        List<Module> modules = moduleService.query(new ModuleQuery().setProjectId(projectId));
        List<String> moduleIds = new ArrayList<>();
        for (Module m : modules) {
            moduleIds.add(m.getId());
        }

        List<Debug> debugs = debugService.query(new DebugQuery().setModuleIds(moduleIds));
        Map<String, List<DebugDto>> mapDebugs = new HashMap<>();
        for (Debug d : debugs) {
            try {
                List<DebugDto> moduleDebugs = mapDebugs.get(d.getModuleId());
                if (moduleDebugs == null) {
                    moduleDebugs = new ArrayList<>();
                    mapDebugs.put(d.getModuleId(), moduleDebugs);
                }
                moduleDebugs.add(DebugAdapter.getDto(d));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        List<DebugInterfaceParamDto> returnlist = new ArrayList<DebugInterfaceParamDto>();
        for (Module m : modules) {
            try {
                DebugInterfaceParamDto debugDto = new DebugInterfaceParamDto();
                debugDto.setModuleId(Tools.unhandleId(m.getId()));
                debugDto.setModuleName(m.getName());
                debugDto.setVersion(m.getVersion());
                debugDto.setStatus(m.getStatus());
                debugDto.setDebugs(mapDebugs.get(m.getId()) == null ? new ArrayList<DebugDto>() : mapDebugs.get(m.getId()));
                returnlist.add(debugDto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new JsonResult(1, returnlist);
    }

    private void addDebug(LoginInfoDto user, DebugInterfaceParamDto d, int totalNum) {
        int debugSequence = 0;
        for (DebugDto debug : d.getDebugs()) {
            debugSequence = debugSequence + 1;
            debug.setSequence(debugSequence);
            try {
                if (MyString.isEmpty(debug.getId())) {
                    continue;
                }
                if (debug.getStatus() == -1) {
                    continue;
                }

                // 更新接口
                Debug old = debugService.getById(debug.getId());
                if (old != null){
                    if (old.getVersion() > debug.getVersion()){
                        continue;
                    }
                    debug.setCreateTime(old.getCreateTime());
                    if (old.getModuleId().equals(debug.getModuleId())) {
                        debug.setStatus(old.getStatus());
                        debug.setUid(user.getId());
                        debugService.update(DebugAdapter.getModel(debug));
                    }
                    continue;
                }
                debug.setUid(user.getId());
                debug.setCreateTime(new Date());
                debugService.insert(DebugAdapter.getModel(debug));
                totalNum = totalNum + 1;
            } catch (Exception e) {
                    e.printStackTrace();
                    continue;
            }
        }
    }

    private void handelModuleIdAndDubugId(LoginInfoDto user, DebugInterfaceParamDto d, String moduleId) {
        for (DebugDto debug : d.getDebugs()) {
            try {
                if (MyString.isEmpty(debug.getId())) {
                    continue;
                }
                debug.setId(Tools.handleId(user, debug.getId()));
                debug.setModuleId(moduleId);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    private void deleteDebug(LoginInfoDto user, DebugInterfaceParamDto d, String moduleId) {
        for (DebugDto debug : d.getDebugs()) {
            try {
                if (MyString.isEmpty(debug.getId())) {
                    continue;
                }
                Debug old = debugService.getById(debug.getId());
                if (old == null || !old.getModuleId().equals(moduleId)){
                    continue;
                }
                if (debug.getStatus() == -1) {
                    debugService.delete(debug.getId());
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    private void handelModule(LoginInfoDto user, Project project, int moduleSequence, DebugInterfaceParamDto d, String moduleId) throws Exception{
        d.setModuleId(moduleId);
        Module module = moduleService.getById(moduleId);

        // 新增模块
        if (module == null && d.getStatus() != -1) {
            module = buildModule(user, project, moduleSequence, d);
            moduleService.insert(module);
        }

        // 删除模块
        else if (d.getStatus() != null && d.getStatus() == -1) {
            Module delete = new Module();
            delete.setId(moduleId);
            moduleService.delete(delete.getId());
            customDebugService.deleteByModelId(moduleId);
        }

        // 更新模块
        else if (d.getVersion() == null || module.getVersion() <= d.getVersion()) {
            module.setVersion(d.getVersion() == null ? 0 : d.getVersion());
            module.setName(d.getModuleName());
            module.setSequence(moduleSequence);
            moduleService.update(module);
        }
    }

    private Module buildModule(LoginInfoDto user, Project project, int moduleSequence, DebugInterfaceParamDto d) {
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
        project.setCover("/resources/images/cover.png");
        project.setLuceneSearch(Byte.valueOf("0"));
        project.setName("默认调试项目");
        project.setStatus(Byte.valueOf("-1"));
        project.setSequence(0);
        project.setType(Byte.valueOf("1"));
        project.setUserId(user.getId());
        project.setCreateTime(new Date());
        project.setRemark("该项目是系统自动创建的apiDebug插件接口，请勿删除！！！！");
        return project;
    }

}
