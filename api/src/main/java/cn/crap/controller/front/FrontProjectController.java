package cn.crap.controller.front;

import java.util.ArrayList;
import java.util.HashMap;
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
import cn.crap.inter.service.table.IArticleService;
import cn.crap.inter.service.table.IInterfaceService;
import cn.crap.inter.service.table.IMenuService;
import cn.crap.inter.service.table.IModuleService;
import cn.crap.inter.service.table.IProjectService;
import cn.crap.inter.service.tool.ICacheService;
import cn.crap.model.Module;
import cn.crap.model.Interface;
import cn.crap.model.User;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;

/**
 * 项目主页
 * @author Ehsan
 *
 */
@Controller
public class FrontProjectController extends BaseController<User> {
	@Autowired
	IMenuService menuService;
	@Autowired
	private IModuleService dataCenterService;
	@Autowired
	private IInterfaceService interfaceService;
	@Autowired
	private ICacheService cacheService;
	@Autowired
	private IArticleService webPageService;
	@Autowired
	private IProjectService projectService;
	
	/**
	 * 前端项目主页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/project.do")
	public String project() throws Exception {
		return "resources/html/project/index.html";
	}
	
	@RequestMapping("/front/project/module/list.do")
	@ResponseBody
	public JsonResult moduleList(@ModelAttribute Interface interFace,
			@RequestParam(defaultValue = "1") Integer currentPage,String password,String visitCode) throws MyException{
		Page page= new Page(15);
		page.setCurrentPage(currentPage);
		List<Module> modules = null;
		Map<String,Object> map = null;
		Module dc = dataCenterService.get(interFace.getModuleId());
		Tools.canVisitModule(dc.getPassword(), password, visitCode, request);
		map = Tools.getMap("parentId", interFace.getModuleId());
		modules = dataCenterService.findByMap(map, null, null);
			
		map.clear();
		map.put("modules", modules);
		map.put("project", dc);
		return new JsonResult(1, map, page);
	}
	
	
	@RequestMapping("/front/project/interface/List.do")
	@ResponseBody
	public JsonResult list(@ModelAttribute Interface interFace,
			@RequestParam(defaultValue = "1") Integer currentPage,String password,String visitCode) throws MyException{
		Page page= new Page(15);
		page.setCurrentPage(currentPage);
		List<Interface> interfaces = null;
		Map<String,Object> map = null;
			Module dc = dataCenterService.get(interFace.getModuleId());
			Tools.canVisitModule(dc.getPassword(), password, visitCode, request);
			map = Tools.getMap("moduleId", interFace.getModuleId());
			interfaces = interfaceService.findByMap( map, " new Interface(id,moduleId,interfaceName,version,createTime,updateBy,updateTime,remark)", page, null );
			
			map.clear();
			map.put("interfaces", interfaces);
			map.put("project", dc);
			return new JsonResult(1, map, page);
	}
	
	
	@RequestMapping("/front/project/menu.do")
	@ResponseBody
	public JsonResult menu(@RequestParam String projectId) throws MyException{
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("project", projectService.get(projectId));
		returnMap.put("modules", dataCenterService.findByMap(Tools.getMap("projectId", projectId), null, null));
		//map = Tools.getMap("moduleId",moduleId, "type", WebPageType.DICTIONARY.name());
		return new JsonResult(1,returnMap);
	}
	
}
