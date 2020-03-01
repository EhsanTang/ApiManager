package cn.crap.adapter;

import cn.crap.dto.DebugDto;
import cn.crap.dto.ParamDto;
import cn.crap.model.Debug;
import cn.crap.model.InterfaceWithBLOBs;
import cn.crap.model.ModulePO;
import cn.crap.model.ProjectPO;
import cn.crap.utils.BeanUtil;
import cn.crap.utils.IConst;
import cn.crap.utils.MyString;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Automatic generation by tools
 * model adapter: convert model to dto
 * Avoid exposing sensitive data and modifying data that is not allowed to be modified
 */
public class DebugAdapter {
    public static DebugDto getDtoFromInterface(ProjectPO project, Map<String, ModulePO> moduleMap, InterfaceWithBLOBs model){
        if (model == null || moduleMap.get(model.getModuleId()) == null){
            return null;
        }

        DebugDto dto = new DebugDto();
        BeanUtil.copyProperties(model, dto);
        dto.setMethod("GET");
        String[] methods = Optional.of(model.getMethod()).orElse("").split(",");
        if (methods.length > 0 && methods[0] != null && !"".equals(methods[0].trim())){
            dto.setMethod(methods[0]);
        }

        List<ParamDto> headerList = JSONArray.parseArray(model.getHeader() == null ? "[]" : model.getHeader(), ParamDto.class);
        ParamDto contentTypeDto = headerList.stream().filter(header -> header.getName() != null && header.getName().equalsIgnoreCase(IConst.C_CONTENT_TYPE)).findFirst().orElse(null);
        dto.setParamType(contentTypeDto == null ? IConst.C_FORM_DATA_TYPE : contentTypeDto.getDef());

        dto.setParams(model.getParam());

        boolean needHuanHang = false;
        if (model.getParam().startsWith(IConst.C_PARAM_FORM_PRE)) {
            List<ParamDto> paramList = JSONArray.parseArray(model.getParam() == null ? "[]" : model.getParam().substring(5), ParamDto.class);
            StringBuilder paramSb = new StringBuilder();

            for (ParamDto paramDto : paramList){
                paramSb.append((needHuanHang ? "\n" : "") + paramDto.getName() + ":" + paramDto.getDef());
                needHuanHang = true;
            }
            dto.setParams(paramSb.toString());
        }

        needHuanHang = false;
        StringBuilder headerSb = new StringBuilder();
        for (ParamDto paramDto : headerList){
            if (paramDto.getName().equalsIgnoreCase(IConst.C_CONTENT_TYPE)){
                continue;
            }
            headerSb.append((needHuanHang ? "\n" : "") + paramDto.getName() + ":" + paramDto.getDef());
            needHuanHang = true;
        }
        dto.setHeaders(headerSb.toString());
        dto.setUrl(model.getFullUrl());
        dto.setVersion(model.getVersionNum());
        dto.setName(model.getInterfaceName());
        dto.setId(model.getUniKey());
        dto.setModuleId(moduleMap.get(model.getModuleId()).getUniKey());
        dto.setModuleUniKey(moduleMap.get(model.getModuleId()).getUniKey());
        dto.setProjectUniKey(project.getUniKey());
        dto.setUid(project.getUserId());
        return dto;
    }

    public static InterfaceWithBLOBs getInterfaceByDebug(ModulePO module, InterfaceWithBLOBs model, DebugDto dto){
        if (dto == null){
            return null;
        }
        model.setInterfaceName(dto.getName());
        model.setStatus(dto.getStatus());
        model.setSequence(dto.getSequence());
        model.setMethod(dto.getMethod());
        // 大于500，可能参数过长，去除参数
        String url = (dto.getUrl() != null && dto.getUrl().length() > 500 ? dto.getUrl().split("\\?")[0] : dto.getUrl());
        model.setFullUrl(url);
        model.setUrl(url);

        // 替换项目前缀
        if (module != null && MyString.isNotEmptyOrNUll(module.getUrl())){
            model.setUrl(url.replaceFirst(module.getUrl(), ""));
        }

        if (MyString.isEmpty(dto.getParamType()) || dto.getParamType().equalsIgnoreCase(IConst.C_FORM_DATA_TYPE)){
            model.setParam(IConst.C_PARAM_FORM_PRE + JSON.toJSONString(getJson(model.getParam(), dto.getParams())));
        } else {
            model.setParam(dto.getParams());
        }

        List<ParamDto> headerList = getJson(model.getHeader(), dto.getHeaders());
        ParamDto paramDto = new ParamDto(IConst.C_CONTENT_TYPE, IConst.C_TRUE, IConst.C_STRING, dto.getParamType(), IConst.C_CONTENT_TYPE_TIP + dto.getParamType());
        headerList.add(paramDto);

        model.setHeader(JSON.toJSONString(headerList));
        model.setVersionNum(dto.getVersion());
        model.setModuleId(module.getId());
        return model;
    }

    // key:value转json
    private static List<ParamDto> getJson(String jsonStr, String keyValueStr){
        // 请求头转换
        jsonStr = (MyString.isEmpty(jsonStr) ? "[]" :jsonStr);
        jsonStr = jsonStr.startsWith(IConst.C_PARAM_FORM_PRE) ? jsonStr.substring(5) : jsonStr;

        Map<String, ParamDto> paramMap = JSONArray.parseArray(jsonStr, ParamDto.class).stream().collect(Collectors.toMap(ParamDto::getName, a -> a,(k1, k2)->k1));
        List<ParamDto> listDTO  = Lists.newArrayList();
        for (String param : Optional.ofNullable(keyValueStr.split("\n")).orElse(new String[]{})){
            String[] split = param.split(":");
            if (split.length !=2 || split[0] == null || split[0].trim().equals("")){
                continue;
            }
            ParamDto DTO = paramMap.get(split[0].trim()) == null ? new ParamDto() : paramMap.get(split[0].trim());
            DTO.setName(split[0]);
            DTO.setDef(split[1]);
            listDTO.add(DTO);
        }
        return listDTO;
    }

    public static DebugDto getDto(Debug model){
        if (model == null){
            return null;
        }

        DebugDto dto = new DebugDto();
        BeanUtil.copyProperties(model, dto);
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
