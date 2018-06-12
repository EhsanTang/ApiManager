package cn.crap.controller.user;

import cn.crap.dto.FindPwdDto;
import cn.crap.dto.LoginDto;
import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.SettingDto;
import cn.crap.enumer.LoginType;
import cn.crap.enumer.MyError;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.ThreadContext;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.mybatis.User;
import cn.crap.model.mybatis.UserCriteria;
import cn.crap.service.IEmailService;
import cn.crap.service.custom.CustomProjectService;
import cn.crap.service.custom.CustomUserService;
import cn.crap.service.mybatis.ProjectUserService;
import cn.crap.service.mybatis.RoleService;
import cn.crap.service.mybatis.UserService;
import cn.crap.beans.Config;
import cn.crap.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController extends BaseController{
	@Autowired
	private IEmailService emailService;
	@Autowired
	private UserService userService;
	@Autowired
	private CustomUserService customUserService;
	@Autowired
	private Config config;
	@Autowired
	private CustomProjectService customProjectService;
	@Autowired
	private ProjectUserService projectUserService;
	@Autowired
	private RoleService roleService;
	
	/**
	 * 退出登录
	 */
	@RequestMapping("/back/loginOut.do")
	@ResponseBody
	public JsonResult loginOut() throws IOException {
		String uid = MyCookie.getCookie(IConst.C_COOKIE_USERID);
		userCache.del(uid);
		MyCookie.deleteCookie(IConst.COOKIE_TOKEN);
        return new JsonResult().success();
	}
	
	
	/**
	 * 登陆页面获取基础数据
	 */
	@RequestMapping("/back/preLogin.do")
	@ResponseBody
	public JsonResult preLogin() {
		Map<String, String> settingMap = new HashMap<>();
		for (SettingDto setting : settingCache.getAll()) {
			settingMap.put(setting.getKey(), setting.getValue());
		}
		LoginDto model = new LoginDto();
		model.setUserName(MyCookie.getCookie(IConst.COOKIE_USERNAME));
		model.setRemberPwd(MyCookie.getCookie(IConst.COOKIE_REMBER_PWD));
		if(!model.getRemberPwd().equalsIgnoreCase("no")){
			model.setPassword(MyCookie.getCookie(IConst.COOKIE_PASSWORD, true));
		}else{
			model.setPassword("");
		}
	
		model.setTipMessage("");
		LoginInfoDto user = LoginUserHelper.tryGetUser();
		model.setSessionAdminName(user == null? null:user.getUserName());
		Map<String,Object> returnMap = new HashMap<>();
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
		HttpServletRequest request = ThreadContext.request();
		String id =  Aes.desEncrypt(i);
		String code = stringCache.get(i);
		stringCache.del(i);
		if(code == null || !code.equals(IConst.REGISTER)){
			ThreadContext.request().setAttribute("result", "抱歉，验证邮件已过期，请重新发送！");
		}else{
			User user = userService.getById(id);
			if(user.getId() != null){
				user.setStatus( Byte.valueOf("2") );
				userService.update(user);
				userCache.add(user.getId(), new LoginInfoDto(user, roleService, customProjectService, projectUserService));
				request.setAttribute("title", "恭喜，操作成功！");
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
	public JsonResult sendValidateEmail() throws Exception {
		LoginInfoDto user = LoginUserHelper.getUser();
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
		
		if (MyString.isEmpty(imgCode) || !imgCode.equals(Tools.getImgCode())) {
			throw new MyException(MyError.E000010);
		}

		UserCriteria example = new UserCriteria();
		example.createCriteria().andEmailEqualTo(email).andLoginTypeEqualTo(LoginType.COMMON.getValue());

		List<User> user = userService.selectByExample(example);
		if(user.size()!=1){
			throw new MyException(MyError.E000030);
		}
		emailService.sendFindPwdEmail(user.get(0).getEmail());
		return new JsonResult(1, user.get(0));
	}
	
	/**
	 * 找回密码：重置密码
	 * @param findPwdDto
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 * @throws MyException 
	 */
	@RequestMapping("/account/findPwd/reset.do")
	@ResponseBody
	public JsonResult reset(@ModelAttribute FindPwdDto findPwdDto) throws UnsupportedEncodingException, MessagingException, MyException{
		findPwdDto.check();
		
		String code = stringCache.get(IConst.CACHE_FINDPWD + findPwdDto.getEmail());
		if(code == null || !code.equalsIgnoreCase(findPwdDto.getCode())){
			throw new MyException(MyError.E000031);
		}

		UserCriteria example = new UserCriteria();
		example.createCriteria().andEmailEqualTo(findPwdDto.getEmail()).andLoginTypeEqualTo(LoginType.COMMON.getValue());

		List<User> users = userService.selectByExample(example);
		if(users.size()!=1){
			throw new MyException(MyError.E000030);
		}
		User user = users.get(0);
		user.setPasswordSalt(Tools.getChar(20));
		user.setPassword( MD5.encrytMD5(findPwdDto.getNewPwd(), user.getPasswordSalt()));
		userService.update(user);
		return new JsonResult(1, user);
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
		
		if (settingCache.get(S_VERIFICATIONCODE).getValue().equals("true")) {
			if (MyString.isEmpty(model.getVerificationCode()) || !model.getVerificationCode().equals(Tools.getImgCode())) {
				model.setTipMessage("验证码有误");
				return new JsonResult(1, model);
			}
		}

		UserCriteria example = new UserCriteria();
		example.createCriteria().andEmailEqualTo(model.getUserName().toLowerCase());

		if( userService.countByExample(example) >0 ){
			model.setTipMessage("邮箱已经注册");
			return new JsonResult(1, model);
		}
		
		User user = new User();
		try{
			user.setUserName(model.getUserName().split("@")[0]);
			// 判断用户名是否重名，重名则修改昵称
			example = new UserCriteria();
			example.createCriteria().andUserNameEqualTo(model.getUserName());

			if( userService.countByExample(example) >0 ){
				user.setUserName("ca_"+ model.getUserName().split("@")[0]+"_"+Tools.getChar(5));
			}
			
			user.setEmail(model.getUserName());
			user.setPasswordSalt(Tools.getChar(20));
			user.setPassword(MD5.encrytMD5(model.getPassword(), user.getPasswordSalt()));
			user.setStatus(Byte.valueOf("1"));
			user.setType(Byte.valueOf("1"));
			userService.insert(user);
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
			if (settingCache.get(S_VERIFICATIONCODE).getValue().equals("true")) {
				if(MyString.isEmpty(model.getVerificationCode()) ){
					model.setTipMessage("验证码为空,请刷新浏览器再试！");
					return new JsonResult(1, model);
				}
				if (!model.getVerificationCode().equals(Tools.getImgCode())) {
					model.setTipMessage("验证码有误,请重新输入或刷新浏览器再试！");
					return new JsonResult(1, model);
				}
			}

			// 只允许普通账号方式登陆，第三方绑定必须通过设置密码，并且没有重复的账号、邮箱才能登陆
			List<User> users = null;
			if(model.getUserName().indexOf("@")>0){ // 用户名中不允许有@符号，有@符号代表邮箱登陆
				UserCriteria example = new UserCriteria();
				example.createCriteria().andEmailEqualTo(model.getUserName()).andLoginTypeEqualTo(LoginType.COMMON.getValue());
				users =  userService.selectByExample(example);
			}else{
				UserCriteria example = new UserCriteria();
				example.createCriteria().andUserNameEqualTo(model.getUserName()).andLoginTypeEqualTo(LoginType.COMMON.getValue());
				users =  userService.selectByExample(example);
			}
			
			if (users.size() == 1) {
				User user = users.get(0);
				if (!MyString.isEmpty(user.getPassword()) && MD5.encrytMD5(model.getPassword(), user.getPasswordSalt()).equals(user.getPassword()) ) {
					customUserService.login(model, user);
					return new JsonResult(1, model);
				}
				model.setTipMessage("用户密码有误");
				return new JsonResult(1, model);
			}else{
				model.setTipMessage("用户名不存在");
				return new JsonResult(1, model);
			}
		}catch(Exception e){
			if(e instanceof MyException){
				MyException myException = (MyException) e;
				model.setTipMessage( myException.getMessage() );
			}else{
				log.error(e.getMessage(), e);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				e.printStackTrace(new PrintStream(baos));
				String exceptionDetail[] = baos.toString().split("Caused by:");
				try {
					baos.close();
				} catch (IOException ioe) {}
				
				String cusedBy = "";
				if (exceptionDetail.length > 0) {
					cusedBy = exceptionDetail[exceptionDetail.length - 1].split("\n")[0];
				}
				model.setTipMessage("未知异常，请查看日志：" + cusedBy);
			}
			return new JsonResult(1, model);
		}
	}
	
}
