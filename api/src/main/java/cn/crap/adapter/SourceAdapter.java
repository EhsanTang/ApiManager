package cn.crap.adapter;

import cn.crap.dto.SourceDto;
import cn.crap.model.mybatis.Source;

import java.util.ArrayList;
import java.util.List;


/**
 * Automatic generation by tools
 * model adapter: convert model to dto
 * Avoid exposing sensitive data and modifying data that is not allowed to be modified
 */
public class SourceAdapter {
    public static SourceDto getDto(Source model){
        if (model == null){
            return null;
        }

        SourceDto dto = new SourceDto();
        dto.setId(model.getId());
		dto.setCreateTime(model.getCreateTime());
		dto.setSequence(model.getSequence());
		dto.setStatus(model.getStatus());
		dto.setName(model.getName());
		dto.setUpdateTime(model.getUpdateTime());
		dto.setModuleId(model.getModuleId());
		dto.setRemark(model.getRemark());
		dto.setFilePath(model.getFilePath());
		dto.setProjectId(model.getProjectId());
		
        return dto;
    }

    public static Source getModel(SourceDto dto){
        if (dto == null){
            return null;
        }
        Source model = new Source();
        model.setId(dto.getId());
		model.setCreateTime(dto.getCreateTime());
		model.setSequence(dto.getSequence());
		model.setStatus(dto.getStatus());
		model.setName(dto.getName());
		model.setUpdateTime(dto.getUpdateTime());
		model.setModuleId(dto.getModuleId());
		model.setRemark(dto.getRemark());
		model.setFilePath(dto.getFilePath());
		model.setProjectId(dto.getProjectId());
		
        return model;
    }

    public static List<SourceDto> getDto(List<Source> models){
        if (models == null){
            return new ArrayList<>();
        }
        List<SourceDto> dtos = new ArrayList<>();
        for (Source model : models){
            dtos.add(getDto(model));
        }
        return dtos;
    }
}
