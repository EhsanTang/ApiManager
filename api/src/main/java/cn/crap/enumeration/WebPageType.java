package cn.crap.enumeration;

public enum WebPageType {
	DICTIONARY("数据字典"),PAGE("网站页面"),ARTICLE("文章");
	private final String name;
	
	private WebPageType(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}
