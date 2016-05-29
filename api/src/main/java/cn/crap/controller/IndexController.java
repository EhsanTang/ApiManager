package cn.crap.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.crap.dto.LoginDto;
import cn.crap.dto.PickDto;
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
import cn.crap.utils.ValidateCodeService;

@Scope("prototype")
@Controller
public class IndexController extends BaseController<User> {
	@Autowired
	IMenuService menuService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IUserService userService;

	/**
	 * 默认页面，重定向web.do，不直接进入web.do是因为进入默认地址，浏览器中的href不会改变， 会导致用户第一点击闪屏
	 * 
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/home.do")
	public void home(HttpServletResponse response) throws Exception {
		response.sendRedirect("web.do");
	}

	/**
	 * 跳转至前段主页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/web.do")
	public String web() throws Exception {
		return "resources/html/frontHtml/index.html";
	}

	/**
	 * 初始化前端页面
	 * 
	 */
	@RequestMapping("/frontInit.do")
	@ResponseBody
	public JsonResult frontInit() throws Exception {
		Map<String, String> settingMap = new HashMap<String, String>();
		for (Setting setting : Cache.getSetting()) {
			settingMap.put(setting.getKey(), setting.getValue());
		}
		returnMap.put("settingMap", settingMap);
		returnMap.put("menuList", menuService.getLeftMenu(map));
		returnMap.put("sessionAdminName", request.getSession().getAttribute(Const.SESSION_ADMIN));
		return new JsonResult(1, returnMap);
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

	@RequestMapping("/loginOut.do")
	public String loginOut() throws IOException {
		request.getSession().invalidate();
		return "resources/html/frontHtml/index.html";
	}

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
	 * 
	 * @param code
	 *            需要显示的pick code
	 * @param key
	 *            可选参数：根据具体情况定义，如当为模块是，key代表父id
	 * @param radio
	 *            是否为单选
	 * @param def
	 *            默认值
	 * @param tag
	 *            保存选中结果的id
	 * @param tagName
	 *            显示名称的输入框id
	 * @param notNull
	 *            是否可以为空：当为单选，且notNull=false是，则可以选着为空
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "pick.do")
	public String pickOut(String code, String key, @RequestParam(defaultValue = "true") String radio, String def,
			String tag, String tagName, String notNull) throws Exception {
		if (MyString.isEmpty(radio)) {
			radio = "true";
		}
		List<PickDto> picks = new ArrayList<PickDto>();
		String pickContent = menuService.pick(picks, radio, code, key, def, notNull);
		request.setAttribute("radio", radio);
		request.setAttribute("picks", picks);
		request.setAttribute("tag", tag);
		request.setAttribute("def", def);
		request.setAttribute("iCallBack", getParam("iCallBack", "voidFunction"));
		request.setAttribute("iCallBackParam", getParam("iCallBackParam", ""));
		request.setAttribute("tagName", tagName);
		request.setAttribute("pickContent", pickContent);
		return "WEB-INF/views/pick.jsp";
	}

	@RequestMapping("getImgCode.do")
	@ResponseBody
	public void getImgvcode() throws IOException {
		// 设置response，输出图片客户端不缓存
		response.setDateHeader("Expires", 0);
		response.addHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
		response.setContentType("image/jpeg");
		ServletOutputStream out = response.getOutputStream();
		ValidateCodeService vservice = new ValidateCodeService();
		request.getSession().setAttribute(Const.SESSION_IMG_CODE, vservice.getCode());
		request.getSession().setAttribute(Const.SESSION_IMGCODE_TIMES, "0");
		try {
			vservice.write(out);
			out.flush();
		} finally {
			out.close();
		}
	}

	/**
	 * 
	 * @param 跳转至指定页面
	 * @return
	 */
	@RequestMapping("go.do")
	public String go(@RequestParam String p) {
		return p;
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
