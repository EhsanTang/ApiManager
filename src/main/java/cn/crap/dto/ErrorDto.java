package cn.crap.dto;

import cn.crap.framework.SpringContextHolder;
import cn.crap.service.tool.ProjectCache;

public class ErrorDto {
    private String id;
    private String errorCode;
    private String errorMsg;
    private String projectId;
    private Byte status;
    private Long sequence;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public String getProjectName() {
        ProjectCache projectCache = SpringContextHolder.getBean("projectCache", ProjectCache.class);
        return projectCache.get(projectId).getName();
    }

}
