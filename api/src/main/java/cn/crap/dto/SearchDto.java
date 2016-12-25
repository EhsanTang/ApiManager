package cn.crap.dto;

import java.io.Serializable;

public class SearchDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;// 主键，不参与分词搜索
	private String title;
	private String type;// 接口，文章等，不参与分词
	private String url;// 相对路径：#/font/interfaceDetail，不参与分词搜索
	private String content;// 参与搜索的类容：简介、备注、参数等的组合 或 搜索到的结果（高亮显示）
	private String version;// 不参与分词搜索
	private String moduleName;
	private String createTime;// 时间，不参与分词搜索
	private boolean needCreateIndex = true; // 是否需要建立索引 
	private String href;//接口、文章的地址，不需要分词，需要建立索引
	
	public SearchDto(){};
	public SearchDto(String id, String title, String type, String url, String content, String version, String moduleName, String createTime){
			this.id = id;
			this.title = title;
			this.type = type;
			this.url = url;
			this.content = content;
			this.version = version;
			this.moduleName = moduleName;
			this.createTime = createTime;
	}
	public SearchDto(String id){
		this.id = id;
	}
	
	public String getId() {
		if(id == null)
			return "";
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		if(title == null)
			return "";
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		if(type == null)
			return "";
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		if(url == null)
			return "";
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		if(content == null)
			return "";
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getVersion() {
		if(version == null)
			return "";
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getModuleName() {
		if(moduleName == null)
			return "";
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getCreateTime() {
		if(createTime == null)
			return "";
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public boolean isNeedCreateIndex() {
		return needCreateIndex;
	}
	public void setNeedCreateIndex(boolean needCreateIndex) {
		this.needCreateIndex = needCreateIndex;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	
	
	
}
