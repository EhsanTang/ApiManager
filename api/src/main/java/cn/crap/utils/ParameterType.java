package cn.crap.utils;

public enum ParameterType {
	HEADER("请求头"),PARAMETER("请求参数");
	private final String name;
	
	private ParameterType(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}
