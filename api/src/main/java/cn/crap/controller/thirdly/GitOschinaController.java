package cn.crap.controller.thirdly;

import java.util.List;

import cn.crap.enumeration.UserStatus;
import cn.crap.enumeration.UserType;
import cn.crap.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.crap.dto.LoginDto;
import cn.crap.dto.thirdly.GitHubUser;
import cn.crap.enumeration.LoginType;
import cn.crap.framework.base.BaseController;
import cn.crap.service.IMenuService;
import cn.crap.service.IUserService;
import cn.crap.model.User;
import cn.crap.service.imp.thirdly.OschinaService;
import cn.crap.springbeans.Config;

/**
 * 前后台共用的Controller
 * @author Ehsan
 *
 */
@Controller
public class GitOschinaController extends BaseController<User> {
	@Autowired
	IMenuService menuService;
	@Autowired
	private Config config;
	@Autowired
	private OschinaService oschinaService;
	@Autowired
	private IUserService userService;
	
	/**
	 * gitHub授权
	 * @throws Exception
	 */
	@RequestMapping("/oschina/authorize.do")
	public void authorize() throws Exception {
		String authorizeUrl = "https://git.oschina.net/oauth/authorize?client_id=%s&response_type=code&redirect_uri=%s";
		response.sendRedirect(String.format(authorizeUrl, config.getOschinaClientID(), config.getDomain()+"/oschina/login.do"));
	}
	@RequestMapping("/oschina/login.do")
	public String login(@RequestParam String code) throws Exception {
			User user = null;
			GitHubUser oschinaUser = oschinaService.getUser(oschinaService.getAccessToken(code, config.getDomain()).getAccess_token());
			List<User> users = userService.findByMap(Tools.getMap(TableField.USER.THIRDLY_ID, getThirdlyId(oschinaUser)), null, null);
			if(users.size() == 0){
				user = new User();
				user.setUserName( Tools.handleUserName(oschinaUser.getLogin()) );
				user.setTrueName(oschinaUser.getName());

				// 登陆用户类型&邮箱有唯一约束，同一个邮箱在同一个登陆类型下不允许绑定两个账号
				if(!MyString.isEmpty(oschinaUser.getEmail())){
					String email = oschinaUser.getEmail();
					List<User> existUser = userService.findByMap(Tools.getMap(TableField.USER.EMAIL, email, TableField.USER.USER_TYPE, LoginType.OSCHINA.getValue()), null, null);
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
				userService.save(user);
			}else{
				user = users.get(0);
			}
			
			// 登陆
			LoginDto model = new LoginDto();
			model.setUserName(user.getUserName());
			model.setRemberPwd("NO");
			userService.login(model, user, request, response);
			
			response.sendRedirect("../admin.do");
		return "";
	}

	private String getThirdlyId(GitHubUser oschinaUser) {
		return Const.OSCHINA + oschinaUser.getId();
	}
}
