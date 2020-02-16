package cn.crap.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DebugDto{
	private String id;
	private String name;
	private Date createTime;
	private Byte status;
	private Long sequence;
	private String interfaceId;
	private String moduleId;
	private String method;
	private String url;
	private String params;
	private String headers;
	private String paramType;
	private Integer version;
	private String uid;
	private String uniKey;
	private String moduleUniKey;
    private String projectUniKey;

}
