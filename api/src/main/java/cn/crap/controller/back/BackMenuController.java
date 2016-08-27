package cn.crap.controller.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.ICacheService;
import cn.crap.inter.service.IMenuService;
import cn.crap.model.Menu;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Scope("prototype")
@Controller
@RequestMapping
public class BackMenuController extends BaseController<Menu> {
	@Autowired
	IMenuService menuService;
	@Autowired
	ICacheService cacheService;

	/**
	 * 根据父菜单、菜单名、菜单类型及页码获取菜单列表
	 * 
	 * @return
	 */
	@RequestMapping("/menu/list.do")
	@ResponseBody
	public JsonResult list(@ModelAttribute Menu menu, @RequestParam(defaultValue = "1") Integer currentPage) {
		page.setCurrentPage(currentPage);
		map = Tools.getMap("parentId", menu.getParentId(), "menuName|like", menu.getMenuName(), "type", menu.getType());
		return new JsonResult(1, menuService.findByMap(map, page, null), page);
	}

	@RequestMapping("/menu/detail.do")
	@ResponseBody
	public JsonResult detail(@ModelAttribute Menu menu) {
		if (!menu.getId().equals(Const.NULL_ID)) {
			model = menuService.get(menu.getId());
		} else {
			model = new Menu();
			model.setParentId(menu.getParentId());
			Menu parentMenu = menuService.get(menu.getParentId());
			model.setType(parentMenu.getType());
		}
		return new JsonResult(1, model);
	}

	/**
	 * 
	 * @param menu
	 * @return
	 */
	@RequestMapping("/menu/addOrUpdate.do")
	@ResponseBody
	@AuthPassport(authority = Const.AUTH_MENU)
	public JsonResult addOrUpdate(@ModelAttribute Menu menu) {
		try {
			// 子菜单类型和父菜单类型一致
			Menu parentMenu = menuService.get(menu.getParentId());
			if (parentMenu != null && parentMenu.getId()!=null)
				menu.setType(parentMenu.getType());

			if (!MyString.isEmpty(menu.getId())) {
				menuService.update(menu);
			} else {
				menu.setId(null);
				menuService.save(menu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 清除缓存
		cacheService.delObj("cache:leftMenu");
		return new JsonResult(1, menu);
	}

	@RequestMapping("/menu/delete.do")
	@ResponseBody
	@AuthPassport(authority = Const.AUTH_MENU)
	public JsonResult delete(@ModelAttribute Menu menu) throws MyException {
		if(menuService.getCount(Tools.getMap("parentId", menu.getId())) > 0){
			throw new MyException("000025");
		}
		menuService.delete(menu);
		// 清除缓存
		cacheService.delObj("cache:leftMenu");
		return new JsonResult(1, null);
	}

	/****
	 * 后台加载菜单列表
	 */
	@RequestMapping("/menu/menu.do")
	@ResponseBody
	public JsonResult menu() {
		return new JsonResult(1, menuService.getLeftMenu(map));
	}

	
	
	@RequestMapping("/back/menu/changeSequence.do")
	@ResponseBody
	@AuthPassport
	public JsonResult changeSequence(@RequestParam String id,@RequestParam String changeId) {
		Menu change = menuService.get(changeId);
		model = menuService.get(id);
		int modelSequence = model.getSequence();
		
		model.setSequence(change.getSequence());
		change.setSequence(modelSequence);
		
		menuService.update(model);
		menuService.update(change);
		
		// 清除缓存
		cacheService.delObj("cache:leftMenu");
		return new JsonResult(1, null);
	}

}
