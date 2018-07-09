package cn.crap.dto;

import java.util.Date;

/**
 * Automatic generation by tools
 * dto: exchange data with view
 */
public class InterfaceDto{
	private String id;
	private String url;
	private String method;
	private String param;
	private String paramRemark;
	private String requestExam;
	private String responseParam;
	private String errorList;
	private String trueExam;
	private String falseExam;
	private Byte status;
	private String statusName;
	private String moduleId;
	private String moduleName;
	private String moduleUrl;
	private String interfaceName;
	private String remark;
	private String errors;
	private String updateBy;
    private String createTimeStr;
    private String updateTimeStr;
	private String version;
	private Integer sequence;
	private String header;
	private String fullUrl;
	private Integer monitorType;
	private String monitorText;
	private String monitorEmails;
	private Boolean isTemplate;
	private String projectId;
	private String projectName;
	private String remarkNoHtml;
	private String contentType;
    private String contentTypeName;

	public void setId(String id){
		this.id=id;
	}
	public String getId(){
		return id;
	}

	public void setUrl(String url){
		this.url=url;
	}
	public String getUrl(){
		return url;
	}

	public void setMethod(String method){
		this.method=method;
	}
	public String getMethod(){
		return method;
	}

	public void setParam(String param){
		this.param=param;
	}
	public String getParam(){
		return param;
	}

	public void setParamRemark(String paramRemark){
		this.paramRemark=paramRemark;
	}
	public String getParamRemark(){
		return paramRemark;
	}

	public void setRequestExam(String requestExam){
		this.requestExam=requestExam;
	}
	public String getRequestExam(){
		return requestExam;
	}

	public void setResponseParam(String responseParam){
		this.responseParam=responseParam;
	}
	public String getResponseParam(){
		return responseParam;
	}

	public void setErrorList(String errorList){
		this.errorList=errorList;
	}
	public String getErrorList(){
		return errorList;
	}

	public void setTrueExam(String trueExam){
		this.trueExam=trueExam;
	}
	public String getTrueExam(){
		return trueExam;
	}

	public void setFalseExam(String falseExam){
		this.falseExam=falseExam;
	}
	public String getFalseExam(){
		return falseExam;
	}

	public void setStatus(Byte status){
		this.status=status;
	}
	public Byte getStatus(){
		return status;
	}

	public void setModuleId(String moduleId){
		this.moduleId=moduleId;
	}
	public String getModuleId(){
		return moduleId;
	}

	public void setInterfaceName(String interfaceName){
		this.interfaceName=interfaceName;
	}
	public String getInterfaceName(){
		return interfaceName;
	}

	public void setRemark(String remark){
		this.remark=remark;
	}
	public String getRemark(){
		return remark;
	}

	public void setErrors(String errors){
		this.errors=errors;
	}
	public String getErrors(){
		return errors;
	}

	public void setUpdateBy(String updateBy){
		this.updateBy=updateBy;
	}
	public String getUpdateBy(){
		return updateBy;
	}

	public void setVersion(String version){
		this.version=version;
	}
	public String getVersion(){
		return version;
	}

	public void setSequence(Integer sequence){
		this.sequence=sequence;
	}
	public Integer getSequence(){
		return sequence;
	}

	public void setHeader(String header){
		this.header=header;
	}
	public String getHeader(){
		return header;
	}

	public void setFullUrl(String fullUrl){
		this.fullUrl=fullUrl;
	}
	public String getFullUrl(){
		return fullUrl;
	}

	public void setMonitorType(Integer monitorType){
		this.monitorType=monitorType;
	}
	public Integer getMonitorType(){
		return monitorType;
	}

	public void setMonitorText(String monitorText){
		this.monitorText=monitorText;
	}
	public String getMonitorText(){
		return monitorText;
	}

	public void setMonitorEmails(String monitorEmails){
		this.monitorEmails=monitorEmails;
	}
	public String getMonitorEmails(){
		return monitorEmails;
	}

	public void setIsTemplate(Boolean isTemplate){
		this.isTemplate=isTemplate;
	}
	public Boolean getIsTemplate(){
		return isTemplate;
	}

	public void setProjectId(String projectId){
		this.projectId=projectId;
	}
	public String getProjectId(){
		return projectId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleUrl() {
		return moduleUrl;
	}

	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}

	public Boolean getTemplate() {
		return isTemplate;
	}

	public void setTemplate(Boolean template) {
		isTemplate = template;
	}

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
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

	public String getRemarkNoHtml() {
		return remarkNoHtml;
	}

	public void setRemarkNoHtml(String remarkNoHtml) {
		this.remarkNoHtml = remarkNoHtml;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

    public String getContentTypeName() {
        return contentTypeName;
    }

    public void setContentTypeName(String contentTypeName) {
        this.contentTypeName = contentTypeName;
    }

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
}
