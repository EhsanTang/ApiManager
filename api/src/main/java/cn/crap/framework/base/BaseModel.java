package cn.crap.framework.base;

import javax.persistence.Column;

public class BaseModel{
	protected String createTime;
	/**
	 * -1:删除，0:不可用，1:可用
	 */
	protected byte status;
	@Column(name="createTime")
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Column(name="status")
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	
	
	
}
