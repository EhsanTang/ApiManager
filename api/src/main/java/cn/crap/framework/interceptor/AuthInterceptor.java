package cn.crap.framework.interceptor;

import cn.crap.beans.Config;
import cn.crap.dto.LoginInfoDto;
import cn.crap.enumer.MyError;
import cn.crap.framework.MyException;
import cn.crap.service.tool.UserCache;
import cn.crap.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;



/**
 * 对登录状态进行拦截
 * @author 
 *
 */
public class AuthInterceptor extends HandlerInterceptorAdapter{
	@Autowired
	private UserCache userCache;
	@Autowired
	private Config config;
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!handler.getClass().isAssignableFrom(HandlerMethod.class)){
        	return true;
        }

		/**
		 * 未登陆用户唯一识别，验证码等需要
		 */
		String uuid = MyCookie.getCookie(IConst.COOKIE_UUID, false);
		if( MyString.isEmpty(uuid) ){
		    uuid = System.currentTimeMillis() + Tools.getChar(10);
			MyCookie.addCookie(IConst.COOKIE_UUID, uuid);
		}

		try{
			// 返回服务器ip
			response.setHeader("serviceIp", InetAddress.getLocalHost().getHostAddress());
		}catch(Exception e){
			e.printStackTrace();
			response.setHeader("serviceIp", "服务器配置异常，无法获取服务器IP");
		}

		/**
		 * 不需要登陆的接口
		 */
		AuthPassport authPassport = ((HandlerMethod) handler).getMethodAnnotation(AuthPassport.class);
		if(authPassport == null || authPassport.validate() == false) {
			return true;
		}


		String token = MyCookie.getCookie(IConst.COOKIE_TOKEN);
		String uid = MyCookie.getCookie(IConst.C_COOKIE_USERID);

		/**
		 * 前端没有传递token，未登录
		 * 前端传递的 uid 和 token不一致，未登陆
		 */
        LoginInfoDto user = userCache.get(uid);
		if(user == null || MyString.isEmpty(token) || MyString.isEmpty(uid) || !Aes.desEncrypt(token).equals(uid)){
			if(request.getRequestURI().endsWith("admin.do")) {
                response.sendRedirect("loginOrRegister.do#/login");
                return false;
            }else {
                throw new MyException(MyError.E000021);
            }
		}

		// 每次访问，将用户登录有效信息延长
        userCache.add(uid, user);

		if(!authPassport.authority().equals("")){
			return LoginUserHelper.checkAuthPassport(authPassport.authority());
		}else{
			return true;
		}
	}
	

}