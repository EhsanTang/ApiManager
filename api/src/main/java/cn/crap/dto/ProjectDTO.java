package cn.crap.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDTO {
	private String id;
	private String name;
	private Byte status;
	private Long sequence;
	private String remark;
	private String userId;
	private String userName;
	private Byte type;
	private String typeName;
	private String password;
	private String cover;
	private Byte luceneSearch;
	private String luceneSearchName;
	private String statusName;
	private String createTimeStr;
	private String uniKey;
	/**
	 * 访问方式
	 */
	private String visitWay;
    /**
     * 邀请链接
     */
    private String inviteUrl;
    private String projectPermission;
}
