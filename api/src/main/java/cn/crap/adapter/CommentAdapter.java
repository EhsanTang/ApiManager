package cn.crap.adapter;

import cn.crap.dto.CommentDTO;
import cn.crap.model.CommentPO;
import cn.crap.utils.BeanUtil;
import cn.crap.utils.DateFormartUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Automatic generation by tools
 * model adapter: convert model to dto
 * Avoid exposing sensitive data and modifying data that is not allowed to be modified
 */
public class CommentAdapter {
    public static CommentDTO getDto(CommentPO model){
        if (model == null){
            return null;
        }

        CommentDTO dto = new CommentDTO();
        BeanUtil.copyProperties(model, dto);
		dto.setCreateTimeStr(DateFormartUtil.getDateByTimeMillis(model.getCreateTime().getTime()));
        dto.setUpdateTimeStr(DateFormartUtil.getDateByTimeMillis(model.getUpdateTime().getTime()));

        return dto;
    }

    public static CommentPO getModel(CommentDTO dto){
        if (dto == null){
            return null;
        }
        CommentPO model = new CommentPO();
        BeanUtil.copyProperties(dto, model);
        return model;
    }

    public static List<CommentDTO> getDto(List<CommentPO> models){
        if (models == null){
            return new ArrayList<>();
        }
        List<CommentDTO> dtos = new ArrayList<>();
        for (CommentPO model : models){
            dtos.add(getDto(model));
        }
        return dtos;
    }
}
