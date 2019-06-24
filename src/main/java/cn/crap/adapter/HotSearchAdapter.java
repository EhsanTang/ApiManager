package cn.crap.adapter;

import cn.crap.dto.HotSearchDto;
import cn.crap.model.HotSearch;
import cn.crap.utils.BeanUtil;
import cn.crap.utils.DateFormartUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Automatic generation by tools
 * model adapter: convert model to dto
 * Avoid exposing sensitive data and modifying data that is not allowed to be modified
 */
public class HotSearchAdapter {
    public static HotSearchDto getDto(HotSearch model) {
        if (model == null) {
            return null;
        }

        HotSearchDto dto = new HotSearchDto();
        BeanUtil.copyProperties(model, dto);

        dto.setCreateTimeStr(DateFormartUtil.getDateByTimeMillis(model.getCreateTime().getTime()));
        return dto;
    }

    public static HotSearch getModel(HotSearchDto dto) {
        if (dto == null) {
            return null;
        }
        HotSearch model = new HotSearch();
        model.setId(dto.getId());
        model.setTimes(dto.getTimes());
        model.setKeyword(dto.getKeyword());

        return model;
    }

    public static List<HotSearchDto> getDto(List<HotSearch> models) {
        if (models == null) {
            return new ArrayList<>();
        }
        List<HotSearchDto> dtos = new ArrayList<>();
        for (HotSearch model : models) {
            dtos.add(getDto(model));
        }
        return dtos;
    }
}
