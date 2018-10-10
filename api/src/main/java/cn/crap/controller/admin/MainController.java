package cn.crap.controller.admin;

import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.SettingDto;
import cn.crap.enumer.SettingStatus;
import cn.crap.framework.JsonResult;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.service.ISearchService;
import cn.crap.service.SettingService;
import cn.crap.utils.HttpPostGet;
import cn.crap.utils.IConst;
import cn.crap.utils.LoginUserHelper;
import cn.crap.utils.Tools;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController extends BaseController {
    @Autowired
    private ISearchService luceneService;
    @Autowired
    private SettingService settingService;
    private final static String JS_COMPRESS_URL = "http://tool.oschina.net/action/jscompress/js_compress?munge=0&linebreakpos=5000";
    private final static String CSS_COMPRESS_URL = "http://tool.oschina.net/action/jscompress/css_compress?linebreakpos=5000";


    /**
     * admin dashboard
     * 后台管理主页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/admin.do")
    @AuthPassport
    public String showHomePage() throws Exception {
        return "resources/html/admin/index.html";
    }

    /**
     * 获取系统最新信息
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/property.do")
    @AuthPassport(authority = C_AUTH_SETTING)
    @ResponseBody
    public JsonResult property() throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("properties", config);
        // 从crapApi获取版本信息
        try {
            String crapApiInfo =
                    HttpPostGet.get("http://api.crap.cn/mock/trueExam.do?id=c107b205-c365-4050-8fa9-dbb7a38b3d11", null, null);
            JSONObject json = JSONObject.fromObject(crapApiInfo);
            if (json.getInt("success") == 1) {
                json = json.getJSONObject("data");
                returnMap.put("crapApiInfo",
                        Tools.getStrMap("latestVersion", json.getString("latestVersion"),
                                "latestVersion", json.getString("latestVersion"),
                                "addFunction", json.getString("addFunction"),
                                "updateUrl", json.getString("updateUrl"),
                                "noticeUrl", json.getString("noticeUrl"),
                                "notice", json.getString("notice")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(1, returnMap);
    }

    /**
     * 登陆 or 注册页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/loginOrRegister.do")
    public String loginOrRegister() throws Exception {
        return "resources/html/admin/loginOrRegister.html";
    }


    /**
     * 删除错误提示
     */
    @RequestMapping("/back/closeErrorTips.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_ADMIN)
    public JsonResult closeErrorTips() throws Exception {
        stringCache.del(C_CACHE_ERROR_TIP);
        return new JsonResult(1, null);
    }

    /**
     * 后台页面初始化
     */
    @RequestMapping("/back/init.do")
    @ResponseBody
    @AuthPassport
    public JsonResult init(HttpServletRequest request) throws Exception {
        Map<String, String> settingMap = new HashMap<>();
        for (SettingDto setting : settingCache.getAll()) {
            if (SettingStatus.COMMON.getStatus().equals(setting.getStatus())) {
                settingMap.put(setting.getKey(), setting.getValue());
            }
        }
        settingMap.put(IConst.DOMAIN, config.getDomain());

        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("settingMap", settingMap);
        LoginInfoDto user = LoginUserHelper.getUser();
        returnMap.put("sessionAdminName", user.getUserName());
        returnMap.put("sessionAdminAuthor", user.getAuthStr());
        returnMap.put("sessionAdminRoleIds", user.getRoleId());
        returnMap.put("sessionAdminId", user.getId());
        returnMap.put("errorTips", stringCache.get(C_CACHE_ERROR_TIP));

        return new JsonResult(1, returnMap);
    }

    /**
     * 重建索引，只有最高管理员才具有该权限
     */
    @ResponseBody
    @RequestMapping("/back/rebuildIndex.do")
    @AuthPassport(authority = C_SUPER)
    public JsonResult rebuildIndex() throws Exception {
        return new JsonResult(1, luceneService.rebuild());
    }

    /**
     * 清除缓存，只有最高管理员才具有该权限
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/back/flushDB.do")
    @AuthPassport(authority = C_SUPER)
    public JsonResult flushDb() {
        projectCache.flushDB();
        stringCache.flushDB();
        moduleCache.flushDB();
        settingCache.flushDB();
        objectCache.flushDB();
        return new JsonResult().success();
    }

    @ResponseBody
    @RequestMapping("/admin/compress.do")
    @AuthPassport(authority = C_SUPER)
    public JsonResult compress() throws Exception{
        String cssFileUrls[] = new String[]{"base.css", "crapApi.css", "setting.css", "admin.css"};
        String jsFileUrls[] = new String[]{"app.js", "core.js", "crapApi.js", "global.js", "router.js", "userCtrls.js",
                "userRouter.js", "visitorControllers.js", "visitorRouter.js"};

        String cssBaseFileUrl = Tools.getServicePath() + "resources/css/";
        String jsBaseFileUrl = Tools.getServicePath() + "resources/js/";

        for (String cssFileUrl : cssFileUrls){
            compress(CSS_COMPRESS_URL, cssBaseFileUrl, cssFileUrl);
        }

        for (String jsFileUrl : jsFileUrls){
            compress(JS_COMPRESS_URL, jsBaseFileUrl, jsFileUrl);
        }

        return new JsonResult().success();
    }

    private void compress(String compressUrl, String cssBaseFileUrl, String cssFileUrl) throws Exception {
        String cssSource = Tools.readFile(cssBaseFileUrl + cssFileUrl);
        String cssCompress = HttpPostGet.postBody(compressUrl, cssSource, null);
        JSONObject jsonObject = JSONObject.fromObject(cssCompress);
        Tools.staticize(jsonObject.getString("result"), cssBaseFileUrl + cssFileUrl);
    }


}
