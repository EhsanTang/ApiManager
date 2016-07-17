package cn.crap.framework.auth;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.crap.framework.MyException;
import cn.crap.inter.dao.ICacheDao;
import cn.crap.utils.Const;
import cn.crap.utils.GetBeanBySetting;
import cn.crap.utils.MyCookie;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;



/**
 * 对登录状态进行拦截验�?
 * @author 
 *
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
        	AuthPassport authPassport = ((HandlerMethod) handler).getMethodAnnotation(AuthPassport.class);
        	String token = MyCookie.getCookie(Const.COOKIE_TOKEN, false, request);
        	 // 未登陆用户唯一识别
        	String uuid = MyCookie.getCookie(Const.COOKIE_UUID, false, request);
            if( MyString.isEmpty(uuid) ){
            	MyCookie.addCookie(Const.COOKIE_UUID, System.currentTimeMillis() + Tools.getChar(10), response);
            }
            if(authPassport == null || authPassport.validate() == false)
                return true;
            else if(token != null && !authPassport.authority().equals("")){
            	ICacheDao cacheDao = GetBeanBySetting.getCacheDao();
            	if(cacheDao.getStr(Const.CACHE_AUTH + token) != null){
            		return Tools.hasAuth(authPassport.authority(), MyString.getValueFromRequest(request, "moduleId"), request);
            	}else{
            		throw new MyException("000003");
            	}
            }else if( token != null){
            	ICacheDao cacheDao = GetBeanBySetting.getCacheDao();
            	if(cacheDao.getObj(Const.CACHE_USER + token) != null)
            		return true;
            }
            response.sendRedirect("go.do?p=resources/html/backHtml/login.html#/preLogin");
            return false;
        }
        else
            return true;   
     }
	

}