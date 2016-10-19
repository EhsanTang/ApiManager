package cn.crap.controller.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.table.IModuleService;
import cn.crap.inter.service.table.IProjectService;
import cn.crap.inter.service.tool.ICacheService;
import cn.crap.model.Module;
import cn.crap.model.Project;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Controller("frontModuleController")
@RequestMapping("/front/module")
public class ModuleController extends BaseController<Module>{

	@Autowired
	private IModuleService moduleService;
	@Autowired
	private IProjectService projectService;
	@Autowired
	private ICacheService cacheService;
	
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@RequestParam String projectId,String password, String visitCode) throws MyException{
		if( MyString.isEmpty(projectId) ){
			throw new MyException("000020");
		}
		Project project = cacheService.getProject(projectId);
		canVisit(project.getPassword(), password, visitCode);
		
		return new JsonResult(1, moduleService.findByMap(Tools.getMap("projectId", projectId), null, null), null, 
				Tools.getMap("crumbs", Tools.getCrumbs( project.getName(), "void") )  );
	}	
	@RequestMapping("/menu.do")
	@ResponseBody
	public JsonResult menu(@RequestParam String projectId) throws MyException{
		if( MyString.isEmpty(projectId) ){
			throw new MyException("000020");
		}
		return new JsonResult(1, moduleService.findByMap(Tools.getMap("projectId", projectId), null, null), null, Tools.getMap("project",  cacheService.getProject(projectId)) );
	}	
}
