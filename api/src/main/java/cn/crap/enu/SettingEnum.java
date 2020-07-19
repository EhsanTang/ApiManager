package cn.crap.enu;

import cn.crap.model.Setting;
import cn.crap.utils.MyString;

import java.util.Date;

public enum SettingEnum {
    // 前端
	FOOTER_BG_COLOR("FOOTER_BG_COLOR", SettingStatus.DELETE, SettingType.COLOR, null, "#233050", "网站顶部、底部部背景颜色"),
    FOOTER_COLOR("FOOTER_COLOR", SettingStatus.DELETE, SettingType.COLOR, null, "#a9a9a9", "前端顶部、底部字体颜色 #a9a9a9"),
    NAV_BG_COLOR("NAV_BG_COLOR", SettingStatus.COMMON, SettingType.COLOR, null,"#233050", "网站顶部、底部导航背景颜色"),
    NAV_COLOR("NAV_COLOR", SettingStatus.COMMON, SettingType.COLOR, null, "#a9a9a9", "前端顶部、导航字体颜色 #a9a9a9"),
    MINI_LOGO("MINI_LOGO", SettingStatus.COMMON, SettingType.IMAGE, null, "resources/images/logo.png", "网站小logo"),
	DOMAIN("DOMAIN", SettingStatus.DELETE, SettingType.TEXT, null, "http://127.0.0.1:8080", "网站域名：废弃"),
	CALLBACK_DOMAIN("CALLBACK_DOMAIN", SettingStatus.COMMON, SettingType.TEXT, null, "http://127.0.0.1:8080/api/", "网站域名，第三方登陆回调使用，如：http://127.0.0.1:8080/api/"),


	ICONFONT("ICONFONT", SettingStatus.COMMON, SettingType.SELECT, null, "//at.alicdn.com/t/font_860205_v11qvy4n2m", "系统图标",
            new String[]{"本地图标地址（服务器不能连接外网时使用）|iconfont", "阿里CDN图标库地址|//at.alicdn.com/t/font_860205_v11qvy4n2m"}),
    MAX_WIDTH("MAX_WIDTH", SettingStatus.COMMON, SettingType.TEXT, 1200, null, "前端显示最大宽度（数字，建议：900-1200）"),

    FONT_FAMILY("FONT_FAMILY", SettingStatus.COMMON, SettingType.SEL_IN, null,
            "Georgia, \"Times New Roman\", Times,\"Microsoft Yahei\",\"Hiragino Sans GB\",sans-serif;", "系统字体",
            new String[]{
                    "默认|Georgia, \"Times New Roman\", Times,\"Microsoft Yahei\",\"Hiragino Sans GB\",sans-serif;",
                    "36氪网字体|\"Lantinghei SC\", \"Open Sans\", Arial, \"Hiragino Sans GB\", \"Microsoft YaHei\", \"STHeiti\", \"WenQuanYi Micro Hei\", SimSun, sans-serif;",
                    "果壳字体|Arial,Helvetica,sans-serif;",
                    "知乎字体|\"Helvetica Neue\",Helvetica,Arial,sans-serif;"

            }),
	// 后端
	DATABASE_CHANGE_LOG("DATABASE_CHANGE_LOG", SettingStatus.HIDDEN, SettingType.TEXT, 0, null, "数据库自动更新记录：记录最后一条sql的序号，更新版本后系统会自动修改，请勿修改"),
	NO_NEED_LOGIN_USER("NO_NEED_LOGIN_USER", SettingStatus.HIDDEN, SettingType.TEXT, null, "0", "不需要登录直接模拟的用户，快速试用，0表示不开放试用功能"),
    IMAGE_CODE("IMAGE_CODE", SettingStatus.HIDDEN, SettingType.SELECT, null, "Times New Roman", "图形验字体（部分系统显示有问题，可切换字体）",
            new String[]{"TimesNewRoman|Times New Roman"}),
    MAX_MODULE("MAX_MODULE", SettingStatus.HIDDEN, SettingType.TEXT, 50, null, "项目下允许创建的最大模块数"),
    MAX_PROJECT("MAX_PROJECT", SettingStatus.HIDDEN, SettingType.TEXT, 50, null, "最大允许创建的项目数"),
	MAX_ERROR("MAX_ERROR", SettingStatus.HIDDEN, SettingType.TEXT, 200, null, "项目下最大允许的错误码数量，最大不能超过1000"),
	SECRETKEY("SECRETKEY", SettingStatus.HIDDEN, SettingType.TEXT, null, "crapApiKey", "秘钥，用于cookie加密等"),
	POST_WOMAN_PROJECT_NUM("PLUG_MAX_PRO_NUM", SettingStatus.COMMON, SettingType.TEXT, 10, null, "postwoman插件最大允许的项目数"),
	POST_WOMAN_VIP_PROJECT_NUM("VIP_PLUG_PRO_NUM", SettingStatus.COMMON, SettingType.TEXT, 50, null, "vip 用户 postwoman插件最大允许的项目数"),

	POST_WOMAN_INTER_NUM("PLUG_MAX_INTER_NUM", SettingStatus.COMMON, SettingType.TEXT, 120, null, "postwoman插件最大允许的接口数"),
	POST_WOMAN_VIP_INTER_NUM("VIP_PLUG_INTER_NUM", SettingStatus.COMMON, SettingType.TEXT, 200, null, "vip 用户 postwoman插件最大允许的接口数"),

	OPEN_ALIYUN("OPEN_ALIYUN", SettingStatus.HIDDEN, SettingType.TEXT, null, "false", "是否开启阿里云图片存储，开通后图片、文件将通过云端读写，true表示打开");


    private String key;
	private String value;
	private String remark;
	private Byte status;
	private String type;
	private Byte canDelete;
	private Integer intValue;
	// 只有当type 为SELECT，SEL_IN 是才有效，option: name|value
	private String[] options;

	public static SettingEnum getByKey(String key){
		if (MyString.isEmpty(key)){
			return null;
		}
		for (SettingEnum settingEnum : SettingEnum.values()){
			if (key.equals(settingEnum.getKey())){
				return settingEnum;
			}
		}
		return null;
	}

    SettingEnum(String key, SettingStatus status, SettingType type, Integer intValue, String value, String remark){
       this(key, status, type, intValue, value, remark, null);
    }

	SettingEnum(String key, SettingStatus status, SettingType type, Integer intValue, String value, String remark, String[] options){
		this.key = key;
		this.value = value;
		this.status = Byte.parseByte(status.getStatus()+"");
		this.type = type.getValue();
		this.canDelete = CanDeleteEnum.CAN_NOT.getCanDelete();
		this.remark = remark;
		this.intValue = intValue;
		this.options = options;
	}

	public Setting getSetting(){
	    Setting setting = new Setting();
	    setting.setMkey(key);
	    setting.setSequence(System.currentTimeMillis());
	    setting.setRemark(remark);
	    setting.setCreateTime(new Date());
	    setting.setStatus(status);
	    setting.setType(type);
	    setting.setValue(intValue != null ? intValue + "" : value);
	    setting.setCanDelete(canDelete);
	    return setting;
    }

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Byte getCanDelete() {
		return canDelete;
	}

	public void setCanDelete(Byte canDelete) {
		this.canDelete = canDelete;
	}

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public Integer getIntValue() {
        return intValue;
    }

    public void setIntValue(Integer intValue) {
        this.intValue = intValue;
    }
}
