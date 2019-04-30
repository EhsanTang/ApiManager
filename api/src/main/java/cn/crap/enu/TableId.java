package cn.crap.enu;

import lombok.Getter;

public enum TableId {
	/**
	 * TABLE：数据库表，没有用到，但是创建索引时需要使用，区分数据类型
	 */
	TABLE("00", "数据字典"), ARTICLE("01", "文章"), SETTING("02"), ERROR("03", "状态码"), COMMENT("04", "评论"),
	MENU("05"), USER("06"),PROJECT("07"), LOG("08"), MODULE("09"),PROJECT_USER("10"),ROLE("11"),
	INTERFACE("12", "接口"),SOURCE("13", "文档"), DEBUG("14"), HOT_SEARCH("15"), BUG("16", "缺陷"),
	BUG_LOG("17"),PROJECT_META("18");

	@Getter
	private final String tableId;
	@Getter
	private final String tableName;

	TableId(String tableId){
		this.tableId = tableId;
		this.tableName = "";
	}

	TableId(String tableId, String tableName){
		this.tableId = tableId;
		this.tableName = tableName;
	}

	public static String getNameByValue(String value){
		if (value == null){
			return "";
		}
		for( TableId tableId : TableId.values()){
			if(tableId.getTableId().equals(value)){
				return tableId.getTableName();
			}
		}
		return "";
	}
}
