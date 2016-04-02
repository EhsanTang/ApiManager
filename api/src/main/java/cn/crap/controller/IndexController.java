package cn.crap.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.framework.BiyaoBizException;
import cn.crap.framework.JsonResult;
import cn.crap.framework.Pick;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.IErrorService;
import cn.crap.inter.service.IMenuService;
import cn.crap.inter.service.IModuleService;
import cn.crap.inter.service.IRoleService;
import cn.crap.inter.service.IUserService;
import cn.crap.model.Role;
import cn.crap.model.User;
import cn.crap.utils.Const;
import cn.crap.utils.MD5;
import cn.crap.utils.MyCookie;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;


@Scope("prototype")
@Controller
public class IndexController extends BaseController{
	@Autowired
	IMenuService menuService;
	@Autowired
	private IModuleService moduleService;
	@Autowired
	private IErrorService errorService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IUserService userService;
	
	@AuthPassport
	@RequestMapping({"/index.do" })
	public String showHomePage() throws Exception {
		return "admin/index";
	}
	
	@RequestMapping({"/web.do" })
	public String web() throws Exception {
		return "web/index";
	}
	@RequestMapping({ "/login.do"})
	public String login(HttpServletResponse response,@RequestParam String userPassword,@RequestParam String userName,@RequestParam(defaultValue="YES") String remberPwd) throws IOException {
		try {
			User user = new User();
			user.setUserName(userName);
			user.setStatus(Byte.valueOf("1"));
			List<User> users = userService.findByExample(user);
			if(users.size()>0){
				user = users.get(0);
				if(userName.equals(user.getUserName())&&MD5.encrytMD5(userPassword).equals(user.getPassword())){
					request.setAttribute("userName", userName);
					request.getSession().setAttribute(Const.SESSION_ADMIN, userName);
					StringBuilder sb = new StringBuilder(","+user.getAuth()+",");
					if(user.getRoleId()!=null&&!user.getRoleId().equals("")){
						List<Role> roles = roleService.findByMap(Tools.getMap("roleId|in",Tools.getIdsFromField(user.getRoleId())),null,null);
						for(Role role:roles){
							roleService.getAuthFromRole(sb, role);
						}
					}
					//将角色组合：数据类型+模块存入session，拦截器中将根据注解类型判断用户是否有权限操作数据
					request.getSession().setAttribute(Const.SESSION_ADMIN_AUTH,sb.toString());
					//菜单页面将根据用户的roleIds判断是否显示菜单
					request.getSession().setAttribute(Const.SESSION_ADMIN_ROLEIDS, user.getRoleId());
					request.getSession().setAttribute(Const.SESSION_ADMIN_TRUENAME, user.getTrueName());
					MyCookie.addCookie(Const.COOKIE_USERNAME, userName, response);
					MyCookie.addCookie(Const.COOKIE_REMBER_PWD, remberPwd, response);
					if(remberPwd.equals("YES")){
						MyCookie.addCookie(Const.COOKIE_PASSWORD, userPassword, true, response);
					}else{
						MyCookie.deleteCookie(Const.COOKIE_PASSWORD, request, response);
					}
					response.sendRedirect("index.do");
					return "admin/index";
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("tipMessage", "账号或密码有误");
		return "admin/login";
	}
	@RequestMapping({ "/loginOut.do"})
	public String loginOut() throws IOException {
		request.getSession().invalidate();
		request.setAttribute("tipMessage", "退出成功！");
		request.setAttribute("userName", MyCookie.getCookie(Const.COOKIE_USERNAME, request));
		request.setAttribute("password", MyCookie.getCookie(Const.COOKIE_PASSWORD, true, request));
		request.setAttribute("remberPwd", MyCookie.getCookie(Const.COOKIE_REMBER_PWD, request));
		return "admin/login";
	}
	/**
	 * @return
	 */
	@RequestMapping({"/preLogin.do"})
	public String preLogin(HttpServletResponse response) {
		request.setAttribute("userName", MyCookie.getCookie(Const.COOKIE_USERNAME, request));
		request.setAttribute("password", MyCookie.getCookie(Const.COOKIE_PASSWORD, true, request));
		request.setAttribute("remberPwd", MyCookie.getCookie(Const.COOKIE_REMBER_PWD, request));
		return "admin/login";
	}
	@RequestMapping(value = "pick.do")
	public String pickOut(String code, String key, String type,@RequestParam(defaultValue="true") String radio, String def, String tag,String tagName) throws Exception {
		if(MyString.isEmpty(radio)){
			radio = "true";
		}
		List<Pick> picks = new ArrayList<Pick>();
		String pickContent = menuService.pick(picks, radio, code, key,def);
		request.setAttribute("radio", radio);
		request.setAttribute("picks", picks);
		request.setAttribute("tag", tag);
		request.setAttribute("def", def);
		request.setAttribute("iCallBack", getParam("iCallBack", "voidFunction"));
		request.setAttribute("iCallBackParam", getParam("iCallBackParam", ""));
		request.setAttribute("tagName",tagName);
		request.setAttribute("pickContent", pickContent);
		
		return "admin/pick";
	}
}
