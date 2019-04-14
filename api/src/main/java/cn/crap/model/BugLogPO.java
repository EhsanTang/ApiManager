package cn.crap.model;

public class BugLogPO extends BasePO {
    private String senior;
    private String operatorStr;
    private Byte status;
    private String remark;
    private String junior;
    private Byte type;
    private String operator;
    private String bugId;
    private String projectId;
    private String newValue;
    private String originalValue;

    public String getSenior() {
        return senior;
    }

    public void setSenior(String senior) {
        this.senior = senior;
    }

    public String getOperatorStr() {
        return operatorStr;
    }

    public void setOperatorStr(String operatorStr) {
        this.operatorStr = operatorStr;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getJunior() {
        return junior;
    }

    public void setJunior(String junior) {
        this.junior = junior;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getBugId() {
        return bugId;
    }

    public void setBugId(String bugId) {
        this.bugId = bugId;
    }

    @Override
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getOriginalValue() {
        return originalValue;
    }

    public void setOriginalValue(String originalValue) {
        this.originalValue = originalValue;
    }
}
