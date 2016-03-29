package cn.crap.utils;

public enum RequestMethod {
	POST("post"),GET("get"),BOTH("post and get");
	private final String name;
	
	private RequestMethod(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}
