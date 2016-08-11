package cn.crap.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import cn.crap.framework.base.BaseModel;
import cn.crap.utils.Const;
import cn.crap.utils.Tools;

@Entity
@Table(name="log")
@GenericGenerator(name="Generator", strategy="cn.crap.framework.IdGenerator")
public class Log extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String modelClass;// 数据对应的class类
	private String updateBy;//用户
	private String remark;//备注
	private String modelName;// 前端展示的名称：如接口、菜单....
	private String type;//操作类型：删除、修改
	private String content;//操作之前的json数据
	private String identy;//数据唯一标识
	
	
	public Log(){}
	public Log(String modelName, String remark, String type, String content, String modelClass,String identy){
		this.modelName = modelName;
		this.updateBy = Tools.getUser().getUserName();
		this.remark = remark;
		this.type = type;
		this.content = content;
		this.modelClass = modelClass;
		this.identy = identy;
	}
	@Column(name="modelClass")
	public String getModelClass() {
		return modelClass;
	}
	public void setModelClass(String modelClass) {
		this.modelClass = modelClass;
	}
	
	@Column(name="updateBy")
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
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
	
	@Column(name="identy")
	public String getIdenty() {
		return identy;
	}
	public void setIdenty(String identy) {
		this.identy = identy;
	}
	
}