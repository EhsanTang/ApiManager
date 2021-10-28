package cn.crap.adapter;

import cn.crap.dto.InterfaceDto;
import cn.crap.dto.ParamDto;
import cn.crap.dto.SearchDto;
import cn.crap.enu.*;
import cn.crap.model.Interface;
import cn.crap.model.InterfaceWithBLOBs;
import cn.crap.model.ModulePO;
import cn.crap.model.ProjectPO;
import cn.crap.utils.*;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Automatic generation by tools
 * model adapter: convert model to dto
 * Avoid exposing sensitive data and modifying data that is not allowed to be modified
 */
public class InterfaceAdapter {
    public static InterfaceWithBLOBs getInit(){
        InterfaceWithBLOBs model = new InterfaceWithBLOBs();
        model.setResponseParam("[]");
        model.setHeader("[]");
        model.setParamRemark("[]");
        model.setErrors("[]");
        model.setMethod(IConst.C_METHOD_GET);
        model.setStatus(InterfaceStatus.ONLINE.getByteValue());
        model.setContentType(InterfaceContentType.JSON.getType());
        model.setParam(IConst.C_PARAM_FORM_PRE + "[]");
        model.setSequence(System.currentTimeMillis());
        return model;
    }
    public static InterfaceDto getDtoWithBLOBs(InterfaceWithBLOBs model, ModulePO module, ProjectPO project, boolean escape){
        if (model == null){
            return null;
        }

		InterfaceDto dto = getDto(model, module, project, escape);

		dto.setParam(model.getParam());
		dto.setParamRemark(model.getParamRemark());
		dto.setRequestExam(model.getRequestExam());
		dto.setResponseParam(model.getResponseParam());
		dto.setErrorList(model.getErrorList());
		dto.setTrueExam(model.getTrueExam());
		dto.setFalseExam(model.getFalseExam());
		dto.setRemark(model.getRemark());
		dto.setErrors(model.getErrors());
		dto.setHeader(model.getHeader());
		dto.setRemarkNoHtml(Tools.removeHtml(model.getRemark()));

        if (escape){
			dto.setParamRemark(Tools.escapeHtml(model.getParamRemark()));
			dto.setRequestExam(Tools.escapeHtml(model.getRequestExam()));
			dto.setResponseParam(Tools.escapeHtml(model.getResponseParam()));
			dto.setErrorList(Tools.escapeHtml(model.getErrorList()));
			dto.setErrors(Tools.escapeHtml(model.getErrors()));
			dto.setHeader(Tools.escapeHtml(model.getHeader()));

			dto.setFalseExam(Tools.escapeHtmlExceptBr(model.getFalseExam()));
			dto.setTrueExam(Tools.escapeHtmlExceptBr(model.getTrueExam()));
			dto.setParam(Tools.escapeHtmlExceptBr(model.getParam()));

			dto.setRemark(removeHtmlExceptBr(model.getRemark()));
		}

		// 参数排序，一级->二级
		List<ParamDto> responseParamList = JSONArray.parseArray(model.getResponseParam() == null ? "[]" : model.getResponseParam(), ParamDto.class);
		dto.setCrShowResponseParamList(sortParam(null, responseParamList, null));

        List<ParamDto> headerList =JSONArray.parseArray(model.getHeader() == null ? "[]" : model.getHeader(), ParamDto.class);
        dto.setCrShowHeaderList(sortParam(null, headerList, null));
        Optional.ofNullable(headerList).orElse(Lists.newArrayList()).forEach(tempHeader ->{
            if (tempHeader.getName() != null && tempHeader.getName().equalsIgnoreCase(IConst.C_CONTENT_TYPE) && tempHeader.getDef() != null){
                dto.setReqContentType(tempHeader.getDef());
            }
        });

        dto.setParamType((model.getParam() == null || model.getParam().startsWith(IConst.C_PARAM_FORM_PRE)) ?
				IConst.C_PARAM_FORM : IConst.C_PARAM_RAW);
        if (IConst.C_PARAM_FORM.equals(dto.getParamType())) {
            List<ParamDto> paramList = JSONArray.parseArray(model.getParam() == null ? "[]" : model.getParam().substring(5, model.getParam().length()), ParamDto.class);
            dto.setCrShowParamList(sortParam(null, paramList, null));
            dto.setParam("");
        }

        return dto;
    }

