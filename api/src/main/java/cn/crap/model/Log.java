package cn.crap.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import cn.crap.framework.base.BaseModel;

@Entity
@Table(name="log")
@GenericGenerator(name="Generator", strategy="cn.crap.framework.IdGenerator")
public class Log extends BaseModel{
	private String id;
	private String modelCalss;// 数据对应的class类
	private String user;//用户
	private String remark;//备注
	private String modelName;// 前端展示的名称：如接口、菜单....
	private String type;//操作类型：删除、修改
	private String content;//操作之前的json数据
	
	@Id
	@GeneratedValue(generator="Generator")
	@Column(name="id")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="modelCalss")
	public String getModelCalss() {
		return modelCalss;
	}
	public void setModelCalss(String modelCalss) {
		this.modelCalss = modelCalss;
	}
	
	@Column(name="user")
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name="modelName")
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	@Column(name="type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name="content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}