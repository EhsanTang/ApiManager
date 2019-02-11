package cn.crap.dto;

/**
 * Automatic generation by tools
 * dto: exchange data with view
 */
public class SourceDto{
	private String id;
	private Long sequence;
	private Byte status;
	private String name;
	private String moduleId;
	private String remark;
	private String filePath;
	private String projectId;
	private String moduleName;
    private String createTimeStr;
    private String updateTimeStr;

	public void setId(String id){
		this.id=id;
	}
	public String getId(){
		return id;
	}

	public void setSequence(Long sequence){
		this.sequence=sequence;
	}
	public Long getSequence(){
		return sequence;
	}

	public void setStatus(Byte status){
		this.status=status;
	}
	public Byte getStatus(){
		return status;
	}

	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}

	public void setModuleId(String moduleId){
		this.moduleId=moduleId;
	}
	public String getModuleId(){
		return moduleId;
	}

	public void setRemark(String remark){
		this.remark=remark;
	}
	public String getRemark(){
		return remark;
	}

	public void setFilePath(String filePath){
		this.filePath=filePath;
	}
	public String getFilePath(){
		return filePath;
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
}
