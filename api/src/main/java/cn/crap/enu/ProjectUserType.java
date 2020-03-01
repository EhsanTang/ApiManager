package cn.crap.enu;

public enum ProjectUserType {
	CREATOR("创建人", 1), MEMBER("成员", 2);

	private final String name;
	private final int type;

	public static String getNameByType(Byte type){
		for( ProjectUserType bugLogType : ProjectUserType.values()){
			if(bugLogType.getByteType().equals(type)){
				return bugLogType.getName();
			}
		}
		return "";
	}

	ProjectUserType(String name, int type){
		this.name = name;
		this.type = type;
	}
	public String getName(){
		return name;
	}

	public Byte getByteType(){
		return new Byte(type + "");
	}

}
