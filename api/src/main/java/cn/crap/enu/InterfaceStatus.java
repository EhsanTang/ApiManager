package cn.crap.enu;

public enum InterfaceStatus {
	IDSCARD("0", "废弃"), ONLINE("1", "已上线"),DEVELOPING("2", "开发中"),TESTING("3", "测试中");
	private final String value;
	private final String name;

	public static String getNameByValue(Byte value){
		for( InterfaceStatus status : InterfaceStatus.values()){
			if(status.value.equals(value + "")){
				return status.getName();
			}
		}
		return "";
	}

	private InterfaceStatus(String value, String name){
		this.value = value;
		this.name = name;
	}

	public Byte getByteValue(){
		return new Byte(value);
	}

	public String getValue(){
		return value;
	}

	public String getName() {
		return name;
	}
}
