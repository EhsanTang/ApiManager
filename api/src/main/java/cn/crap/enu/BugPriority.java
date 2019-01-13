package cn.crap.enu;

import java.util.Optional;

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

	public static BugPriority getByValue(String value){
		return getByValue(Byte.parseByte(value));
	}

	public static BugPriority getByValue(Byte value){
		if (value == null){
			return null;
		}
		for(BugPriority status : BugPriority.values()){
			if(status.value.equals(value + "")){
				return status;
			}
		}
		return null;
	}

	public static String getNameByValue(Byte value){
		BugPriority priority = getByValue(value);
		return Optional.ofNullable(priority).map(s->s.getName()).orElse("");
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
