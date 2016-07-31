package cn.crap.controller.front;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.ICacheService;
import cn.crap.inter.service.IDataCenterService;
import cn.crap.inter.service.IInterfaceService;
import cn.crap.inter.service.IMenuService;
import cn.crap.model.DataCenter;
import cn.crap.model.Interface;
import cn.crap.model.User;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

/**
 * 项目主页
 * @author Ehsan
 *
 */
@Scope("prototype")
@Controller
public class FrontProjectController extends BaseController<User> {
	@Autowired
	IMenuService menuService;
	@Autowired
	private IDataCenterService dataCenterService;
	@Autowired
	private IInterfaceService interfaceService;
	@Autowired
	private ICacheService cacheService;
	
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
	
	@RequestMapping("/front/project/list.do")
	@ResponseBody
	public JsonResult list(@ModelAttribute Interface interFace,
			@RequestParam(defaultValue = "1") Integer currentPage,String password,String visitCode) throws MyException{
		page.setCurrentPage(currentPage);
		List<DataCenter> modules = null;
		List<Interface> interfaces = null;
		if( MyString.isEmpty(interFace.getModuleId()) || interFace.getModuleId().equals("0") ){
			// 查询所有推荐的
			map = Tools.getMap( "status", Byte.valueOf("3"), "type", "MODULE");
			modules = dataCenterService.findByMap(map, null, null);
			interfaces = new ArrayList<Interface>();
		}else{
			DataCenter dc = dataCenterService.get(interFace.getModuleId());
			Tools.canVisitModule(dc.getPassword(), password, visitCode, request);
			map = Tools.getMap("parentId", interFace.getModuleId());
			modules = dataCenterService.findByMap(map, null, null);
			
			map.clear();
			map = Tools.getMap("moduleId", interFace.getModuleId());
			interfaces = interfaceService.findByMap( map, " new Interface(id,moduleId,interfaceName,version,createTime,updateBy,updateTime)", page, null );
		}
		
		map.clear();
		map.put("interfaces", interfaces);
		map.put("modules", modules);
		return new JsonResult(1, map, page, Tools.getMap("module", cacheService.getModule(interFace.getModuleId())));
	}
}
