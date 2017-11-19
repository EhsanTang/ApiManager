package cn.crap.adapter;

import cn.crap.dto.RoleDto;
import cn.crap.model.mybatis.Role;
import java.util.ArrayList;
import java.util.List;



/**
 * Automatic generation by tools
 * model adapter: convert model to dto
 * Avoid exposing sensitive data and modifying data that is not allowed to be modified
 */
public class RoleAdapter {
    public static RoleDto getDto(Role model){
        if (model == null){
            return null;
        }

        RoleDto dto = new RoleDto();
        dto.setId(model.getId());
		dto.setRoleName(model.getRoleName());
		dto.setAuth(model.getAuth());
		dto.setAuthName(model.getAuthName());
		dto.setCreateTime(model.getCreateTime());
		dto.setStatus(model.getStatus());
		dto.setSequence(model.getSequence());
		
        return dto;
    }

    public static Role getModel(RoleDto dto){
        if (dto == null){
            return null;
        }
        Role model = new Role();
        model.setId(dto.getId());
		model.setRoleName(dto.getRoleName());
		model.setAuth(dto.getAuth());
		model.setAuthName(dto.getAuthName());
		model.setCreateTime(dto.getCreateTime());
		model.setStatus(dto.getStatus());
		model.setSequence(dto.getSequence());
		
        return model;
    }

    public static List<RoleDto> getDto(List<Role> models){
        if (models == null){
            return new ArrayList<>();
        }
        List<RoleDto> dtos = new ArrayList<>();
        for (Role model : models){
            dtos.add(getDto(model));
        }
        return dtos;
    }
}
