package cn.crap.enu;

import java.util.Optional;

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

    public static BugSeverity getByValue(String value){
        return getByValue(Byte.parseByte(value));
    }

    public static BugSeverity getByValue(Byte value){
        if (value == null){
            return null;
        }
        for( BugSeverity status : BugSeverity.values()){
            if(status.value.equals(value + "")){
                return status;
            }
        }
        return null;
    }


    public static String getNameByValue(Byte value){
        BugSeverity severity = getByValue(value);
        return Optional.ofNullable(severity).map(s->s.getName()).orElse("");
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
