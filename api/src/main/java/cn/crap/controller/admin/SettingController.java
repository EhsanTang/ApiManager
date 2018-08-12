package cn.crap.controller.admin;

import cn.crap.adapter.SettingAdapter;
import cn.crap.beans.Config;
import cn.crap.dto.SettingDto;
import cn.crap.enumer.MyError;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.Setting;
import cn.crap.query.SettingQuery;
import cn.crap.service.SettingService;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SettingController extends BaseController {

    @Autowired
    private SettingService settingService;
    @Autowired
    private SettingService customSettingService;
    @Autowired
    private Config config;
    private final static String[] indexUrls = new String[]{"index.do", "visitor/", "project.do", "dashboard.htm"};

    /**
     * @return
     */
    @RequestMapping("/admin/setting/list.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_SETTING)
    public JsonResult list(@ModelAttribute SettingQuery query) throws MyException{
        Page page = new Page(query);

        page.setAllRow(settingService.count(query));
        return new JsonResult().data(SettingAdapter.getDto(settingService.query(query))).page(page);
    }

    @RequestMapping("/admin/setting/detail.do")
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

    @RequestMapping("/admin/setting/addOrUpdate.do")
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

        // 更新css模板，静态化css文件
        String cssPath = Tools.getServicePath() + "resources/css/";
        Tools.createFile(cssPath);
        String cssContent = Tools.readFile(cssPath + "setting.tpl");
        for (SettingDto s : settingCache.getAll()) {
            String value = s.getValue();
            if (value != null && (value.toLowerCase().endsWith(".jpg") || value.toLowerCase().endsWith(".png"))) {
                if (!value.startsWith("http://") && !value.startsWith("https://")) {
                    value = config.getDomain() + "/" + value;
                }
            }
            cssContent = cssContent.replace("[" + s.getKey() + "]", value);
        }
        cssContent = cssContent.replace("[MAIN_COLOR_HOVER]",
                Tools.getRgba(0.1f, settingCache.get(S_MAIN_COLOR).getValue()));

        Tools.staticize(cssContent, cssPath + "/setting.css");
        return new JsonResult().data(settingDto);
    }

    @RequestMapping("/admin/setting/delete.do")
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

    @RequestMapping("/admin/setting/changeSequence.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_SETTING)
    public JsonResult changeSequence(@RequestParam String id, @RequestParam String changeId) {
        Setting change = settingService.getById(changeId);
        Setting model = settingService.getById(id);
        int modelSequence = model.getSequence();

        model.setSequence(change.getSequence());
        change.setSequence(modelSequence);

        settingService.update(model);
        settingService.update(change);
        return SUCCESS;
    }

}
