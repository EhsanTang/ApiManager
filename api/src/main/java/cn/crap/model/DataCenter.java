package cn.crap.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;

import cn.crap.enumeration.ModuleStatus;
import cn.crap.framework.SpringContextHolder;
import cn.crap.framework.base.BaseModel;
import cn.crap.inter.service.ICacheService;
import cn.crap.service.CacheService;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;


@Entity
@Table(name="datacenter")
@GenericGenerator(name="Generator", strategy="cn.crap.framework.IdGenerator")
public class DataCenter extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String parentId;
	private String password;
	private String url;
	private byte canDelete;
	private String type;
	private String remark;
	private String userId;
	

	public DataCenter(){};
	public DataCenter(String parentId, String name) {
		this.parentId = parentId;
		this.name = name;
		this.userId = "superAdmin";
	}

	@Column(name="password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="parentId")
	public String getParentId() {
		if(parentId == null)
			return "";
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
	
	@Column(name="canDelete")
	public byte getCanDelete() {
		return canDelete;
	}
	public void setCanDelete(byte canDelete) {
		this.canDelete = canDelete;
	}
	
	@Column(name="type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name="userId")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Transient
	public String getStatusName(){
		return ModuleStatus.getNameByValue(status+"");
	}
	
	@Transient
	public String getParentName(){
		if(!MyString.isEmpty(parentId)){
			if(parentId != null && parentId.equals("0") && type != null && type.equals(Const.DIRECTORY)){
				return "根目录";
			}
			ICacheService cacheService = SpringContextHolder.getBean("cacheService", CacheService.class);
			DataCenter module = cacheService.getModule(parentId);
			if(module!=null)
				return module.getName();
		}
		return "";
	}
	// 所在项目
	@Transient
	public String getProjectId(){
		if(MyString.isEmpty(parentId) || parentId.equals(Const.TOP_MODULE)){
			return "";
		}
		// 该模块为项目
		if( parentId.equals(Const.PRIVATE_MODULE) || parentId.equals(Const.ADMIN_MODULE)){
			return id;
		}
		ICacheService cacheService = SpringContextHolder.getBean("cacheService", CacheService.class);
		DataCenter module = cacheService.getModule(parentId);
		// 最多支持100层模块查询，防止死循环
		for(int i=0; i<100; i++){
			if(module.getParentId().equals(Const.PRIVATE_MODULE) || module.getParentId().equals(Const.ADMIN_MODULE)){
				return module.getId();
			}
			
			if(MyString.isEmpty(module.getParentId()) || module.getParentId().equals(module.getId())){
				break;
			}
			
			module = cacheService.getModule(module.getParentId());
		}
		return "";
	}
}