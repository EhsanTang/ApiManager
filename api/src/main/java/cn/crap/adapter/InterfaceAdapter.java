package cn.crap.adapter;

import cn.crap.dto.InterfaceDto;
import cn.crap.dto.SearchDto;
import cn.crap.enumer.InterfaceContentType;
import cn.crap.enumer.InterfaceStatus;
import cn.crap.enumer.LuceneSearchType;
import cn.crap.enumer.ProjectType;
import cn.crap.framework.SpringContextHolder;
import cn.crap.model.mybatis.Interface;
import cn.crap.model.mybatis.InterfaceWithBLOBs;
import cn.crap.model.mybatis.Module;
import cn.crap.model.mybatis.Project;
import cn.crap.service.tool.ModuleCache;
import cn.crap.service.tool.ProjectCache;
import cn.crap.utils.DateFormartUtil;
import cn.crap.utils.Tools;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;



/**
 * Automatic generation by tools
 * model adapter: convert model to dto
 * Avoid exposing sensitive data and modifying data that is not allowed to be modified
 */
public class InterfaceAdapter {
    public static InterfaceDto getDto(InterfaceWithBLOBs model, Module module, boolean handleText){
        if (model == null){
            return null;
        }

        InterfaceDto dto = new InterfaceDto();
        dto.setId(model.getId());
		dto.setUrl(handleText(model.getUrl(), handleText));
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
		dto.setRemark(handleText(model.getRemark(), handleText));
		dto.setErrors(model.getErrors());
		dto.setUpdateBy(model.getUpdateBy());
		dto.setVersion(model.getVersion());
		dto.setSequence(model.getSequence());
		dto.setHeader(model.getHeader());
		dto.setFullUrl(handleText(model.getFullUrl(), handleText));
		dto.setMonitorType(model.getMonitorType());
		dto.setMonitorText(model.getMonitorText());
		dto.setMonitorEmails(model.getMonitorEmails());
		dto.setIsTemplate(model.getIsTemplate());
		dto.setProjectId(model.getProjectId());
		dto.setRemarkNoHtml(Tools.removeHtml(model.getRemark()));
		dto.setContentType(model.getContentType());
		dto.setContentTypeName(InterfaceContentType.getNameByType(model.getContentType()));

		if (model.getCreateTime() != null) {
            dto.setCreateTimeStr(DateFormartUtil.getDateByTimeMillis(model.getCreateTime().getTime()));
        }
        if (model.getUpdateTime() != null) {
            dto.setUpdateTimeStr(DateFormartUtil.getDateByTimeMillis(model.getUpdateTime().getTime()));
        }

        if (model.getStatus() != null){
		    dto.setStatusName(InterfaceStatus.getNameByValue(model.getStatus()));
        }

		if (module != null){
			dto.setModuleName(module.getName());
			dto.setModuleUrl(module.getUrl());
		}
		
        return dto;
    }

    private static String handleText(String str, boolean handleText){
    	if (handleText){
    		return Tools.removeHtml(str);
		}
		return str;
	}


    public static InterfaceWithBLOBs getModel(InterfaceDto dto){
        if (dto == null){
            return null;
        }
		InterfaceWithBLOBs model = new InterfaceWithBLOBs();
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
		model.setVersion(dto.getVersion());
		model.setSequence(dto.getSequence());
		model.setHeader(dto.getHeader());
		model.setFullUrl(dto.getFullUrl());
		model.setMonitorType(dto.getMonitorType());
		model.setMonitorText(dto.getMonitorText());
		model.setMonitorEmails(dto.getMonitorEmails());
		model.setIsTemplate(dto.getIsTemplate());
		model.setProjectId(dto.getProjectId());
		model.setContentType(dto.getContentType());
		
        return model;
    }

    public static List<InterfaceDto> getDtoWithBLOBs(List<InterfaceWithBLOBs> models){
        if (models == null){
            return new ArrayList<>();
        }
        List<InterfaceDto> dtos = new ArrayList<>();
        for (InterfaceWithBLOBs model : models){
            dtos.add(getDto(model, null, false));
        }
        return dtos;
    }

	public static List<InterfaceDto> getDto(List<InterfaceWithBLOBs> models, Module module){
		if (models == null){
			return new ArrayList<>();
		}
		List<InterfaceDto> dtos = new ArrayList<>();
		for (InterfaceWithBLOBs model : models){
			dtos.add(getDto(model, module, false));
		}
		return dtos;
	}

	public static List<SearchDto> getSearchDto(List<InterfaceDto> models){
		if (models == null){
			return new ArrayList<>();
		}
		List<SearchDto> dtos = new ArrayList<>();
		for (InterfaceDto model : models){
		    try{
			    dtos.add(getSearchDto(model));
            }catch (Exception e){
                e.printStackTrace();
            }
		}
		return dtos;
	}

	public static SearchDto getSearchDto(InterfaceDto model) {
		Assert.notNull(model);
		Assert.notNull(model.getProjectId());
		Assert.notNull(model.getModuleId());

		ModuleCache moduleCache = SpringContextHolder.getBean("moduleCache", ModuleCache.class);
		ProjectCache projectCache = SpringContextHolder.getBean("projectCache", ProjectCache.class);

		Module module = moduleCache.get(model.getModuleId());
		Project project = projectCache.get(model.getProjectId());

		SearchDto dto = new SearchDto();
		dto.setId(model.getId());
		dto.setCreateTime(DateFormartUtil.getByFormat(model.getCreateTimeStr(), DateFormartUtil.YYYY_MM_DD_HH_mm));
		dto.setContent(model.getRemark() + model.getResponseParam() + model.getParam());
		dto.setModuleName(module.getName());
		dto.setTitle(model.getInterfaceName());
		dto.setType(Interface.class.getSimpleName());
		dto.setUrl("#/"+model.getProjectId()+"/front/interfaceDetail/" + model.getId());
		dto.setVersion(model.getVersion());
		dto.setHref(model.getFullUrl());
		dto.setProjectId(model.getProjectId());
		// 私有项目不能建立索引

		if(project.getType() == ProjectType.PRIVATE.getType()){
			dto.setNeedCreateIndex(false);
		}

		if(LuceneSearchType.No.getByteValue().equals(project.getLuceneSearch())){
			dto.setNeedCreateIndex(false);
		}
		return dto;

	}
}
