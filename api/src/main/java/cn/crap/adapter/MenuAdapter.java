package cn.crap.adapter;

import cn.crap.dto.MenuDto;
import cn.crap.enu.MenuType;
import cn.crap.model.Menu;
import cn.crap.utils.BeanUtil;
import cn.crap.utils.IConst;

import java.util.ArrayList;
import java.util.List;


/**
 * 用户model适配器，将DTO转为Model，将Model转为DTO
 * 避免暴露敏感数据和修改不允许修改得数据
 */
public class MenuAdapter {
    public static MenuDto getDto(Menu menu){
        if (menu == null){
            return null;
        }

        MenuDto menuDto = new MenuDto();
        BeanUtil.copyProperties(menu, menuDto);
        menuDto.setTypeName(menu.getType() == null ? "" : MenuType.getChineseNameByValue(menu.getType()));

        return menuDto;
    }

    public static Menu getModel(MenuDto menuDto){
        if (menuDto == null){
            return null;
        }
        Menu menu = new Menu();
        menu.setIconRemark(menuDto.getIconRemark());
        if (IConst.NULL.equals(menuDto.getIconRemark())){
            menu.setIconRemark("");
        }
        menu.setId(menuDto.getId());
        menu.setMenuName(menuDto.getMenuName());
        menu.setMenuUrl(menuDto.getMenuUrl());
        menu.setParentId(menuDto.getParentId());
        menu.setRoleIds(menuDto.getRoleIds());
        menu.setSequence(menuDto.getSequence());
        menu.setStatus(menuDto.getStatus());
        menu.setType(menuDto.getType());
        return menu;
    }

    public static List<MenuDto> getDto(List<Menu> menus){
        if (menus == null){
            return new ArrayList<>();
        }
        List<MenuDto> menuDtos = new ArrayList<>();
        for (Menu menu : menus){
            menuDtos.add(getDto(menu));
        }
        return menuDtos;
    }
}
