package cn.crap.adapter;

import cn.crap.dto.UserDto;
import cn.crap.model.mybatis.User;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 用户model适配器，将DTO转为Model，将Model转为DTO
 * 避免暴露敏感数据和修改不允许修改得数据
 */
public class UserAdapter {
    public static UserDto getDto(User user){
        if (user == null){
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setAuth(user.getAuth());
        userDto.setAuthName(user.getAuthName());
        userDto.setAvatarUrl(user.getAvatarUrl());
        userDto.setEmail(user.getEmail());
        userDto.setLoginType(user.getLoginType());
        userDto.setRoleId(user.getRoleId());
        userDto.setRoleName(user.getRoleName());
        userDto.setThirdlyId(user.getThirdlyId());
        userDto.setTrueName(user.getTrueName());
        userDto.setType(user.getType());
        userDto.setUserName(user.getUserName());
        userDto.setStatus(user.getStatus());
        return userDto;
    }

    public static List<UserDto> getDto(List<User> users){
        if (users == null){
            return new ArrayList<>();
        }
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users){
            userDtos.add(getDto(user));
        }
        return userDtos;
    }

    /**
     * 将前端不能修改得字段赋值为null
     * @param dto
     * @return
     */
    public static User getModel(User dto){
        if (dto == null){
            return null;
        }
        dto.setCreateTime(null);
        dto.setLoginType(null);
        dto.setPasswordSalt(null);
        return dto;
    }

    public static cn.crap.model.User getUser(User model){
        if (model == null){
            return null;
        }
        cn.crap.model.User user = new cn.crap.model.User();
        JSONObject json = JSONObject.fromObject(model);
        user = (cn.crap.model.User) JSONObject.toBean(json, cn.crap.model.User.class);
        return user;
    }
}
