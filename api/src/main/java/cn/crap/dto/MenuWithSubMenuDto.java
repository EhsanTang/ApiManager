package cn.crap.dto;

import java.io.Serializable;
import java.util.List;

import cn.crap.model.Menu;

public class MenuWithSubMenuDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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