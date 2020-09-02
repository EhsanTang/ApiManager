package cn.crap.controller.thirdly;

import cn.crap.beans.Config;
import cn.crap.dto.thirdly.GitHubUser;
import cn.crap.enu.*;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.model.UserPO;
import cn.crap.query.UserQuery;
import cn.crap.service.SettingService;
import cn.crap.service.UserService;
import cn.crap.service.thirdly.OschinaService;
import cn.crap.utils.*;
import org.apache.http.conn.ConnectTimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

/**
 * 前后台共用的Controller
 *
 * @author Ehsan
 */
@Controller
public class GitOschinaController extends BaseController {
    @Autowired
    private OschinaService oschinaService;
    @Autowired
    private UserService userService;

    private static final String authorizeUrl = "https://gitee.com/oauth/authorize?client_id=%s&response_type=code&redirect_uri=%s";

    /**
     * gitHub授权
     *
     * @throws Exception
     */
    @RequestMapping("/oschina/authorize.ignore")
    public void authorize(HttpServletResponse response, String domain) throws Exception {
        response.sendRedirect(String.format(authorizeUrl, Config.oschinaClientID, getCallBackUrl(domain)));
    }

    @RequestMapping("/oschina/login.ignore")
    public String login(HttpServletRequest request, HttpServletResponse response,
                        @RequestParam String code, String domain) throws Exception {
        UserPO user;
        GitHubUser oschinaUser;

        try {
            // callbackUrl 必须与授权时的callbackUrl一致
            oschinaUser = oschinaService.getUser(oschinaService.getAccessToken(code, getCallBackUrl(domain)).getAccess_token());
        } catch (Throwable e){
            e.printStackTrace();
            request.setAttribute("result", "授权失败，请重试！");
            return ERROR_VIEW;
        }

        List<UserPO> users = userService.select(new UserQuery().setThirdlyId(getThirdlyId(oschinaUser)));
        if (users.size() == 0) {
            user = new UserPO();
            user.setUserName(Tools.handleUserName(oschinaUser.getLogin()));
            user.setTrueName(oschinaUser.getName());

            // 登录用户类型&邮箱有唯一约束，同一个邮箱在同一个登录类型下不允许绑定两个账号
            if (!MyString.isEmpty(oschinaUser.getEmail())) {
                List<UserPO> existUser = userService.select(new UserQuery().setEqualEmail(oschinaUser.getEmail()).setLoginType(LoginType.GITHUB.getValue()));
                if (existUser == null || existUser.size() == 0) {
                    user.setEmail(oschinaUser.getEmail());
                }
            }

            user.setPassword("");
            user.setStatus(UserStatus.INVALID.getType());
            user.setType(UserType.USER.getType());
            String avatarUrl = oschinaUser.getAvatar_url();
            if (avatarUrl.contains("?")) {
                avatarUrl = avatarUrl.substring(0, avatarUrl.indexOf("?"));
            }
            user.setAvatarUrl(avatarUrl);
            user.setThirdlyId(getThirdlyId(oschinaUser));
            user.setLoginType(LoginType.OSCHINA.getValue());
            userService.insert(user);
        } else {
            user = users.get(0);
        }

        // 跳转至访问的域名
        domain = URLDecoder.decode(domain, "utf-8");
        String authCode = Aes.encrypt(user.getId() + "|" + DateFormartUtil.getDateByFormat(DateFormartUtil.YYYY_MM_DD_HH_mm_ss));
        userService.updateAttribute(user.getId(), AttributeEnum.LOGIN_AUTH_CODE.getKey(), authCode, new UserPO());
        response.sendRedirect(domain + "/user/mock.do?authCode=" + authCode);
        return null;
    }

    private String getThirdlyId(GitHubUser oschinaUser) {
        return IConst.OSCHINA + oschinaUser.getId();
    }

    private String getCallBackUrl(String callDomain) throws Exception{
        return URLEncoder.encode(Tools.getUrlPath() + "/oschina/login.ignore?domain=" + URLEncoder.encode(callDomain, "utf-8"), "utf-8");
    }
}
