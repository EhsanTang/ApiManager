package cn.crap.utils;

import cn.crap.dto.BaseDTO;
import cn.crap.enu.TableId;

/**
 * 后端地址处理工具
 * @author Ehsan
 * @date 2019/4/30 18:05
 */
public class UseHrefUtil {
    private final static String USE_BASE_URL = "user/%s/detail?id=%s&projectId=%s&moduleId=%s&dataType=%s&menu_a=menu-project&menu_b=menu_%s&projectName=%s&pageName=%s";

    public static String getUseDetailHref(BaseDTO DTO){
        TableId tableId = TableId.getByValue(DTO.getTableId());

        return String.format(USE_BASE_URL, tableId.getTableEnName(), DTO.getId(), DTO.getProjectId(), DTO.getModuleId(),
                tableId.getTableEnName(), tableId.getTableEnName(), DTO.getProjectName(), tableId.getTableName() + "详情");
    }

}
