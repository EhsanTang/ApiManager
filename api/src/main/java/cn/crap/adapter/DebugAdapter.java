package cn.crap.adapter;

import cn.crap.dto.DebugDto;
import cn.crap.model.mybatis.Debug;
import cn.crap.utils.Tools;

import java.util.ArrayList;
import java.util.List;



/**
 * Automatic generation by tools
 * model adapter: convert model to dto
 * Avoid exposing sensitive data and modifying data that is not allowed to be modified
 */
public class DebugAdapter {
    public static DebugDto getDto(Debug model){
        if (model == null){
            return null;
        }

        DebugDto dto = new DebugDto();
        dto.setId(Tools.unhandleId(model.getId()));
		dto.setName(model.getName());
		dto.setCreateTime(model.getCreateTime());
		dto.setStatus(model.getStatus());
		dto.setSequence(model.getSequence());
		dto.setInterfaceId(model.getInterfaceId());
		dto.setModuleId(Tools.unhandleId(model.getModuleId()));
		dto.setMethod(model.getMethod());
		dto.setUrl(model.getUrl());
		dto.setParams(model.getParams());
		dto.setHeaders(model.getHeaders());
		dto.setParamType(model.getParamType());
		dto.setVersion(model.getVersion());
		dto.setUid(model.getUid());
		
        return dto;
    }

    public static Debug getModel(DebugDto dto){
        if (dto == null){
            return null;
        }
        Debug model = new Debug();
        model.setId(dto.getId());
		model.setName(dto.getName());
		model.setCreateTime(dto.getCreateTime());
		model.setStatus(dto.getStatus());
		model.setSequence(dto.getSequence());
		model.setInterfaceId(dto.getInterfaceId());
		model.setModuleId(dto.getModuleId());
		model.setMethod(dto.getMethod());
		model.setUrl(dto.getUrl());
		model.setParams(dto.getParams());
		model.setHeaders(dto.getHeaders());
		model.setParamType(dto.getParamType());
		model.setVersion(dto.getVersion());
		model.setUid(dto.getUid());
		
        return model;
    }

    public static List<DebugDto> getDto(List<Debug> models){
        if (models == null){
            return new ArrayList<>();
        }
        List<DebugDto> dtos = new ArrayList<>();
        for (Debug model : models){
            dtos.add(getDto(model));
        }
        return dtos;
    }
}
