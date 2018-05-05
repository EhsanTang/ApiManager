package cn.crap.controller.thirdly;

import java.util.List;

import cn.crap.enumer.UserStatus;
import cn.crap.enumer.UserType;
import cn.crap.framework.ThreadContext;
import cn.crap.model.mybatis.UserCriteria;
import cn.crap.service.custom.CustomUserService;
import cn.crap.service.mybatis.UserService;
import cn.crap.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.crap.dto.LoginDto;
import cn.crap.dto.thirdly.GitHubUser;
import cn.crap.enumer.LoginType;
import cn.crap.framework.base.BaseController;
import cn.crap.model.mybatis.User;
import cn.crap.service.thirdly.OschinaService;
import cn.crap.beans.Config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 前后台共用的Controller
 * @author Ehsan
 *
 */
@Controller
public class GitOschinaController extends BaseController{
	@Autowired
	private Config config;
	@Autowired
	private OschinaService oschinaService;
	@Autowired
	private UserService userService;
	@Autowired
	private CustomUserService customUserService;
	/**
	 * gitHub授权
	 * @throws Exception
	 */
	@RequestMapping("/oschina/authorize.do")
	public void authorize(HttpServletResponse response) throws Exception {
		String authorizeUrl = "https://git.oschina.net/oauth/authorize?client_id=%s&response_type=code&redirect_uri=%s";
		response.sendRedirect(String.format(authorizeUrl, config.getOschinaClientID(), config.getDomain()+"/oschina/login.do"));
	}
	@RequestMapping("/oschina/login.do")
	public String login(@RequestParam String code) throws Exception {
			User user = null;
			GitHubUser oschinaUser = oschinaService.getUser(oschinaService.getAccessToken(code, config.getDomain()).getAccess_token());
		HttpServletResponse response = ThreadContext.response();
		UserCriteria example = new UserCriteria();
		example.createCriteria().andThirdlyIdEqualTo(getThirdlyId(oschinaUser));

		List<User> users = userService.selectByExample(example);
			if(users.size() == 0){
				user = new User();
				user.setUserName( Tools.handleUserName(oschinaUser.getLogin()) );
				user.setTrueName(oschinaUser.getName());

				// 登陆用户类型&邮箱有唯一约束，同一个邮箱在同一个登陆类型下不允许绑定两个账号
				if(!MyString.isEmpty(oschinaUser.getEmail())){
					String email = oschinaUser.getEmail();

					example = new UserCriteria();
					example.createCriteria().andEmailEqualTo(email).andLoginTypeEqualTo(LoginType.GITHUB.getValue());
					List<User> existUser = userService.selectByExample(example);
					if (existUser == null || existUser.size() == 0){
						user.setEmail(oschinaUser.getEmail());
					}
				}
				user.setPassword("");
				user.setStatus(UserStatus.INVALID.getType());
				user.setType(UserType.USER.getType());
				String avatarUrl = oschinaUser.getAvatar_url();
				if (avatarUrl.contains("?")){
					avatarUrl = avatarUrl.substring(0, avatarUrl.indexOf("?"));
				}
				user.setAvatarUrl(avatarUrl);
				user.setThirdlyId(getThirdlyId(oschinaUser));
				user.setLoginType(LoginType.OSCHINA.getValue());
				userService.insert(user);
			}else{
				user = users.get(0);
			}
			
			// 登陆
			LoginDto model = new LoginDto();
			model.setUserName(user.getUserName());
			model.setRemberPwd("NO");
			customUserService.login(model, user);
			
			response.sendRedirect("../admin.do");
		return "";
	}

	private String getThirdlyId(GitHubUser oschinaUser) {
		return IConst.OSCHINA + oschinaUser.getId();
	}
}
