package cn.crap.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import cn.crap.framework.base.BaseModel;


/**
 * @author lizhiyong
 * @date 2016-01-06
 */
@Entity
@Table(name="menu")
@GenericGenerator(name="Generator", strategy="cn.crap.framework.IdGenerator")
public class Menu extends BaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2919550199541804532L;
	/**
	 * menuId
	 * */
	private String menuId;
	/**
	 * menuName(菜单名称)
	 * */
	private String menuName;
	/**
	 * menuUrl(菜单链接)
	 * */
	private String menuUrl;
	/**
	 * roleIds(角色可见集合  （ID之间以逗号分隔）)
	 * */
	private String roleIds;//待删除
	
	private String parentId;
	private String iconRemark;
	private String type;


	@Id
	@GeneratedValue(generator="Generator")
	@Column(name="menuId")
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@Column(name="menuName")
	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Column(name="menuUrl")
	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	@Column(name="roleIds")
	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	@Column(name="parentId")
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	@Column(name="iconRemark")
	public String getIconRemark() {
		return iconRemark;
	}

	public void setIconRemark(String iconRemark) {
		this.iconRemark = iconRemark;
	}
	@Column(name="type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	

}