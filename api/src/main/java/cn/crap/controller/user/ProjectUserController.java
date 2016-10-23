package cn.crap.controller.user;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.table.IProjectUserService;
import cn.crap.inter.service.table.IUserService;
import cn.crap.inter.service.tool.ICacheService;
import cn.crap.model.ProjectUser;
import cn.crap.model.User;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;

@Controller
@RequestMapping("/user/projectUser")
public class ProjectUserController extends BaseController<ProjectUser>{

	@Autowired
	private IProjectUserService projectUserService;
	@Autowired
	private IUserService userService;
	@Autowired
	private ICacheService cacheService;
	
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@RequestParam String projectId, @RequestParam(defaultValue="1") int currentPage) throws MyException{
			Page page= new Page(15);
			page.setCurrentPage(currentPage);
			Map<String,Object> map = Tools.getMap("projectId", projectId);
			
			hasPermission( cacheService.getProject(projectId) );
			
			return new JsonResult(1, projectUserService.findByMap(map, page, null), page);
	}	
	
	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult detail(@RequestParam String id, @RequestParam String projectId) throws MyException{
		ProjectUser model;
		if(!id.equals(Const.NULL_ID)){
			model= projectUserService.get(id);
			hasPermission(cacheService.getProject( model.getProjectId() ));
		}else{
			hasPermission(cacheService.getProject( projectId ));
			model=new ProjectUser();
			model.setStatus(Byte.valueOf("1"));
			model.setProjectId( projectId );
		}
		return new JsonResult(1,model);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute ProjectUser projectUser) throws Exception{
		hasPermission( cacheService.getProject( projectUser.getProjectId() ));
		User search = null;
		if( !MyString.isEmpty(projectUser.getUserId()) ){
			search = userService.get( projectUser.getUserId() );
		}else if( !MyString.isEmpty( projectUser.getUserEmail()) ){
			List<User> users = userService.findByMap(Tools.getMap("email", projectUser.getUserEmail()), null, null);
			if( users.size() == 1){
				search = users.get(0);
			}
		}
		
		if(search == null ||  MyString.isEmpty( search.getId() )){
			throw new MyException("000040");
		}
		
		projectUser.setUserEmail(search.getEmail());
		projectUser.setUserName(search.getUserName());
		// 修改
		if(!MyString.isEmpty(projectUser.getId())){
			ProjectUser old = projectUserService.get(projectUser.getId());
			hasPermission( cacheService.getProject( old.getProjectId() ));
		}
		
		try{
			if(!MyString.isEmpty(projectUser.getId())){
				projectUserService.update(projectUser);
			}else{
				projectUserService.save(projectUser);
			}
		}catch(Exception e){
			// 重复添加
			throw new MyException("000039");
		}
		
		return new JsonResult(1,projectUser);
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(@RequestParam String id) throws Exception{
		ProjectUser projectUser = projectUserService.get(id);
		hasPermission(cacheService.getProject( projectUser.getProjectId() ));
		projectUserService.delete(projectUser);
		return new JsonResult(1,null);
	}
}
