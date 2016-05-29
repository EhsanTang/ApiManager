package cn.crap.framework.auth;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.crap.framework.MyException;
import cn.crap.utils.Const;
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
            HttpSession session =  request.getSession();
            if(authPassport == null || authPassport.validate() == false)
                return true;
            else if(!authPassport.authority().equals("")){
            	/***
            	 * 查询是否有模块id
            	 */
            	String moduleId = request.getParameter("moduleId");
            	if(moduleId==null){
            		moduleId = "";
            	}
            	if(session.getAttribute(Const.SESSION_ADMIN_AUTH)!=null){
            		return Tools.hasAuth(authPassport.authority(), session, moduleId);
            	}else{
            		throw new MyException("000003");
            	}
            }else if(session.getAttribute(Const.SESSION_ADMIN)!=null){
            	return true;
            }
            response.sendRedirect("go.do?p=resources/html/backHtml/login.html#/preLogin");
            return false;
        }
        else
            return true;   
     }
	

}