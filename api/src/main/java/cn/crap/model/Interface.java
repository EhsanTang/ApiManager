package cn.crap.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import cn.crap.dto.SearchDto;
import cn.crap.framework.SpringContextHolder;
import cn.crap.framework.base.BaseModel;
import cn.crap.inter.service.ICacheService;
import cn.crap.service.CacheService;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;

@Entity
@Table(name="interface")
@GenericGenerator(name="Generator", strategy="cn.crap.framework.IdGenerator")
public class Interface extends BaseModel implements Serializable{
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
	private String remark;//备注
	private String errors;
	private String version;//版本号
	private String header;//请求头
	
	public Interface(){}
	
	
	public Interface(String id, String moduleId, String interfaceName, String version, String createTime, String updateBy, String updateTime, String remark, int sequence) {
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


	@Transient
	public SearchDto toSearchDto(){
		SearchDto dto = new SearchDto();
		dto.setId(id);
		dto.setCreateTime(createTime);
		dto.setContent(remark + responseParam + param);
		dto.setModuleName(getModuleName());
		dto.setTitle(interfaceName);
		dto.setType(Interface.class.getSimpleName());
		dto.setUrl("#/font/interfaceDetail/"+id);
		dto.setVersion(version);
		return dto;
		
	}
	
	
	@Transient
	@Override
	public String getLogRemark(){
		return interfaceName;
	}
	
	@Transient
	public String getModuleName(){
		if(!MyString.isEmpty(moduleId)){
			ICacheService cacheService = SpringContextHolder.getBean("cacheService", CacheService.class);
			DataCenter module = cacheService.getModule(moduleId);
			if(module!=null)
				return module.getName();
		}
		return "";
	}
	
	@Transient
	public String getModuleUrl(){
		if(!MyString.isEmpty(moduleId)){
			ICacheService cacheService = SpringContextHolder.getBean("cacheService", CacheService.class);
			DataCenter module = cacheService.getModule(moduleId);
			if(module!=null)
				return MyString.isEmpty(module.getUrl())?"":module.getUrl();
		}
		return "";
	}
	
	@Column(name="errors")
	public String getErrors() {
		return errors;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}

	@Column(name="url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name="method")
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Column(name="param")
	public String getParam() {
		if(MyString.isEmpty(param))
			return "form=[]";
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	@Column(name="requestExam")
	public String getRequestExam() {
		return requestExam;
	}

	public void setRequestExam(String requestExam) {
		this.requestExam = requestExam;
	}

	@Column(name="responseParam")
	public String getResponseParam() {
		if(MyString.isEmpty(responseParam))
			return "[]";
		return responseParam;
	}

	public void setResponseParam(String responseParam) {
		this.responseParam = responseParam;
	}

	@Column(name="errorList")
	public String getErrorList() {
		return errorList;
	}

	public void setErrorList(String errorList) {
		this.errorList = errorList;
	}

	@Column(name="trueExam")
	public String getTrueExam() {
		return trueExam;
	}

	public void setTrueExam(String trueExam) {
		this.trueExam = trueExam;
	}

	@Column(name="falseExam")
	public String getFalseExam() {
		return falseExam;
	}

	public void setFalseExam(String falseExam) {
		this.falseExam = falseExam;
	}

	@Column(name="moduleId")
	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	
	@Column(name="interfaceName")
	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="updateBy")
	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	@Column(name="updateTime")
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

	@Column(name="header")
	public String getHeader() {
		if(MyString.isEmpty(header))
			return "[]";
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
	
	// 所在项目
	@Transient
	public String getProjectId(){
		if(MyString.isEmpty(moduleId) || moduleId.equals(Const.TOP_MODULE)){
			return "";
		}
		// 该模块为项目
		if( moduleId.equals(Const.PRIVATE_MODULE) || moduleId.equals(Const.ADMIN_MODULE)){
			return moduleId;
		}
		ICacheService cacheService = SpringContextHolder.getBean("cacheService", CacheService.class);
		DataCenter module = cacheService.getModule(moduleId);
		// 最多支持100层模块查询，防止死循环
		for(int i=0; i<100; i++){
			if(module.getParentId().equals(Const.PRIVATE_MODULE) || module.getParentId().equals(Const.ADMIN_MODULE)){
				return module.getId();
			}
			
			if(MyString.isEmpty(module.getParentId()) || module.getParentId().equals(module.getId())){
				break;
			}
			module = cacheService.getModule(module.getParentId());
			if(i==90){
				System.out.println("接口projectId异常："+ moduleId);
			}
		}
		return "";
	}

}