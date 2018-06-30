package cn.crap.model;

import java.io.Serializable;
import java.util.Date;

public class BasePo implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private Integer sequence;
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}