package cn.crap.adapter;

import cn.crap.dto.CommentDto;
import cn.crap.model.mybatis.Comment;
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
        dto.setId(model.getId());
		dto.setArticleId(model.getArticleId());
		dto.setContent(model.getContent());
		dto.setUserId(model.getUserId());
		dto.setParentId(model.getParentId());
		dto.setStatus(model.getStatus());
		dto.setCreateTimeStr(DateFormartUtil.getDateByTimeMillis(model.getCreateTime().getTime()));
		dto.setSequence(model.getSequence());
		dto.setReply(model.getReply());
		dto.setUserName(model.getUserName());
		dto.setAvatarUrl(model.getAvatarUrl());
		
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
