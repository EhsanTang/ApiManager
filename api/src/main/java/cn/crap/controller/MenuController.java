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

import cn.crap.dto.MenuDto;
import cn.crap.framework.JsonResult;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.IMenuService;
import cn.crap.model.Menu;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Scope("prototype")
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController<Menu> {

	@Autowired
	IMenuService menuService;

	/**
	 * 根据父菜单、菜单名、菜单类型及页码获取菜单列表
	 * 
	 * @return
	 */
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@ModelAttribute Menu menu, @RequestParam(defaultValue = "1") Integer currentPage) {
		page.setCurrentPage(currentPage);
		map = Tools.getMap("parentId", menu.getParentId(), "menuName|like", menu.getMenuName(), "type", menu.getType());
		return new JsonResult(1, menuService.findByMap(map, page, null), page);
	}

	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult detail(@ModelAttribute Menu menu) {
		if (!menu.getMenuId().equals(Const.NULL_ID)) {
			model = menuService.get(menu.getMenuId());
		} else {
			model = new Menu();
			model.setParentId(menu.getParentId());
			Menu parentMenu = menuService.get(menu.getParentId());
			model.setType(parentMenu.getType());
		}
		return new JsonResult(1, model);
	}

	/**
	 * roleIds=0 表示前端菜单
	 * 
	 * @param menu
	 * @return
	 */
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport(authority = Const.AUTH_MENU)
	public JsonResult addOrUpdate(@ModelAttribute Menu menu) {
		try {
			// 子菜单类型和父菜单类型一致
			Menu parentMenu = menuService.get(menu.getParentId());
			if (parentMenu != null || menu.getParentId().equals("0"))
				menu.setType(parentMenu.getType());

			if (!MyString.isEmpty(menu.getMenuId())) {
				menuService.update(menu);
			} else {
				menu.setMenuId(null);
				menuService.save(menu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new JsonResult(1, menu);
	}

	@RequestMapping("/delete.do")
	@ResponseBody
	@AuthPassport(authority = Const.AUTH_MENU)
	public JsonResult delete(@ModelAttribute Menu menu) {
		menuService.delete(menu);
		return new JsonResult(1, null);
	}

	/****
	 * 后台加载菜单列表
	 */
	@RequestMapping("/menu.do")
	@ResponseBody
	public JsonResult menu() {
		map = Tools.getMap("parentId", "0");
		List<Menu> menus = menuService.findByMap(map, null, null);
		map.clear();
		List<String> menuIds = new ArrayList<String>();
		for (Menu menu : menus) {
			menuIds.add(menu.getMenuId());
		}
		map.put("parentId|in", menuIds);
		List<Menu> subMenus = menuService.findByMap(map, null, null);
		List<MenuDto> menuVOs = new ArrayList<MenuDto>();
		for (Menu menu : menus) {
			MenuDto menuVO = new MenuDto();
			menuVO.setMenu(menu);
			menuVO.setSubMenu(new ArrayList<Menu>());
			for (Menu subMenu : subMenus) {
				if (subMenu.getParentId().equals(menu.getMenuId())) {
					menuVO.getSubMenu().add(subMenu);
				}
			}
			menuVOs.add(menuVO);
		}
		return new JsonResult(1, menuVOs);
	}

}
