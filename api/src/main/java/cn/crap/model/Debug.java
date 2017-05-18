package cn.crap.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import cn.crap.framework.base.BaseModel;

@Entity
@Table(name="debug")
@GenericGenerator(name="Generator", strategy="cn.crap.framework.IdGenerator")
public class Debug extends BaseModel implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private String interfaceId;
	private String moduleId;
	private String method;
	private String params;
	private String headers;
	private String url;
	private String paramType;
	private Integer version;// 版本号，客户端的版本比服务器低，将忽略
	private String uid;
	
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="interfaceId")
	public String getInterfaceId() {
		return interfaceId;
	}
	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}
	
	@Column(name="moduleId")
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	@Column(name="method")
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	@Column(name="params")
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	@Column(name="headers")
	public String getHeaders() {
		return headers;
	}
	public void setHeaders(String headers) {
		this.headers = headers;
	}
	@Column(name="url")
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Column(name="paramType")
	public String getParamType() {
		return paramType;
	}
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	
	@Column(name="version")
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	@Column(name="uid")
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	
}