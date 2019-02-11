package cn.crap.dto;

import cn.crap.enu.SettingType;
import cn.crap.utils.MyString;

public class SettingDto{
    private String id;
    private String key;
    private String value;
    private String remark;
    private Byte status;
    private String type;
    private Byte canDelete;
    private Long sequence;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        this.type = type;
    }

    public Byte getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Byte canDelete) {
        this.canDelete = canDelete;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public String getTypeName(){
        if(!MyString.isEmpty(type)){
            return SettingType.getName(type);
        }
        return "";

    }
}