package cn.crap.dto;

import java.util.Date;

/**
 * Automatic generation by tools
 * dto: exchange data with view
 */
public class CommentDto {
	private String id;
	private String articleId;
	private String content;
	private String userId;
	private String parentId;
	private Byte status;
	private String createTimeStr;
	private Integer sequence;
	private String reply;
	private String userName;
	private String avatarUrl;

	public void setId(String id){
		this.id=id;
	}
	public String getId(){
		return id;
	}

	public void setArticleId(String articleId){
		this.articleId=articleId;
	}
	public String getArticleId(){
		return articleId;
	}

	public void setContent(String content){
		this.content=content;
	}
	public String getContent(){
		return content;
	}

	public void setUserId(String userId){
		this.userId=userId;
	}
	public String getUserId(){
		return userId;
	}

	public void setParentId(String parentId){
		this.parentId=parentId;
	}
	public String getParentId(){
		return parentId;
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

	public void setReply(String reply){
		this.reply=reply;
	}
	public String getReply(){
		return reply;
	}


	public void setUserName(String userName){
		this.userName=userName;
	}
	public String getUserName(){
		return userName;
	}

	public void setAvatarUrl(String avatarUrl){
		this.avatarUrl=avatarUrl;
	}
	public String getAvatarUrl(){
		return avatarUrl;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
}
