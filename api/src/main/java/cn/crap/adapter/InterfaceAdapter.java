package cn.crap.adapter;

import cn.crap.dto.InterfaceDto;
import cn.crap.model.mybatis.Interface;
import java.util.ArrayList;
import java.util.List;



/**
 * Automatic generation by tools
 * model adapter: convert model to dto
 * Avoid exposing sensitive data and modifying data that is not allowed to be modified
 */
public class InterfaceAdapter {
    public static InterfaceDto getDto(Interface model){
        if (model == null){
            return null;
        }

        InterfaceDto dto = new InterfaceDto();
        dto.setId(model.getId());
		dto.setUrl(model.getUrl());
		dto.setMethod(model.getMethod());
		dto.setParam(model.getParam());
		dto.setParamRemark(model.getParamRemark());
		dto.setRequestExam(model.getRequestExam());
		dto.setResponseParam(model.getResponseParam());
		dto.setErrorList(model.getErrorList());
		dto.setTrueExam(model.getTrueExam());
		dto.setFalseExam(model.getFalseExam());
		dto.setStatus(model.getStatus());
		dto.setModuleId(model.getModuleId());
		dto.setInterfaceName(model.getInterfaceName());
		dto.setRemark(model.getRemark());
		dto.setErrors(model.getErrors());
		dto.setUpdateBy(model.getUpdateBy());
		dto.setUpdateTime(model.getUpdateTime());
		dto.setCreateTime(model.getCreateTime());
		dto.setVersion(model.getVersion());
		dto.setSequence(model.getSequence());
		dto.setHeader(model.getHeader());
		dto.setFullUrl(model.getFullUrl());
		dto.setMonitorType(model.getMonitorType());
		dto.setMonitorText(model.getMonitorText());
		dto.setMonitorEmails(model.getMonitorEmails());
		dto.setIsTemplate(model.getIsTemplate());
		dto.setProjectId(model.getProjectId());
		
        return dto;
    }

    public static Interface getModel(InterfaceDto dto){
        if (dto == null){
            return null;
        }
        Interface model = new Interface();
        model.setId(dto.getId());
		model.setUrl(dto.getUrl());
		model.setMethod(dto.getMethod());
		model.setParam(dto.getParam());
		model.setParamRemark(dto.getParamRemark());
		model.setRequestExam(dto.getRequestExam());
		model.setResponseParam(dto.getResponseParam());
		model.setErrorList(dto.getErrorList());
		model.setTrueExam(dto.getTrueExam());
		model.setFalseExam(dto.getFalseExam());
		model.setStatus(dto.getStatus());
		model.setModuleId(dto.getModuleId());
		model.setInterfaceName(dto.getInterfaceName());
		model.setRemark(dto.getRemark());
		model.setErrors(dto.getErrors());
		model.setUpdateBy(dto.getUpdateBy());
		model.setUpdateTime(dto.getUpdateTime());
		model.setCreateTime(dto.getCreateTime());
		model.setVersion(dto.getVersion());
		model.setSequence(dto.getSequence());
		model.setHeader(dto.getHeader());
		model.setFullUrl(dto.getFullUrl());
		model.setMonitorType(dto.getMonitorType());
		model.setMonitorText(dto.getMonitorText());
		model.setMonitorEmails(dto.getMonitorEmails());
		model.setIsTemplate(dto.getIsTemplate());
		model.setProjectId(dto.getProjectId());
		
        return model;
    }

    public static List<InterfaceDto> getDto(List<Interface> models){
        if (models == null){
            return new ArrayList<>();
        }
        List<InterfaceDto> dtos = new ArrayList<>();
        for (Interface model : models){
            dtos.add(getDto(model));
        }
        return dtos;
    }
}
