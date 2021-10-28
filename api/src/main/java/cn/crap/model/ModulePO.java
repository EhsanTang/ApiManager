package cn.crap.model;

import lombok.Getter;
import lombok.Setter;

public class ModulePO extends BasePO {
    private String name;

    @Getter
    @Setter
    private Byte status;

    private String url;

    @Getter
    @Setter
    private Byte canDelete;

    private String remark;

    private String userId;

    private String projectId;

    private String templateId;

    @Getter
    @Setter
    private Integer versionNum;

    private String category;

    @Getter
    @Setter
    private String uniKey;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }
}