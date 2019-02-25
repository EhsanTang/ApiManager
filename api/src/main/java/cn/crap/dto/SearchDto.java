package cn.crap.dto;

import cn.crap.enu.TableId;

import java.io.Serializable;
import java.util.Date;

public class SearchDto implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;// 主键，不参与分词搜索
	private String title; // 标题，参与分词
	private String type;// 接口，文档等，不参与分词
	private String content;// 参与搜索的类容：简介、备注、参数等的组合 或 搜索到的结果（高亮显示）
	private boolean open = true; // 是否开放搜索，false表示只能登录才能搜索
	private String projectId; // 不参与分词
	private String moduleId; // 不参与分词
	private String custom; // 接口url，文件url等，不需要分词，需要建立索引，各个类型自定义数据
    private Date createTime;// 时间，不参与分词搜索

	/**
	 * 不存储至索引文件中
     * href: 前端页面地址
     * userHref: 用户登陆后的后端地址
	 */
	private String href;
	private String createTimeStr;
	private String userHref;

    public SearchDto(){}
	public SearchDto(String projectId, String moduleId, String id, String title, TableId type,
                     String content, String custom, boolean open, Date createTime){
			this.projectId = projectId;
			this.moduleId = moduleId;
			this.id = id;
			this.title = title;
			this.type = type.getTableId();
			this.content = content;
			this.createTime = createTime;
			this.custom = custom;
			this.open = open;
	}
	public SearchDto(String id){
		this.id = id;
	}
	
	public String getId() {
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
	public String getContent() {
		if(content == null)
			return "";
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		if(createTime == null)
			return null;
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getModuleId() {
		if (moduleId == null){
			return "";
		}
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getCustom() {
		if (custom == null){
			return "";
		}
		return custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getUserHref() {
        return userHref;
    }

    public void setUserHref(String userHref) {
        this.userHref = userHref;
    }
}
