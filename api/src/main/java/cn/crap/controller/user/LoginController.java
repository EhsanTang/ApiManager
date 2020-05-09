package cn.crap.controller.user;

import cn.crap.beans.Config;
import cn.crap.dto.FindPwdDto;
import cn.crap.dto.LoginDto;
import cn.crap.dto.LoginInfoDto;
import cn.crap.enu.AttributeEnum;
import cn.crap.enu.LoginType;
import cn.crap.enu.MyError;
import cn.crap.enu.SettingEnum;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.ThreadContext;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.UserPO;
import cn.crap.query.UserQuery;
import cn.crap.service.IEmailService;
import cn.crap.service.UserService;
import cn.crap.service.tool.StringCache;
import cn.crap.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class LoginController extends BaseController{
	@Autowired
	private IEmailService emailService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserService customUserService;
	@Autowired
	private StringCache stringCache;

	/**
	 * 退出登录
	 */
	@RequestMapping("/loginOut.do")
	@ResponseBody
	public JsonResult loginOut() throws IOException {
		String uid = MyCookie.getCookie(IConst.C_COOKIE_USERID);
		userCache.del(uid);
		MyCookie.deleteCookie(IConst.COOKIE_TOKEN);
        return new JsonResult().success();
	}
	
	
	/**
	 * 登录页面获取基础数据
	 */
	@RequestMapping("/preLogin.do")
	@ResponseBody
	public JsonResult preLogin() {
        LoginDto model = new LoginDto();
		model.setUserName(MyCookie.getCookie(IConst.COOKIE_USERNAME));
		model.setRemberPwd(MyCookie.getCookie(IConst.COOKIE_REMBER_PWD));
		if(!model.getRemberPwd().equalsIgnoreCase("no")){
			model.setPassword(MyCookie.getCookie(IConst.COOKIE_PASSWORD, true));
		}else{
			model.setPassword("");
		}
		LoginInfoDto user = LoginUserHelper.tryGetUser();
		model.setSessionAdminName(user == null? null:user.getUserName());
		return new JsonResult(1, model);
	}
	
	/**
	 * 注册页面获取基础数据
	 */
	@RequestMapping("/preRegister.do")
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
	@RequestMapping("/validateEmail.do")
	public String validateEmail(@RequestParam String i) throws MyException {
		HttpServletRequest request = ThreadContext.request();
		String id =  Aes.desEncrypt(i);
		String code = stringCache.get(i);
		stringCache.del(i);
		if(code == null || !code.equals(IConst.REGISTER)){
			ThreadContext.request().setAttribute("result", "抱歉，验证邮件已过期，请重新发送！");
		}else{
			UserPO user = userService.get(id);
			if(user.getId() != null){
				user.setStatus( Byte.valueOf("2") );
				userService.update(user);
				userCache.add(user.getId(), new LoginInfoDto(user));
				request.setAttribute("title", "恭喜，操作成功！");
				request.setAttribute("result", "验证通过！");
			}else{
				request.setAttribute("result", "抱歉，账号不存在！");
			}
		}
		return ERROR_VIEW;
	}
	

	/**
	 * 发送验证邮件
	 * @return
	 * @throws MessagingException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/sendValidateEmail.do")
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
	@RequestMapping("/findPwd/sendEmail.do")
	@ResponseBody
	public JsonResult findPwdSendEmail(String email, String imgCode) throws UnsupportedEncodingException, MessagingException, MyException{
        if (MyString.isEmpty(email)) {
            throw new MyException(MyError.E000065, "邮箱不能为空");
        }
		if (MyString.isEmpty(imgCode) || !imgCode.equals(Tools.getImgCode())) {
			throw new MyException(MyError.E000010);
		}

		UserQuery query = new UserQuery().setEqualEmail(email).setLoginType(LoginType.COMMON.getValue());

		List<UserPO> user = userService.select(query);
		if(user.size()!=1){
			throw new MyException(MyError.E000030);
		}
		emailService.sendFindPwdEmail(user.get(0).getEmail());
		return new JsonResult(1, user.get(0)).tip("邮件发送成功");
	}
	
	/**
	 * 找回密码：重置密码
	 * @param findPwdDto
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 * @throws MyException 
	 */
	@RequestMapping("/findPwd/reset.do")
	@ResponseBody
	public JsonResult reset(@ModelAttribute FindPwdDto findPwdDto) throws UnsupportedEncodingException, MessagingException, MyException{
		findPwdDto.check();
		
		String code = stringCache.get(IConst.CACHE_FINDPWD + findPwdDto.getEmail());
		if(code == null || !code.equalsIgnoreCase(findPwdDto.getCode())){
			throw new MyException(MyError.E000031);
		}

        UserQuery query = new UserQuery().setEqualEmail(findPwdDto.getEmail()).setLoginType(LoginType.COMMON.getValue());

		List<UserPO> users = userService.select(query);
		if(users.size()!=1){
			throw new MyException(MyError.E000030);
		}
		UserPO user = users.get(0);
		user.setPasswordSalt(Tools.getChar(20));
		user.setPassword( MD5.encrytMD5(findPwdDto.getNewPwd(), user.getPasswordSalt()));
		userService.update(user);
		return new JsonResult(1, user).tip("重置密码成功，请重新登录");
	}
	
	
	@RequestMapping("/register.do")
	@ResponseBody
	public JsonResult register(@ModelAttribute LoginDto loginDto) throws MyException, UnsupportedEncodingException, MessagingException {
		if( !Config.openRegister ){
		    throw new MyException(MyError.E000065, "系统尚未开放注册功能，请联系管理员开放");
		}
		if( MyString.isEmpty(loginDto.getEmail())){
            throw new MyException(MyError.E000065, "邮箱不能为空");
		}
		if( MyString.isEmpty(loginDto.getPassword()) || loginDto.getPassword().length()<6 ){
            throw new MyException(MyError.E000065, "密码不能为空，且长度不能少于6位");
        }
		if( !loginDto.getPassword().equals(loginDto.getRpassword()) ){
            throw new MyException(MyError.E000065, "两次输入密码不一致");
		}
		
		if (settingCache.get(S_VERIFICATIONCODE).getValue().equals("true")) {
			if (MyString.isEmpty(loginDto.getVerificationCode()) || !loginDto.getVerificationCode().equals(Tools.getImgCode())) {
                throw new MyException(MyError.E000065, "验证码有误");
			}
		}

        UserQuery query = new UserQuery().setEqualEmail(loginDto.getEmail().toLowerCase());
		if( userService.count(query) >0 ){
            throw new MyException(MyError.E000065, "邮箱已经注册");
		}
		
		UserPO user = new UserPO();
        user.setUserName(loginDto.getEmail().split("@")[0]);
        // 判断用户名是否重名，重名则修改昵称
        query = new UserQuery().setEqualUserName(user.getUserName());

        if (userService.count(query) > 0) {
            user.setUserName("ca_" + user.getUserName() + "_" + Tools.getChar(5));
        }

        user.setEmail(loginDto.getEmail());
        user.setPasswordSalt(Tools.getChar(20));
        user.setPassword(MD5.encrytMD5(loginDto.getPassword(), user.getPasswordSalt()));
        user.setStatus(Byte.valueOf("1"));
        user.setType(Byte.valueOf("1"));
        userService.insert(user);

        try{
			emailService.sendRegisterEmail(user.getEmail(), user.getId());
		}catch(Exception e){
            log.error("注册验证邮件发送失败:" + user.getUserName(), e);
            // throw new MyException(MyError.E000065, "注册验证邮件发送失败");
		}
		loginDto.setId(user.getId());
		return new JsonResult(1, loginDto);
		
	}
	
	/**
	 * 登录，该方法必须在根目录下，即/user/login.do 前不能添加其他路径，如：back/user/login.do，否者设置cookie会失败
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws MyException
	 */
	@RequestMapping("/login.do")
	@ResponseBody
	public JsonResult JsonResult(@ModelAttribute LoginDto model) throws MyException {
		String uuid = MyCookie.getCookie(IConst.COOKIE_UUID);

		// 已经错过一次，则需要输入密码
		if (stringCache.get(IConst.C_NEED_VERIFICATION_IMG + uuid) != null){
			if (settingCache.get(S_VERIFICATIONCODE).getValue().equals("true")) {
				if(MyString.isEmpty(model.getVerificationCode()) ){
					throw new MyException(MyError.E000065, "验证码有误");
				}
				if (!model.getVerificationCode().equals(Tools.getImgCode())) {
					throw new MyException(MyError.E000065, "验证码有误");
				}
			}
		}


		// 只允许普通账号方式登录，第三方绑定必须通过设置密码，并且没有重复的账号、邮箱才能登录
		List<UserPO> users = null;
		if(model.getUserName().indexOf("@")>0){ // 用户名中不允许有@符号，有@符号代表邮箱登录
			UserQuery query = new UserQuery().setEqualEmail(model.getUserName()).setLoginType(LoginType.COMMON.getValue());
			users = userService.select(query);
		}else{
			UserQuery query = new UserQuery().setEqualUserName(model.getUserName()).setLoginType(LoginType.COMMON.getValue());
			users =  userService.select(query);
		}

		if (users.size() == 1) {
			UserPO user = users.get(0);
			if (!MyString.isEmpty(user.getPassword()) && MD5.encrytMD5(model.getPassword(), user.getPasswordSalt()).equals(user.getPassword()) ) {
				customUserService.login(model, user);
				return new JsonResult().success();
			}
            stringCache.add(IConst.C_NEED_VERIFICATION_IMG + uuid, "true");
			throw new MyException(MyError.E000014);
		}else{
            stringCache.add(IConst.C_NEED_VERIFICATION_IMG + uuid, "true");
            throw new MyException(MyError.E000013);
		}
	}


	/**
	 * @param request
	 * @param response
	 * @param userId
	 * @param authCode 免登秘钥
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mock.do")
	public String mock(HttpServletRequest request, HttpServletResponse response, String userId, String authCode) throws Exception{
		// authCode免登陆：有效时间1分钟，解决多域名授权问题
		if (MyString.isNotEmpty(authCode)){
			String desAuthCode = Aes.desEncrypt(authCode);
			userId = desAuthCode.split("\\|")[0];
			String dateStr = desAuthCode.split("\\|")[1];

            UserPO user = userService.get(userId);
            String attr = AttributeUtils.getAttr(user.getAttributes(), AttributeEnum.LOGIN_AUTH_CODE);

            if (!authCode.equals(attr)){
                request.setAttribute("result", "非法请求，授权码无效！");
                return ERROR_VIEW;
            }

            if (DateFormartUtil.getCurrentTimeMillis(dateStr) + 5 * 60 *1000 > System.currentTimeMillis()){
				return mockUser(request, response, user);
			} else {
				request.setAttribute("result", "抱歉，授权码失效，请重试！");
				return ERROR_VIEW;
			}
		}

		// 最高管理员能模拟所有用户
		if (MyString.isNotEmpty(userId) && LoginUserHelper.isSuperAdmin()){
			return mockUser(request, response, userService.get(userId));
		}

		// 普通用户只能免登陆指定测试用户
		userId = settingCache.get(SettingEnum.NO_NEED_LOGIN_USER.getKey()).getValue();
		return mockUser(request, response, userService.get(userId));
	}

	private String mockUser(HttpServletRequest request, HttpServletResponse response,UserPO user) throws MyException, IOException {
		if (user == null){
			request.setAttribute("title", "抱歉，系统不允许未登录试用！");
			request.setAttribute("result", "抱歉，系统不允许未登录试用！");
			return ERROR_VIEW;
		}

		LoginDto loginDto = new LoginDto();
		loginDto.setRemberPwd("NO");
		loginDto.setUserName(user.getUserName());
		customUserService.login(loginDto, user);
		response.sendRedirect(request.getContextPath() + "/admin.do");
		return null;
	}
}
