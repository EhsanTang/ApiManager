package cn.crap.utils;

public enum WebPageType {
	DICTIONARY("数据字典");
	private final String name;
	
	private WebPageType(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}
