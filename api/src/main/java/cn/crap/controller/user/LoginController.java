package cn.crap.controller.user;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.dto.FindPwdDto;
import cn.crap.dto.LoginDto;
import cn.crap.dto.LoginInfoDto;
import cn.crap.enumeration.LoginType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.table.IMenuService;
import cn.crap.inter.service.table.IProjectService;
import cn.crap.inter.service.table.IProjectUserService;
import cn.crap.inter.service.table.IRoleService;
import cn.crap.inter.service.table.IUserService;
import cn.crap.inter.service.tool.ICacheService;
import cn.crap.inter.service.tool.IEmailService;
import cn.crap.model.Setting;
import cn.crap.model.User;
import cn.crap.springbeans.Config;
import cn.crap.utils.Aes;
import cn.crap.utils.Const;
import cn.crap.utils.MD5;
import cn.crap.utils.MyCookie;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Controller
public class LoginController extends BaseController<User> {
	@Autowired
	IMenuService menuService;
	@Autowired
	private ICacheService cacheService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IEmailService emailService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IProjectService projectService;
	@Autowired
	private IProjectUserService projectUserService;
	@Autowired
	private Config config;
	
	/**
	 * 退出登录
	 */
	@RequestMapping("/back/loginOut.do")
	public String loginOut() throws IOException {
		String uid = MyCookie.getCookie(Const.COOKIE_USERID, false, request);
		cacheService.delObj(Const.CACHE_USER + uid);
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
		LoginDto model = new LoginDto();
		model.setUserName(MyCookie.getCookie(Const.COOKIE_USERNAME, request));
		model.setPassword(MyCookie.getCookie(Const.COOKIE_PASSWORD, true, request));
		model.setRemberPwd(MyCookie.getCookie(Const.COOKIE_REMBER_PWD, request));
		model.setTipMessage("");
		LoginInfoDto user = (LoginInfoDto) Tools.getUser();
		model.setSessionAdminName(user == null? null:user.getUserName());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("model", model);
		return new JsonResult(1, returnMap);
	}
	
	/**
	 * 注册页面获取基础数据
	 */
	@RequestMapping("/back/preRegister.do")
	@ResponseBody
	public JsonResult preRegister() {
		LoginDto model = new LoginDto();
		return new JsonResult(1, model);
	}
	
	/**
	 * 验证邮箱是否正确
	 * @return
	 * @throws MessagingException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/back/validateEmail.do")
	public String validateEmail(@RequestParam String i) throws UnsupportedEncodingException, MessagingException {
		String id =  Aes.desEncrypt(i);
		String code = cacheService.getStr(i);
		cacheService.delStr(i);
		if(code == null || !code.equals(Const.REGISTER)){
			request.setAttribute("result", "抱歉，验证邮件已过期，请重新发送！");
		}else{
			User user = userService.get(id);
			if(user.getId() != null){
				user.setStatus( Byte.valueOf("2") );
				userService.update(user);
				cacheService.setObj(Const.CACHE_USER + user.getId(), 
						new LoginInfoDto(user, roleService, projectService, projectUserService), config.getLoginInforTime());
				request.setAttribute("result", "验证通过！");
			}else{
				request.setAttribute("result", "抱歉，账号不存在！");
			}
		}
		return "WEB-INF/views/result.jsp";
	}
	

	/**
	 * 发送验证邮件
	 * @return
	 * @throws MessagingException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/back/sendValidateEmail.do")
	@ResponseBody
	@AuthPassport
	public JsonResult sendValidateEmail() throws UnsupportedEncodingException, MessagingException {
		LoginInfoDto user = Tools.getUser();
		emailService.sendRegisterEmail(user.getEmail(), user.getId());
		return new JsonResult(1, null);
	}
	
	/**
	 * 找回密码发送邮件
	 * @return
	 * @throws MessagingException 
	 * @throws UnsupportedEncodingException 
	 * @throws MyException 
	 */
	@RequestMapping("/account/findPwd/sendEmail.do")
	@ResponseBody
	public JsonResult findPwdSendEmail(@RequestParam String email, @RequestParam String imgCode) throws UnsupportedEncodingException, MessagingException, MyException{
		
		if (MyString.isEmpty(imgCode) || !imgCode.equals(Tools.getImgCode(request))) {
			throw new MyException("000010");
		}
		
		List<User> user = userService.findByMap(Tools.getMap("email",email,"loginType", LoginType.COMMON.getValue()), null, null);
		if(user.size()!=1){
			throw new MyException("000030");
		}
		emailService.sendFindPwdEmail(user.get(0).getEmail());
		return new JsonResult(1, user.get(0));
	}
	
