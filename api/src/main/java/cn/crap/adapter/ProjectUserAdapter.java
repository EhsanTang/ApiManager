package cn.crap.adapter;

import cn.crap.dto.ProjectUserDto;
import cn.crap.model.mybatis.ProjectUser;
import java.util.ArrayList;
import java.util.List;



/**
 * Automatic generation by tools
 * model adapter: convert model to dto
 * Avoid exposing sensitive data and modifying data that is not allowed to be modified
 */
public class ProjectUserAdapter {
    public static ProjectUserDto getDto(ProjectUser model){
        if (model == null){
            return null;
        }

        ProjectUserDto dto = new ProjectUserDto();
        dto.setId(model.getId());
		dto.setStatus(model.getStatus());
		dto.setSequence(model.getSequence());
		dto.setCreateTime(model.getCreateTime());
		dto.setProjectId(model.getProjectId());
		dto.setUserId(model.getUserId());
		dto.setAddModule(model.getAddModule());
		dto.setDelModule(model.getDelModule());
		dto.setModModule(model.getModModule());
		dto.setAddInter(model.getAddInter());
		dto.setDelInter(model.getDelInter());
		dto.setModInter(model.getModInter());
		dto.setAddArticle(model.getAddArticle());
		dto.setDelArticle(model.getDelArticle());
		dto.setModArticle(model.getModArticle());
		dto.setAddSource(model.getAddSource());
		dto.setDelSource(model.getDelSource());
		dto.setModSource(model.getModSource());
		dto.setAddDict(model.getAddDict());
		dto.setDelDict(model.getDelDict());
		dto.setModDict(model.getModDict());
		dto.setUserEmail(model.getUserEmail());
		dto.setUserName(model.getUserName());
		dto.setAddError(model.getAddError());
		dto.setDelError(model.getDelError());
		dto.setModError(model.getModError());
		
        return dto;
    }

    public static ProjectUser getModel(ProjectUserDto dto){
        if (dto == null){
            return null;
        }
        ProjectUser model = new ProjectUser();
        model.setId(dto.getId());
		model.setStatus(dto.getStatus());
		model.setSequence(dto.getSequence());
		model.setCreateTime(dto.getCreateTime());
		model.setProjectId(dto.getProjectId());
		model.setUserId(dto.getUserId());
		model.setAddModule(dto.getAddModule());
		model.setDelModule(dto.getDelModule());
		model.setModModule(dto.getModModule());
		model.setAddInter(dto.getAddInter());
		model.setDelInter(dto.getDelInter());
		model.setModInter(dto.getModInter());
		model.setAddArticle(dto.getAddArticle());
		model.setDelArticle(dto.getDelArticle());
		model.setModArticle(dto.getModArticle());
		model.setAddSource(dto.getAddSource());
		model.setDelSource(dto.getDelSource());
		model.setModSource(dto.getModSource());
		model.setAddDict(dto.getAddDict());
		model.setDelDict(dto.getDelDict());
		model.setModDict(dto.getModDict());
		model.setUserEmail(dto.getUserEmail());
		model.setUserName(dto.getUserName());
		model.setAddError(dto.getAddError());
		model.setDelError(dto.getDelError());
		model.setModError(dto.getModError());
		
        return model;
    }

    public static List<ProjectUserDto> getDto(List<ProjectUser> models){
        if (models == null){
            return new ArrayList<>();
        }
        List<ProjectUserDto> dtos = new ArrayList<>();
        for (ProjectUser model : models){
            dtos.add(getDto(model));
        }
        return dtos;
    }
}
