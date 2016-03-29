package cn.crap.dto;

import java.util.List;

import cn.crap.model.MenuInfoModel;

/**
 * @author lizhiyong
 * @date 2016-01-06
 */
public class MenuInfoDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5741888656063923409L;
	private MenuInfoModel menuInfo;
	private List<MenuInfoModel> subMenuInfo;
	public MenuInfoModel getMenuInfo() {
		return menuInfo;
	}
	public void setMenuInfo(MenuInfoModel menuInfo) {
		this.menuInfo = menuInfo;
	}
	public List<MenuInfoModel> getSubMenuInfo() {
		return subMenuInfo;
	}
	public void setSubMenuInfo(List<MenuInfoModel> subMenuInfo) {
		this.subMenuInfo = subMenuInfo;
	}
	
}