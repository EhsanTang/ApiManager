package cn.crap.service.tool;

import cn.crap.dto.PickDto;
import cn.crap.enu.*;
import cn.crap.framework.MyException;
import cn.crap.model.Menu;
import cn.crap.query.MenuQuery;
import cn.crap.service.IPickService;
import cn.crap.service.MenuService;
import cn.crap.utils.IConst;
import cn.crap.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.*;
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
    private MenuService customMenuService;

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
                    pick = new PickDto(status.getValue(), status.getName());
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
                for (Menu m : customMenuService.query(new MenuQuery().setParentId("0"))) {
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
                for (String iconfont : SettingEnum.FONT_FAMILY.getOptions()) {
                    String[] split = iconfont.split("\\|");
                    pick = new PickDto(split[0], split[1], split[0]);
                    picks.add(pick);
                }
                return picks;
            case ICONFONT:// 图标库
                for (String iconfont : SettingEnum.ICONFONT.getOptions()) {
                    String[] split = iconfont.split("\\|");
                    pick = new PickDto(split[0], split[1], split[0]);
                    picks.add(pick);
                }
                return picks;
            case IMAGE_CODE:
                pick = new PickDto(IConst.C_SEPARATOR, "默认字体");
                picks.add(pick);
                for (String iconfont : SettingEnum.IMAGE_CODE.getOptions()) {
                    String[] split = iconfont.split("\\|");
                    pick = new PickDto(split[0], split[1], split[0]);
                    picks.add(pick);
                }

                pick = new PickDto(IConst.C_SEPARATOR, "服务器支持的字体");
                picks.add(pick);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                String[] fontFamilies = ge.getAvailableFontFamilyNames();
                for (String s : fontFamilies) {
                    pick = new PickDto("FONT_" + MD5.encrytMD5(s, ""), s, s);
                    picks.add(pick);
                }
                return picks;
            case PRIORITY:// 优先级
                for (BugPriority priority : BugPriority.values()) {
                    pick = new PickDto(priority.name(), priority.getValue(), priority.getName());
                    picks.add(pick);
                }
                return picks;
            case BUG_STATUS:// bug状态
                for (BugStatus status : BugStatus.values()) {
                    if (status.getByteValue() == 0){
                        pick = new PickDto(IConst.C_SEPARATOR, "完结");
                        picks.add(pick);
                    }else if (status.getByteValue() == 10){
                        pick = new PickDto(IConst.C_SEPARATOR, "激活");
                        picks.add(pick);
                    }else if (status.getByteValue() == 20){
                        pick = new PickDto(IConst.C_SEPARATOR, "解决");
                        picks.add(pick);
                    }else if (status.getByteValue() == 30){
                        pick = new PickDto(IConst.C_SEPARATOR, "测试");
                        picks.add(pick);
                    }
                    pick = new PickDto(status.name(), status.getValue(), status.getName());
                    picks.add(pick);
                }
                return picks;
            case SEVERITY:// bug严重程度
                for (BugSeverity severity : BugSeverity.values()) {
                    pick = new PickDto(severity.name(), severity.getValue(), severity.getName());
                    picks.add(pick);
                }
                return picks;
            case BUG_TYPE:// 跟踪类型
                for (BugType type : BugType.values()) {
                    pick = new PickDto(type.name(), type.getValue(), type.getName());
                    picks.add(pick);
                }
                return picks;

            case PROJECT_PERMISSION:// 项目权限
                for (ProjectPermissionEnum permissionEnum : ProjectPermissionEnum.values()) {
                    if (permissionEnum.getValue().equals("read") || permissionEnum.getValue().equals("myData")){
                        continue;
                    }
                    if (permissionEnum.isSeparator()){
                        pick = new PickDto(IConst.C_SEPARATOR, permissionEnum.getSeparatorTitle());
                        picks.add(pick);
                    }
                    pick = new PickDto(permissionEnum.getValue(), permissionEnum.getValue(), permissionEnum.getDesc());
                    picks.add(pick);
                }
                return picks;
        }

        return userPickService.getPickList(code, key);
    }

}
