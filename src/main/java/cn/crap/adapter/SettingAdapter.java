package cn.crap.adapter;

import cn.crap.dto.SettingDto;
import cn.crap.model.Setting;
import cn.crap.utils.BeanUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 用户model适配器，将DTO转为Model，将Model转为DTO
 * 避免暴露敏感数据和修改不允许修改得数据
 */
public class SettingAdapter {
    public static SettingDto getDto(Setting model) {
        if (model == null) {
            return null;
        }

        SettingDto dto = new SettingDto();
        BeanUtil.copyProperties(model, dto);
        dto.setKey(model.getMkey());

        return dto;
    }

    public static Setting getModel(SettingDto dto) {
        if (dto == null) {
            return null;
        }
        Setting model = new Setting();
        model.setId(dto.getId());
        model.setMkey(dto.getKey());
        model.setRemark(dto.getRemark());
        model.setValue(dto.getValue());
        model.setSequence(dto.getSequence());
        model.setStatus(dto.getStatus());
        return model;
    }

    public static List<SettingDto> getDto(List<Setting> models) {
        if (models == null) {
            return new ArrayList<>();
        }
        List<SettingDto> dtos = new ArrayList<>();
        for (Setting model : models) {
            dtos.add(getDto(model));
        }
        return dtos;
    }
}
