package cn.crap.adapter;

import cn.crap.dto.BugDto;
import cn.crap.model.Bug;
import cn.crap.utils.BeanUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Automatic generation by tools
 * model adapter: convert model to dto
 * Avoid exposing sensitive data and modifying data that is not allowed to be modified
 */
public class BugAdapter {
    public static BugDto getDto(Bug model){
        if (model == null){
            return null;
        }

        BugDto dto = new BugDto();
        BeanUtil.copyProperties(model, dto);
        return dto;
    }

    public static Bug getModel(BugDto dto){
        if (dto == null){
            return null;
        }
        Bug model = new Bug();
        BeanUtil.copyProperties(model, dto, "createTime", "createdBy");
        return model;
    }

    public static List<BugDto> getDto(List<Bug> models){
        if (models == null){
            return new ArrayList<>();
        }
        List<BugDto> dtos = new ArrayList<>();
        for (Bug model : models){
            dtos.add(getDto(model));
        }
        return dtos;
    }
}
