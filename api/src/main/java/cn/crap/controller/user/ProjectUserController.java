package cn.crap.controller.user;

import cn.crap.adapter.ProjectUserAdapter;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.model.mybatis.ProjectUser;
import cn.crap.model.mybatis.User;
import cn.crap.model.mybatis.UserCriteria;
import cn.crap.service.mybatis.custom.CustomProjectUserService;
import cn.crap.service.mybatis.custom.CustomUserService;
import cn.crap.service.mybatis.imp.MybatisProjectUserService;
import cn.crap.service.mybatis.imp.MybatisUserService;
import cn.crap.utils.Const;
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
	private MybatisProjectUserService projectUserService;
	@Autowired
	private CustomUserService customUserService;
	@Autowired
	private MybatisUserService userService;
	@Autowired
	private CustomProjectUserService customProjectUserService;
	
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@RequestParam String projectId, @RequestParam(defaultValue="1") int currentPage) throws MyException{
		Assert.isTrue(currentPage > 0);
			Page<ProjectUser> page= new Page(SIZE, currentPage);
			hasPermission( projectCache.get(projectId) );
			page = customProjectUserService.queryByProjectId(projectId, page);
			return new JsonResult(1, ProjectUserAdapter.getDto(page.getList()), page);
	}	
	
	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult detail(@RequestParam String id, @RequestParam String projectId) throws MyException{
		ProjectUser model;
		if(!id.equals(Const.NULL_ID)){
			model= projectUserService.selectByPrimaryKey(id);
			hasPermission(projectCache.get( model.getProjectId() ));
		}else{
			hasPermission(projectCache.get( projectId ));
			model=new ProjectUser();
			model.setStatus(Byte.valueOf("1"));
			model.setProjectId( projectId );
		}
		return new JsonResult(1,model);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute ProjectUser projectUser) throws Exception{
		hasPermission( projectCache.get( projectUser.getProjectId() ));
		User search = null;
		if( !MyString.isEmpty(projectUser.getUserId()) ){
			search = userService.selectByPrimaryKey( projectUser.getUserId() );
		}else if( !MyString.isEmpty( projectUser.getUserEmail()) ){
			UserCriteria example = new UserCriteria();
			example.createCriteria().andEmailEqualTo(projectUser.getUserEmail());
			List<User> users = userService.selectByExample(example);
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
			ProjectUser old = projectUserService.selectByPrimaryKey(projectUser.getId());
			hasPermission( projectCache.get( old.getProjectId() ));
		}
		
		try{
			if(!MyString.isEmpty(projectUser.getId())){
				projectUserService.update(projectUser);
			}else{
				projectUserService.insert(projectUser);
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
		ProjectUser projectUser = projectUserService.selectByPrimaryKey(id);
		hasPermission(projectCache.get( projectUser.getProjectId() ));
		projectUserService.delete(projectUser.getId());
		return new JsonResult(1,null);
	}
}