	public static InterfaceDto getDto(Interface model, ModulePO module, ProjectPO project, boolean escape){
		if (model == null){
			return null;
		}

		InterfaceDto dto = new InterfaceDto();
		BeanUtil.copyProperties(model, dto);
		dto.setContentTypeName(InterfaceContentType.getNameByType(model.getContentType()));
		dto.setReqContentType(InterfaceContentType.JSON.getType());
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
			dto.setFullUrl(module.getUrl() == null ? "" : module.getUrl() + dto.getUrl());
		}
		if (project != null){
			dto.setProjectName(project.getName());
		}

		if (escape){
			dto.setUrl(Tools.escapeHtml(dto.getUrl()));
			dto.setInterfaceName(Tools.escapeHtml(dto.getInterfaceName()));
			dto.setUpdateBy(Tools.escapeHtml(dto.getUpdateBy()));
			dto.setVersion(Tools.escapeHtml(dto.getVersion()));
			dto.setFullUrl(Tools.escapeHtml(dto.getFullUrl()));
			dto.setMonitorText(Tools.escapeHtml(dto.getMonitorText()));
			dto.setModuleName(Tools.escapeHtml(dto.getModuleName()));
			dto.setModuleUrl(Tools.escapeHtml(dto.getModuleUrl()));
		}
		return dto;
	}

    /**
     * html 转word 保留换行
	 * 转义 < >
     * @param str
     * @return
     */
    private static String removeHtmlExceptBr(String str){
        if (MyString.isEmpty(str)){
            return "";
        }
		str = str.replaceAll("</div>", "_CARP_BR_");
		str = str.replaceAll("</span>", "_CARP_BR_");
		str = str.replaceAll("<br/>", "_CARP_BR_");
		str = str.replaceAll("<br>", "_CARP_BR_");
		str = str.replaceAll("</p>", "_CARP_BR_");
		str = str.replaceAll("\r\n", "_CARP_BR_");
		str = str.replaceAll("\n", "_CARP_BR_");
		str = Tools.removeHtml(str);
		str = Tools.escapeHtml(str);
		str = str.replaceAll("_CARP_BR_", "<w:br/>");
		return str;
	}


    public static InterfaceWithBLOBs getModel(InterfaceDto dto){
        if (dto == null){
            return null;
        }

		InterfaceWithBLOBs interfaceWithBLOBs = new InterfaceWithBLOBs();
		BeanUtil.copyProperties(dto, interfaceWithBLOBs);
        interfaceWithBLOBs.setCreateTime(null);
        return interfaceWithBLOBs;
    }

    public static List<InterfaceDto> getDtoWithBLOBs(List<InterfaceWithBLOBs> models){
        if (models == null){
            return new ArrayList<>();
        }
        List<InterfaceDto> dtos = new ArrayList<>();
        for (InterfaceWithBLOBs model : models){
            dtos.add(getDtoWithBLOBs(model, null, null, false));
        }
        return dtos;
    }

	public static List<InterfaceDto> getDtoWithBLOBs(List<InterfaceWithBLOBs> models, ModulePO module, ProjectPO project){
		if (models == null){
			return new ArrayList<>();
		}
		List<InterfaceDto> dtos = new ArrayList<>();
		for (InterfaceWithBLOBs model : models){
			dtos.add(getDtoWithBLOBs(model, module, project, false));
		}
		return dtos;
	}
	public static List<InterfaceDto> getDto(List<Interface> models, ModulePO module, ProjectPO project){
		if (models == null){
			return new ArrayList<>();
		}
		List<InterfaceDto> dtos = new ArrayList<>();
		for (Interface model : models){
			dtos.add(getDto(model, module, project, false));
		}
		return dtos;
	}

	public static List<SearchDto> getSearchDto(List<InterfaceWithBLOBs> models){
		if (models == null){
			return new ArrayList<>();
		}
		List<SearchDto> dtos = new ArrayList<>();
		for (InterfaceWithBLOBs model : models){
		    try{
			    dtos.add(getSearchDto(model));
            }catch (Exception e){
                e.printStackTrace();
            }
		}
		return dtos;
	}

	public static SearchDto getSearchDto(InterfaceWithBLOBs model){
		ProjectPO project = ServiceFactory.getInstance().getProjectCache().get(model.getProjectId());
		boolean open = false;
		if(LuceneSearchType.Yes.getByteValue().equals(project.getLuceneSearch())){
			open = true;
		}

		// 私有项目不能建立索引
		if(project.getType() == ProjectType.PRIVATE.getType()){
			open = false;
		}

		return new SearchDto(model.getProjectId(), model.getModuleId(), model.getId(), model.getInterfaceName(), TableId.INTERFACE,
				MyString.getStr(model.getRemark()) + MyString.getStr(model.getResponseParam()) + MyString.getStr(model.getParam()),
                model.getFullUrl(),  open, model.getCreateTime());
	}


	private static final String PARAM_SEPARATOR = "->";
    /**
     *
     * @param finished
     * @param unfinished
     * @param deep 一级参数的 deep = 1
     */
	public static List<ParamDto> sortParam(List<ParamDto> finished, List<ParamDto> unfinished, Integer deep){
	    if (deep == null){
            deep = 1;
        }
        if (finished == null){
            finished = new ArrayList<>();
        }
	    if (CollectionUtils.isEmpty(unfinished)){
	        return finished;
        }
        List<ParamDto> newUnfinished = new ArrayList<>();
        for (ParamDto paramDto : unfinished){
            if (paramDto.getName() == null || StringUtils.isEmpty(paramDto.getName())){
                continue;
            }
            String name = paramDto.getName().trim();
            paramDto.setName(name);
            paramDto.setRealName(name);
            if (paramDto.getName().split(PARAM_SEPARATOR).length == deep){
                // 一级参数没有父参数，直接放入finished
                if (deep == 1){
                    paramDto.setDeep(deep);
                    finished.add(paramDto);
                    continue;
                }
                for (int i = 0; i < finished.size(); i++){
                    String parentParam = finished.get(i).getName();
                    if (parentParam.split(PARAM_SEPARATOR).length == deep - 1){
                        String parentNodeName = parentParam + PARAM_SEPARATOR;
                        if(name.startsWith(parentNodeName)){
                            paramDto.setDeep(deep);
                            String[] paramNames = name.split(PARAM_SEPARATOR);
                            paramDto.setRealName(paramNames[paramNames.length-1]);
                            finished.add(i + 1, paramDto);
                            break;
                        }
                    }

                    // 没有找到父节点，追加到最后
                    if (i == finished.size() - 1){
                        paramDto.setDeep(1);
                        finished.add(paramDto);
                        break;
                    }
                }
            } else if(paramDto.getName().split(PARAM_SEPARATOR).length > deep){
                newUnfinished.add(paramDto);
            }
        }
        return sortParam(finished, newUnfinished, ++deep);
    }

//    public static void main(String args[]){
//        List<ParamDto> finished = new ArrayList<>();
//        List<ParamDto> unfinished = new ArrayList<>();
//
//        unfinished.add(getParamDto("first->second->third"));
//        unfinished.add(getParamDto("first"));
//        unfinished.add(getParamDto("second"));
//        unfinished.add(getParamDto("first->second->third->4444"));
//        unfinished.add(getParamDto("second4"));
//        unfinished.add(getParamDto("first->second2"));
//        unfinished.add(getParamDto("second->second2"));
//        unfinished.add(getParamDto("second->first"));
//        unfinished.add(getParamDto("first->second"));
//        unfinished.add(getParamDto("first->second->third2"));
//
//        sortParam(finished, unfinished, 1);
//        for (ParamDto paramDto : finished){
//            System.out.println(paramDto.getName() + "--" + paramDto.getDeep());
//        }
//    }
//
//    private static ParamDto getParamDto(String name) {
//        ParamDto paramDto5 = new ParamDto();
//        paramDto5.setName(name);
//        return paramDto5;
//    }
}
