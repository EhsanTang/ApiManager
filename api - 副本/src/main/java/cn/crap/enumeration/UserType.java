package cn.crap.enumeration;

public enum UserType {
	管理员("100"),普通用户("1");
	private final String name;
	
	public static String getNameByValue(String value){
		for(UserType type : UserType.values()){
			if(type.getName().equals(value))
				return type.name();
		}
		return "";
	}
	
	private UserType(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
}
