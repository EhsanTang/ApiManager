package cn.crap.adapter;

import cn.crap.dto.ProjectDto;
import cn.crap.model.mybatis.Project;

import java.util.ArrayList;
import java.util.List;


/**
 * Automatic generation by tools
 * model adapter: convert model to dto
 * Avoid exposing sensitive data and modifying data that is not allowed to be modified
 */
public class ProjectAdapter {
    public static ProjectDto getDto(Project model){
        if (model == null){
            return null;
        }

        ProjectDto dto = new ProjectDto();
        dto.setId(model.getId());
		dto.setName(model.getName());
		dto.setCreateTime(model.getCreateTime());
		dto.setStatus(model.getStatus());
		dto.setSequence(model.getSequence());
		dto.setRemark(model.getRemark());
		dto.setUserId(model.getUserId());
		dto.setType(model.getType());
		dto.setPassword(model.getPassword());
		dto.setCover(model.getCover());
		dto.setLuceneSearch(model.getLuceneSearch());
		
        return dto;
    }

    public static Project getModel(ProjectDto dto){
        if (dto == null){
            return null;
        }
        Project model = new Project();
        model.setId(dto.getId());
		model.setName(dto.getName());
		model.setCreateTime(dto.getCreateTime());
		model.setStatus(dto.getStatus());
		model.setSequence(dto.getSequence());
		model.setRemark(dto.getRemark());
		model.setUserId(dto.getUserId());
		model.setType(dto.getType());
		model.setCover(dto.getCover());
		model.setLuceneSearch(dto.getLuceneSearch());
		
        return model;
    }

    public static List<ProjectDto> getDto(List<Project> models){
        if (models == null){
            return new ArrayList<>();
        }
        List<ProjectDto> dtos = new ArrayList<>();
        for (Project model : models){
            dtos.add(getDto(model));
        }
        return dtos;
    }
}
