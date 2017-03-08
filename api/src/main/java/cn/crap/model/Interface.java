package cn.crap.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import cn.crap.dto.ILuceneDto;
import cn.crap.dto.SearchDto;
import cn.crap.enumeration.MonitorType;
import cn.crap.enumeration.ProjectType;
import cn.crap.framework.SpringContextHolder;
import cn.crap.framework.base.BaseModel;
import cn.crap.inter.service.tool.ICacheService;
import cn.crap.service.tool.CacheService;
import cn.crap.utils.MyString;

@Entity
@Table(name = "interface")
@GenericGenerator(name = "Generator", strategy = "cn.crap.framework.IdGenerator")
public class Interface extends BaseModel implements Serializable,ILuceneDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;
	private String method;
	private String param;
	private String requestExam;
	private String responseParam;
	private String errorList;
	private String trueExam;
	private String falseExam;
	private String moduleId;
	private String interfaceName;
	private String updateBy;
	private String updateTime;
	private String remark;// 备注
	private String errors;
	private String version;// 版本号
	private String header;// 请求头
	private String fullUrl;// 完整的url = moduleUrl + url
	private int monitorType;
	private String monitorText;
	private String monitorEmails;
	private String paramRemark; // 参数说明
	private boolean template; // 是否是模板
	public Interface() {
	}

	public Interface(String id, String moduleId, String interfaceName, String version, String createTime,
			String updateBy, String updateTime, String remark, int sequence) {
		super();
		this.id = id;
		this.moduleId = moduleId;
		this.interfaceName = interfaceName;
		this.version = version;
		this.createTime = createTime;
		this.updateBy = updateBy;
		this.updateTime = updateTime;
		this.remark = remark;
		this.sequence = sequence;
	}
	public Interface(String id, String moduleId, String interfaceName, String version, String createTime,
			String updateBy, String updateTime, String remark, int sequence, boolean template) {
		this(id, moduleId, interfaceName, version, createTime, updateBy, updateTime, remark, sequence);
		this.template = template;
	}
    // 以get开头的方法在jsaon返回时会调用
	@Transient
	public SearchDto toSearchDto(ICacheService cacheService) {
		SearchDto dto = new SearchDto();
		dto.setId(id);
		dto.setCreateTime(createTime);
		dto.setContent(remark + responseParam + param);
		dto.setModuleName(getModuleName());
		dto.setTitle(interfaceName);
		dto.setType(Interface.class.getSimpleName());
		dto.setUrl("#/"+getProjectId()+"/front/interfaceDetail/" + id);
		dto.setVersion(version);
		dto.setHref(getFullUrl());
		dto.setProjectId(getProjectId());
		// 私有项目不能建立索引
		if(cacheService.getProject(getProjectId()).getType() == ProjectType.PRIVATE.getType()){
			dto.setNeedCreateIndex(false);
		}
		return dto;

	}

	@Transient
	@Override
	public String getLogRemark() {
		return interfaceName;
	}

	@Transient
	public String getModuleName() {
		ICacheService cacheService = SpringContextHolder.getBean("cacheService", CacheService.class);
		Module module = cacheService.getModule(moduleId);
		return MyString.isEmpty(module.getName()) ? "" : module.getName();
	}

	@Transient
	public String getModuleUrl() {
		ICacheService cacheService = SpringContextHolder.getBean("cacheService", CacheService.class);
		Module module = cacheService.getModule(moduleId);
		return MyString.isEmpty(module.getUrl()) ? "" : module.getUrl();
	}

	@Column(name = "errors")
	public String getErrors() {
		return errors;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}

	@Column(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "method")
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Column(name = "param")
	public String getParam() {
		if (MyString.isEmpty(param))
			return "form=[]";
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
	
	@Column(name = "paramRemark")
	public String getParamRemark() {
		if (MyString.isEmpty(paramRemark))
			return "[]";
		return paramRemark;
	}

	public void setParamRemark(String paramRemark) {
		this.paramRemark = paramRemark;
	}

	@Column(name = "requestExam")
	public String getRequestExam() {
		return requestExam;
	}

	public void setRequestExam(String requestExam) {
		this.requestExam = requestExam;
	}

	@Column(name = "responseParam")
	public String getResponseParam() {
		if (MyString.isEmpty(responseParam))
			return "[]";
		return responseParam;
	}

	public void setResponseParam(String responseParam) {
		this.responseParam = responseParam;
	}

	@Column(name = "errorList")
	public String getErrorList() {
		return errorList;
	}

	public void setErrorList(String errorList) {
		this.errorList = errorList;
	}

	@Column(name = "trueExam")
	public String getTrueExam() {
		return trueExam;
	}

	public void setTrueExam(String trueExam) {
		this.trueExam = trueExam;
	}

	@Column(name = "falseExam")
	public String getFalseExam() {
		return falseExam;
	}

	public void setFalseExam(String falseExam) {
		this.falseExam = falseExam;
	}

	@Column(name = "moduleId")
	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	@Column(name = "interfaceName")
	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "updateBy")
	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@Column(name = "updateTime")
	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "header")
	public String getHeader() {
		if (MyString.isEmpty(header))
			return "[]";
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	@Column(name = "fullUrl")
	public String getFullUrl() {
		return fullUrl;
	}

	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}
	
	@Column(name = "monitorType")
	public int getMonitorType() {
		return monitorType;
	}

	public void setMonitorType(int monitorType) {
		this.monitorType = monitorType;
	}

	@Column(name = "monitorText")
	public String getMonitorText() {
		return monitorText;
	}

	public void setMonitorText(String monitorText) {
		this.monitorText = monitorText;
	}

	@Column(name = "monitorEmails")
	public String getMonitorEmails() {
		if(monitorEmails == null)
			return "";
		return monitorEmails;
	}

	public void setMonitorEmails(String monitorEmails) {
		this.monitorEmails = monitorEmails;
	}

	
	@Column(name = "isTemplate")
	public boolean isTemplate() {
		return template;
	}

	public void setTemplate(boolean template) {
		this.template = template;
	}

	@Transient
	public String getProjectId() {
		if (!MyString.isEmpty(moduleId)) {
			ICacheService cacheService = SpringContextHolder.getBean("cacheService", CacheService.class);
			Module module = cacheService.getModule(moduleId);
			if (module != null)
				return module.getProjectId();
		}
		return "";
	}
	@Transient
	public String getMonitorTypeName(){
		return MonitorType.getName(monitorType);
	}
}