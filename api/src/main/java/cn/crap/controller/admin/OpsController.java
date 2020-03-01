package cn.crap.controller.admin;

import cn.crap.adapter.DebugAdapter;
import cn.crap.adapter.InterfaceAdapter;
import cn.crap.dto.DebugDto;
import cn.crap.framework.JsonResult;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.Debug;
import cn.crap.model.InterfaceWithBLOBs;
import cn.crap.model.ModulePO;
import cn.crap.query.DebugQuery;
import cn.crap.service.*;
import cn.crap.utils.TableField;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 数据修复运维类
 */
@Controller
@RequestMapping("/ops")
public class OpsController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectUserService projectUserService;
    @Autowired
    private UserService customUserService;
    @Autowired
    private InterfaceService interfaceService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private DebugService debugService;

    @RequestMapping("/addDebug.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_SETTING)
    public JsonResult addDebug() throws Exception {
        DebugQuery debugQuery = new DebugQuery().setSort(TableField.SORT.ID_ASC).setPageSize(20);
        int i = 0;
        while (true) {
            i++;
            debugQuery.setCurrentPage(i);
            List<Debug> debugs = debugService.query(debugQuery);
            if (CollectionUtils.isEmpty(debugs)) {
                return JsonResult.of();
            }

            for (Debug d : debugs) {
                try {
                    ModulePO module = moduleService.get(d.getModuleId());
                    InterfaceWithBLOBs interfaceWithBLOBs = InterfaceAdapter.getInit();
                    interfaceWithBLOBs.setProjectId(module.getProjectId());

                    DebugDto debugDto = new DebugDto();
                    BeanUtils.copyProperties(d, debugDto);

                    if (interfaceService.getById(d.getId()) == null) {
                        InterfaceWithBLOBs interfaceByDebug = DebugAdapter.getInterfaceByDebug(module, interfaceWithBLOBs, debugDto);
                        interfaceByDebug.setId(d.getId());

                        if (d.getId().lastIndexOf("-") > 0){
                            interfaceByDebug.setUniKey(d.getId().substring(0, d.getId().lastIndexOf("-")));
                        } else {
                            interfaceByDebug.setUniKey(d.getId());
                        }
                        interfaceService.insert(interfaceByDebug);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    System.out.println("---add-error---" + d.getId() + "----" + d.getUrl());
                }
            }

        }
    }
    // TODO debug / interface 合并
}
