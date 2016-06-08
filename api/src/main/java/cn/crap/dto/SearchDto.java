package cn.crap.dto;

public class SearchDto {
	private String id;// 主键，不参与分词搜索
	private String title;
	private String type;// 接口，文章等，不参与分词
	private String url;// 相对路径：web.do#/webInterfaceDetail，不参与分词搜索
	private String content;// 参与搜索的类容：简介、备注、参数等的组合 或 搜索到的结果（高亮显示）
	private String version;// 不参与分词搜索
	private String modelName;
	private String createTime;// 时间，不参与分词搜索
	
	public SearchDto(){};
	public SearchDto(String id, String title, String type, String url, String content, String version, String modelName, String createTime){
		this.id = id;
		this.title = title;
		this.type = type;
		this.url = url;
		this.content = content;
		this.version = version;
		this.modelName = modelName;
		this.createTime = createTime;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
}
