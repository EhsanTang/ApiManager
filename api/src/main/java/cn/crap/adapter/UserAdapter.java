package cn.crap.adapter;

import cn.crap.dto.UserDto;
import cn.crap.model.mybatis.User;
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
        userDto.setLoginType(user.getLoginType());
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
     * @param userDto
     * @return
     */
    public static User getModel(UserDto userDto){
        if (userDto == null){
            return null;
        }
        User user = new User();
        user.setId(userDto.getId());
        user.setAuth(userDto.getAuth());
        user.setAuthName(userDto.getAuthName());
        user.setAvatarUrl(userDto.getAvatarUrl());
        user.setEmail(userDto.getEmail());
        user.setRoleId(userDto.getRoleId());
        user.setRoleName(userDto.getRoleName());
        user.setThirdlyId(userDto.getThirdlyId());
        user.setTrueName(userDto.getTrueName());
        user.setType(userDto.getType());
        user.setUserName(userDto.getUserName());
        user.setStatus(userDto.getStatus());
        user.setLoginType(userDto.getLoginType());
        return user;
    }

}
