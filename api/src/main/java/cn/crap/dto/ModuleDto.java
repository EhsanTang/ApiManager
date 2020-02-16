package cn.crap.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ModuleDTO {
	private String id;
	private String name;
	private Byte status;
	private Long sequence;
	private String url;
	private Byte canDelete;
	private String remark;
	private String userId;
	private String projectId;
	private String templateId;
	private String templateName;
	private Integer versionNum;
	private String category;
	private String projectName;
	private String createTimeStr;
	private Boolean hasStaticize;
    private String uniKey;
}
