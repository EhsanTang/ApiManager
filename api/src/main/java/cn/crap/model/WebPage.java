package cn.crap.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import cn.crap.dto.SearchDto;
import cn.crap.enumeration.WebPageType;
import cn.crap.framework.SpringContextHolder;
import cn.crap.framework.base.BaseModel;
import cn.crap.inter.service.ICacheService;
import cn.crap.service.CacheService;
import cn.crap.utils.MyString;

@Entity
@Table(name="webpage")
@GenericGenerator(name="Generator", strategy="cn.crap.framework.IdGenerator")
public class WebPage extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String brief;
	private String content;
	private int click;
	private String type;
	private String moduleId;
	private String key;
	private byte canDelete;
	private String category;
	private byte canComment;
	private int commentCount;
	private String password;
	private String markdown;
	
	public WebPage(){};
	
	public WebPage(String id, String type, String name, int click, String category, String createTime, String key, String moduleId, String brief, int sequence) {
		this(id, type, name, click, category, createTime, key, moduleId);
		this.brief = brief;
		this.sequence = sequence;
	}
	
	public WebPage(String id, String type, String name, int click, String category, String createTime, String key, String moduleId) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
		this.click = click;
		this.category = category;
		this.createTime = createTime;
		this.key = key;
		this.moduleId = moduleId;
	}


	@Transient
	public SearchDto toSearchDto(){
		SearchDto dto = new SearchDto();
		dto.setId(id);
		dto.setCreateTime(createTime);
		dto.setContent(brief + content);
		dto.setModuleName(getModuleName());
		dto.setTitle(name);
		dto.setType(WebPage.class.getSimpleName());
		if(type.equals(WebPageType.ARTICLE.name()))
			dto.setUrl("#/"+getModuleId()+"/webPage/detail/ARTICLE/"+id);
		else if(type.equals(WebPageType.DICTIONARY.name()))
			dto.setUrl("#/"+getModuleId()+"/webPage/detail/DICTIONARY/"+id);
		else if(type.equals(WebPageType.PAGE.name()))
			dto.setUrl("#/"+getModuleId()+"/webPage/detail/PAGE/"+key);
		else
			dto.setUrl("");
		dto.setVersion("");
		return dto;
	}
	
	
	@Transient
	public String getTypeName(){
		if(!MyString.isEmpty(type))
			return WebPageType.valueOf(type).getName();
		return "";
	}
	@Transient
	public String getCanDeleteName(){
		if(canDelete==1)
			return "可以修改Key，可以删除";
		else
			return "系统数据，不可修改Key，不可删除";
	}
	@Transient
	public String getModuleName(){
		if(!MyString.isEmpty(moduleId)){
			ICacheService cacheService = SpringContextHolder.getBean("cacheService", CacheService.class);
			DataCenter module = cacheService.getModule(moduleId);
			if(module!=null)
				return module.getName();
		}
		return "";
	}
	
	@Column(name="category")
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	@Column(name="mkey")
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	@Column(name="canDelete")
	public byte getCanDelete() {
		return canDelete;
	}
	public void setCanDelete(byte canDelete) {
		this.canDelete = canDelete;
	}
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="brief")
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	
	@Column(name="content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name="click")
	public int getClick() {
		return click;
	}
	public void setClick(int click) {
		this.click = click;
	}
	
	@Column(name="type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name="moduleId")
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	
	@Column(name="canComment")
	public byte getCanComment() {
		return canComment;
	}
	public void setCanComment(byte canComment) {
		this.canComment = canComment;
	}
	
	@Column(name="commentCount")
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	
	@Column(name="password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name="markdown")
	public String getMarkdown() {
		return markdown;
	}
	public void setMarkdown(String markdown) {
		this.markdown = markdown;
	}
	
	
}