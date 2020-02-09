package cn.crap.adapter;

import cn.crap.dto.DebugDto;
import cn.crap.dto.ParamDto;
import cn.crap.model.Debug;
import cn.crap.model.InterfaceWithBLOBs;
import cn.crap.model.Module;
import cn.crap.utils.BeanUtil;
import cn.crap.utils.IConst;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;
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
    public static DebugDto getDtoFromInterface(InterfaceWithBLOBs model){
        if (model == null){
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

        if (model.getParam().startsWith(IConst.C_PARAM_FORM_PRE)) {
            List<ParamDto> paramList = JSONArray.parseArray(model.getParam() == null ? "[]" : model.getParam().substring(5), ParamDto.class);
            StringBuilder paramSb = new StringBuilder();

            for (ParamDto paramDto : paramList){
                paramSb.append(paramDto.getName() + ":" + paramDto.getDef() + "\\r\\n");
            }
            dto.setParams(paramSb.toString());
        }

        StringBuilder headerSb = new StringBuilder();
        for (ParamDto paramDto : headerList){
            headerSb.append(paramDto.getName() + ":" + paramDto.getDef() + "\\r\\n");
        }
        dto.setHeaders(headerSb.toString());
        dto.setUrl(model.getFullUrl());
        dto.setVersion(model.getVersionNum());
        dto.setName(model.getInterfaceName());
        return dto;
    }

    public static InterfaceWithBLOBs getInterfaceByDebug(Module module, InterfaceWithBLOBs model, DebugDto dto){
        if (dto == null){
            return null;
        }
        model.setId(dto.getId());
        model.setInterfaceName(dto.getName());
        model.setStatus(dto.getStatus());
        model.setSequence(dto.getSequence());
        model.setModuleId(dto.getModuleId());
        model.setMethod(dto.getMethod());
        model.setFullUrl(dto.getUrl());
        model.setUrl(dto.getUrl());

        // 替换项目前缀
        if (module != null && MyString.isNotEmptyOrNUll(module.getUrl())){
            model.setUrl(dto.getUrl().replaceFirst(module.getUrl(), ""));
        }
        if (model.getParam() != null && dto.getParamType().equals(IConst.C_FORM_DATA_TYPE)){
            model.setParam(IConst.C_PARAM_FORM_PRE + JSON.toJSONString(getJson(model.getParam(), dto.getParams())));
        } else {
            model.setParam(dto.getParams());
        }

        List<ParamDto> headerList = getJson(model.getHeader(), dto.getHeaders());
        ParamDto paramDto = new ParamDto(IConst.C_CONTENT_TYPE, IConst.C_TRUE, IConst.C_STRING, dto.getParamType(), IConst.C_CONTENT_TYPE_TIP + dto.getParamType());
        headerList.add(paramDto);

        model.setHeader(JSON.toJSONString(headerList));
        model.setVersionNum(dto.getVersion());
        return model;
    }

    // key:value转json
    private static List<ParamDto> getJson(String jsonStr, String keyValueStr){
        // 请求头转换
        Map<String, ParamDto> paramMap = JSONArray.parseArray(jsonStr == null ? "[]" :jsonStr, ParamDto.class).stream().collect(Collectors.toMap(ParamDto::getName, a -> a,(k1, k2)->k1));
        List<ParamDto> listDTO  = Lists.newArrayList();
        for (String param : Optional.ofNullable(keyValueStr.split("\n|\r")).orElse(new String[]{})){
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
