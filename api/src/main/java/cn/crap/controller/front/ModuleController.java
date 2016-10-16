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
	
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@RequestParam String projectId,String password, String visitCode, @RequestParam(defaultValue="1") int currentPage) throws MyException{
		if( MyString.isEmpty(projectId) ){
			throw new MyException("000020");
		}
		Project project = projectService.get(projectId);
		
		return new JsonResult(1, moduleService.findByMap(Tools.getMap("projectId", projectId), null, null), null, 
				Tools.getMap("project", project, "crumbs", Tools.getCrumbs( project.getName(), "void") )  );
	}	
}
