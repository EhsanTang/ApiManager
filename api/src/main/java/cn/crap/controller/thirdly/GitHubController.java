package cn.crap.controller.thirdly;

import cn.crap.beans.Config;
import cn.crap.dto.LoginDto;
import cn.crap.dto.thirdly.GitHubUser;
import cn.crap.enu.*;
import cn.crap.framework.MyException;
import cn.crap.framework.ThreadContext;
import cn.crap.framework.base.BaseController;
import cn.crap.model.UserPO;
import cn.crap.query.UserQuery;
import cn.crap.service.UserService;
import cn.crap.service.thirdly.GitHubService;
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
public class GitHubController extends BaseController {
    @Autowired
    private GitHubService githHubService;
    @Autowired
    private UserService userService;
    @Autowired
    private static final String authorizeUrl = "https://github.com/login/oauth/authorize?client_id=%s&state=%s&redirect_uri=%s";

    /**
     * gitHub授权
     *
     * @throws Exception
     */
    @RequestMapping("/github/authorize.ignore")
    public void authorize(HttpServletResponse response, String domain) throws Exception {
        String state = Tools.getChar(20);
        response.sendRedirect(String.format(authorizeUrl, Config.clientID, state, getCallBackUrl(domain)));
    }

    @RequestMapping("/github/login.ignore")
    public String login(@RequestParam String code, String domain, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserPO user;
        GitHubUser gitHubUser;
        try {
            gitHubUser = githHubService.getUser(githHubService.getAccessToken(code, getCallBackUrl(domain)).getAccess_token());
        } catch (Throwable e){
            e.printStackTrace();
            request.setAttribute("result", "授权失败，请重试！");
            return ERROR_VIEW;
        }

        List<UserPO> users = userService.select(new UserQuery().setThirdlyId(getThirdlyId(gitHubUser)));

        if (users.size() == 0) {
            user = new UserPO();
            user.setUserName(Tools.handleUserName(gitHubUser.getLogin()));
            user.setTrueName(gitHubUser.getName());

            // 登录用户类型&邮箱有唯一约束，同一个邮箱在同一个登录类型下不允许绑定两个账号
            if (!MyString.isEmpty(gitHubUser.getEmail())) {
                String email = gitHubUser.getEmail();
                List<UserPO> existUser = userService.select(new UserQuery().setLoginType(LoginType.GITHUB.getValue()).setEqualEmail(email));

                if (existUser == null || existUser.size() == 0) {
                    user.setEmail(gitHubUser.getEmail());
                }
            }

            user.setPassword("");
            user.setStatus(UserStatus.INVALID.getType());
            user.setType(UserType.USER.getType());
            user.setAvatarUrl(gitHubUser.getAvatar_url());
            user.setThirdlyId(getThirdlyId(gitHubUser));
            user.setLoginType(LoginType.GITHUB.getValue());
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

    private String getThirdlyId(GitHubUser gitHubUser) {
        return IConst.GITHUB + gitHubUser.getId();
    }

    private String getCallBackUrl(String callDomain) throws Exception{
        return URLEncoder.encode(Tools.getUrlPath() + "/github/login.ignore?domain=" + URLEncoder.encode(callDomain, "utf-8"), "utf-8");
    }
}
