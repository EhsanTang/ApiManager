package cn.crap.enumer;

import cn.crap.model.Setting;
import cn.crap.utils.MyString;

import java.util.Date;

public enum SettingEnum {
    // 前端
	FOOTER_BG_COLOR("FOOTER_BG_COLOR", SettingStatus.DELETE, SettingType.COLOR, false, 100, "#233050", "网站顶部、底部部背景颜色", null),
    FOOTER_COLOR("FOOTER_COLOR", SettingStatus.DELETE, SettingType.COLOR, false, 100, "#a9a9a9", "前端顶部、底部字体颜色 #a9a9a9", null),
    NAV_BG_COLOR("NAV_BG_COLOR", SettingStatus.COMMON, SettingType.COLOR, false, 100, "#233050", "网站顶部、底部导航背景颜色", null),
    NAV_COLOR("NAV_COLOR", SettingStatus.COMMON, SettingType.COLOR, false, 100, "#a9a9a9", "前端顶部、导航字体颜色 #a9a9a9", null),
    MINI_LOGO("MINI_LOGO", SettingStatus.COMMON, SettingType.IMAGE, false, 101, "resources/images/logo.png", "网站小logo", null),

    ICONFONT("ICONFONT", SettingStatus.COMMON, SettingType.SELECT, false, 101, "//at.alicdn.com/t/font_73680_mziil8i9bgk", "系统图标",
            new String[]{"本地图标地址（服务器不能连接外网时使用）|iconfont", "阿里CDN图标库地址|//at.alicdn.com/t/font_73680_mziil8i9bgk"}),
    MAX_WIDTH("MAX_WIDTH", SettingStatus.COMMON, SettingType.TEXT, true, 101, "1200", "前端显示最大宽度（数字，建议：900-1200）", null),

    FONT_FAMILY("FONT_FAMILY", SettingStatus.COMMON, SettingType.SEL_IN, false, 101,
            "Georgia, \"Times New Roman\", Times,\"Microsoft Yahei\",\"Hiragino Sans GB\",sans-serif;", "系统字体",
            new String[]{
                    "默认|Georgia, \"Times New Roman\", Times,\"Microsoft Yahei\",\"Hiragino Sans GB\",sans-serif;",
                    "36氪网字体|\"Lantinghei SC\", \"Open Sans\", Arial, \"Hiragino Sans GB\", \"Microsoft YaHei\", \"STHeiti\", \"WenQuanYi Micro Hei\", SimSun, sans-serif;",
                    "果壳字体|Arial,Helvetica,sans-serif;",
                    "知乎字体|\"Helvetica Neue\",Helvetica,Arial,sans-serif;"

            }),

    // 后端
    IMAGE_CODE("IMAGE_CODE", SettingStatus.HIDDEN, SettingType.SELECT, false, 101, "Times New Roman", "图形验字体（部分系统显示有问题，可切换字体）",
            new String[]{"TimesNewRoman|Times New Roman"}),
    MAX_MODULE("MAX_MODULE", SettingStatus.HIDDEN, SettingType.TEXT, true, 101, "50", "项目下允许创建的最大模块数", null),
    MAX_PROJECT("MAX_PROJECT", SettingStatus.HIDDEN, SettingType.TEXT, true, 101, "15", "最大允许创建的项目数", null),
	MAX_ERROR("MAX_ERROR", SettingStatus.HIDDEN, SettingType.TEXT, true, 101, "200", "项目下最大允许的错误码数量，最大不能超过1000", null),
	SECRETKEY("SECRETKEY", SettingStatus.HIDDEN, SettingType.TEXT, false, 101, "crapApiKey", "秘钥，用于cookie加密等", null);

    private String key;
	private String value;
	private String remark;
	private Byte status;
	private String type;
	private Byte canDelete;
	// 是否必须为数字
	private Boolean mustBeInt;
	// 只有当type 为SELECT，SEL_IN 是才有效，option: name|value
	private String[] options;

	private Integer sequence;
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

	SettingEnum(String key, SettingStatus status, SettingType type, Boolean mustBeInt,
                Integer sequence, String value, String remark, String[] options){
		this.key = key;
		this.value = value;
		this.status = Byte.parseByte(status.getStatus()+"");
		this.type = type.getValue();
		this.canDelete = CanDeleteEnum.CAN_NOT.getCanDelete();
		this.remark = remark;
		this.sequence = sequence;
		this.mustBeInt = mustBeInt;
		this.options = options;
	}

	public Setting getSetting(){
	    Setting setting = new Setting();
	    setting.setMkey(key);
	    setting.setSequence(sequence);
	    setting.setRemark(remark);
	    setting.setCreateTime(new Date());
	    setting.setStatus(status);
	    setting.setType(type);
	    setting.setValue(value);
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

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Boolean getMustBeInt() {
		return mustBeInt;
	}

	public void setMustBeInt(Boolean mustBeInt) {
		this.mustBeInt = mustBeInt;
	}

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }
}
