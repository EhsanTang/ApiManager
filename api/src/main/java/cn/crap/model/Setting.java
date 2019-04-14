package cn.crap.model;

import java.util.Date;

public class Setting extends BasePO {
    private String id;

    private String mkey;

    private String value;

    private String remark;

    private Date createTime;

    private Byte status;

    private String type;

    private Byte canDelete;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMkey() {
        return mkey;
    }

    public void setMkey(String mkey) {
        this.mkey = mkey == null ? null : mkey.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Byte getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Byte canDelete) {
        this.canDelete = canDelete;
    }
}