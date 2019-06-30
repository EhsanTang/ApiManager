package cn.crap.utils;

import cn.crap.dto.SearchDto;
import cn.crap.enu.TableId;

/**
 * 后端地址处理工具
 * @author Ehsan
 * @date 2019/4/30 18:05
 */
public class UseHrefUtil {
    private final static String USE_BASE_URL = "user/%s/detail?id=%s&projectId=%s&moduleId=%s&dataType=%s&menu_a=menu-project&menu_b=menu_%s&projectName=%s&pageName=%s";
    private final static String USE_BUG_EDIT_URL = "user/%s/edit?id=%s&projectId=%s&moduleId=%s&dataType=%s&menu_a=menu-project&menu_b=menu_%s&projectName=%s&pageName=%s&type=BUG";

    private final static String BASE_URL = "%s/detail?projectId=%s&id=%s";
    private final static String BASE_ABSOLUTE_URL = "/index.do#%s/detail?projectId=%s&id=%s";


    /**
     * 后端搜索页面地址
     * @param DTO
     * @return
     */
    public static String getUserHref(SearchDto DTO){
        TableId tableId = TableId.getByValue(DTO.getTableId());
        if (tableId.getId().equals(TableId.BUG.getId())){
            return String.format(USE_BUG_EDIT_URL, tableId.getTableEnName(), DTO.getId(), DTO.getProjectId(), DTO.getModuleId(),
                    tableId.getTableEnName(), tableId.getTableEnName(), DTO.getProjectName(), tableId.getTableName() + "详情");
        }
        return String.format(USE_BASE_URL, tableId.getTableEnName(), DTO.getId(), DTO.getProjectId(), DTO.getModuleId(),
                tableId.getTableEnName(), tableId.getTableEnName(), DTO.getProjectName(), tableId.getTableName() + "详情");
    }

    /**
     * 前端登陆搜索看到的页面
     * @param DTO
     * @return
     */
    public static String getHref(SearchDto DTO){
        TableId tableId = TableId.getByValue(DTO.getTableId());
        String href = String.format(BASE_ABSOLUTE_URL, tableId.getTableEnName(), DTO.getProjectId(), DTO.getId());

        if (DTO.getTableId().equals(TableId.ARTICLE.getId())){
            href = href + "&type=ARTICLE";
        } else if (DTO.getTableId().equals(TableId.DICTIONARY.getId())){
            href = String.format(BASE_ABSOLUTE_URL, TableId.ARTICLE.getTableEnName(), DTO.getProjectId(), DTO.getId());
            href = href + "&type=DICTIONARY";
        } else if (DTO.getTableId().equals(TableId.BUG.getId())){
            return String.format("/admin.do#/" + USE_BUG_EDIT_URL, tableId.getTableEnName(), DTO.getId(), DTO.getProjectId(), DTO.getModuleId(),
                    tableId.getTableEnName(), tableId.getTableEnName(), DTO.getProjectName(), tableId.getTableName() + "详情");
        } else if (DTO.getTableId().equals(TableId.SOURCE.getId())){
            href = String.format(BASE_ABSOLUTE_URL, TableId.SOURCE.getTableEnName(), DTO.getProjectId(), DTO.getId());
        }
        return href;
    }

}
