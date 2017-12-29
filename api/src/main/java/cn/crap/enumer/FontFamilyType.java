package cn.crap.enumer;

public enum FontFamilyType {
	FONT_DEF("Georgia, \"Times New Roman\", Times,\"Microsoft Yahei\",\"Hiragino Sans GB\",sans-serif;","默认字体"),
	FONT_36KE("\"Lantinghei SC\", \"Open Sans\", Arial, \"Hiragino Sans GB\", \"Microsoft YaHei\", \"STHeiti\", \"WenQuanYi Micro Hei\", SimSun, sans-serif;","36氪字体"),
	FONT_GUOKE("Arial,Helvetica,sans-serif;","果壳字体"),
	FONT_ZHIHU("\"Helvetica Neue\",Helvetica,Arial,sans-serif;","知乎字体");
	private String name;
	private String value;
	
	private FontFamilyType(String value, String name){
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
