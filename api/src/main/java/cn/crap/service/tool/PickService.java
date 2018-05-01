package cn.crap.service.tool;

import cn.crap.dto.PickDto;
import cn.crap.enumer.*;
import cn.crap.framework.MyException;
import cn.crap.model.mybatis.Menu;
import cn.crap.service.IPickService;
import cn.crap.service.custom.CustomMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 采用责任链模式
 * 下拉选择框
 *
 * @author Ehsan
 */
@Service("pickService")
public class PickService implements IPickService{
    @Resource(name = "userPickService")
    private IPickService userPickService;

    @Autowired
    private CustomMenuService customMenuService;

    @Override
    public List<PickDto> getPickList(String code, String key) throws MyException {
        PickCode pickCode = PickCode.getByCode(code);
        if (pickCode == null) {
            throw new MyException(MyError.E000065, "code 有误");
        }

        PickDto pick = null;
        List<PickDto> picks = new ArrayList<>();

        switch (pickCode) {
            case REQUEST_METHOD:
                for (RequestMethod status : RequestMethod.values()) {
                    pick = new PickDto(status.name(), status.getName(), status.getName());
                    picks.add(pick);
                }
                return picks;

            case INTERFACE_STATUS:
                for (InterfaceStatus status : InterfaceStatus.values()) {
                    pick = new PickDto(status.getValue(), status.name());
                    picks.add(pick);
                }
                return picks;

            case INTERFACE_CONTENT_TYPE:
                for (InterfaceContentType contentType : InterfaceContentType.values()) {
                    pick = new PickDto(contentType.name(), contentType.getType(), contentType.getName());
                    picks.add(pick);
                }
                return picks;

            case MONITOR_TYPE:
                for (MonitorType monitorType : MonitorType.values()) {
                    pick = new PickDto(monitorType.name(), monitorType.getValue() + "", monitorType.getName());
                    picks.add(pick);
                }
                return picks;

            case PROJECT_TYPE:
                for (ProjectType pt : ProjectType.values()) {
                    pick = new PickDto(pt.getType() + "", pt.getName());
                    picks.add(pick);
                }
                return picks;

            case LUCENE_SEARCH_TYPE:
                for (LuceneSearchType lc : LuceneSearchType.values()) {
                    pick = new PickDto(lc.getValue() + "", lc.getName());
                    picks.add(pick);
                }
                return picks;

            case PROJECT_STATUE:// 管理员项目、推荐项目...
                for (ProjectStatus ps : ProjectStatus.values()) {
                    pick = new PickDto(ps.name(), ps.getStatus() + "", ps.getName());
                    picks.add(pick);
                }
                return picks;

            // 一级菜单
            case MENU:
                for (Menu m : customMenuService.queryByParentId("0")) {
                    pick = new PickDto(m.getId(), m.getMenuName());
                    picks.add(pick);
                }
                return picks;

            case SETTING_TYPE:
                for (SettingType type : SettingType.values()) {
                    pick = new PickDto(type.name(), type.getName());
                    picks.add(pick);
                }
                return picks;

            case MENU_TYPE:
                for (MenuType type : MenuType.values()) {
                    pick = new PickDto(type.name(), type.getChineseName());
                    picks.add(pick);
                }
                return picks;

            case FONT_FAMILY:// 字体
                for (FontFamilyType font : FontFamilyType.values()) {
                    pick = new PickDto(font.name(), font.getValue(), font.getName());
                    picks.add(pick);
                }
                return picks;
            case ICONFONT:// 图标库
                for (Iconfont iconfont : Iconfont.values()) {
                    pick = new PickDto(iconfont.name(), iconfont.getValue(), iconfont.getName());
                    picks.add(pick);
                }
                return picks;
        }

        return userPickService.getPickList(code, key);
    }

}
