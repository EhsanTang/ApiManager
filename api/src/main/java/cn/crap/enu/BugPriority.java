package cn.crap.enu;

/**
 * bug严重程度
 */
public enum BugPriority {
    /**
     * 优先级：1低，2中，3高，4紧急
     */
    LOW("1", "低"), MIDDLE("2", "中"), HIGH("3", "高"), URGENT("4","紧急");
	private final String value;
	private final String name;

	public static String getNameByValue(Byte value){
		for( BugPriority status : BugPriority.values()){
			if(status.value.equals(value + "")){
				return status.getName();
			}
		}
		return "";
	}

	BugPriority(String value, String name){
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
