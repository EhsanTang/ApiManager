package cn.crap.model;

import java.util.Date;

public class Log extends BasePO {

    private Byte status;

    private Date createTime;

    private String modelClass;

    private String modelName;

    private String type;

    private String updateBy;

    private String remark;

    private String identy;

    private String content;

    private static final long serialVersionUID = 1L;

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModelClass() {
        return modelClass;
    }

    public void setModelClass(String modelClass) {
        this.modelClass = modelClass == null ? null : modelClass.trim();
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName == null ? null : modelName.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getIdenty() {
        return identy;
    }

    public void setIdenty(String identy) {
        this.identy = identy == null ? null : identy.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}