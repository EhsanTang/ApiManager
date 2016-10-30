package cn.crap.dto;

import java.io.Serializable;

public class ResponseParamDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String type;
	private String remark;
	private int deep;
	private String necessary;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getDeep() {
		return deep;
	}
	public void setDeep(int deep) {
		this.deep = deep;
	}
	public String getNecessary() {
		return necessary;
	}
	public void setNecessary(String necessary) {
		this.necessary = necessary;
	}
	
	
}
