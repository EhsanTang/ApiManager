package cn.crap.model;

import lombok.Data;

import java.util.Date;

@Data
public class BugPO extends BasePO {
    private String executor;
    private String executorStr;
    private Byte priority;
    // 最后修改人
    private String updateBy;
    private String name;
    private String content;
    private Byte severity;
    private String creator;
    private String creatorStr;
    private String moduleId;
    private String attributes;
    private String tester;
    private String testerStr;
    private String projectId;
    private Byte type;
    private String tracer;
    private String tracerStr;
    private Byte status;
    private Date deadline;
}
