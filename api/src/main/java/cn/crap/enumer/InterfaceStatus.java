package cn.crap.enumer;

public enum InterfaceStatus {
	废弃("0"), 已上线("1"),开发中("2"),测试中("3");
	private final String value;

	public static String getNameByValue(Byte value){
		for( InterfaceStatus status : InterfaceStatus.values()){
			if(status.value.equals(value + "")){
				return status.name();
			}
		}
		return "";
	}

	private InterfaceStatus(String value){
		this.value = value;
	}

	public String getValue(){
		return value;
	}
}
