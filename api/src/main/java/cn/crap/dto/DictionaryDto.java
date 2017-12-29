package cn.crap.dto;

import java.io.Serializable;

import cn.crap.enumer.DictionaryPropertyType;

/**
 * 
 * @author Ehsan
 *
 */
//[{"name":"name","type":"string","def":"","remark":"","notNull":"false","flag":"common"}]
public class DictionaryDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String type;
	private String def = "";
	private String remark ="";
	private String notNull;
	private String flag = DictionaryPropertyType.common.getName();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDef() {
		return def;
	}
	public void setDef(String def) {
		this.def = def;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getNotNull() {
		return notNull;
	}
	public void setNotNull(String notNull) {
		this.notNull = notNull;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	} 
	
}