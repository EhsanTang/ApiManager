package cn.crap.adapter;

import cn.crap.dto.ModuleDto;
import cn.crap.model.mybatis.Module;
import cn.crap.model.mybatis.Project;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Automatic generation by tools
 * model adapter: convert model to dto
 * Avoid exposing sensitive data and modifying data that is not allowed to be modified
 */
public class ModuleAdapter {
    public static ModuleDto getDto(Module model, Project project){
        if (model == null){
            return null;
        }

        ModuleDto dto = new ModuleDto();
        dto.setId(model.getId());
		dto.setName(model.getName());
		dto.setStatus(model.getStatus());
		dto.setSequence(model.getSequence());
		dto.setUrl(model.getUrl());
		dto.setCanDelete(model.getCanDelete());
		dto.setRemark(model.getRemark());
		dto.setUserId(model.getUserId());
		dto.setProjectId(model.getProjectId());
		dto.setTemplateId(model.getTemplateId());
		dto.setVersion(model.getVersion());
		dto.setCategory(model.getCategory());
		if (project != null) {
            dto.setProjectName(project.getName());
        }
		
        return dto;
    }

    public static Module getModel(ModuleDto dto){
        if (dto == null){
            return null;
        }
        Module model = new Module();
        model.setId(dto.getId());
		model.setName(dto.getName());
		model.setSequence(dto.getSequence());
		model.setUrl(dto.getUrl());
		model.setRemark(dto.getRemark());
		model.setCategory(dto.getCategory());
		
        return model;
    }

    public static List<ModuleDto> getDto(List<Module> models){
        if (CollectionUtils.isEmpty(models)){
            return new ArrayList<>();
        }
        List<ModuleDto> dtos = new ArrayList<>();
        for (Module model : models){
            dtos.add(getDto(model, null));
        }
        return dtos;
    }
}
