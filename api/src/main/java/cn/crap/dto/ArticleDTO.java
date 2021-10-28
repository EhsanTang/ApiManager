package cn.crap.dto;

import cn.crap.model.ArticleWithBLOBs;
import lombok.Data;

/**
 */
@Data
public class ArticleDTO extends ArticleWithBLOBs {
	private String id;
	private String moduleId;
	private String projectId;
	private String projectName;
	private String name;
	private String brief;
	private String content;
	private Integer click;
	private String type;
	private String typeName;
	private Byte status;
	private String statusName;
	private String moduleName;
	private String mkey;
	private Byte canDelete;
	private String category;
	private Byte canComment;
	private String canCommentName;
	private Integer commentCount;
	private Long sequence;
	private String markdown;
	private String createTimeStr;
	private String attributes;
	/**
	 * 是否用markdown
	 */
	private Boolean useMarkdown;

}
