package cn.crap.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.dto.MenuInfoDto;
import cn.crap.framework.JsonResult;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.MenuInfoService;
import cn.crap.model.MenuInfoModel;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Scope("prototype")
@Controller
@RequestMapping("/menuInfo")
public class MenuInfoController extends BaseController<MenuInfoModel>{

	@Autowired
	MenuInfoService menuInfoService;
	/**
	 * MenuInfoDemo
	 * @return 
	 * */
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult MenuInfoDemo(@ModelAttribute MenuInfoModel menu,@RequestParam(defaultValue="1") Integer currentPage){
		page.setCurrentPage(currentPage);
		map = Tools.getMap("parentId",menu.getParentId(),"menuName|like",menu.getMenuName());
		return new JsonResult(1,menuInfoService.findByMap(map,page,null),page);
	}
	
	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult detail(@ModelAttribute MenuInfoModel menu,String currentId){
		menu= menuInfoService.get(menu.getMenuId());
		if(menu==null){
			menu=new MenuInfoModel();
			menu.setParentId(currentId);
		}
		return new JsonResult(1,menu);
	}
	
	/**
	 * roleIds=0 表示前端菜单
	 * @param menu
	 * @return
	 */
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_MENU)
	public JsonResult addOrUpdate(@ModelAttribute MenuInfoModel menu){
		try{
		if(!MyString.isEmpty(menu.getMenuId())){
			menuInfoService.update(menu);
		}else{
			menu.setMenuId(null);
			menuInfoService.save(menu);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return new JsonResult(1,menu);
	}
	@RequestMapping("/delete.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_MENU)
	public JsonResult delete(@ModelAttribute MenuInfoModel menu){
		menuInfoService.delete(menu);
		return new JsonResult(1,null);
	}
	
	/****
	 * 后台加载菜单列表
	 */
	@RequestMapping("/menu.do")
	@ResponseBody
	public JsonResult menu( ){
		map = Tools.getMap("parentId","0");
		List<MenuInfoModel> menus = menuInfoService.findByMap(map,null,null);
		StringBuilder parentIds = new StringBuilder("'0'");
		menus.forEach(e->parentIds.append(",'"+e.getMenuId()+"'"));
		map.clear();
		map.put("parentId|in", parentIds.toString());
		List<MenuInfoModel> subMenus = menuInfoService.findByMap(map,null,null);
		List<MenuInfoDto> menuInfoVOs = new ArrayList<MenuInfoDto>();
		for(MenuInfoModel menu:menus){
			MenuInfoDto menuInfoVO = new MenuInfoDto();
			menuInfoVO.setMenuInfo(menu);
			menuInfoVO.setSubMenuInfo(new ArrayList<MenuInfoModel>());
			for(MenuInfoModel subMenu:subMenus){
				if(subMenu.getParentId().equals(menu.getMenuId())){
					menuInfoVO.getSubMenuInfo().add(subMenu);
				}
			}
			menuInfoVOs.add(menuInfoVO);
		}
		return new JsonResult(1,menuInfoVOs);
	}

}
