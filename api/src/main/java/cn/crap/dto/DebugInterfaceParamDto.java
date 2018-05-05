package cn.crap.dto;

import java.util.List;

public class DebugInterfaceParamDto {
	private List<DebugDto> debugs;
	private String moduleId;
	private String moduleName;
	private Integer version;
	private Byte status;
	
	public List<DebugDto> getDebugs() {
		return debugs;
	}
	public void setDebugs(List<DebugDto> debugs) {
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
