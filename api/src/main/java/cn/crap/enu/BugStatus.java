package cn.crap.enu;

import java.util.Optional;

public enum BugStatus {
    /**
     * 10-19 待解决的状态
     * 10新建，11打开，12重新打开
     */
    NEW("10", "新建"),
    OPEN("11", "打开"),
    REOPEN("12", "重新打开"),

    /**
     * 20-29 开发哥哥的操作项
     * 20修复中，21已修复，22部署成功
     */
    FIXING("20", "修复中"),
    FIXED("21", "已修复"),
    DEPLOYMENT("22", "部署成功"),
    LATER("23", "延迟处理"),

    /**
     * 30-39 测试小姐姐的操作项
     * 30测试中，31测试通过
     */
    TESTING("30", "测试中"),
    PASS_TEST("31", "测试通过"),

    /**
     * 完结状态
     * -4不修复，-3重复，-2无效，-1关闭，0已上线
     */
    SUCCESS("0", "已解决"),
    IGNORE("-4", "不修复"),
    REPEAT("-3", "重复"),
    INVALID("-2", "无效"),
    CLOSE("-1", "关闭");

	private final String value;
	private final String name;

    public static BugStatus getByValue(String value){
        return getByValue(Byte.parseByte(value));
    }
    public static BugStatus getByValue(Byte value){
        if (value == null){
            return null;
        }
        for( BugStatus status : BugStatus.values()){
            if(status.value.equals(value + "")){
                return status;
            }
        }
        return null;
    }

	public static String getNameByValue(Byte value){
        BugStatus bugStatus = getByValue(value);
        return Optional.ofNullable(bugStatus).map(s->s.getName()).orElse("");

    }

	BugStatus(String value, String name){
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
