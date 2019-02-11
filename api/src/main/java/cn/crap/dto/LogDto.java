package cn.crap.dto;

/**
 * Automatic generation by tools
 * dto: exchange data with view
 */
public class LogDto {
	private String id;
	private Byte status;
	private Long sequence;
	private String modelClass;
	private String modelName;
	private String type;
	private String updateBy;
	private String remark;
	private String content;
	private String identy;
	private String createTimeStr;

	public void setId(String id){
		this.id=id;
	}
	public String getId(){
		return id;
	}

	public void setStatus(Byte status){
		this.status=status;
	}
	public Byte getStatus(){
		return status;
	}

	public void setSequence(Long sequence){
		this.sequence=sequence;
	}
	public Long getSequence(){
		return sequence;
	}

	public void setModelClass(String modelClass){
		this.modelClass=modelClass;
	}
	public String getModelClass(){
		return modelClass;
	}

	public void setModelName(String modelName){
		this.modelName=modelName;
	}
	public String getModelName(){
		return modelName;
	}

	public void setType(String type){
		this.type=type;
	}
	public String getType(){
		return type;
	}

	public void setUpdateBy(String updateBy){
		this.updateBy=updateBy;
	}
	public String getUpdateBy(){
		return updateBy;
	}

	public void setRemark(String remark){
		this.remark=remark;
	}
	public String getRemark(){
		return remark;
	}

	public void setContent(String content){
		this.content=content;
	}
	public String getContent(){
		return content;
	}

	public void setIdenty(String identy){
		this.identy=identy;
	}
	public String getIdenty(){
		return identy;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
}
