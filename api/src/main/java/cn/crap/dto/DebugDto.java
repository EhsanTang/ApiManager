package cn.crap.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DebugDto{
	// 废弃，接口uniKey
	private String id;
	private String name;
	private Date createTime;
	private Byte status;
	private Long sequence;
	private String interfaceId;
	// 废弃，模块uniKey
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

	private String webModuleId;
	private String webProjectId;
	private String webId;

	public String getUniKey(){
		return uniKey == null ? id : uniKey;
	}
}
