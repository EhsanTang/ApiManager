package cn.crap.dto;

import java.util.List;

import cn.crap.model.Menu;

/**
 * @author lizhiyong
 * @date 2016-01-06
 */
public class MenuDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5741888656063923409L;
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