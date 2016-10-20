package cn.crap.enumeration;

public enum DataType {
	SETTING("系统设置"),USER("用户管理"),MENU("菜单管理"),ROLE("权限管理"),LOG("日志管理");
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
