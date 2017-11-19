package cn.crap.dto;

import java.util.Date;

/**
 * Automatic generation by tools
 * dto: exchange data with view
 */
public class DebugDto{
	private String id;
	private String name;
	private Date createTime;
	private Byte status;
	private Integer sequence;
	private String interfaceId;
	private String moduleId;
	private String method;
	private String url;
	private String params;
	private String headers;
	private String paramType;
	private Integer version;
	private String uid;

	public void setId(String id){
		this.id=id;
	}
	public String getId(){
		return id;
	}

	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}

	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}

	public void setStatus(Byte status){
		this.status=status;
	}
	public Byte getStatus(){
		return status;
	}

	public void setSequence(Integer sequence){
		this.sequence=sequence;
	}
	public Integer getSequence(){
		return sequence;
	}

	public void setInterfaceId(String interfaceId){
		this.interfaceId=interfaceId;
	}
	public String getInterfaceId(){
		return interfaceId;
	}

	public void setModuleId(String moduleId){
		this.moduleId=moduleId;
	}
	public String getModuleId(){
		return moduleId;
	}

	public void setMethod(String method){
		this.method=method;
	}
	public String getMethod(){
		return method;
	}

	public void setUrl(String url){
		this.url=url;
	}
	public String getUrl(){
		return url;
	}

	public void setParams(String params){
		this.params=params;
	}
	public String getParams(){
		return params;
	}

	public void setHeaders(String headers){
		this.headers=headers;
	}
	public String getHeaders(){
		return headers;
	}

	public void setParamType(String paramType){
		this.paramType=paramType;
	}
	public String getParamType(){
		return paramType;
	}

	public void setVersion(Integer version){
		this.version=version;
	}
	public Integer getVersion(){
		return version;
	}

	public void setUid(String uid){
		this.uid=uid;
	}
	public String getUid(){
		return uid;
	}


}
