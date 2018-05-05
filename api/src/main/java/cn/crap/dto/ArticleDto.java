package cn.crap.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Automatic generation by tools
 * dto: exchange data with VIEW
 */
public class ArticleDto implements Serializable {
	private String id;
	private String name;
	private String brief;
	private String content;
	private Integer click;
	private String type;
	private String typeName;
	private Byte status;
	private String statusName;
	private String moduleId;
	private String moduleName;
	private String mkey;
	private Byte canDelete;
	private String category;
	private Byte canComment;
	private Integer commentCount;
	private Integer sequence;
	private String markdown;
	private String projectId;
	private String createTimeStr;

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

	public void setBrief(String brief){
		this.brief=brief;
	}
	public String getBrief(){
		return brief;
	}

	public void setContent(String content){
		this.content=content;
	}
	public String getContent(){
		return content;
	}

	public void setClick(Integer click){
		this.click=click;
	}
	public Integer getClick(){
		return click;
	}

	public void setType(String type){
		this.type=type;
	}
	public String getType(){
		return type;
	}

	public void setStatus(Byte status){
		this.status=status;
	}
	public Byte getStatus(){
		return status;
	}

	public void setModuleId(String moduleId){
		this.moduleId=moduleId;
	}
	public String getModuleId(){
		return moduleId;
	}

	public void setMkey(String mkey){
		this.mkey=mkey;
	}
	public String getMkey(){
		return mkey;
	}

	public void setCanDelete(Byte canDelete){
		this.canDelete=canDelete;
	}
	public Byte getCanDelete(){
		return canDelete;
	}

	public void setCategory(String category){
		this.category=category;
	}
	public String getCategory(){
		return category;
	}

	public void setCanComment(Byte canComment){
		this.canComment=canComment;
	}
	public Byte getCanComment(){
		return canComment;
	}

	public void setCommentCount(Integer commentCount){
		this.commentCount=commentCount;
	}
	public Integer getCommentCount(){
		return commentCount;
	}

	public void setSequence(Integer sequence){
		this.sequence=sequence;
	}
	public Integer getSequence(){
		return sequence;
	}

	public void setMarkdown(String markdown){
		this.markdown=markdown;
	}
	public String getMarkdown(){
		return markdown;
	}

	public void setProjectId(String projectId){
		this.projectId=projectId;
	}
	public String getProjectId(){
		return projectId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
}
