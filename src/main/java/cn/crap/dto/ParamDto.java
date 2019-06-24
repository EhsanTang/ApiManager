package cn.crap.dto;

import java.io.Serializable;

public class ParamDto implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String name = "";
    // 前端显示，不带->的名称
    private String realName = "";
    private String necessary = "";
    private String type = "";
    private String remark = "";
    private String def = "";
    private String inUrl = "";
    private Integer deep = 0;

    public ParamDto() {
    }

    public ParamDto(String name, String necessary, String type, String def, String remark) {
        this.name = name;
        this.necessary = necessary;
        this.type = type;
        this.def = def;
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNecessary() {
        return necessary;
    }

    public void setNecessary(String necessary) {
        this.necessary = necessary;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public String getInUrl() {
        return inUrl;
    }

    public void setInUrl(String inUrl) {
        this.inUrl = inUrl;
    }

    public Integer getDeep() {
        return deep;
    }

    public void setDeep(Integer deep) {
        this.deep = deep;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getParamPosition() {
        if ("true".equals(inUrl)) {
            return "请求URL";
        } else {
            return "普通参数";
        }
    }

}
