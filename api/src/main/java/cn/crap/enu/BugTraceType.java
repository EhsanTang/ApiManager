package cn.crap.enu;

/**
 * bug严重程度
 */
public enum BugTraceType {
    /**
     * 问题类型:1线上问题，2功能缺陷，3需求问题，4性能瓶颈，5反馈意见
     */
    ONLINE("1", "线上问题"),
	FUNCTION("2", "功能缺陷"),
	DEMAND("3", "需求问题"),
	PERFORMANCE("4","性能瓶颈"),
	SUGGEST("5", "反馈意见");
	private final String value;
	private final String name;

	public static String getNameByValue(Byte value){
		for( BugTraceType status : BugTraceType.values()){
			if(status.value.equals(value + "")){
				return status.getName();
			}
		}
		return "";
	}

	BugTraceType(String value, String name){
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
