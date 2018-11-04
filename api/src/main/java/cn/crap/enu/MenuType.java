package cn.crap.enu;

public enum MenuType {
	FRONT("前端左侧菜单"),BOTTOM("前端底部菜单"),TOP("前端顶部菜单"),FUNCTION("功能介绍"),FRIEND("底部友情链接");
	private final String chineseName;

	public static String getChineseNameByValue(String enumName){
		for( MenuType menuType : MenuType.values()){
			if(menuType.name().equals(enumName)){
				return menuType.getChineseName();
			}
		}
		return "";
	}

	private MenuType(String name){
		this.chineseName = name;
	}
	public String getChineseName(){
		return chineseName;
	}
}
