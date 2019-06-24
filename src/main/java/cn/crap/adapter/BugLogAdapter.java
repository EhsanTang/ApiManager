package cn.crap.adapter;

import cn.crap.dto.BugLogDTO;
import cn.crap.enu.BugLogType;
import cn.crap.model.BugLogPO;
import cn.crap.utils.BeanUtil;
import cn.crap.utils.DateFormartUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Automatic generation by tools
 * model adapter: convert model to dto
 * Avoid exposing sensitive data and modifying data that is not allowed to be modified
 */
public class BugLogAdapter {
    public static BugLogDTO getDto(BugLogPO model) {
        if (model == null) {
            return null;
        }

        BugLogDTO dto = new BugLogDTO();
        BeanUtil.copyProperties(model, dto);
        dto.setCreateTimeStr(DateFormartUtil.getDateByTimeMillis(model.getCreateTime()));
        dto.setUpdateTimeStr(DateFormartUtil.getDateByTimeMillis(model.getUpdateTime()));
        dto.setTypeStr(BugLogType.getNameByType(model.getType()));
        return dto;
    }

    public static BugLogPO getPO(BugLogDTO bugDTO) {
        if (bugDTO == null) {
            return null;
        }

        BugLogPO bugPO = new BugLogPO();
        BeanUtil.copyProperties(bugDTO, bugPO);
        return bugPO;
    }


    public static BugLogPO getDTO(BugLogDTO dto) {
        if (dto == null) {
            return null;
        }
        BugLogPO model = new BugLogPO();
        BeanUtil.copyProperties(dto, model);

        return model;
    }

    public static List<BugLogDTO> getDto(List<BugLogPO> models) {
        if (models == null) {
            return new ArrayList<>();
        }
        List<BugLogDTO> dtos = new ArrayList<>();
        for (BugLogPO model : models) {
            dtos.add(getDto(model));
        }
        return dtos;
    }
}
