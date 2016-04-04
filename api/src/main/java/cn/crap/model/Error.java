package cn.crap.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import cn.crap.framework.base.BaseModel;

@Entity
@Table(name="error")
@GenericGenerator(name="Generator", strategy="cn.crap.framework.IdGenerator")
public class Error extends BaseModel{

	private String errorId;
	private String errorCode;
	private String errorMsg;
	private String moduleId;
	private String moduleName;

	@Column(name="moduleName")
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@Id
	@GeneratedValue(generator="Generator")
	@Column(name="errorId")
	public String getErrorId() {
		return errorId;
	}

	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}

	@Column(name="errorCode")
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@Column(name="errorMsg")
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	@Column(name="moduleId")
	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	
	
}