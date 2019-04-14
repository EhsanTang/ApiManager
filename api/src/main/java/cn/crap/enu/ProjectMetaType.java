package cn.crap.enu;

public enum ProjectMetaType {
	ENV(1, "环境");

	private final Byte type;
	private final String name;

	public static String getNameByType(Byte type){
		if (type == null){
			return "";
		}
		for( ProjectMetaType metaType : ProjectMetaType.values()){
			if(metaType.getType().equals(type)){
				return metaType.getName();
			}
		}
		return "";
	}

	ProjectMetaType(int type, String name){
		this.type = (byte) type;
		this.name = name;
	}
	public String getName(){
		return name;
	}

	public Byte getType(){
		return type;
	}
}
