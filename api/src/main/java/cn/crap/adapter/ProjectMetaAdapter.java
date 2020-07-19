package cn.crap.adapter;

import cn.crap.dto.ProjectMetaDTO;
import cn.crap.enu.AttributeEnum;
import cn.crap.enu.ProjectMetaType;
import cn.crap.model.ModulePO;
import cn.crap.model.ProjectMetaPO;
import cn.crap.utils.AttributeUtils;
import cn.crap.utils.BeanUtil;
import cn.crap.utils.DateFormartUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProjectMetaAdapter {
    public static ProjectMetaDTO getDto(ProjectMetaPO po, ModulePO module){
        if (po == null){
            return null;
        }

        ProjectMetaDTO dto = new ProjectMetaDTO();
        BeanUtil.copyProperties(po, dto);
        if (po.getCreateTime() != null) {
            dto.setCreateTimeStr(DateFormartUtil.getDateByTimeMillis(po.getCreateTime().getTime()));
        }
        dto.setEnvUrl(AttributeUtils.getAttr(po.getAttributes(), AttributeEnum.ENV_URL));
        if (module != null){
            dto.setModuleName(module.getName());
        }
        dto.setTypeStr(ProjectMetaType.getNameByType(po.getType()));
        return dto;
    }

    public static List<ProjectMetaDTO> getDto(List<ProjectMetaPO> pos){
        if (pos == null){
            return new ArrayList<>();
        }
        List<ProjectMetaDTO> dtos = new ArrayList<>();
        for (ProjectMetaPO po : pos){
            dtos.add(getDto(po, null));
        }
        return dtos;
    }

    /**
     * @param dto
     * @return
     */
    public static ProjectMetaPO getModel(ProjectMetaDTO dto){
        if (dto == null){
            return null;
        }
        ProjectMetaPO po = new ProjectMetaPO();
        BeanUtil.copyProperties(dto, po);

        Map<String, String> attributeMap = new HashMap();
        attributeMap.put(AttributeEnum.ENV_URL.getKey(), dto.getEnvUrl());

        po.setAttributes(AttributeUtils.getAttributeStr(attributeMap));
        return po;
    }

}
