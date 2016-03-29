package cn.crap.framework.base;

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

import cn.crap.framework.Pick;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.inter.ErrorInfoService;
import cn.crap.inter.MenuInfoService;
import cn.crap.inter.ModuleInfoService;
import cn.crap.inter.RoleInfoService;
import cn.crap.inter.UserInfoService;
import cn.crap.model.RoleInfoModel;
import cn.crap.model.UserInfoModel;
import cn.crap.utils.Const;
import cn.crap.utils.MyCookie;
import cn.crap.utils.Tools;


@Scope("prototype")
@Controller
public class IndexController extends BaseController{
	@Autowired
	MenuInfoService menuInfoService;
	@Autowired
	private ModuleInfoService moduleInfoService;
	@Autowired
	private ErrorInfoService errorInfoService;
	@Autowired
	private RoleInfoService roleInfoService;
	@Autowired
	private UserInfoService userInfoService;
	
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
	public String login(HttpServletResponse response,@RequestParam String userPassword,@RequestParam String userName) throws IOException {
		try {
			UserInfoModel user = new UserInfoModel();
			user.setUserName(userName);
			List<UserInfoModel> users = userInfoService.findByExample(user);
			if(users.size()>0){
				user = users.get(0);
				if(userName.equals(user.getUserName())&&userPassword.equals(user.getPassword())){
					request.setAttribute("userName", userName);
					request.getSession().setAttribute(Const.SESSION_ADMIN, userName);
					StringBuilder sb = new StringBuilder(","+user.getAuth()+",");
					if(user.getRoleId()!=null&&!user.getRoleId().equals("")){
						List<RoleInfoModel> roles = roleInfoService.findByMap(Tools.getMap("roleId|in",Tools.getIdsFromField(user.getRoleId())),null,null);
						for(RoleInfoModel role:roles){
							//权限继承
							roleInfoService.getSubAuth(sb, role);
						}
					}
					//将角色组合：数据类型+模块存入session，拦截器中将根据注解类型判断用户是否有权限操作数据
					request.getSession().setAttribute(Const.SESSION_ADMIN_AUTH,sb.toString());
					//菜单页面将根据用户的roleIds判断是否显示菜单
					request.getSession().setAttribute(Const.SESSION_ADMIN_ROLEIDS, user.getRoleId());
					request.getSession().setAttribute(Const.SESSION_ADMIN_TRUENAME, user.getTrueName());
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
		return "admin/login";
	}
	

	/**
	 * 
	 * @param p
	 * @return
	 */
	@RequestMapping({"/go.do"})
	public String showHomePage(HttpServletResponse response,String p) {
		@SuppressWarnings("unchecked")
		HashMap<String, String> params = getRequestParams();
		StringBuilder sb = new StringBuilder();
		if (params != null) {
			for (String key : params.keySet()) {
				sb.append("&" + key + "=" + params.get(key));
			}
		}
		MyCookie.addCookie("params", sb.toString(), response);
		return p;
	}
	@RequestMapping(value = "pick.do")
	public String pickOut(String code, String key, String type, String radio, String def, String tag) throws Exception {
		List<Pick> picks = new ArrayList<Pick>();
		menuInfoService.pick(picks, radio, code, key);
		request.setAttribute("radio", radio);
		request.setAttribute("picks", picks);
		request.setAttribute("tag", tag);
		request.setAttribute("def", def);
		request.setAttribute("iCallBack", getParam("iCallBack", "voidFunction"));
		request.setAttribute("iCallBackParam", getParam("iCallBackParam", ""));
		return "admin/pick";
	}
}
