package cn.crap.enumer;

public enum TableId {
	ARTICLE("01"), SETTING("02"), ERROR("03"), COMMENT("04"), MENU("05"), USER("06"),PROJECT("07"),
	LOG("08"),MODULE("09"),PROJECT_USER("10"),ROLE("11"),INTERFACE("12"),SOURCE("13"),DEBUG("14"),HOT_SEARCH("15");
	private final String tableId;

	TableId(String tableId){
		this.tableId = tableId;
	}

	public String getTableId(){
		return tableId;
	}
}
