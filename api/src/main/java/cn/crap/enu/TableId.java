package cn.crap.enu;

import lombok.Getter;
import org.springframework.util.Assert;

public enum TableId {
	/**
	 * DICTIONARY：数据库表，没有用到，但是创建索引时需要使用，区分数据类型
	 */
	DICTIONARY("00", "数据字典", "dictionary"), ARTICLE("01", "文章", "article"), SETTING("02"), ERROR("03", "状态码", "error"), COMMENT("04"),
	MENU("05"), USER("06"),PROJECT("07"), LOG("08"), MODULE("09"),PROJECT_USER("10"),ROLE("11"),
	INTERFACE("12", "接口", "interface"),SOURCE("13", "文档", "source"), DEBUG("14"), HOT_SEARCH("15"), BUG("16", "缺陷", "bug"),
	BUG_LOG("17"),PROJECT_META("18");

	@Getter
	private final String id;
	@Getter
	private final String tableName;
	@Getter
	private final String tableEnName;

	TableId(String id){
		this.id = id;
		this.tableName = "";
		this.tableEnName = "";
	}

	TableId(String id, String tableName, String tableEnName){
		this.id = id;
		this.tableName = tableName;
		this.tableEnName = tableEnName;
	}

    public static TableId getByValue(String value){
        Assert.notNull(value);
        for(TableId tableId : TableId.values()){
            if(tableId.getId().equals(value)){
                return tableId;
            }
        }
        return null;
    }

	public static String getNameByValue(String value){
        Assert.notNull(value);
        return getByValue(value).getTableName();
	}
}
