package cn.crap.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Automatic generation by tools
 * dto: exchange data with READ
 */
@Data
public class ArticleDTO extends BaseDTO {
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
