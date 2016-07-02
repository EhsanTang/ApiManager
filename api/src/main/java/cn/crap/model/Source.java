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


/**
 * @date 2016-01-06
 */
@Entity
@Table(name="source")
@GenericGenerator(name="Generator", strategy="cn.crap.framework.IdGenerator")
public class Source extends BaseModel{
	
	private String name;// 资源名称
	private String updateTime;
	private String directoryId;// 文件夹目录：dataCenter id
	private String remark; // 资源内容
	private String filePath;
	
	@Column(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="updateTime")
	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name="directoryId")
	public String getDirectoryId() {
		return directoryId;
	}

	public void setDirectoryId(String directoryId) {
		this.directoryId = directoryId;
	}

	@Column(name="remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="filePath")
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	@Transient
	public String getDirectoryName(){
		if(!MyString.isEmpty(directoryId)){
			ICacheService cacheService = SpringContextHolder.getBean("cacheService", CacheService.class);
			DataCenter module = cacheService.getModule(directoryId);
			if(module!=null)
				return module.getName();
		}
		return "";
	}
	
	
	
	
}