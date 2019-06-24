package cn.crap.dto;

import java.io.Serializable;

/**
 * Automatic generation by tools
 * dto: exchange data with READ
 */
public class ArticleDto implements Serializable {
    private String id;
    private String name;
    private String brief;
    private String content;
    private Integer click;
    private String type;
    private String typeName;
    private Byte status;
    private String statusName;
    private String moduleId;
    private String moduleName;
    private String mkey;
    private Byte canDelete;
    private String category;
    private Byte canComment;
    private String canCommentName;
    private Integer commentCount;
    private Long sequence;
    private String markdown;
    private String projectId;
    private String createTimeStr;
    private String projectName;
    private String attributes;
    /**
     * 是否用markdown
     */
    private Boolean useMarkdown;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getClick() {
        return click;
    }

    public void setClick(Integer click) {
        this.click = click;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getMkey() {
        return mkey;
    }

    public void setMkey(String mkey) {
        this.mkey = mkey;
    }

    public Byte getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Byte canDelete) {
        this.canDelete = canDelete;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Byte getCanComment() {
        return canComment;
    }

    public void setCanComment(Byte canComment) {
        this.canComment = canComment;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public String getMarkdown() {
        return markdown;
    }

    public void setMarkdown(String markdown) {
        this.markdown = markdown;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getCanCommentName() {
        return canCommentName;
    }

    public void setCanCommentName(String canCommentName) {
        this.canCommentName = canCommentName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public Boolean getUseMarkdown() {
        return useMarkdown;
    }

    public void setUseMarkdown(Boolean useMarkdown) {
        this.useMarkdown = useMarkdown;
    }
}
