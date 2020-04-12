package cn.crap.adapter;

import cn.crap.dto.ProjectDTO;
import cn.crap.enu.LuceneSearchType;
import cn.crap.enu.ProjectStatus;
import cn.crap.enu.ProjectType;
import cn.crap.model.ProjectPO;
import cn.crap.model.UserPO;
import cn.crap.service.UserService;
import cn.crap.utils.BeanUtil;
import cn.crap.utils.DateFormartUtil;
import cn.crap.utils.MyString;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Automatic generation by tools
 * model adapter: convert model to dto
 * Avoid exposing sensitive data and modifying data that is not allowed to be modified
 */
public class ProjectAdapter {
    public static ProjectDTO getDTO(ProjectPO model, UserPO user){
        if (model == null){
            return null;
        }

        ProjectDTO dto = new ProjectDTO();
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
            dto.setVisitWay("授权访问（项目成员或创建者登录后才能访问）-- 安全度：高");
        }else if (model.getType() != null && ProjectType.PUBLIC.getType() == model.getType() && MyString.isNotEmpty(model.getPassword())){
            dto.setVisitWay("通过访问密码访问 -- 安全度：中");
        }else{
            dto.setVisitWay("通过项目地址访问 -- 安全度：低");
        }

        return dto;
    }

    public static ProjectPO getModel(ProjectDTO dto){
        if (dto == null){
            return null;
        }
        ProjectPO model = new ProjectPO();
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

    public static List<ProjectDTO> getDTOS(List<ProjectPO> models, UserService userService){
        if (models == null){
            return new ArrayList<>();
        }
        List<ProjectDTO> dtos = new ArrayList<>();
        Map<String , UserPO> userPOMAP = Maps.newHashMap();

        for (ProjectPO model : models){
            String userId = model.getUserId();
            UserPO userPO = userPOMAP.get(userId);

            if (userPO == null && userService != null){
                userPO = userService.get(userId);
                if (userPO != null){
                    userPOMAP.put(userId, userPO);
                }
            }
            dtos.add(getDTO(model, userPO));
        }
        return dtos;
    }
}
