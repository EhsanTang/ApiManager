package cn.crap.enu;

public enum GenerateType {
	MY_BATIS_XML("MyBatis xml"), JAVA_PO("java 对象");
	private final String name;

	public static String getByEnumName(String enumName){
		for( GenerateType article : GenerateType.values()){
			if(article.name().equals(enumName)){
				return article.getName();
			}
		}
		return "";
	}

	GenerateType(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}
