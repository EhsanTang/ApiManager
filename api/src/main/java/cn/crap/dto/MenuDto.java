package cn.crap.dto;

import java.util.List;

import cn.crap.model.Menu;

public class MenuDto{

	private Menu menu;
	private List<Menu> subMenu;
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	public List<Menu> getSubMenu() {
		return subMenu;
	}
	public void setSubMenu(List<Menu> subMenu) {
		this.subMenu = subMenu;
	}
	
}