package cn.crap.enumeration;

public enum DataType {
	DICTIONARY("数据字典管理"),WEBPAGE("站点页面：页面、新闻等"),SETTING("系统设置"),USER("用户管理"),MENU("菜单管理"),ERROR("错误码管理"),
	INTERFACE("接口管理"),MODULE("模块管理"),ROLE("权限管理"),MYMENU("我的菜单"),LOG("日志管理"),SOURCE("文档资源管理");
	private final String name;
	
	public static String getValue(String name){
		try{
			return DataType.valueOf(name).getName();
		}catch(Exception e){
			return "";
		}
	}
	
	private DataType(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}
