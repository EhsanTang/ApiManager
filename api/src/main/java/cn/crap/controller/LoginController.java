package cn.crap.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.dto.LoginDto;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.IMenuService;
import cn.crap.inter.service.IRoleService;
import cn.crap.inter.service.IUserService;
import cn.crap.model.Role;
import cn.crap.model.Setting;
import cn.crap.model.User;
import cn.crap.utils.Cache;
import cn.crap.utils.Const;
import cn.crap.utils.MD5;
import cn.crap.utils.MyCookie;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Scope("prototype")
@Controller
public class LoginController extends BaseController<User> {
	@Autowired
	IMenuService menuService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IUserService userService;

	
	/**
	 * 登陆页面获取基础数据
	 */
	@RequestMapping("/preLogin.do")
	@ResponseBody
	public JsonResult preLogin() {
		Map<String, String> settingMap = new HashMap<String, String>();
		for (Setting setting : Cache.getSetting()) {
			settingMap.put(setting.getKey(), setting.getValue());
		}
		LoginDto model = new LoginDto();
		model.setUserName(MyCookie.getCookie(Const.COOKIE_USERNAME, request));
		model.setPassword(MyCookie.getCookie(Const.COOKIE_PASSWORD, true, request));
		model.setRemberPwd(MyCookie.getCookie(Const.COOKIE_REMBER_PWD, request));
		model.setTipMessage("");
		Object obj = request.getSession().getAttribute(Const.SESSION_ADMIN);
		model.setSessionAdminName(obj == null? null:obj.toString());
		
		returnMap.put("settingMap", settingMap);
		returnMap.put("model", model);
		return new JsonResult(1, returnMap);
	}
	
	/**
	 * 登陆
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws MyException
	 */
	@RequestMapping("/login.do")
	@ResponseBody
	public JsonResult JsonResult(@ModelAttribute LoginDto model) throws IOException, MyException {
			if (Cache.getSetting(Const.SETTING_VERIFICATIONCODE).getValue().equals("true")) {
				if (MyString.isEmpty(model.getVerificationCode()) || !model.getVerificationCode().equals(Tools.getImgCode(request))) {
					model.setTipMessage("验证码有误");
					return new JsonResult(1, model);
				}
			}
			User user = new User();
			user.setUserName(model.getUserName());
			user.setStatus(Byte.valueOf("1"));// 未设置值得时候，status为基本类型变量，会自动赋值为0
			List<User> users = userService.findByExample(user);
			if (users.size() > 0) {
				user = users.get(0);
				if (model.getUserName().equals(user.getUserName()) && MD5.encrytMD5(model.getPassword()).equals(user.getPassword())) {
					request.getSession().setAttribute(Const.SESSION_ADMIN, model.getUserName());
					StringBuilder sb = new StringBuilder("," + user.getAuth() + ",");
					if (user.getRoleId() != null && !user.getRoleId().equals("")) {
						List<Role> roles = roleService.findByMap(
								Tools.getMap("roleId|in", Tools.getIdsFromField(user.getRoleId())), null, null);
						for (Role role : roles) {
							roleService.getAuthFromRole(sb, role);
						}
					}
					// 将角色组合：数据类型+模块存入session，拦截器中将根据注解类型判断用户是否有权限操作数据
					request.getSession().setAttribute(Const.SESSION_ADMIN_AUTH, sb.toString());
					// 菜单页面将根据用户的roleIds判断是否显示菜单
					request.getSession().setAttribute(Const.SESSION_ADMIN_ROLEIDS, user.getRoleId());
					request.getSession().setAttribute(Const.SESSION_ADMIN_TRUENAME, user.getTrueName());
					
					MyCookie.addCookie(Const.COOKIE_USERNAME, model.getUserName(), response);
					MyCookie.addCookie(Const.COOKIE_REMBER_PWD, model.getRemberPwd() , response);
					if (model.getRemberPwd().equals("YES")) {
						MyCookie.addCookie(Const.COOKIE_PASSWORD, model.getPassword(), true, response);
					} else {
						MyCookie.deleteCookie(Const.COOKIE_PASSWORD, request, response);
					}
					model.setSessionAdminName(model.getUserName());
					return new JsonResult(1, model);
				}
				model.setTipMessage("用密码有误");
				return new JsonResult(1, model);
			}else{
				model.setTipMessage("用户名不存在");
				return new JsonResult(1, model);
			}
	}
	
	
	/**
	 * 后台管理主页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@AuthPassport
	@RequestMapping("/index.do")
	public String showHomePage() throws Exception {
		return "resources/html/backHtml/index.html";
	}
	
	/**
	 * 后台页面初始化
	 * 
	 */
	@RequestMapping("/backInit.do")
	@ResponseBody
	public JsonResult backInit() throws Exception {
		Map<String, String> settingMap = new HashMap<String, String>();
		for (Setting setting : Cache.getSetting()) {
			settingMap.put(setting.getKey(), setting.getValue());
		}
		returnMap.put("settingMap", settingMap);
		returnMap.put("menuList", menuService.getLeftMenu(map));
		returnMap.put("sessionAdminName", request.getSession().getAttribute(Const.SESSION_ADMIN));
		returnMap.put("sessionAdminAuthor", request.getSession().getAttribute(Const.SESSION_ADMIN_AUTH));
		returnMap.put("sessionAdminRoleIds", request.getSession().getAttribute(Const.SESSION_ADMIN_ROLEIDS));
		
		return new JsonResult(1, returnMap);
	}


	@RequestMapping("/loginOut.do")
	public String loginOut() throws IOException {
		request.getSession().invalidate();
		return "resources/html/frontHtml/index.html";
	}

	@Override
	public JsonResult detail(User model) {
		return null;
	}

	@Override
	public JsonResult changeSequence(String id, String changeId) {
		return null;
	}
}
