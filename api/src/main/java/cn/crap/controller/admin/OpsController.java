package cn.crap.controller.admin;

import cn.crap.adapter.ProjectUserAdapter;
import cn.crap.dto.LoginInfoDto;
import cn.crap.enu.ProjectUserType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.ProjectPO;
import cn.crap.model.ProjectUserPO;
import cn.crap.query.ProjectQuery;
import cn.crap.query.ProjectUserQuery;
import cn.crap.service.ProjectService;
import cn.crap.service.ProjectUserService;
import cn.crap.service.UserService;
import cn.crap.utils.TableField;
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

    @RequestMapping("/addProjectUser.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_SETTING)
    public JsonResult addProjectUser() throws Exception{
        ProjectQuery projectQuery = new ProjectQuery().setSort(TableField.SORT.ID_ASC).setPageSize(2);
        int i = 0;
        while (true) {
            i++;
            projectQuery.setCurrentPage(i);

            List<ProjectPO> projectPOS = projectService.select(projectQuery);
            if (CollectionUtils.isEmpty(projectPOS)) {
                return JsonResult.of();
            }

            for (ProjectPO p : projectPOS) {
                try {
                    List<ProjectUserPO> projectUserPOS = projectUserService.select(new ProjectUserQuery().setUserId(p.getUserId()).setProjectId(p.getId()));

                    LoginInfoDto loginInfoDto = new LoginInfoDto(userService.getById(p.getUserId()));
                    if (CollectionUtils.isEmpty(projectUserPOS)) {
                        ProjectUserPO initProjectUserPO = ProjectUserAdapter.getInitProjectUserPO(p.getId(), loginInfoDto);
                        initProjectUserPO.setType(ProjectUserType.CREATOR.getByteType());
                        projectUserService.insert(initProjectUserPO);
                        System.out.println("--add--" + p.getId() + "-----" + p.getName() + "----" + p.getUserId());
                        continue;
                    }
                    ProjectUserPO projectUserPO = projectUserPOS.get(0);
                    if (projectUserPO.getType().equals(ProjectUserType.CREATOR.getByteType())) {
                        continue;
                    }
                    projectUserPO.setType(ProjectUserType.CREATOR.getByteType());
                    projectUserService.update(projectUserPO);
                    System.out.println("--update--" + p.getId() + "-----" + p.getName() + "----" + p.getUserId());
                }catch (Exception e){
                    System.out.println("--+++++++++++++++++++++error++++++++++++++--" + p.getId() + "-----" + p.getName() + "----" + p.getUserId());
                }
            }
        }
    }
    // TODO 项目添加成员
    // TODO debug / interface 合并
}
