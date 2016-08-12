package cn.crap.enumeration;

public enum DataCeneterType {
	MODULE("模块"),DIRECTORY("文件目录");
	
	private final String name;
	
	public static String getValue(String name){
		try{
			return DataCeneterType.valueOf(name).getName();
		}catch(Exception e){
			return "";
		}
	}
	
	private DataCeneterType(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}
