package cn.crap.model;

import lombok.Getter;
import lombok.Setter;

public class ProjectPO extends BasePO {
    private String name;

    @Getter
    @Setter
    private Byte status;

    private String remark;

    private String userId;

    @Getter
    @Setter
    private Byte type;

    private String password;

    private String cover;

    @Getter
    @Setter
    private Byte luceneSearch;

    @Getter
    @Setter
    private String uniKey;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }
}