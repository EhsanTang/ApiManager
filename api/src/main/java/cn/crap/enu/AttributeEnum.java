package cn.crap.enu;

import lombok.Getter;

/**
 * 属性字段
 */
public enum AttributeEnum {
	MARK_DOWN("markdown", "1", "文章是否是markdown模式"),
	ENV_URL("envUrl", "", ""),
	VIP_POST_WOMAN_PROJECT_NUM("vipPostWoman", "50", "插件VIP用户项目数量"),
    VIP_POST_WOMAN_INTER_NUM("vipPostWomanInterNum", "200", "插件VIP用户接口数量"),

    LOGIN_AUTH_CODE("loginAuthCode", "", "通过第三方平台登录授权码");

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
