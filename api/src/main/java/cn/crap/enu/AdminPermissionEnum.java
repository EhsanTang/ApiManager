package cn.crap.enu;

public enum AdminPermissionEnum {
	SUPER("超级管理员"),SETTING("系统设置"),USER("用户管理"),MENU("菜单管理"),HOT_SEARCH("搜索热词管理"),ARTICLE("站点页面&文档推荐");
	private final String name;
	
	public static String getValue(String name){
		try{
			return AdminPermissionEnum.valueOf(name).getName();
		}catch(Exception e){
			return "";
		}
	}
	
	AdminPermissionEnum(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}
