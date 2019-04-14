package cn.crap.controller.user;

import cn.crap.adapter.ProjectUserAdapter;
import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.ProjectUserDto;
import cn.crap.enu.MyError;
import cn.crap.enu.ProjectPermissionEnum;
import cn.crap.enu.ProjectUserStatus;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.Project;
import cn.crap.model.ProjectUserPO;
import cn.crap.model.User;
import cn.crap.query.ProjectUserQuery;
import cn.crap.service.ProjectService;
import cn.crap.service.ProjectUserService;
import cn.crap.service.UserService;
import cn.crap.utils.LoginUserHelper;
import cn.crap.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user/projectUser")
public class ProjectUserController extends BaseController{

	@Autowired
	private ProjectService projectService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProjectUserService projectUserService;

	@RequestMapping("/list.do")
	@ResponseBody
    @AuthPassport
	public JsonResult list(@ModelAttribute ProjectUserQuery query) throws MyException{
		Assert.notNull(query.getProjectId());
        checkPermission( projectCache.get(query.getProjectId()));

		List<ProjectUserPO> projectUsers = projectUserService.select(query);
        Page page= new Page(query);
        page.setAllRow(projectUserService.count(query));

        List<ProjectUserDto> dto = ProjectUserAdapter.getDto(projectUsers);
        return new JsonResult(1, dto, page);
	}	
	
	@RequestMapping("/detail.do")
	@ResponseBody
    @AuthPassport
	public JsonResult detail(String id) throws MyException{
	    Assert.notNull(id);
		ProjectUserPO projectUser = projectUserService.get(id);
        Project project = projectCache.get(projectUser.getProjectId());
		checkPermission(project);
        ProjectUserDto projectUserDto = ProjectUserAdapter.getDto(projectUser, project);
		return new JsonResult(1, projectUserDto);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
    @AuthPassport
	public JsonResult addOrUpdate(@ModelAttribute ProjectUserDto projectUser) throws Exception{
	    Assert.notNull(projectUser.getId());
        ProjectUserPO old = projectUserService.get(projectUser.getId());
        checkPermission(old.getProjectId());

        User user = userService.getById(old.getUserId());
        projectUser.setUserEmail(user.getEmail());
		projectUser.setUserName(user.getUserName());

        ProjectUserPO model = ProjectUserAdapter.getModel(projectUser);
        /**
         * 禁止修改
         */
        model.setProjectId(null);
        model.setUserId(null);
        model.setStatus(null);
        projectUserService.update(model);
		return new JsonResult(1,projectUser);
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
    @AuthPassport
	public JsonResult delete(@RequestParam String id) throws Exception{
		ProjectUserPO projectUser = projectUserService.get(id);
		checkPermission(projectCache.get( projectUser.getProjectId() ));
		projectUserService.delete(projectUser.getId());
		return new JsonResult(1,null);
	}


    @RequestMapping("/quit.do")
    public String quit(@RequestParam String projectId, HttpServletRequest request) throws Exception{
        LoginInfoDto loginInfoDto = LoginUserHelper.getUser();
        String userId = loginInfoDto.getId();
        List<ProjectUserPO> projectUser = projectUserService.select(new ProjectUserQuery().setUserId(userId).setProjectId(projectId));
        if (CollectionUtils.isEmpty(projectUser)){
            request.setAttribute("title", "操作成功");
            request.setAttribute("result", "退出成功");
            return ERROR_VIEW;
        }
        projectUserService.delete(projectUser.get(0).getId());
        request.setAttribute("title", "操作成功");
        request.setAttribute("result", "退出成功");
        return ERROR_VIEW;
    }

    @RequestMapping("/invite.do")
    public String invite(@RequestParam String code, HttpServletRequest request, HttpServletResponse response) throws Exception{
        String projectId = projectService.getProjectIdFromInviteCode(code);
        Project project = projectService.getById(projectId);
        if (project == null){
            request.setAttribute("result", "抱歉，来得太晚了，项目已经被删除了");
            return ERROR_VIEW;
        }

        LoginInfoDto loginInfoDto = LoginUserHelper.tryGetUser();
        if (loginInfoDto == null){
            response.sendRedirect("/loginOrRegister.do#/login");
            return null;
        }

        String userId = loginInfoDto.getId();
        if (projectUserService.count(new ProjectUserQuery().setUserId(userId).setProjectId(projectId)) > 0){
            request.setAttribute("result", MyError.E000039.getMessage());
            return ERROR_VIEW;
        }

        if (userId.equals(project.getUserId())){
            request.setAttribute("result", "项目成员不能添加自己");
            return ERROR_VIEW;
        }

        ProjectUserPO projectUser = new ProjectUserPO();
        projectUser.setProjectId(projectId);
        projectUser.setUserId(userId);
        projectUser.setStatus(ProjectUserStatus.NORMAL.getStatus());
        projectUser.setUserEmail(loginInfoDto.getEmail());
        projectUser.setUserName(loginInfoDto.getUserName());
        StringBuilder sb = new StringBuilder(",");
        for(ProjectPermissionEnum permissionEnum : ProjectPermissionEnum.values()){
            if (ProjectPermissionEnum.isDefaultPermission(permissionEnum)) {
                sb.append(permissionEnum.getValue() + ",");
            }
        }
        projectUser.setPermission(sb.toString());
        projectUserService.insert(projectUser);
        request.setAttribute("title", "操作成功");
        request.setAttribute("result", "加入成功");
        return ERROR_VIEW;
    }




}
