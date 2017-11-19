package cn.crap.enumeration;

public enum TableId {
	ARTICLE("01"), SETTING("02"), ERROR("03"), COMMENT("04"), MENU("05"), USER("06"),PROJECT("07"),LOG("08"),MODULE("09");
	private final String tableId;

	TableId(String tableId){
		this.tableId = tableId;
	}

	public String getTableId(){
		return tableId;
	}
}
