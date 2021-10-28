package cn.crap.model;

import cn.crap.utils.NotNullFieldStringStyle;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

public class BasePO implements Serializable{
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private Long sequence;

    @Getter
    @Setter
    private Date createTime;

    @Getter
    @Setter
    private Date updateTime;

    @Getter
    @Setter
    private String attributes;

    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, new NotNullFieldStringStyle());
    }

}