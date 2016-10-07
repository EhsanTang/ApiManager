package cn.crap.controller.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.crap.beans.Config;
import cn.crap.dto.LoginInfoDto;
import cn.crap.enumeration.ProjectType;
import cn.crap.enumeration.UserType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.ICacheService;
import cn.crap.inter.service.IDataCenterService;
import cn.crap.inter.service.IMenuService;
import cn.crap.inter.service.IProjectService;
import cn.crap.inter.service.IRoleService;
import cn.crap.inter.service.IUserService;
import cn.crap.model.Project;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;

@Controller
@RequestMapping("/user/project")
public class ProjectController extends BaseController<Project> {
	@Autowired
	IMenuService menuService;
	@Autowired
	private ICacheService cacheService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IProjectService projectService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IDataCenterService moduleService;
	@Autowired
	private Config config;
	
	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(@ModelAttribute Project project, @RequestParam(defaultValue="1") int currentPage, 
			@RequestParam(defaultValue="false") boolean myself) throws MyException{
		
		Page page= new Page(15);
		page.setCurrentPage(currentPage);
		Map<String,Object> map = null;
		if(project.getType() != -1){
			map = Tools.getMap("type", project.getType(), "name|like", project.getName());
		}else{
			map = Tools.getMap("name|like", project.getName());
		}
		
		// 普通用户，管理员我的项目菜单只能查看自己的项目
		LoginInfoDto user = Tools.getUser();
		if( Tools.getUser().getType() == UserType.USER.getType() || myself){
			map.put("userId", user.getId());
		}
		
		return new JsonResult(1, projectService.findByMap(map, page, null), page);
	}
	
	@RequestMapping("/detail.do")
	@ResponseBody
	@AuthPassport
	public JsonResult detail(@ModelAttribute Project project) throws MyException{
		Project model;
		if(!project.getId().equals(Const.NULL_ID)){
			model= projectService.get(project.getId());
			hasPermission(model);
		}else{
			model=new Project();
		}
		return new JsonResult(1,model);
	}
	
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute Project project) throws Exception{
		
		Project model;
		LoginInfoDto user = Tools.getUser();
		// 修改
		if(!MyString.isEmpty(project.getId())){
			model= projectService.get(project.getId());
			hasPermission(model);
			
			// 不允许转移项目
			project.setUserId(model.getUserId());
			
			// 普通用户不能推荐项目，将项目类型修改为原有类型
			if( Tools.getUser().getType() == UserType.USER.getType()){
				if(project.getType() == ProjectType.RECOMMEND.getType()){
					project.setType(model.getType());
				}
			}
						
			projectService.update(project);
		}
		
		// 新增
		else{
			project.setUserId(user.getId());
			
			// 普通用户不能推荐项目
			if( Tools.getUser().getType() == UserType.USER.getType()){
				if(project.getType() == ProjectType.RECOMMEND.getType()){
					project.setType(ProjectType.PRIVATE.getType());
				}
			}
			
			projectService.save(project);
		}
		
		// 清楚缓存
		cacheService.delObj(Const.CACHE_PROJECT+project.getId());
		
		// 刷新用户权限 将用户信息存入缓存
		cacheService.setObj(Const.CACHE_USER + user.getId(), new LoginInfoDto(userService.get(user.getId()), roleService, projectService), config.getLoginInforTime());
		return new JsonResult(1,project);
	}
	
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(@ModelAttribute Project project) throws Exception{
		Project model= projectService.get(project.getId());
		hasPermission(model);
		
		
		// 只有子模块数量为0，才允许删除模块
		if(moduleService.getCount(Tools.getMap("parentId", model.getId())) > 0){
			throw new MyException("000023");
		}
		
		// TODO 文章、数据字段、文件
		
		cacheService.delObj(Const.CACHE_PROJECT+project.getId());
		projectService.delete(project);
		return new JsonResult(1,null);
	}
	
	@RequestMapping("/changeSequence.do")
	@ResponseBody
	@AuthPassport
	public JsonResult changeSequence(@RequestParam String id,@RequestParam String changeId) throws MyException {
		Project change = projectService.get(changeId);
		Project model = projectService.get(id);
		
		hasPermission(change);
		hasPermission(model);
		
		int modelSequence = model.getSequence();
		model.setSequence(change.getSequence());
		change.setSequence(modelSequence);
		
		projectService.update(model);
		projectService.update(change);

		return new JsonResult(1, null);
	}
}
