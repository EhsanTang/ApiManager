package cn.crap.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import cn.crap.framework.base.BaseModel;
import cn.crap.inter.ErrorInfoService;

/**
 * @author lizhiyong
 * @date 2016-01-06
 */
@Entity
@Table(name="interface_info")
@GenericGenerator(name="Generator", strategy="cn.crap.framework.IdGenerator")
public class InterfaceInfoModel extends BaseModel{
	@Autowired
	private ErrorInfoService errorInfoService;
	/**
	 * id(主键)
	 * */
	private String id;
	/**
	 * url(api链接)
	 * */
	private String url;
	/**
	 * method( 请求方式)
	 * */
	private String method;
	/**
	 * param(参数列表)
	 * */
	private String param;
	/**
	 * requestExam(请求示例)
	 * */
	private String requestExam;
	/**
	 * responseParam(返回参数说明)
	 * */
	private String responseParam;
	/**
	 * errorList(接口错误码列表)
	 * */
	private String errorList;
	/**
	 * trueExam(正确返回示例)
	 * */
	private String trueExam;
	/**
	 * falseExam(错误返回示例)
	 * */
	private String falseExam;
	/**
	 * status(是否可用;0不可用；1可用)
	 * */
	private Integer status;
	/**
	 * moduleId(所属模块ID)
	 * */
	private String moduleId;
	/**
	 * moduleName(所属模块名称)
	 * */
	private String moduleName;
	
	private String interfaceName;
	private String updateBy;
	private String updateTime;
	
	private String remark;//备注
	private String errors;
	@Column(name="errors")
	public String getErrors() {
		return errors;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}

	@Id
	@GeneratedValue(generator="Generator")
	@Column(name="id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	@Column(name="status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name="moduleId")
	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	@Column(name="moduleName")
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
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

}