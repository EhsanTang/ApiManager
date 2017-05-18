package cn.crap.dto;

import java.util.List;

import cn.crap.model.Debug;

public class DebugInterfaceParamDto {
	private List<Debug> debugs;
	private String moduleId;
	private String moduleName;
	private Integer version;
	private Byte status;
	
	public List<Debug> getDebugs() {
		return debugs;
	}
	public void setDebugs(List<Debug> debugs) {
		this.debugs = debugs;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	
}
