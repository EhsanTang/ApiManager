package cn.crap.enumeration;

public enum ArticleType {
	DICTIONARY("项目数据字典"),PAGE("站点页面"),ARTICLE("文章");
	private final String name;
	
	private ArticleType(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}
