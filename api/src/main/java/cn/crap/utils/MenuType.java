package cn.crap.utils;

public enum MenuType {
	BACK("后台菜单"),FRONT("前端菜单"),BOTTOM("底部菜单"),TOP("顶部菜单"),FRIEND("友情链接");
	private final String name;
	
	private MenuType(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}
