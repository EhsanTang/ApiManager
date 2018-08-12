package cn.crap.dto;

import cn.crap.framework.SpringContextHolder;
import cn.crap.service.tool.ProjectCache;

import java.util.Date;

public class ErrorDto {
	private String id;
	private String errorCode;
	private String errorMsg;
	private String projectId;
	private Byte status;
	private Integer sequence;

	public void setId(String id){
		this.id=id;
	}
	public String getId(){
		return id;
	}

	public void setErrorCode(String errorCode){
		this.errorCode=errorCode;
	}
	public String getErrorCode(){
		return errorCode;
	}

	public void setErrorMsg(String errorMsg){
		this.errorMsg=errorMsg;
	}
	public String getErrorMsg(){
		return errorMsg;
	}

	public void setProjectId(String projectId){
		this.projectId=projectId;
	}
	public String getProjectId(){
		return projectId;
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

	public String getProjectName(){
		ProjectCache projectCache = SpringContextHolder.getBean("projectCache", ProjectCache.class);
		return projectCache.get(projectId).getName();
	}

}
