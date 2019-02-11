package cn.crap.enu;

public enum BugLogType {
	// 操作类型：1标题，2内容，3状态，4优先级，5严重程度，6问题类型，7模块，8执行人，9测试，10抄送人
	TITLE("标题", 1), CONTENT("描述", 2), STATUS("状态", 3), PRIORITY("优先级", 4), SEVERITY("严重程度", 5), TYPE("问题类型", 6),
	MODULE("模块", 7), EXECUTOR("执行人", 8), TESTER("测试", 9), TRACER("抄送人", 10);

	private final String name;
	private final int type;

	public static String getNameByType(Byte type){
		for( BugLogType bugLogType : BugLogType.values()){
			if(bugLogType.getByteType().equals(type)){
				return bugLogType.getName();
			}
		}
		return "";
	}

	BugLogType(String name, int type){
		this.name = name;
		this.type = type;
	}
	public String getName(){
		return name;
	}

	public Byte getByteType(){
		return new Byte(type + "");
	}

}
