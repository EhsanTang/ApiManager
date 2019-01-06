package cn.crap.enu;

/**
 * bug严重程度
 */
public enum BugSeverity {
    /**
     * 严重程度：1-blocker,2-Major,3-Normal,4-Trivial
     */
    BLOCK("1", "阻塞"), MAJOR("2", "严重"), NORMAL("3", "普通"), TRIVIAL("4","不重要");
	private final String value;
	private final String name;

	public static String getNameByValue(Byte value){
		for( BugSeverity status : BugSeverity.values()){
			if(status.value.equals(value + "")){
				return status.getName();
			}
		}
		return "";
	}

	BugSeverity(String value, String name){
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
