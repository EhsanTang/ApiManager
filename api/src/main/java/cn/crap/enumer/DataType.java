package cn.crap.enumer;

public enum DataType {
	SETTING("系统设置"),USER("用户管理"),MENU("菜单管理"),ROLE("权限管理")
	,HOT_SEARCH("搜索热词管理"),ARTICLE("站点页面&文章推荐");
	private final String name;
	
	public static String getValue(String name){
		try{
			return DataType.valueOf(name).getName();
		}catch(Exception e){
			return "";
		}
	}
	
	DataType(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}
