package cn.crap.model;

import lombok.Data;

@Data
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
}
