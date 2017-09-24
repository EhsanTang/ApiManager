package cn.crap.adapter;

import cn.crap.dto.ModuleDto;
import cn.crap.model.mybatis.Module;

import java.util.ArrayList;
import java.util.List;


/**
 * Automatic generation by tools
 * model adapter: convert model to dto
 * Avoid exposing sensitive data and modifying data that is not allowed to be modified
 */
public class ModuleAdapter {
    public static ModuleDto getDto(Module model){
        if (model == null){
            return null;
        }

        ModuleDto dto = new ModuleDto();
        dto.setId(model.getId());
		dto.setName(model.getName());
		dto.setCreateTime(model.getCreateTime());
		dto.setStatus(model.getStatus());
		dto.setSequence(model.getSequence());
		dto.setUrl(model.getUrl());
		dto.setCanDelete(model.getCanDelete());
		dto.setRemark(model.getRemark());
		dto.setUserId(model.getUserId());
		dto.setProjectId(model.getProjectId());
		dto.setTemplateId(model.getTemplateId());
		dto.setVersion(model.getVersion());
		
        return dto;
    }

    public static Module getModel(ModuleDto dto){
        if (dto == null){
            return null;
        }
        Module model = new Module();
        model.setId(dto.getId());
		model.setName(dto.getName());
		model.setCreateTime(dto.getCreateTime());
		model.setStatus(dto.getStatus());
		model.setSequence(dto.getSequence());
		model.setUrl(dto.getUrl());
		model.setCanDelete(dto.getCanDelete());
		model.setRemark(dto.getRemark());
		model.setUserId(dto.getUserId());
		model.setProjectId(dto.getProjectId());
		model.setTemplateId(dto.getTemplateId());
		model.setVersion(dto.getVersion());
		
        return model;
    }

    public static List<ModuleDto> getDto(List<Module> models){
        if (models == null){
            return new ArrayList<>();
        }
        List<ModuleDto> dtos = new ArrayList<>();
        for (Module model : models){
            dtos.add(getDto(model));
        }
        return dtos;
    }
}
