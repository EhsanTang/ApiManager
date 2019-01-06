package cn.crap.dto;

import cn.crap.enu.BugStatus;
import cn.crap.model.Bug;

import java.io.Serializable;

public class BugDto extends Bug implements Serializable {
    private String statusStr;
    private String severityStr;
    private String priorityStr;
    private String traceTypeStr;
    private String createTimeStr;
    private String updateTimeStr;

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public String getSeverityStr() {
        return severityStr;
    }

    public void setSeverityStr(String severityStr) {
        this.severityStr = severityStr;
    }

    public String getPriorityStr() {
        return priorityStr;
    }

    public void setPriorityStr(String priorityStr) {
        this.priorityStr = priorityStr;
    }

    public String getTraceTypeStr() {
        return traceTypeStr;
    }

    public void setTraceTypeStr(String traceTypeStr) {
        this.traceTypeStr = traceTypeStr;
    }
}
