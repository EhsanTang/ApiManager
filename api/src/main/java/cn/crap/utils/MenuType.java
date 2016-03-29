package cn.crap.utils;

public enum MenuType {
	BACK("后台菜单"),FRONT("前端菜单");
	private final String name;
	
	private MenuType(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}
