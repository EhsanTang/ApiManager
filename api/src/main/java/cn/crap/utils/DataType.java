package cn.crap.utils;

public enum DataType {
	USER("用户管理"),MENU("菜单管理"),ERROR("错误码管理"),INTERFACE("接口管理"),MODULE("模块管理"),ROLE("权限管理"),MYMENU("我的菜单");
	private final String name;
	
	private DataType(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}
