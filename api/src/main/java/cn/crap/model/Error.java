package cn.crap.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import cn.crap.framework.base.BaseModel;


/**
 * @author lizhiyong
 * @date 2016-01-06
 */
@Entity
@Table(name="error")
@GenericGenerator(name="Generator", strategy="cn.crap.framework.IdGenerator")
public class Error extends BaseModel{
	/**
	 * errorId(主键)
	 * */
	private String errorId;
	/**
	 * errorCode(错误码编码)
	 * */
	private String errorCode;
	/**
	 * errorMsg(错误码描述)
	 * */
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