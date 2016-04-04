package cn.crap.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.framework.MyException;
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
import cn.crap.utils.Cache;
import cn.crap.utils.Const;
import cn.crap.utils.MD5;
import cn.crap.utils.MyCookie;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;
import cn.crap.utils.ValidateCodeService;


@Scope("prototype")
@Controller
public class IndexController extends BaseController<User>{
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
	private Logger log = Logger.getLogger(getClass());
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
	public String login(@RequestParam String userPassword,@RequestParam String userName,@RequestParam(defaultValue="YES") String remberPwd
			,String verificationCode) throws IOException {
		try {
			request.setAttribute("remberPwd", MyCookie.getCookie(Const.COOKIE_REMBER_PWD, request));
			request.setAttribute("userName", userName);
			if(Cache.getSetting(Const.SETTING_VERIFICATIONCODE).getValue().equals("true")){
				if(MyString.isEmpty(verificationCode)||!verificationCode.equals(request.getSession().getAttribute(Const.SESSION_IMG_CODE).toString())){
					request.setAttribute("tipMessage", "验证码输入有误");
					return "admin/login";
				}
			}
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
			request.setAttribute("tipMessage", "账号或密码有误");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			request.setAttribute("tipMessage", e.getMessage());
		}
		return "admin/login";
	}
	@RequestMapping({ "/loginOut.do"})
	public String loginOut() throws IOException {
		try{
		request.getSession().invalidate();
		request.setAttribute("tipMessage", "退出成功！");
		request.setAttribute("userName", MyCookie.getCookie(Const.COOKIE_USERNAME, request));
		request.setAttribute("password", MyCookie.getCookie(Const.COOKIE_PASSWORD, true, request));
		request.setAttribute("remberPwd", MyCookie.getCookie(Const.COOKIE_REMBER_PWD, request));
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return "admin/login";
	}
	/**
	 * @return
	 */
	@RequestMapping({"/preLogin.do"})
	public String preLogin(HttpServletResponse response) {
		try{
			request.setAttribute("userName", MyCookie.getCookie(Const.COOKIE_USERNAME, request));
			request.setAttribute("password", MyCookie.getCookie(Const.COOKIE_PASSWORD, true, request));
			request.setAttribute("remberPwd", MyCookie.getCookie(Const.COOKIE_REMBER_PWD, request));
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return "admin/login";
	}
	/**
	 * 
	 * @param code 需要显示的pick code
	 * @param key 可选参数：根据具体情况定义，如当为模块是，key代表父id
	 * @param radio 是否为单选
	 * @param def 默认值
	 * @param tag 保存选中结果的id
	 * @param tagName 显示名称的输入框id
	 * @param notNull 是否可以为空：当为单选，且notNull=false是，则可以选着为空
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "pick.do")
	public String pickOut(String code, String key,@RequestParam(defaultValue="true") String radio, String def, String tag,String tagName, String notNull) throws Exception {
		if(MyString.isEmpty(radio)){
			radio = "true";
		}
		List<Pick> picks = new ArrayList<Pick>();
		String pickContent = menuService.pick(picks, radio, code, key,def, notNull);
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
	@RequestMapping("getImgCode.do")
	@ResponseBody
	public void getImgvcode() throws IOException{
		HttpSession session = request.getSession();
		// 设置response，输出图片客户端不缓存
		response.setDateHeader("Expires", 0);
		response.addHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
		response.setContentType("image/jpeg");
		session.setAttribute(Const.SESSION_OLD_IMG_CODE, session.getAttribute(Const.SESSION_IMG_CODE));
		ServletOutputStream out = response.getOutputStream();
		ValidateCodeService vservice = new ValidateCodeService();
		request.getSession().setAttribute(Const.SESSION_IMG_CODE, vservice.getCode());
		try {
	    	vservice.write(out);
			out.flush();
		}finally {
			out.close();
		}
	}
}
