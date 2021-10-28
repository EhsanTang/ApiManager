package cn.crap.model;

import lombok.Data;

/**
 * 项目元数据
 * 用来存储环境变量等
 * @author Ehsan
 */
@Data
public class ProjectMetaPO extends BasePO {
	private Byte type;
	private String value;
	private String moduleId;
	private String projectId;
	private String name;
	private Byte status;
}
