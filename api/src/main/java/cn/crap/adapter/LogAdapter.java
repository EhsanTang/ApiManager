package cn.crap.adapter;

import cn.crap.dto.LogDto;
import cn.crap.model.Log;
import cn.crap.utils.BeanUtil;
import cn.crap.utils.DateFormartUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Automatic generation by tools
 * model adapter: convert model to dto
 * Avoid exposing sensitive data and modifying data that is not allowed to be modified
 */
public class LogAdapter {
    public static LogDto getDto(Log model){
        if (model == null){
            return null;
        }

        LogDto dto = new LogDto();
        BeanUtil.copyProperties(model, dto);
        dto.setCreateTimeStr(DateFormartUtil.getDateByTimeMillis(model.getCreateTime().getTime()));

        return dto;
    }

    public static Log getModel(LogDto dto){
        if (dto == null){
            return null;
        }
        Log model = new Log();
        model.setId(dto.getId());
		model.setStatus(dto.getStatus());
		model.setSequence(dto.getSequence());
		model.setModelClass(dto.getModelClass());
		model.setModelName(dto.getModelName());
		model.setType(dto.getType());
		model.setUpdateBy(dto.getUpdateBy());
		model.setRemark(dto.getRemark());
		model.setContent(dto.getContent());
		model.setIdenty(dto.getIdenty());
		
        return model;
    }

    public static List<LogDto> getDto(List<Log> models){
        if (models == null){
            return new ArrayList<>();
        }
        List<LogDto> dtos = new ArrayList<>();
        for (Log model : models){
            dtos.add(getDto(model));
        }
        return dtos;
    }
}
