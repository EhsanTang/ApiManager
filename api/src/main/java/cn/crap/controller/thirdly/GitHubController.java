package cn.crap.controller.thirdly;

import cn.crap.dto.LoginDto;
import cn.crap.dto.thirdly.GitHubUser;
import cn.crap.enumer.LoginType;
import cn.crap.enumer.UserStatus;
import cn.crap.enumer.UserType;
import cn.crap.framework.ThreadContext;
import cn.crap.framework.base.BaseController;
import cn.crap.model.mybatis.User;
import cn.crap.model.mybatis.UserCriteria;
import cn.crap.service.thirdly.GitHubService;
import cn.crap.service.custom.CustomUserService;
import cn.crap.service.mybatis.UserService;
import cn.crap.beans.Config;
import cn.crap.utils.IConst;
import cn.crap.utils.MyCookie;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 前后台共用的Controller
 * @author Ehsan
 *
 */
@Controller
public class GitHubController extends BaseController {
	@Autowired
	private Config config;
	@Autowired
	private GitHubService githHubService;
	@Autowired
	private UserService userService;
	@Autowired
	private CustomUserService customUserService;

	
	/**
	 * gitHub授权
	 * @throws Exception
	 */
	@RequestMapping("/github/authorize.do")
	public void authorize(HttpServletResponse response) throws Exception {
		String authorizeUrl = "https://github.com/login/oauth/authorize?client_id=%s&state=%s";
		String state = Tools.getChar(20);
		stringCache.add( MyCookie.getCookie(IConst.COOKIE_TOKEN) + IConst.CACHE_AUTHORIZE, state);
		response.sendRedirect(String.format(authorizeUrl, config.getClientID(), state));
	}
	@RequestMapping("/github/login.do")
	public String login(@RequestParam String code,@RequestParam String state) throws Exception {
		String myState = stringCache.get(MyCookie.getCookie(IConst.COOKIE_TOKEN) + IConst.CACHE_AUTHORIZE);
		if(myState == null || !myState.equals(state)){
			ThreadContext.request().setAttribute("result", "非法参数，登陆失败！");
			return "WEB-INF/views/result.jsp";
		}else{
			User user = null;
			GitHubUser gitHubUser = githHubService.getUser(githHubService.getAccessToken(code, "").getAccess_token());

			UserCriteria example = new UserCriteria();
			example.createCriteria().andThirdlyIdEqualTo(getThirdlyId(gitHubUser));
			List<User> users = userService.selectByExample(example);

			if(users.size() == 0){
				user = new User();
				user.setUserName( Tools.handleUserName(gitHubUser.getLogin()) );
				user.setTrueName(gitHubUser.getName());

				// 登陆用户类型&邮箱有唯一约束，同一个邮箱在同一个登陆类型下不允许绑定两个账号
				if(!MyString.isEmpty(gitHubUser.getEmail())){
					String email = gitHubUser.getEmail();

					example = new UserCriteria();
					example.createCriteria().andEmailEqualTo(email).andLoginTypeEqualTo(LoginType.GITHUB.getValue());
					List<User> existUser = userService.selectByExample(example);

					if (existUser == null || existUser.size() == 0){
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
			}else{
				user = users.get(0);
			}
			
			// 登陆
			LoginDto model = new LoginDto();
			model.setUserName(user.getUserName());
			model.setRemberPwd("NO");
			customUserService.login(model, user);
			
			ThreadContext.response().sendRedirect("../admin.do");
		}
		return "";
	}

	private String getThirdlyId(GitHubUser gitHubUser) {
		return IConst.GITHUB + gitHubUser.getId();
	}
}
