package cn.crap.controller.front;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.dto.LoginInfoDto;
import cn.crap.enumeration.ProjectType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.table.IModuleService;
import cn.crap.inter.service.tool.ICacheService;
import cn.crap.model.Module;
import cn.crap.model.Project;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Controller("frontModuleController")
@RequestMapping("/front/module")
public class ModuleController extends BaseController<Module>{

	@Autowired
	private IModuleService moduleService;
	@Autowired
	private ICacheService cacheService;
	
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@RequestParam String projectId,String password, String visitCode) throws MyException{
		if( MyString.isEmpty(projectId) ){
			throw new MyException("000020");
		}
		
		// 如果是私有项目，必须登录才能访问，公开项目需要查看是否需要密码
		Project project = cacheService.getProject(projectId);
		isPrivateProject(password, visitCode, project);
		
		return new JsonResult(1, moduleService.findByMap(Tools.getMap("projectId", projectId),
				"new  Module( id, name,  url,  remark,  userId,  createTime,  projectId, canDelete)",
				null, null), null, 
				Tools.getMap("crumbs", Tools.getCrumbs( project.getName(), "void"),  "project", project) );
	}	
	@RequestMapping("/menu.do")
	@ResponseBody
	public JsonResult menu(@RequestParam String projectId) throws MyException{
		if( MyString.isEmpty(projectId) ){
			throw new MyException("000020");
		}
		// 如果是私有项目，必须登录才能访问，公开项目需要查看是否需要密码
		Project project = cacheService.getProject(projectId);
		if(project.getType() == ProjectType.PRIVATE.getType()){
			LoginInfoDto user = Tools.getUser();
			if (user == null) {
				throw new MyException("000041");
			}
			// 最高管理员修改项目
			// 自己的项目
			if ( ("," + user.getRoleId()).indexOf("," + Const.SUPER + ",") < 0 && !user.getId().equals(project.getUserId())
					&& user.getProjects().get(project.getId()) == null) {
				throw new MyException("000042");
			}
		}
		
		Project returnProject = new Project();
		BeanUtils.copyProperties(project, returnProject);
		returnProject.setPassword("");
		
		return new JsonResult(1, 
					moduleService.findByMap(Tools.getMap("projectId", projectId),
							"new  Module( id, name,  url,  remark,  userId,  createTime,  projectId, canDelete)",
							null, null), null, Tools.getMap("project",  returnProject) );
	}	
}
