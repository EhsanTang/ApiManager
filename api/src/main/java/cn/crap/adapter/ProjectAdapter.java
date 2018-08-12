package cn.crap.adapter;

import cn.crap.dto.ProjectDto;
import cn.crap.enumer.LuceneSearchType;
import cn.crap.enumer.ProjectStatus;
import cn.crap.enumer.ProjectType;
import cn.crap.model.Project;
import cn.crap.model.User;
import cn.crap.service.UserService;
import cn.crap.utils.BeanUtil;
import cn.crap.utils.DateFormartUtil;
import cn.crap.utils.MyString;

import java.util.ArrayList;
import java.util.List;


/**
 * Automatic generation by tools
 * model adapter: convert model to dto
 * Avoid exposing sensitive data and modifying data that is not allowed to be modified
 */
public class ProjectAdapter {
    public static ProjectDto getDto(Project model, User user){
        if (model == null){
            return null;
        }

        ProjectDto dto = new ProjectDto();
        BeanUtil.copyProperties(model, dto);

        if (model.getCreateTime() != null) {
            dto.setCreateTimeStr(DateFormartUtil.getDateByTimeMillis(model.getCreateTime().getTime()));
        }
		dto.setUserName(user == null ? "" : user.getUserName());
		dto.setTypeName(ProjectType.getNameByValue(model.getType()));
		dto.setStatusName(ProjectStatus.getNameByValue(model.getStatus()));
		if (dto.getType() != null && ProjectType.PRIVATE.getType() == dto.getType()){
            dto.setLuceneSearchName("私有项目，不创建索引");
        } else {
            dto.setLuceneSearchName(LuceneSearchType.getName(model.getLuceneSearch()));
        }

		if (model.getType() != null && ProjectType.PRIVATE.getType() == model.getType()){
            dto.setVisitWay("授权访问（项目成员或创建者登陆后才能访问）-- 安全度：高");
        }else if (model.getType() != null && ProjectType.PUBLIC.getType() == model.getType() && MyString.isNotEmpty(model.getPassword())){
            dto.setVisitWay("通过访问密码访问 -- 安全度：中");
        }else{
            dto.setVisitWay("通过项目地址访问 -- 安全度：低");
        }

        return dto;
    }

    public static Project getModel(ProjectDto dto){
        if (dto == null){
            return null;
        }
        Project model = new Project();
        model.setId(dto.getId());
		model.setName(dto.getName());
		model.setStatus(dto.getStatus());
		model.setSequence(dto.getSequence());
		model.setRemark(dto.getRemark());
		model.setType(dto.getType());
		model.setCover(dto.getCover());
		model.setLuceneSearch(dto.getLuceneSearch());
		model.setPassword(dto.getPassword());
		
        return model;
    }

    public static List<ProjectDto> getDto(List<Project> models, UserService userService){
        if (models == null){
            return new ArrayList<>();
        }
        List<ProjectDto> dtos = new ArrayList<>();
        for (Project model : models){
            dtos.add(getDto(model, userService == null? null : userService.getById(model.getUserId())));
        }
        return dtos;
    }
}
