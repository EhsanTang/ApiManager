package cn.crap.dto;

import java.util.Date;

/**
 * Automatic generation by tools
 * dto: exchange data with READ
 */
public class ProjectUserDto{
	private String id;
	private Byte status;
	private Integer sequence;
	private String projectId;
	private String projectName;
	private String userId;
	private Boolean addModule;
	private Boolean delModule;
	private Boolean modModule;
	private Boolean addInter;
	private Boolean delInter;
	private Boolean modInter;
	private Boolean addArticle;
	private Boolean delArticle;
	private Boolean modArticle;
	private Boolean addSource;
	private Boolean delSource;
	private Boolean modSource;
	private Boolean addDict;
	private Boolean delDict;
	private Boolean modDict;
	private String userEmail;
	private String userName;
	private Boolean addError;
	private Boolean delError;
	private Boolean modError;
	private Boolean[] projectAuth;

	public void setId(String id){
		this.id=id;
	}
	public String getId(){
		return id;
	}

	public void setStatus(Byte status){
		this.status=status;
	}
	public Byte getStatus(){
		return status;
	}

	public void setSequence(Integer sequence){
		this.sequence=sequence;
	}
	public Integer getSequence(){
		return sequence;
	}

	public void setProjectId(String projectId){
		this.projectId=projectId;
	}
	public String getProjectId(){
		return projectId;
	}

	public void setUserId(String userId){
		this.userId=userId;
	}
	public String getUserId(){
		return userId;
	}

	public void setAddModule(Boolean addModule){
		this.addModule=addModule;
	}
	public Boolean getAddModule(){
		return addModule;
	}

	public void setDelModule(Boolean delModule){
		this.delModule=delModule;
	}
	public Boolean getDelModule(){
		return delModule;
	}

	public void setModModule(Boolean modModule){
		this.modModule=modModule;
	}
	public Boolean getModModule(){
		return modModule;
	}

	public void setAddInter(Boolean addInter){
		this.addInter=addInter;
	}
	public Boolean getAddInter(){
		return addInter;
	}

	public void setDelInter(Boolean delInter){
		this.delInter=delInter;
	}
	public Boolean getDelInter(){
		return delInter;
	}

	public void setModInter(Boolean modInter){
		this.modInter=modInter;
	}
	public Boolean getModInter(){
		return modInter;
	}

	public void setAddArticle(Boolean addArticle){
		this.addArticle=addArticle;
	}
	public Boolean getAddArticle(){
		return addArticle;
	}

	public void setDelArticle(Boolean delArticle){
		this.delArticle=delArticle;
	}
	public Boolean getDelArticle(){
		return delArticle;
	}

	public void setModArticle(Boolean modArticle){
		this.modArticle=modArticle;
	}
	public Boolean getModArticle(){
		return modArticle;
	}

	public void setAddSource(Boolean addSource){
		this.addSource=addSource;
	}
	public Boolean getAddSource(){
		return addSource;
	}

	public void setDelSource(Boolean delSource){
		this.delSource=delSource;
	}
	public Boolean getDelSource(){
		return delSource;
	}

	public void setModSource(Boolean modSource){
		this.modSource=modSource;
	}
	public Boolean getModSource(){
		return modSource;
	}

	public void setAddDict(Boolean addDict){
		this.addDict=addDict;
	}
	public Boolean getAddDict(){
		return addDict;
	}

	public void setDelDict(Boolean delDict){
		this.delDict=delDict;
	}
	public Boolean getDelDict(){
		return delDict;
	}

	public void setModDict(Boolean modDict){
		this.modDict=modDict;
	}
	public Boolean getModDict(){
		return modDict;
	}

	public void setUserEmail(String userEmail){
		this.userEmail=userEmail;
	}
	public String getUserEmail(){
		return userEmail;
	}

	public void setUserName(String userName){
		this.userName=userName;
	}
	public String getUserName(){
		return userName;
	}

	public void setAddError(Boolean addError){
		this.addError=addError;
	}
	public Boolean getAddError(){
		return addError;
	}

	public void setDelError(Boolean delError){
		this.delError=delError;
	}
	public Boolean getDelError(){
		return delError;
	}

	public void setModError(Boolean modError){
		this.modError=modError;
	}
	public Boolean getModError(){
		return modError;
	}

	public Boolean[] getProjectAuth() {
		return projectAuth;
	}

	public void setProjectAuth(Boolean[] projectAuth) {
		this.projectAuth = projectAuth;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


}
