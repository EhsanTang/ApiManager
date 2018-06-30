package cn.crap.model;

import java.util.Date;

public class ProjectUser extends BasePo{
    private String id;

    private Byte status;

    private Integer sequence;

    private Date createTime;

    private String projectId;

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

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Boolean getAddModule() {
        return addModule;
    }

    public void setAddModule(Boolean addModule) {
        this.addModule = addModule;
    }

    public Boolean getDelModule() {
        return delModule;
    }

    public void setDelModule(Boolean delModule) {
        this.delModule = delModule;
    }

    public Boolean getModModule() {
        return modModule;
    }

    public void setModModule(Boolean modModule) {
        this.modModule = modModule;
    }

    public Boolean getAddInter() {
        return addInter;
    }

    public void setAddInter(Boolean addInter) {
        this.addInter = addInter;
    }

    public Boolean getDelInter() {
        return delInter;
    }

    public void setDelInter(Boolean delInter) {
        this.delInter = delInter;
    }

    public Boolean getModInter() {
        return modInter;
    }

    public void setModInter(Boolean modInter) {
        this.modInter = modInter;
    }

    public Boolean getAddArticle() {
        return addArticle;
    }

    public void setAddArticle(Boolean addArticle) {
        this.addArticle = addArticle;
    }

    public Boolean getDelArticle() {
        return delArticle;
    }

    public void setDelArticle(Boolean delArticle) {
        this.delArticle = delArticle;
    }

    public Boolean getModArticle() {
        return modArticle;
    }

    public void setModArticle(Boolean modArticle) {
        this.modArticle = modArticle;
    }

    public Boolean getAddSource() {
        return addSource;
    }

    public void setAddSource(Boolean addSource) {
        this.addSource = addSource;
    }

    public Boolean getDelSource() {
        return delSource;
    }

    public void setDelSource(Boolean delSource) {
        this.delSource = delSource;
    }

    public Boolean getModSource() {
        return modSource;
    }

    public void setModSource(Boolean modSource) {
        this.modSource = modSource;
    }

    public Boolean getAddDict() {
        return addDict;
    }

    public void setAddDict(Boolean addDict) {
        this.addDict = addDict;
    }

    public Boolean getDelDict() {
        return delDict;
    }

    public void setDelDict(Boolean delDict) {
        this.delDict = delDict;
    }

    public Boolean getModDict() {
        return modDict;
    }

    public void setModDict(Boolean modDict) {
        this.modDict = modDict;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Boolean getAddError() {
        return addError;
    }

    public void setAddError(Boolean addError) {
        this.addError = addError;
    }

    public Boolean getDelError() {
        return delError;
    }

    public void setDelError(Boolean delError) {
        this.delError = delError;
    }

    public Boolean getModError() {
        return modError;
    }

    public void setModError(Boolean modError) {
        this.modError = modError;
    }

}