package cn.crap.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import cn.crap.framework.SpringContextHolder;
import cn.crap.framework.base.BaseController;
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
	
	private boolean addModule;// 5
	private boolean delModule;// 6
	private boolean modModule;// 4
	
	private boolean addInter;// 2
	private boolean delInter;// 3
	private boolean modInter;// 1
	
	private boolean addArticle;// 8
	private boolean delArticle;// 9
	private boolean modArticle;// 7
	
	private boolean addSource;// 14
	private boolean delSource;// 15
	private boolean modSource;// 13
	
	private boolean addDict;// 11
	private boolean delDict;// 12
	private boolean modDict;// 10
	
	private boolean addError;// 17
	private boolean delError;// 18
	private boolean modError;// 16
	private boolean[] projectAuth = new boolean[19];
	
	/**
	 * 转换为数组，以非get名称开头
	 * @return
	 */
	
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
		projectAuth[BaseController.addModule] = addModule;
		this.addModule = addModule;
	}
	
	@Column(name="delModule")
	public boolean isDelModule() {
		return delModule;
	}
	public void setDelModule(boolean delModule) {
		projectAuth[BaseController.delModule] = delModule;
		this.delModule = delModule;
	}
	
	@Column(name="modModule")
	public boolean isModModule() {
		return modModule;
	}
	public void setModModule(boolean modModule) {
		projectAuth[BaseController.modModule] = modModule;
		this.modModule = modModule;
	}
	
	@Column(name="addInter")
	public boolean isAddInter() {
		return addInter;
	}
	public void setAddInter(boolean addInter) {
		projectAuth[BaseController.addInter] = addInter;
		this.addInter = addInter;
	}
	
	@Column(name="delInter")
	public boolean isDelInter() {
		return delInter;
	}
	public void setDelInter(boolean delInter) {
		projectAuth[BaseController.delInter] = delInter;
		this.delInter = delInter;
	}
	
	@Column(name="modInter")
	public boolean isModInter() {
		return modInter;
	}
	public void setModInter(boolean modInter) {
		projectAuth[BaseController.modInter] = modInter;
		this.modInter = modInter;
	}
	
	@Column(name="addArticle")
	public boolean isAddArticle() {
		return addArticle;
	}
	public void setAddArticle(boolean addArticle) {
		projectAuth[BaseController.addArticle] = addArticle;
		this.addArticle = addArticle;
	}
	
	@Column(name="delArticle")
	public boolean isDelArticle() {
		return delArticle;
	}
	public void setDelArticle(boolean delArticle) {
		projectAuth[BaseController.delArticle] = delArticle;
		this.delArticle = delArticle;
	}
	
	@Column(name="modArticle")
	public boolean isModArticle() {
		return modArticle;
	}
	public void setModArticle(boolean modArticle) {
		projectAuth[BaseController.modArticle] = modArticle;
		this.modArticle = modArticle;
	}
	
	@Column(name="addSource")
	public boolean isAddSource() {
		return addSource;
	}
	public void setAddSource(boolean addSource) {
		projectAuth[BaseController.addSource] = addSource;
		this.addSource = addSource;
	}
	
	@Column(name="delSource")
	public boolean isDelSource() {
		return delSource;
	}
	public void setDelSource(boolean delSource) {
		projectAuth[BaseController.delSource] = delSource;
		this.delSource = delSource;
	}
	
	@Column(name="modSource")
	public boolean isModSource() {
		return modSource;
	}
	public void setModSource(boolean modSource) {
		projectAuth[BaseController.modSource] = modSource;
		this.modSource = modSource;
	}
	
	@Column(name="addDict")
	public boolean isAddDict() {
		return addDict;
	}
	public void setAddDict(boolean addDict) {
		projectAuth[BaseController.addDict] = addDict;
		this.addDict = addDict;
	}
	
	@Column(name="delDict")
	public boolean isDelDict() {
		return delDict;
	}
	public void setDelDict(boolean delDict) {
		projectAuth[BaseController.delDict] = delDict;
		this.delDict = delDict;
	}
	
	@Column(name="modDict")
	public boolean isModDict() {
		return modDict;
	}
	public void setModDict(boolean modDict) {
		projectAuth[BaseController.modDict] = modDict;
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
		projectAuth[BaseController.addError] = addError;
		this.addError = addError;
	}
	
	@Column(name="delError")
	public boolean isDelError() {
		return delError;
	}
	public void setDelError(boolean delError) {
		projectAuth[BaseController.delError] = delError;
		this.delError = delError;
	}
	
	@Column(name="modError")
	public boolean isModError() {
		return modError;
	}
	public void setModError(boolean modError) {
		projectAuth[BaseController.modError] = modError;
		this.modError = modError;
	}
	
	
	@Transient
	public boolean[] projectAuth() {
		return projectAuth;
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