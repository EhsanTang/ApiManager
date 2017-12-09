package cn.crap.dto;

import cn.crap.enumeration.ProjectType;
import cn.crap.model.mybatis.Source;
import cn.crap.service.ICacheService;
import cn.crap.utils.GetTextFromFile;
import cn.crap.utils.MyString;

import java.util.Date;

/**
 * Automatic generation by tools
 * dto: exchange data with view
 */
public class SourceDto implements ILuceneDto {
	private String id;
	private Date createTime;
	private Integer sequence;
	private Byte status;
	private String name;
	private Date updateTime;
	private String moduleId;
	private String remark;
	private String filePath;
	private String projectId;

	public void setId(String id){
		this.id=id;
	}
	public String getId(){
		return id;
	}

	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}

	public void setSequence(Integer sequence){
		this.sequence=sequence;
	}
	public Integer getSequence(){
		return sequence;
	}

	public void setStatus(Byte status){
		this.status=status;
	}
	public Byte getStatus(){
		return status;
	}

	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}

	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
	}
	public Date getUpdateTime(){
		return updateTime;
	}

	public void setModuleId(String moduleId){
		this.moduleId=moduleId;
	}
	public String getModuleId(){
		return moduleId;
	}

	public void setRemark(String remark){
		this.remark=remark;
	}
	public String getRemark(){
		return remark;
	}

	public void setFilePath(String filePath){
		this.filePath=filePath;
	}
	public String getFilePath(){
		return filePath;
	}

	public void setProjectId(String projectId){
		this.projectId=projectId;
	}
	public String getProjectId(){
		return projectId;
	}


	@Override
	public SearchDto toSearchDto(ICacheService cacheService){
		SearchDto dto = new SearchDto();
		dto.setId(id);
		dto.setCreateTime(createTime);
		dto.setTitle(name);
		dto.setType(Source.class.getSimpleName());
		dto.setUrl("#/"+getProjectId()+"/source/detail/"+id);
		dto.setVersion("");
		dto.setProjectId(getProjectId());
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
