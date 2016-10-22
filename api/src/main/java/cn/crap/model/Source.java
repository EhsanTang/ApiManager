package cn.crap.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import cn.crap.dto.ILuceneDto;
import cn.crap.dto.SearchDto;
import cn.crap.enumeration.ProjectType;
import cn.crap.framework.SpringContextHolder;
import cn.crap.framework.base.BaseModel;
import cn.crap.inter.service.tool.ICacheService;
import cn.crap.service.tool.CacheService;
import cn.crap.utils.GetTextFromFile;
import cn.crap.utils.MyString;


/**
 * @date 2016-01-06
 */
@Entity
@Table(name="source")
@GenericGenerator(name="Generator", strategy="cn.crap.framework.IdGenerator")
public class Source extends BaseModel implements Serializable,ILuceneDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;// 资源名称
	private String updateTime;
	private String moduleId;// 文件夹目录：dataCenter id
	private String remark; // 资源内容
	private String filePath;
	
	public Source(){};
	public Source(String id, String createTime, byte status, int sequence, String name, String remark, String filePath, String moduleId, String updateTime){
		this.id = id;
		this.createTime = createTime;
		this.status = status;
		this.sequence = sequence;
		this.name = name;
		this.remark = remark;
		this.filePath = filePath;
		this.moduleId = moduleId;
		this.updateTime = updateTime;
		
	}
	
	@Column(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="updateTime")
	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name="moduleId")
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	@Column(name="remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="filePath")
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	@Transient
	public String getProjectId() {
		if (!MyString.isEmpty(moduleId)) {
			ICacheService cacheService = SpringContextHolder.getBean("cacheService", CacheService.class);
			Module module = cacheService.getModule(moduleId);
			if (module != null)
				return module.getProjectId();
		}
		return "";
	}
	
	@Transient
	public String getModuleName(){
		if(!MyString.isEmpty(moduleId)){
			ICacheService cacheService = SpringContextHolder.getBean("cacheService", CacheService.class);
			Module module = cacheService.getModule(moduleId);
			if(module!=null)
				return module.getName();
		}
		return "";
	}

	@Transient
	public SearchDto toSearchDto(ICacheService cacheService){
		SearchDto dto = new SearchDto();
		dto.setId(id);
		dto.setCreateTime(createTime);
		dto.setTitle(name);
		dto.setType(Source.class.getSimpleName());
		dto.setUrl("#/"+getProjectId()+"/source/detail/"+id);
		dto.setVersion("");
		//索引内容 = 备注内容 + 文档内容
		String docContent = "";
		try {
			docContent = GetTextFromFile.getText(this.filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dto.setContent(remark + docContent);
		//如果备注为空，则提取文档内容前2500 个字
		if( MyString.isEmpty(this.remark) ){
			this.remark = docContent.length() > 2500? docContent.substring(0, 2500) +" ... \r\n..." : docContent;
		}
		// 私有项目不能建立索引
		if(cacheService.getProject(getProjectId()).getType() == ProjectType.PRIVATE.getType()){
			dto.setNeedCreateIndex(false);
		}
		return dto;
	}
	
	
	
	
}