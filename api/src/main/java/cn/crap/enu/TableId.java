package cn.crap.enu;

public enum TableId {
	/**
	 * TABLE：数据库表，没有用到，但是创建索引时需要使用，区分数据类型
	 */
	TABLE("00"), ARTICLE("01"), SETTING("02"), ERROR("03"), COMMENT("04"), MENU("05"), USER("06"),PROJECT("07"),
	LOG("08"),MODULE("09"),PROJECT_USER("10"),ROLE("11"),INTERFACE("12"),SOURCE("13"),DEBUG("14"),
	HOT_SEARCH("15"),BUG("16"),BUG_LOG("17");
	private final String tableId;

	TableId(String tableId){
		this.tableId = tableId;
	}

	public String getTableId(){
		return tableId;
	}
}
