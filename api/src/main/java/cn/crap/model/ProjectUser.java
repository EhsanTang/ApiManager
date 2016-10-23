package cn.crap.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import cn.crap.framework.SpringContextHolder;
import cn.crap.framework.base.BaseModel;
import cn.crap.inter.service.tool.ICacheService;
import cn.crap.service.tool.CacheService;
import cn.crap.utils.MyString;

@Entity
@Table(name="project_user")
@GenericGenerator(name="Generator", strategy="cn.crap.framework.IdGenerator")
public class ProjectUser extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String projectId;
	private String userId;
	private String userName;
	private String userEmail;
	
	private boolean addModule;
	private boolean delModule;
	private boolean modModule;
	
	private boolean addInter;
	private boolean delInter;
	private boolean modInter;
	
	private boolean addArticle;
	private boolean delArticle;
	private boolean modArticle;
	
	private boolean addSource;
	private boolean delSource;
	private boolean modSource;
	
	private boolean addDict;
	private boolean delDict;
	private boolean modDict;
	
	private boolean addError;
	private boolean delError;
	private boolean modError;
	
	@Column(name="projectId")
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@Column(name="userId")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name="addModule")
	public boolean isAddModule() {
		return addModule;
	}
	public void setAddModule(boolean addModule) {
		this.addModule = addModule;
	}
	
	@Column(name="delModule")
	public boolean isDelModule() {
		return delModule;
	}
	public void setDelModule(boolean delModule) {
		this.delModule = delModule;
	}
	
	@Column(name="modModule")
	public boolean isModModule() {
		return modModule;
	}
	public void setModModule(boolean modModule) {
		this.modModule = modModule;
	}
	
	@Column(name="addInter")
	public boolean isAddInter() {
		return addInter;
	}
	public void setAddInter(boolean addInter) {
		this.addInter = addInter;
	}
	
	@Column(name="delInter")
	public boolean isDelInter() {
		return delInter;
	}
	public void setDelInter(boolean delInter) {
		this.delInter = delInter;
	}
	
	@Column(name="modInter")
	public boolean isModInter() {
		return modInter;
	}
	public void setModInter(boolean modInter) {
		this.modInter = modInter;
	}
	
	@Column(name="addArticle")
	public boolean isAddArticle() {
		return addArticle;
	}
	public void setAddArticle(boolean addArticle) {
		this.addArticle = addArticle;
	}
	
	@Column(name="delArticle")
	public boolean isDelArticle() {
		return delArticle;
	}
	public void setDelArticle(boolean delArticle) {
		this.delArticle = delArticle;
	}
	
	@Column(name="modArticle")
	public boolean isModArticle() {
		return modArticle;
	}
	public void setModArticle(boolean modArticle) {
		this.modArticle = modArticle;
	}
	
	@Column(name="addSource")
	public boolean isAddSource() {
		return addSource;
	}
	public void setAddSource(boolean addSource) {
		this.addSource = addSource;
	}
	
	@Column(name="delSource")
	public boolean isDelSource() {
		return delSource;
	}
	public void setDelSource(boolean delSource) {
		this.delSource = delSource;
	}
	
	@Column(name="modSource")
	public boolean isModSource() {
		return modSource;
	}
	public void setModSource(boolean modSource) {
		this.modSource = modSource;
	}
	
	@Column(name="addDict")
	public boolean isAddDict() {
		return addDict;
	}
	public void setAddDict(boolean addDict) {
		this.addDict = addDict;
	}
	
	@Column(name="delDict")
	public boolean isDelDict() {
		return delDict;
	}
	public void setDelDict(boolean delDict) {
		this.delDict = delDict;
	}
	
	@Column(name="modDict")
	public boolean isModDict() {
		return modDict;
	}
	public void setModDict(boolean modDict) {
		this.modDict = modDict;
	}
	
	@Column(name="userName")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name="userEmail")
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	@Column(name="addError")
	public boolean isAddError() {
		return addError;
	}
	public void setAddError(boolean addError) {
		this.addError = addError;
	}
	
	@Column(name="delError")
	public boolean isDelError() {
		return delError;
	}
	public void setDelError(boolean delError) {
		this.delError = delError;
	}
	
	@Column(name="modError")
	public boolean isModError() {
		return modError;
	}
	public void setModError(boolean modError) {
		this.modError = modError;
	}
	@Transient
	public String getProjectName(){
		if(!MyString.isEmpty(projectId)){
			ICacheService cacheService = SpringContextHolder.getBean("cacheService", CacheService.class);
			Project project = cacheService.getProject(projectId);
			if(project!=null)
				return project.getName();
		}
		return "";
	}
	
	
}