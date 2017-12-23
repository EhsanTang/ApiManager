package cn.crap.dto;

import java.util.Date;

/**
 * Automatic generation by tools
 * dto: exchange data with view
 */
public class ProjectDto {
	private String id;
	private String name;
	private Date createTime;
	private Byte status;
	private Integer sequence;
	private String remark;
	private String userId;
	private Byte type;
	private String typeName;
	private String password;
	private String cover;
	private Byte luceneSearch;

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

	public void setRemark(String remark){
		this.remark=remark;
	}
	public String getRemark(){
		return remark;
	}

	public void setUserId(String userId){
		this.userId=userId;
	}
	public String getUserId(){
		return userId;
	}

	public void setType(Byte type){
		this.type=type;
	}
	public Byte getType(){
		return type;
	}

	public void setPassword(String password){
		this.password=password;
	}
	public String getPassword(){
		return password;
	}

	public void setCover(String cover){
		this.cover=cover;
	}
	public String getCover(){
		return cover;
	}

	public void setLuceneSearch(Byte luceneSearch){
		this.luceneSearch=luceneSearch;
	}
	public Byte getLuceneSearch(){
		return luceneSearch;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
