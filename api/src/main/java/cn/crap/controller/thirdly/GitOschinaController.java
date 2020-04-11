package cn.crap.controller.thirdly;

import cn.crap.beans.Config;
import cn.crap.dto.LoginDto;
import cn.crap.dto.thirdly.GitHubUser;
import cn.crap.enu.LoginType;
import cn.crap.enu.MyError;
import cn.crap.enu.UserStatus;
import cn.crap.enu.UserType;
import cn.crap.framework.MyException;
import cn.crap.framework.ThreadContext;
import cn.crap.framework.base.BaseController;
import cn.crap.model.UserPO;
import cn.crap.query.UserQuery;
import cn.crap.service.UserService;
import cn.crap.service.thirdly.OschinaService;
import cn.crap.utils.IConst;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;
import org.apache.http.conn.ConnectTimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @Autowired
    private UserService customUserService;

    /**
     * gitHub授权
     *
     * @throws Exception
     */
    @RequestMapping("/oschina/authorize.ignore")
    public void authorize(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String authorizeUrl = "https://gitee.com/oauth/authorize?client_id=%s&response_type=code&redirect_uri=%s";
        response.sendRedirect(String.format(authorizeUrl, Config.oschinaClientID, Tools.getUrlPath() + "/oschina/login.ignore"));
    }

    @RequestMapping("/oschina/login.ignore")
    public String login(@RequestParam String code, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserPO user = null;
        GitHubUser oschinaUser = null;
        try {
            oschinaUser = oschinaService.getUser(oschinaService.getAccessToken(code, Tools.getUrlPath()).getAccess_token());
        } catch (ConnectTimeoutException e){
            throw new MyException(MyError.E000075);
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

        // 登录
        LoginDto model = new LoginDto();
        model.setUserName(user.getUserName());
        model.setRemberPwd("NO");
        ThreadContext.set(request, response);
        try {
            customUserService.login(model, user);
        }catch (Exception e){
            throw e;
        }finally {
            ThreadContext.clear();
        }

        response.sendRedirect(request.getContextPath() + "/admin.do");
        return null;
    }

    private String getThirdlyId(GitHubUser oschinaUser) {
        return IConst.OSCHINA + oschinaUser.getId();
    }
}
