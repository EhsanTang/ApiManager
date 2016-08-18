package cn.crap.controller.back;

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
import cn.crap.dto.LoginInfoDto;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.ICacheService;
import cn.crap.inter.service.IDataCenterService;
import cn.crap.inter.service.IMenuService;
import cn.crap.inter.service.IRoleService;
import cn.crap.inter.service.IUserService;
import cn.crap.model.Setting;
import cn.crap.model.User;
import cn.crap.utils.Aes;
import cn.crap.utils.Config;
import cn.crap.utils.Const;
import cn.crap.utils.MD5;
import cn.crap.utils.MyCookie;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Scope("prototype")
@Controller
public class BackLoginController extends BaseController<User> {
	@Autowired
	IMenuService menuService;
	@Autowired
	private ICacheService cacheService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IDataCenterService dataCenterService;
	/**
	 * 退出登录
	 */
	@RequestMapping("/back/loginOut.do")
	public String loginOut() throws IOException {
		String token = MyCookie.getCookie(Const.COOKIE_TOKEN, false, request);
		cacheService.delObj(Const.CACHE_USER + token);
		MyCookie.deleteCookie(Const.COOKIE_TOKEN, request, response);
		return "resources/html/frontHtml/index.html";
	}
	
	
	/**
	 * 登陆页面获取基础数据
	 */
	@RequestMapping("/back/preLogin.do")
	@ResponseBody
	public JsonResult preLogin() {
		Map<String, String> settingMap = new HashMap<String, String>();
		for (Setting setting : cacheService.getSetting()) {
			settingMap.put(setting.getKey(), setting.getValue());
		}
		String token = MyCookie.getCookie(Const.COOKIE_TOKEN, false, request);
		LoginDto model = new LoginDto();
		model.setUserName(MyCookie.getCookie(Const.COOKIE_USERNAME, request));
		model.setPassword(MyCookie.getCookie(Const.COOKIE_PASSWORD, true, request));
		model.setRemberPwd(MyCookie.getCookie(Const.COOKIE_REMBER_PWD, request));
		model.setTipMessage("");
		LoginInfoDto user = (LoginInfoDto) cacheService.getObj(Const.CACHE_USER + token);
		model.setSessionAdminName(user == null? null:user.getUserName());
		
		returnMap.put("model", model);
		return new JsonResult(1, returnMap);
	}
	
	/**
	 * 登陆页面获取基础数据
	 */
	@RequestMapping("/back/preRegister.do")
	@ResponseBody
	public JsonResult preRegister() {
		LoginDto model = new LoginDto();
		return new JsonResult(1, model);
	}
	@RequestMapping("/back/register.do")
	@ResponseBody
	public JsonResult register(@ModelAttribute LoginDto model) throws MyException {
		if( MyString.isEmpty(model.getUserName())){
			model.setTipMessage("邮箱不能为空");
			return new JsonResult(1, model);
		}
		if( MyString.isEmpty(model.getPassword()) || model.getPassword().length()<6 ){
			model.setTipMessage("密码不能为空，且长度不能少于6位");
			return new JsonResult(1, model);
		}
		if( !model.getPassword().equals(model.getRpassword()) ){
			model.setTipMessage("两次输入密码不一致");
			return new JsonResult(1, model);
		}
		
		if (cacheService.getSetting(Const.SETTING_VERIFICATIONCODE).getValue().equals("true")) {
			if (MyString.isEmpty(model.getVerificationCode()) || !model.getVerificationCode().equals(Tools.getImgCode(request))) {
				model.setTipMessage("验证码有误");
				return new JsonResult(1, model);
			}
		}
		
		if( userService.getCount(Tools.getMap("userName", model.getUserName())) >0 ){
			model.setTipMessage("邮箱已经注册");
			return new JsonResult(1, model);
		}
		
		if( userService.getCount(Tools.getMap("email", model.getUserName())) >0 ){
			model.setTipMessage("邮箱已经注册");
			return new JsonResult(1, model);
		}
		
		User user = new User();
		try{
			user.setUserName(model.getUserName());
			user.setEmail(model.getUserName());
			user.setPassword(MD5.encrytMD5(model.getPassword()));
			user.setStatus(Byte.valueOf("1"));
			user.setType(Byte.valueOf("1"));
			userService.save(user);
		}catch(Exception e){
			e.printStackTrace();
			model.setTipMessage(e.getMessage());
			model.setId(null);
			return new JsonResult(1, model);
		}
		
		model.setId(user.getId());
		return new JsonResult(1, model);
		
	}
	
	/**
	 * 登陆，该方法必须在根目录下，即/login.do 前不能添加其他路径，如：back/login.do，否者设置cookie会失败
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws MyException
	 */
	@RequestMapping("/login.do")
	@ResponseBody
	public JsonResult JsonResult(@ModelAttribute LoginDto model) throws IOException, MyException {
			if (cacheService.getSetting(Const.SETTING_VERIFICATIONCODE).getValue().equals("true")) {
				if (MyString.isEmpty(model.getVerificationCode()) || !model.getVerificationCode().equals(Tools.getImgCode(request))) {
					model.setTipMessage("验证码有误");
					return new JsonResult(1, model);
				}
			}

			List<User> users = userService.findByMap(Tools.getMap("userName", model.getUserName()), null, null);
			if (users.size() > 0) {
				User user = users.get(0);
				if (model.getUserName().equals(user.getUserName()) && MD5.encrytMD5(model.getPassword()).equals(user.getPassword())) {
					String token  = Aes.encrypt(user.getId());
					MyCookie.addCookie(Const.COOKIE_TOKEN, token, response);
					
					// 将用户信息存入缓存
					cacheService.setObj(Const.CACHE_USER + token, new LoginInfoDto(user, roleService, dataCenterService), Config.getLoginInforTime());
					
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
				model.setTipMessage("用户密码有误");
				return new JsonResult(1, model);
			}else{
				model.setTipMessage("用户名不存在");
				return new JsonResult(1, model);
			}
	}
	
}
