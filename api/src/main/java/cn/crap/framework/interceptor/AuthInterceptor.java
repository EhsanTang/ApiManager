package cn.crap.framework.interceptor;

import cn.crap.dto.LoginInfoDto;
import cn.crap.enu.MyError;
import cn.crap.framework.MyException;
import cn.crap.framework.ThreadContext;
import cn.crap.service.tool.UserCache;
import cn.crap.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
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
	private static String serviceIp = "null";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean threadHasRequest = true;
        try {

            if (!handler.getClass().isAssignableFrom(HandlerMethod.class)) {
                return true;
            }

            if (RequestMethod.OPTIONS.name().equals(request.getMethod())){
                return false;
            }

            if (ThreadContext.request() == null) {
                threadHasRequest = false;
            }

            if (!threadHasRequest) {
                ThreadContext.set(request, response);
            }
            /**
             * 未登录用户唯一识别，验证码等需要
             */
            String uuid = MyCookie.getCookie(IConst.COOKIE_UUID, false);
            if (MyString.isEmpty(uuid)) {
                uuid = System.currentTimeMillis() + Tools.getChar(10);
                MyCookie.addCookie(IConst.COOKIE_UUID, uuid);
            }

            try {
                if (serviceIp == null){serviceIp = InetAddress.getLocalHost().getHostAddress();}
                response.setHeader("serviceIp", serviceIp);
            } catch (Exception e) {
                e.printStackTrace();
                serviceIp = "can not get service ip";
                response.setHeader("serviceIp", serviceIp);
            }

            /**
             * 不需要登录的接口
             */
            AuthPassport authPassport = ((HandlerMethod) handler).getMethodAnnotation(AuthPassport.class);
            if (authPassport == null || authPassport.validate() == false) {
                return true;
            }


            /**
             * 前端没有传递token，未登录
             * 前端传递的 uid 和 token不一致，未登录
             */
            LoginInfoDto user = LoginUserHelper.tryGetUser();
            if (user == null) {
                if (request.getRequestURI().endsWith("admin.do")) {
                    response.sendRedirect(request.getContextPath() + "/loginOrRegister.do#/login");
                    return false;
                } else {
                    throw new MyException(MyError.E000021);
                }
            }

            // 每次访问，将用户登录有效信息延长
            String uid = MyCookie.getCookie(IConst.C_COOKIE_USERID);
            userCache.add(uid, user);

            if (!authPassport.authority().equals("")) {
                if (!LoginUserHelper.checkAuthPassport(authPassport.authority())){
                    throw new MyException(MyError.E000022);
                }
            }

            return true;
        }catch (Exception e){
            throw e;
        }finally {
            if (!threadHasRequest) {
                ThreadContext.clear();
            }
        }
    }
	

}