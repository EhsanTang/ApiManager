package cn.crap.enu;

import lombok.Getter;

/**
 * 属性字段
 */
public enum AttributeEnum {
	MARK_DOWN("markdown", "1", "文章是否是markdown模式"), ENV_URL("envUrl", "", ""), VIP_POST_WOMAN("vipPostWoman", "1", "插件VIP用户");

	@Getter
	private final String value;

	@Getter
	private final String key;

	@Getter
	private final String desc;

	AttributeEnum(String key, String value, String desc){
		this.value = value;
		this.key = key;
		this.desc = desc;
	}

	public static AttributeEnum getNameByKey(String key){
		if (key == null){
			return null;
		}
		for(AttributeEnum articleStatus : AttributeEnum.values()){
			if(articleStatus.getKey().equals(key))
				return articleStatus;
		}
		return null;
	}
}