	/**
	 * 找回密码：重置密码
	 * @param email
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 * @throws MyException 
	 */
	@RequestMapping("/account/findPwd/reset.do")
	@ResponseBody
	public JsonResult reset(@ModelAttribute FindPwdDto findPwdDto) throws UnsupportedEncodingException, MessagingException, MyException{
		findPwdDto.check();
		
		String code = cacheService.getStr(Const.CACHE_FINDPWD + findPwdDto.getEmail());
		if(code == null || !code.equalsIgnoreCase(findPwdDto.getCode())){
			throw new MyException("000031");
		}
		
		List<User> user = userService.findByMap(Tools.getMap("email",findPwdDto.getEmail(), "loginType", LoginType.COMMON.getValue()), null, null);
		if(user.size()!=1){
			throw new MyException("000030");
		}
		user.get(0).setPassword( MD5.encrytMD5(findPwdDto.getNewPwd()) );
		userService.update(user.get(0));
		return new JsonResult(1, user.get(0));
	}
	
	
	@RequestMapping("/back/register.do")
	@ResponseBody
	public JsonResult register(@ModelAttribute LoginDto model) throws MyException, UnsupportedEncodingException, MessagingException {
		if( !config.isOpenRegister() ){
			model.setTipMessage("系统尚未开放注册功能，请联系管理员开放");
			return new JsonResult(1, model); 
		}
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
		
		if( userService.getCount(Tools.getMap("email", model.getUserName().toLowerCase())) >0 ){
			model.setTipMessage("邮箱已经注册");
			return new JsonResult(1, model);
		}
		
		User user = new User();
		try{
			user.setUserName(model.getUserName().split("@")[0]);
			// 判断用户名是否重名，重名则修改昵称
			if( userService.getCount(Tools.getMap("userName", user.getUserName())) >0 ){
				user.setUserName("ca_"+ model.getUserName().split("@")[0]+"_"+Tools.getChar(5));
			}
			
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
		try{
			emailService.sendRegisterEmail(user.getEmail(), user.getId());
		}catch(Exception e){
			log.error("注册验证邮件发送失败:" + user.getUserName(), e);
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
		try{
			if (cacheService.getSetting(Const.SETTING_VERIFICATIONCODE).getValue().equals("true")) {
				if (MyString.isEmpty(model.getVerificationCode()) || !model.getVerificationCode().equals(Tools.getImgCode(request))) {
					model.setTipMessage("验证码有误");
					return new JsonResult(1, model);
				}
			}

			// 只允许普通账号方式登陆，第三方绑定必须通过设置密码，并且没有重复的账号、邮箱才能登陆
			List<User> users = null;
			if(model.getUserName().indexOf("@")>0){
				users =  userService.findByMap(Tools.getMap("email", model.getUserName().toLowerCase(),"loginType" , LoginType.COMMON.getValue()), null, null);
			}else{
				users =  userService.findByMap(Tools.getMap("userName", model.getUserName(),"loginType" , LoginType.COMMON.getValue()), null, null);
			}
			
			if (users.size() == 1) {
				User user = users.get(0);
				if (!MyString.isEmpty(user.getPassword()) && MD5.encrytMD5(model.getPassword()).equals(user.getPassword()) && 
						(model.getUserName().equals(user.getUserName()) || model.getUserName().toLowerCase().equals(user.getEmail())) ) {
					userService.login(model, user, request, response);
					return new JsonResult(1, model);
				}
				model.setTipMessage("用户密码有误");
				return new JsonResult(1, model);
			}else{
				model.setTipMessage("用户名不存在");
				return new JsonResult(1, model);
			}
		}catch(Exception e){
			e.printStackTrace();
			model.setTipMessage("未知异常，请查看日志："+e.getMessage());
			return new JsonResult(1, model);
		}
	}
	
	
}
