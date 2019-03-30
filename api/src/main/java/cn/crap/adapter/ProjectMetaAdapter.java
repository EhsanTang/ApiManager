package cn.crap.adapter;

import cn.crap.dto.ProjectMetaDTO;
import cn.crap.model.ProjectMetaPO;
import cn.crap.utils.AttributeUtils;
import cn.crap.utils.BeanUtil;
import cn.crap.utils.IAttributeConst;
import cn.crap.utils.MyHashMap;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;


public class ProjectMetaAdapter {
    public static ProjectMetaDTO getDto(ProjectMetaPO po){
        if (po == null){
            return null;
        }

        ProjectMetaDTO dto = new ProjectMetaDTO();
        BeanUtil.copyProperties(po, dto);
        dto.setEnvUrl(AttributeUtils.getAttributeMap(po.getAttributes()).get(IAttributeConst.ENV_URL));
        return dto;
    }

    public static List<ProjectMetaDTO> getDto(List<ProjectMetaPO> pos){
        if (pos == null){
            return new ArrayList<>();
        }
        List<ProjectMetaDTO> dtos = new ArrayList<>();
        for (ProjectMetaPO po : pos){
            dtos.add(getDto(po));
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

        IAttributeConst.ENV_URL, dto.getEnvUrl()
        po.setAttributes(AttributeUtils.getAttributeStr());
        return po;
    }

}
