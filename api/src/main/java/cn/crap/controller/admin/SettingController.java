package cn.crap.controller.admin;

import cn.crap.adapter.SettingAdapter;
import cn.crap.dto.SettingDto;
import cn.crap.enu.MyError;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.Setting;
import cn.crap.query.SettingQuery;
import cn.crap.service.SettingService;
import cn.crap.service.tool.SystemService;
import cn.crap.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class SettingController extends BaseController {

    @Autowired
    private SettingService settingService;
    @Autowired
    private SettingService customSettingService;
    @Autowired
    private SystemService systemService;
    private final static String[] indexUrls = new String[]{"index.do", "visitor/", "project.do", "dashboard.htm"};

    /**
     * @return
     */
    @RequestMapping("/setting/list.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_SETTING)
    public JsonResult list(@ModelAttribute SettingQuery query) throws MyException{
        Page page = new Page(query);

        page.setAllRow(settingService.count(query));
        return new JsonResult().data(SettingAdapter.getDto(settingService.query(query))).page(page);
    }

    @RequestMapping("/setting/detail.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_SETTING)
    public JsonResult detail(String id, String key, String type) {
        Setting setting = null;
        if (id != null) {
            setting = settingService.getById(id);
        } else if (key != null) {
            setting = customSettingService.getByKey(key);
        }

        if (setting == null) {
            setting = new Setting();
            setting.setType(type);
        }
        return new JsonResult().data(SettingAdapter.getDto(setting));
    }

    @RequestMapping("/setting/addOrUpdate.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_SETTING)
    public JsonResult addOrUpdate(@ModelAttribute SettingDto settingDto) throws Exception {
        if (settingDto.getId() != null) {
            // index url must start with indexUrls
            if (S_INDEX_PAGE.equals(settingDto.getKey())) {
                boolean legalUrl = false;
                for (String indexUrl : indexUrls) {
                    if (settingDto.getValue().startsWith(indexUrl)) {
                        legalUrl = true;
                        break;
                    }
                }
                if (!legalUrl) {
                    return new JsonResult(MyError.E000059);
                }
            }
            settingService.update(SettingAdapter.getModel(settingDto));
        } else {
            Setting dbSetting = customSettingService.getByKey(settingDto.getKey());
            if (dbSetting != null) {
                return new JsonResult(MyError.E000006);
            }
            settingService.insert(SettingAdapter.getModel(settingDto));
        }
        settingCache.del(settingDto.getKey());
        systemService.updateSettingCss();
        systemService.mergeSource();

        return new JsonResult().data(settingDto);
    }

    @RequestMapping("/setting/delete.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_SETTING)
    public JsonResult delete(@RequestParam String id) throws MyException {
        Setting setting = settingService.getById(id);
        if (setting.getCanDelete() == 0) {
            throw new MyException(MyError.E000009);
        }
        settingService.delete(id);
        settingCache.del(setting.getMkey());
        return SUCCESS;
    }

    @RequestMapping("/setting/changeSequence.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_SETTING)
    public JsonResult changeSequence(@RequestParam String id, @RequestParam String changeId) {
        Setting change = settingService.getById(changeId);
        Setting model = settingService.getById(id);
        long modelSequence = model.getSequence();

        model.setSequence(change.getSequence());
        change.setSequence(modelSequence);

        settingService.update(model);
        settingService.update(change);
        return SUCCESS;
    }

}
