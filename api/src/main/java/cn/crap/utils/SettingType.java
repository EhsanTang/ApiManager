package cn.crap.utils;

public enum SettingType {
	IMAGE("图片"),TEXT("文本"),COLOR("颜色");
	private final String name;
	
	public static String getValue(String name){
		try{
			return SettingType.valueOf(name).getName();
		}catch(Exception e){
			return "";
		}
	}
	private SettingType(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}
