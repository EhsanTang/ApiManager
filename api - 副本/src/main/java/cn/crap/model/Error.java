package cn.crap.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import cn.crap.framework.SpringContextHolder;
import cn.crap.framework.base.BaseModel;
import cn.crap.inter.service.ICacheService;
import cn.crap.service.CacheService;
import cn.crap.utils.MyString;

@Entity
@Table(name="error")
@GenericGenerator(name="Generator", strategy="cn.crap.framework.IdGenerator")
public class Error extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String errorMsg;
	private String moduleId;
	
	@Transient
	public String getModuleName(){
		if(!MyString.isEmpty(moduleId)){
			ICacheService cacheService = SpringContextHolder.getBean("cacheService", CacheService.class);
			DataCenter module = cacheService.getModule(moduleId);
			if(module!=null)
				return module.getName();
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