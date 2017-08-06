package cn.crap.controller.thirdly;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.crap.dto.LoginDto;
import cn.crap.dto.thirdly.GitHubUser;
import cn.crap.enumeration.LoginType;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.table.IMenuService;
import cn.crap.inter.service.table.IUserService;
import cn.crap.inter.service.tool.ICacheService;
import cn.crap.model.User;
import cn.crap.service.thirdly.GitHubService;
import cn.crap.service.thirdly.OschinaService;
import cn.crap.springbeans.Config;
import cn.crap.utils.Const;
import cn.crap.utils.HttpPostGet;
import cn.crap.utils.MyCookie;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

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
	private ICacheService cacheService;
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
			GitHubUser gitHubUser = oschinaService.getUser(oschinaService.getAccessToken(code, config.getDomain()).getAccess_token());
			List<User> users = userService.findByMap(Tools.getMap("thirdlyId",Const.OSCHINA + gitHubUser.getId()), null, null);
			if(users.size() == 0){
				user = new User();
				user.setUserName(gitHubUser.getLogin());
				user.setTrueName(gitHubUser.getName());
				if(!MyString.isEmpty(gitHubUser.getEmail()))
					user.setEmail(gitHubUser.getEmail());
				user.setPassword("");
				user.setStatus(Byte.valueOf("1"));
				user.setType(Byte.valueOf("1"));
				String avatarUrl = gitHubUser.getAvatar_url();
				if (avatarUrl.contains("?")){
					avatarUrl = avatarUrl.substring(0, avatarUrl.indexOf("?"));
				}
				user.setAvatarUrl(avatarUrl);
				user.setThirdlyId(Const.OSCHINA + gitHubUser.getId());
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
}
