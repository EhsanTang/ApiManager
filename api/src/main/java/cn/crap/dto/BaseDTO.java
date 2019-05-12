package cn.crap.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author nick
 */
@Data
public class BaseDTO implements Serializable {
	private String id;
	private String moduleId;
	private String projectId;
	private String projectName;
	// 数据类型 接口，文档等，不参与分词
	private String tableId;
	private String useDetailHref;
}
