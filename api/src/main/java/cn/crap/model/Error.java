package cn.crap.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import cn.crap.framework.base.BaseModel;
import cn.crap.utils.Cache;
import cn.crap.utils.MyString;

@Entity
@Table(name="error")
@GenericGenerator(name="Generator", strategy="cn.crap.framework.IdGenerator")
public class Error extends BaseModel{

	private String errorCode;
	private String errorMsg;
	private String moduleId;
	
	@Transient
	public String getModuleName(){
		if(!MyString.isEmpty(moduleId)){
			Module module = Cache.getModule(moduleId);
			if(module!=null)
				return module.getModuleName();
		}
		return "";
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