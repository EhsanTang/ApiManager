package cn.crap.model;

import java.io.Serializable;

public class InterfaceWithBLOBs extends Interface implements Serializable {
    private String param;

    private String paramRemark;

    private String requestExam;

    private String responseParam;

    private String errorList;

    private String trueExam;

    private String falseExam;

    private String remark;

    private String errors;

    private String header;

    private static final long serialVersionUID = 1L;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param == null ? null : param.trim();
    }

    public String getParamRemark() {
        return paramRemark;
    }

    public void setParamRemark(String paramRemark) {
        this.paramRemark = paramRemark == null ? null : paramRemark.trim();
    }

    public String getRequestExam() {
        return requestExam;
    }

    public void setRequestExam(String requestExam) {
        this.requestExam = requestExam == null ? null : requestExam.trim();
    }

    public String getResponseParam() {
        return responseParam;
    }

    public void setResponseParam(String responseParam) {
        this.responseParam = responseParam == null ? null : responseParam.trim();
    }

    public String getErrorList() {
        return errorList;
    }

    public void setErrorList(String errorList) {
        this.errorList = errorList == null ? null : errorList.trim();
    }

    public String getTrueExam() {
        return trueExam;
    }

    public void setTrueExam(String trueExam) {
        this.trueExam = trueExam == null ? null : trueExam.trim();
    }

    public String getFalseExam() {
        return falseExam;
    }

    public void setFalseExam(String falseExam) {
        this.falseExam = falseExam == null ? null : falseExam.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors == null ? null : errors.trim();
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header == null ? null : header.trim();
    }
}