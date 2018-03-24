package cn.crap.enumer;

public enum InterfaceStatus {
	不可用("0"),可用("1");
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
