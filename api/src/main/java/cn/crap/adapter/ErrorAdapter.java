package cn.crap.adapter;

import cn.crap.dto.ErrorDto;
import cn.crap.model.Error;
import cn.crap.utils.BeanUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * model adapter convert model to dto
 * Avoid exposing sensitive data and modifying data that is not allowed to be modified
 */
public class ErrorAdapter {
    public static ErrorDto getDto(Error model){
        if (model == null){
            return null;
        }

        ErrorDto dto = new ErrorDto();
        BeanUtil.copyProperties(model, dto);

        return dto;
    }

    public static Error getModel(ErrorDto dto){
        if (dto == null){
            return null;
        }
        Error model = new Error();
        model.setId(dto.getId());
		model.setErrorCode(dto.getErrorCode());
		model.setErrorMsg(dto.getErrorMsg());
		model.setProjectId(dto.getProjectId());

        return model;
    }

    public static List<ErrorDto> getDto(List<Error> models){
        if (models == null){
            return new ArrayList<>();
        }
        List<ErrorDto> dtos = new ArrayList<>();
        for (Error model : models){
            dtos.add(getDto(model));
        }
        return dtos;
    }
}
