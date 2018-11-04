package cn.crap.enu;

public enum IndexPageUrl {
	INDEX_HTML("dashboard.htm", "数据大盘"),
	RECOMMEND_PROJECT("index.do#/project/list?projectShowType=4","推荐项目"),
	MY_PROJECT("index.do#/project/list?projectShowType=3","我的项目");
	private String name;
	private String value;
	
	private IndexPageUrl(String value, String name){
		this.name = name;
		this.value = value;
	}
	public String getName(){
		return name;
	}
	public String getValue(){
		return value;
	}
}
