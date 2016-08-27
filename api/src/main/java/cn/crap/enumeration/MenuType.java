package cn.crap.enumeration;

public enum MenuType {
	BACK("后台管理员菜单"),FRONT("前端左侧菜单"),BOTTOM("前端底部菜单"),TOP("前端顶部菜单"),FRIEND("底部友情链接");
	private final String name;
	
	private MenuType(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}
