package cn.crap.framework.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.dto.LoginInfoDto;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.SpringContextHolder;
import cn.crap.inter.service.table.IProjectService;
import cn.crap.inter.service.tool.ICacheService;
import cn.crap.model.Module;
import cn.crap.model.Project;
import cn.crap.service.tool.CacheService;
import cn.crap.utils.Const;
import cn.crap.utils.MyCookie;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

public abstract class BaseController<T extends BaseModel> {
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Logger log = Logger.getLogger(getClass());
	@Autowired
	private IProjectService projectService;
	@Autowired
	private ICacheService cacheService;
	
	/**
	 * spring 中request、response是线程安全的，可以直接注入
	 * @ModelAttribute注解只有在被@Controller和@ControllerAdvice两个注解的类下使用
	 * ModelAttribute的作用 
	 * 1)放置在方法的形参上：表示引用Model中的数据
	 * 2)放置在方法上面：表示请求该类的每个Action前都会首先执行它，也可以将一些准备数据的操作放置在该方法里面。
	 * @param request
	 * @param response
	 */
	 @ModelAttribute   
	 public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) { 
	        this.request = request;   
	        this.response = response;  
	 } 

	 
	/**
	 * @return
	 */
	protected HashMap<String, String> getRequestHeaders()  {
		HashMap<String, String> requestHeaders = new HashMap<String, String>();
		@SuppressWarnings("unchecked")
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			String headerValue = request.getHeader(headerName);
			requestHeaders.put(headerName, headerValue);
		}
		return requestHeaders;
	}

	/**
	 * @return
	 */
	protected HashMap<String, String> getRequestParams() {
		HashMap<String, String> requestParams = new HashMap<String, String>();
		@SuppressWarnings("unchecked")
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			String paramValue = request.getParameter(paramName);
			requestParams.put(paramName, paramValue);
		}
		return requestParams;
	}
	
	 @ExceptionHandler({ Exception.class})
	 @ResponseBody  
     public JsonResult expHandler(HttpServletRequest request, Exception ex) {  
        if(ex instanceof MyException) {  
            return new JsonResult((MyException)ex);
        } else {  
        	ex.printStackTrace();
        	log.error(ex.getMessage());
        	ex.printStackTrace();
        	return new JsonResult(new MyException("000001",ex.getMessage()));
        }  
    }  
	 
	 protected void printMsg(String message){
			response.setHeader("Content-Type" , "text/html");
			response.setCharacterEncoding("utf-8");
			try {
				PrintWriter out = response.getWriter();
				out.write(message);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	 
	 	/**
		 * 权限检查
		 * @param project
		 * @throws MyException
		 */
		protected void hasPermission(Project project) throws MyException{
			LoginInfoDto user = Tools.getUser();
			if(user != null ){
				
				// 最高管理员修改项目
				if( user != null && (","+user.getRoleId()).indexOf(","+Const.SUPER+",")>=0){
					return;
				}

				// 拥有项目权限的普通管理员修改项目
				String authority = user.getAuthStr();
				if(authority.indexOf(","+Const.AUTH_PROJECT+project.getId()+",") >=0){
					return;
				}
				
				// 修改自己的项目
				if( user.getId().equals(project.getUserId())){
					return;
				}
				
			}
			throw new MyException("000003");
		}
		protected void hasPermission(String projectId) throws MyException{
			hasPermission(cacheService.getProject(projectId));
		}
		protected void hasPermissionModuleId(String moduleId) throws MyException{
			hasPermission(cacheService.getProject(cacheService.getModule(moduleId).getProjectId()));
		}
		
		/**
		 * 密码访问
		 * @return
		 */
		/**********************模块访问密码***************************/
		public void canVisitModuleId(String moduleId,String password, String visitCode) throws MyException{
			Module module = cacheService.getModule(moduleId);
			if(MyString.isEmpty(module.getPassword())){
				canVisit(cacheService.getProject(module.getProjectId()).getPassword(), password, visitCode);
			}
		}
		public void canVisitModule(Module module,String password, String visitCode) throws MyException{
			if(MyString.isEmpty(module.getPassword())){
				canVisit(cacheService.getProject(module.getProjectId()).getPassword(), password, visitCode);
			}
		}
		
		public void canVisit(String neddPassword,String password, String visitCode) throws MyException{
			ICacheService cacheService = SpringContextHolder.getBean("cacheService", CacheService.class);
			String temPwd = cacheService.getStr(Const.CACHE_TEMP_PWD + MyCookie.getCookie(Const.COOKIE_UUID, false, request));
			if(!MyString.isEmpty(neddPassword)){
				if(!MyString.isEmpty(temPwd)&&temPwd.toString().equals(neddPassword)){
					return;
				}
				if(MyString.isEmpty(password)||!password.equals(neddPassword)){
					throw new MyException("000007");
				}
				if(cacheService.getSetting(Const.SETTING_VISITCODE).getValue().equals("true")){
					Object imgCode = Tools.getImgCode(request);
					if(MyString.isEmpty(visitCode)||imgCode==null||!visitCode.equals(imgCode.toString())){
						throw new MyException("000007");
					}
				}
				cacheService.setStr(Const.CACHE_TEMP_PWD + MyCookie.getCookie(Const.COOKIE_UUID, false, request), password, 10 * 60);
			}
		}
		
		
//	 protected void handleBindingValidation(BindingResult bindingResult) throws MyException{
//	        if(bindingResult.hasErrors()){
//	            List<ObjectError> list = bindingResult.getAllErrors();
//	            StringBuilder msg= new StringBuilder();
//	            for(ObjectError error:list){
//	            	msg.append(error.getDefaultMessage()+";");
//	            }
//	            throw new MyException("0",msg.toString());
//	        }
//	    }
	 
	 protected Object getParam(String key, String def) {
			String value = request.getParameter(key);
			return value==null?def:value;
	}
	 
}  