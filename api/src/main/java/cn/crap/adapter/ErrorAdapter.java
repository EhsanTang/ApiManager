package cn.crap.adapter;

import cn.crap.dto.ErrorDto;
import cn.crap.model.mybatis.Error;

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
        dto.setId(model.getId());
		dto.setErrorCode(model.getErrorCode());
		dto.setErrorMsg(model.getErrorMsg());
		dto.setProjectId(model.getProjectId());
//		dto.setCreateTimeStr(model.getCreateTimeStr());
//		dto.setStatus(model.getCanDelete());
//		dto.setSequence(model.getSequence());
		

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
//		model.setCreateTimeStr(dto.getCreateTimeStr());
//		model.setStatus(dto.getCanDelete());
//		model.setSequence(dto.getSequence());


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
