package cn.crap.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import cn.crap.enumeration.ProjectStatus;
import cn.crap.enumeration.ProjectType;
import cn.crap.framework.SpringContextHolder;
import cn.crap.framework.base.BaseModel;
import cn.crap.inter.service.tool.ICacheService;
import cn.crap.service.tool.CacheService;
import cn.crap.utils.MyString;


@Entity
@Table(name="project")
@GenericGenerator(name="Generator", strategy="cn.crap.framework.IdGenerator")
public class Project extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private int type;
	private String remark;
	private String userId;
	private String password;
	private String cover = "resources/images/cover.png";
	
	public Project(String id,String name, int type, String remark, String userId, String createTime, String cover){
		this.id = id;
		this.name = name;
		this.type = type;
		this.remark = remark;
		this.userId = userId;
		this.createTime = createTime;
		this.cover = cover;
	}
	
	public Project(){};
	
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="type")
	public int getType() {
		return type;
	}
	public void setType(int type) {
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
	
	@Column(name="password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name="cover")
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}

	@Transient
	public String getTypeName(){
		return ProjectType.getNameByValue(type);
	}
	@Transient
	public String getStatusName(){
		return ProjectStatus.getNameByValue(status);
	}
	@Transient
	public String getUserName(){
		if(!MyString.isEmpty(userId)){
			ICacheService cacheService = SpringContextHolder.getBean("cacheService", CacheService.class);
			User user = cacheService.getUser(userId);
			if(user!=null)
				return user.getUserName();
		}
		return "";
	}
}