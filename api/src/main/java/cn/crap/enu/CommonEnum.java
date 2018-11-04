package cn.crap.enu;

public enum CommonEnum {
	TRUE("true", new Byte("1")),
	FALSE("false", new Byte(("0")));
	private final String stringValue;
	private final Byte byteValue;

	
	private CommonEnum(String stringValue, Byte byteValue){
		this.stringValue = stringValue;
		this.byteValue = byteValue;
	}

	public Byte getByteValue(){
		return byteValue;
	}

	public String getStringValue(){
		return stringValue;
	}
}
