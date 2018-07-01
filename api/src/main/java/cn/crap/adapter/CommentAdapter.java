package cn.crap.adapter;

import cn.crap.dto.CommentDto;
import cn.crap.model.Comment;
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
    public static CommentDto getDto(Comment model){
        if (model == null){
            return null;
        }

        CommentDto dto = new CommentDto();
        BeanUtil.copyProperties(model, dto);

		dto.setCreateTimeStr(DateFormartUtil.getDateByTimeMillis(model.getCreateTime().getTime()));
        return dto;
    }

    public static Comment getModel(CommentDto dto){
        if (dto == null){
            return null;
        }
        Comment model = new Comment();
        model.setId(dto.getId());
		model.setContent(dto.getContent());
		model.setStatus(dto.getStatus());
		model.setReply(dto.getReply());

        return model;
    }

    public static List<CommentDto> getDto(List<Comment> models){
        if (models == null){
            return new ArrayList<>();
        }
        List<CommentDto> dtos = new ArrayList<>();
        for (Comment model : models){
            dtos.add(getDto(model));
        }
        return dtos;
    }
}
