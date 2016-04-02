package cn.crap.utils;

public enum SettingType {
	IMAGE("图片"),TEXT("文本"),LINK("超链接"),RICHTEXT("富文本");
	private final String name;
	
	private SettingType(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}
