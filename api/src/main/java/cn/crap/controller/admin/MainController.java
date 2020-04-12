package cn.crap.controller.admin;

import cn.crap.beans.Config;
import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.SearchDto;
import cn.crap.enu.MyError;
import cn.crap.enu.ProjectPermissionEnum;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.query.SearchQuery;
import cn.crap.service.ISearchService;
import cn.crap.service.tool.SystemService;
import cn.crap.utils.HttpPostGet;
import cn.crap.utils.LoginUserHelper;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController extends BaseController {
    @Autowired
    private ISearchService luceneService;
    @Autowired
    private SystemService systemService;

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
    @RequestMapping("/admin/property.do")
    @AuthPassport(authority = C_AUTH_SETTING)
    @ResponseBody
    public JsonResult property() throws Exception {
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> properties = new HashMap<>();
        properties.put("domain", Tools.getUrlPath());
        properties.put("openRegister", Config.openRegister);
        properties.put("luceneSearchNeedLogin", Config.luceneSearchNeedLogin);
        properties.put("openRegister", Config.openRegister);
        properties.put("canRepeatUrl", Config.canRepeatUrl);
        properties.put("cacheTime", Config.cacheTime);
        properties.put("loginInforTime", Config.loginInforTime);
        properties.put("fileSize", Config.fileSize);
        properties.put("fileType", Config.fileType);
        properties.put("imageType", Config.imageType);
        properties.put("mail", Config.mail);
        properties.put("clientID", Config.clientID);

        returnMap.put("properties", properties);
        // 从crapApi获取版本信息
        try {
            String crapApiInfo =
                    HttpPostGet.get("http://api.crap.cn/mock/trueExam.do?id=c107b205-c365-4050-8fa9-dbb7a38b3d11&cache=true", null, null);
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
     * 登录 or 注册页面
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
    @RequestMapping("/admin/closeErrorTips.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_ADMIN)
    public JsonResult closeErrorTips() throws Exception {
        stringCache.del(C_CACHE_ERROR_TIP);
        return new JsonResult(1, null);
    }

    /**
     * 后台页面初始化
     */
    @RequestMapping({"/admin/init.do", "back/init.do"})
    @ResponseBody
    @AuthPassport
    public JsonResult init(HttpServletRequest request) throws Exception {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("settingMap", settingCache.getCommonMap());
        LoginInfoDto user = LoginUserHelper.getUser();
        returnMap.put("sessionAdminName", user.getUserName());
        returnMap.put("adminPermission", user.getAuthStr());
        returnMap.put("sessionAdminId", user.getId());
        returnMap.put("attributes", user.getAttributes());
        returnMap.put("errorTips", stringCache.get(C_CACHE_ERROR_TIP));

        return new JsonResult(1, returnMap);
    }

    /**
     * 重建索引，只有最高管理员才具有该权限
     */
    @ResponseBody
    @RequestMapping("/admin/rebuildIndex.do")
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
    @RequestMapping("/admin/flushDB.do")
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
        try {
            systemService.compressSource();
        } catch (Throwable e){
            log.error("压缩js、css文件异常", e);
        }
        systemService.mergeSource();
        return new JsonResult().success();
    }

    @ResponseBody
    @RequestMapping("/admin/cleanLog.do")
    @AuthPassport(authority = C_SUPER)
    public JsonResult cleanLog() throws Exception{
        systemService.cleanLog();
        return new JsonResult().success();
    }

    /**
     * 搜索目前只支持项目下搜索
     * 跨项目涉及到用户、成员权限问题，暂不实现
     * 搜索
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/user/search.do")
    @AuthPassport
    public JsonResult search(@ModelAttribute SearchQuery query) throws Exception{
        if (query.getProjectId() == null){
            throw new MyException(MyError.E000056);
        }
        checkPermission(query.getProjectId(), ProjectPermissionEnum.READ);

        String keyword = (query.getKeyword() == null ? "" : query.getKeyword().trim());
        query.setKeyword(keyword.length() > 200 ? keyword.substring(0, 200) : keyword.trim());
        
        List<SearchDto> search = luceneService.search(query);
        Page page = new Page(query);
        return new JsonResult().success().data(search).page(page);
    }

}
