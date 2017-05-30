package cn.crap.dto;

import cn.crap.model.Debug;
import cn.crap.utils.Tools;

public class DebugDto {
	private String name;
	private String interfaceId;
	private String moduleId;
	private String method;
	private String params;
	private String headers;
	private String url;
	private String paramType;
	private Integer version;// 版本号，客户端的版本比服务器低，将忽略
	private String uid;
	protected String id;
	protected String createTime;
	protected byte status;
	protected int sequence;// 排序
	
	public DebugDto(){
		
	}
	public DebugDto(Debug debug){
		this.name = debug.getName();
		this.interfaceId = debug.getInterfaceId();
		this.moduleId = Tools.unhandleId(debug.getModuleId());
		this.method = debug.getMethod();
		this.params = debug.getParams();
		this.headers = debug.getHeaders();
		this.url = debug.getUrl();
		this.paramType = debug.getParamType();
		this.version = debug.getVersion();
		this.uid = debug.getUid();
		this.id = Tools.unhandleId(debug.getId());
		this.createTime = debug.getCreateTime();
		this.status = debug.getStatus();
		this.sequence = debug.getSequence();
	}
	public Debug convertToDebug(){
		Debug debugModel = new Debug();
		debugModel.setName(this.name);
		debugModel.setInterfaceId(this.interfaceId);
		debugModel.setModuleId(this.moduleId);
		debugModel.setMethod(this.method);
		debugModel.setParams(this.params);
		debugModel.setHeaders(this.headers);
		debugModel.setUrl(this.url);
		debugModel.setParamType(this.paramType);
		debugModel.setVersion(this.version);
		debugModel.setUid(this.uid) ;
		debugModel.setId(this.id);
		debugModel.setCreateTime(this.createTime);
		debugModel.setStatus(this.status);
		debugModel.setSequence(this.sequence);
		return debugModel;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInterfaceId() {
		return interfaceId;
	}
	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getHeaders() {
		return headers;
	}
	public void setHeaders(String headers) {
		this.headers = headers;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getParamType() {
		return paramType;
	}
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	
	
}