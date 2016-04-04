package cn.crap.utils;

public enum WebPageType {
	DICTIONARY("数据字典"),PAGE("页面");
	private final String name;
	
	private WebPageType(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}
