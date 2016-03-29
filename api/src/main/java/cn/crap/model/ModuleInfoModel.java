package cn.crap.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import cn.crap.framework.base.BaseModel;


/**
 * @author lizhiyong
 * @date 2016-01-06
 */
@Entity
@Table(name="module_info")
@GenericGenerator(name="Generator", strategy="cn.crap.framework.IdGenerator")
public class ModuleInfoModel extends BaseModel{
	/**
	 * moduleId(所属模块ID)
	 * */
	private String moduleId;
	/**
	 * moduleName(所属模块名称)
	 * */
	private String moduleName;
	/**
	 * parentId(父级节点ID)
	 * */
	private String parentId;


	@Id
	@GeneratedValue(generator="Generator")
	@Column(name="moduleId")
	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	@Column(name="moduleName")
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@Column(name="parentId")
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}


}