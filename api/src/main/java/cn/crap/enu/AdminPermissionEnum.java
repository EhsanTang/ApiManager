package cn.crap.enu;

public enum AdminPermissionEnum {
	SUPER("超级管理员"),SETTING("系统设置"),USER("用户管理"),MENU("菜单管理"),HOT_SEARCH("搜索热词管理"), PROJECT("项目管理");
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
