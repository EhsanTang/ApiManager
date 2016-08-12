package cn.crap.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import cn.crap.enumeration.SettingType;
import cn.crap.framework.base.BaseModel;
import cn.crap.utils.MyString;

@Entity
@Table(name="setting")
@GenericGenerator(name="Generator", strategy="cn.crap.framework.IdGenerator")
public class Setting extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String key;
	private String value;
	private String remark;
	private String type;
	private byte canDelete;
	
	@Column(name="mkey")
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	@Column(name="type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(name="value")
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name="canDelete")
	public byte getCanDelete() {
		return canDelete;
	}
	public void setCanDelete(byte canDelete) {
		this.canDelete = canDelete;
	}
	@Transient
	public String getTypeName(){
		if(!MyString.isEmpty(type)){
			return SettingType.getValue(type);
		}
		return "";
			
	}
}