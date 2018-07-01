package cn.crap.controller.user;

import cn.crap.adapter.ProjectUserAdapter;
import cn.crap.dto.ProjectUserDto;
import cn.crap.enumer.MyError;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.model.Project;
import cn.crap.model.ProjectUser;
import cn.crap.model.User;
import cn.crap.query.ProjectUserQuery;
import cn.crap.query.UserQuery;
import cn.crap.service.ProjectUserService;
import cn.crap.service.UserService;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/user/projectUser")
public class ProjectUserController extends BaseController{

	@Autowired
	private UserService customUserService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProjectUserService projectUserService;
	
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@ModelAttribute ProjectUserQuery query) throws MyException{
		Assert.notNull(query.getProjectId());
        Page page= new Page(query);

        checkUserPermissionByProject( projectCache.get(query.getProjectId()));

		List<ProjectUser> projectUsers = projectUserService.query(query);
        page.setAllRow(projectUserService.count(query));

        return new JsonResult(1, ProjectUserAdapter.getDto(projectUsers), page);
	}	
	
	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult detail(String id, @RequestParam String projectId) throws MyException{
		ProjectUser projectUser;
		Project project = projectCache.get(projectId);
		if(id != null){
			projectUser= projectUserService.getById(id);
			checkUserPermissionByProject(project);
		}else{
			checkUserPermissionByProject(project);
			projectUser = new ProjectUser();
			projectUser.setStatus(Byte.valueOf("1"));
			projectUser.setProjectId(projectId);
		}
        ProjectUserDto projectUserDto = ProjectUserAdapter.getDto(projectUser, project);
        projectUserDto.setProjectAuth(null);
		return new JsonResult(1, projectUserDto);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute ProjectUserDto projectUser) throws Exception{
		checkUserPermissionByProject( projectCache.get( projectUser.getProjectId() ));
		User search = null;
		if( !MyString.isEmpty(projectUser.getUserId()) ){
			search = userService.getById( projectUser.getUserId() );
		}else if( !MyString.isEmpty( projectUser.getUserEmail()) ){
            UserQuery query = new UserQuery().setEqualEmail(projectUser.getUserEmail());
			List<User> users = userService.query(query);
			if( users.size() == 1){
				search = users.get(0);
			}
		}
		
		if(search == null ||  MyString.isEmpty( search.getId() )){
			throw new MyException(MyError.E000040);
		}
		
		projectUser.setUserEmail(search.getEmail());
		projectUser.setUserName(search.getUserName());
		// 修改
		if(!MyString.isEmpty(projectUser.getId())){
			ProjectUser old = projectUserService.getById(projectUser.getId());
			checkUserPermissionByProject( projectCache.get( old.getProjectId() ));
		}
		
		try{
			if(!MyString.isEmpty(projectUser.getId())){
				projectUserService.update(ProjectUserAdapter.getModel(projectUser));
			}else{
				projectUserService.insert(ProjectUserAdapter.getModel(projectUser));
			}
		}catch(Exception e){
			// 重复添加
			throw new MyException(MyError.E000039);
		}
		
		return new JsonResult(1,projectUser);
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(@RequestParam String id) throws Exception{
		ProjectUser projectUser = projectUserService.getById(id);
		checkUserPermissionByProject(projectCache.get( projectUser.getProjectId() ));
		projectUserService.delete(projectUser.getId());
		return new JsonResult(1,null);
	}
}
