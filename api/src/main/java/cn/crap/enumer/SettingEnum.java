package cn.crap.enumer;

import cn.crap.model.mybatis.Setting;
import cn.crap.utils.MyString;

import java.util.Date;

public enum SettingEnum {
	FOOTER_BG_COLOR("FOOTER_BG_COLOR", "#233050", 1, SettingType.COLOR.getValue(), CanDeleteEnum.CAN_NOT.getCanDelete(), 100, "网站顶部、顶部被叫颜色"),
    MINI_LOGO("MINI_LOGO", "resources/images/logo.png", 1, SettingType.IMAGE.getValue(), CanDeleteEnum.CAN_NOT.getCanDelete(), 101, "网站小logo"),
    MAX_MODULE("MAX_MODULE", "50", 1, SettingType.TEXT.getValue(), CanDeleteEnum.CAN_NOT.getCanDelete(), 101, "夏目下允许创建的最大模块数"),
	MAX_PROJECT("MAX_PROJECT", "15", 1, SettingType.TEXT.getValue(), CanDeleteEnum.CAN_NOT.getCanDelete(), 101, "最大允许创建的项目数");


	private String key;
	private String value;
	private String remark;
	private Byte status;
	private String type;
	private Byte canDelete;

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

	SettingEnum(String key, String value, Integer status, String type, Byte canDelete, Integer sequence, String remark){
		this.key = key;
		this.value = value;
		this.status = Byte.parseByte(status+"");
		this.type = type;
		this.canDelete = canDelete;
		this.remark = remark;
		this.sequence = sequence;
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
}
