package cn.crap.model;

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
@Table(name="module")
@GenericGenerator(name="Generator", strategy="cn.crap.framework.IdGenerator")
public class Module extends BaseModel{
	private String moduleName;
	private String parentId;
	private String password;
	private String url;

	public Module(){};
	public Module(String parentId, String moduleName) {
		this.parentId = parentId;
		this.moduleName = moduleName;
	}

	@Column(name="password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name="moduleName")
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@Column(name="parentId")
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name="url")
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Transient
	public String getParentName(){
		if(!MyString.isEmpty(parentId)){
			ICacheService cacheService = SpringContextHolder.getBean("cacheService", CacheService.class);
			Module module = cacheService.getModule(parentId);
			if(module!=null)
				return module.getModuleName();
		}
		return "";
	}

}