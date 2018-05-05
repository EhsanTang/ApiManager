package cn.crap.controller.admin;

import cn.crap.adapter.SettingAdapter;
import cn.crap.beans.Config;
import cn.crap.dto.SettingDto;
import cn.crap.enumer.MyError;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.mybatis.Setting;
import cn.crap.model.mybatis.SettingCriteria;
import cn.crap.service.custom.CustomSettingService;
import cn.crap.service.mybatis.SettingService;
import cn.crap.utils.Page;
import cn.crap.utils.TableField;
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
    private CustomSettingService customSettingService;
    @Autowired
    private Config config;
    private final static String[] indexUrls = new String[]{"index.do", "front/", "project.do", "dashboard.htm"};

    /**
     * @param currentPage 当前页
     * @return
     */
    @RequestMapping("/setting/list.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_SETTING)
    public JsonResult list(String key, String remark, Integer currentPage) {
        Page page = new Page(currentPage);

        SettingCriteria example = new SettingCriteria();
        SettingCriteria.Criteria criteria = example.createCriteria();
        if (key != null) {
            criteria.andMkeyLike(key);
        }
        if (remark != null) {
            criteria.andRemarkLike(remark);
        }
        example.setOrderByClause(TableField.SORT.SEQUENCE_DESC);
        example.setLimitStart(page.getStart());
        example.setMaxResults(page.getSize());

        page.setAllRow(settingService.countByExample(example));
        return new JsonResult().data(SettingAdapter.getDto(settingService.selectByExample(example))).page(page);
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
            cssContent = cssContent.replace("{{settings." + s.getKey() + "}}", value);
        }
        Tools.staticize(cssContent, cssPath + "/setting.css");
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

    @RequestMapping("/back/setting/changeSequence.do")
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
