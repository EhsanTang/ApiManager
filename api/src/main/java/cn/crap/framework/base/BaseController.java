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
import cn.crap.enumeration.ProjectType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.SpringContextHolder;
import cn.crap.inter.service.table.IProjectService;
import cn.crap.inter.service.table.IProjectUserService;
import cn.crap.inter.service.tool.ICacheService;
import cn.crap.model.Module;
import cn.crap.model.Project;
import cn.crap.model.ProjectUser;
import cn.crap.service.tool.CacheService;
import cn.crap.utils.Const;
import cn.crap.utils.MyCookie;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

public abstract class BaseController<T extends BaseModel> {
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Logger log = Logger.getLogger(getClass());
	public final static int view = 100;
	public final static int modInter = 1;
	public final static int addInter = 2;
	public final static int delInter = 3;

	public final static int modModule = 4;
	public final static int addModule = 5;
	public final static int delModule = 6;

	public final static int modArticle = 7;
	public final static int addArticle = 8;
	public final static int delArticle = 9;

	public final static int modDict = 10;
	public final static int addDict = 11;
	public final static int delDict = 12;

	public final static int modSource = 13;
	public final static int addSource = 14;
	public final static int delSource = 15;

	public final static int modError = 16;
	public final static int addError = 17;
	public final static int delError = 18;

	@Autowired
	protected ICacheService cacheService;
	@Autowired
	protected IProjectService projectService;
	@Autowired
	protected IProjectUserService projectUserService;

	/**
	 * spring 中request、response是线程安全的，可以直接注入
	 * 
	 * @ModelAttribute注解只有在被
	 * @Controller和@ControllerAdvice两个注解的类下使用 ModelAttribute的作用
	 *    1)放置在方法的形参上： 表示引用Model中的数据
	 *    2)放置在方法上面：表示请求该类的每个Action前都会首先执行它也可以将一些准备数据的操作放置在该方法里面。
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
	protected HashMap<String, String> getRequestHeaders() {
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

	@ExceptionHandler({ Exception.class })
	@ResponseBody
	public JsonResult expHandler(HttpServletRequest request, Exception ex) {
		if (ex instanceof MyException) {
			return new JsonResult((MyException) ex);
		}else if(ex instanceof NullPointerException){
			log.error(ex.getMessage(), ex);
			return new JsonResult( new MyException("000051"));
		} else {
			log.error(ex.getMessage(), ex);
			return new JsonResult(new MyException("000001", ex.getMessage()));
		}
	}

	protected void printMsg(String message) {
		response.setHeader("Content-Type", "text/html");
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
	 * 
	 * @param project
	 * @throws MyException
	 */
	protected void hasPermission(Project project, int type) throws MyException {
		LoginInfoDto user = Tools.getUser();
		if (user != null) {

			// 最高管理员修改项目
			if (user != null && ("," + user.getRoleId()).indexOf("," + Const.SUPER + ",") >= 0) {
				return;
			}

			// 修改自己的项目
			if (user.getId().equals(project.getUserId())) {
				return;
			}

			// 项目成员
			if (type > 0) {
				ProjectUser pu = user.getProjects().get(project.getId());
				if (pu == null) {
					throw new MyException("000003");
				}
				if (type == view) {
					return;
				}
				
				if( pu.projectAuth()[type]){
					return;
				}
				
			}

		}
		throw new MyException("000003");
	}

	protected void hasPermission(Project project) throws MyException {
		hasPermission(project, 0);
	}

	protected void hasPermission(String projectId, int type) throws MyException {
		hasPermission(cacheService.getProject(projectId), type);
	}

	protected void hasPermissionModuleId(String moduleId, int type) throws MyException {
		hasPermission(cacheService.getProject(cacheService.getModule(moduleId).getProjectId()), type);
	}

	protected void hasPermission(String projectId) throws MyException {
		hasPermission(cacheService.getProject(projectId), 0);
	}

	protected void hasPermissionModuleId(String moduleId) throws MyException {
		hasPermission(cacheService.getProject(cacheService.getModule(moduleId).getProjectId()), 0);
	}

	/**
	 * 密码访问
	 * 
	 * @return
	 */
	/********************** 模块访问密码 ***************************/
	public void canVisitModuleId(String moduleId, String password, String visitCode) throws MyException {
		Module module = cacheService.getModule(moduleId);
		String needPassword = cacheService.getProject(module.getProjectId()).getPassword();
		canVisit(needPassword, password, visitCode);
	}

	public void canVisitModule(Module module, String password, String visitCode) throws MyException {
		String needPassword = cacheService.getProject(module.getProjectId()).getPassword();
		canVisit(needPassword, password, visitCode);
	}

	/**
	 * 初次输入浏览密码是需要验证码，然后记录至缓存中，第二次访问若缓存中有密码，则不需要检查验证码是否争取
	 * @param neddPassword
	 * @param password
	 * @param visitCode
	 * @throws MyException
	 */
	public void canVisit(String neddPassword, String password, String visitCode) throws MyException {
		if (!MyString.isEmpty(neddPassword)) {
			ICacheService cacheService = SpringContextHolder.getBean("cacheService", CacheService.class);
			String temPwd = cacheService
					.getStr(Const.CACHE_TEMP_PWD + MyCookie.getCookie(Const.COOKIE_UUID, false, request));
			if (!MyString.isEmpty(temPwd) && temPwd.toString().equals(neddPassword)) {
				return;
			}
			if (MyString.isEmpty(password) || !password.equals(neddPassword)) {
				throw new MyException("000007");
			}
			if (cacheService.getSetting(Const.SETTING_VISITCODE).getValue().equals("true")) {
				Object imgCode = Tools.getImgCode(request);
				if (MyString.isEmpty(visitCode) || imgCode == null || !visitCode.equals(imgCode.toString())) {
					throw new MyException("000007");
				}
			}
			cacheService.setStr(Const.CACHE_TEMP_PWD + MyCookie.getCookie(Const.COOKIE_UUID, false, request), password,
					10 * 60);
		}
	}

	protected void isPrivateProject(String password, String visitCode, Project project)
			throws MyException {
		// web项目为默认的公开项目
		if(project.getId().equals(Const.WEB_MODULE)){
			return;
		}
		// 如果是私有项目，必须登录才能访问，公开项目需要查看是否需要密码
		if (project.getType() == ProjectType.PRIVATE.getType()) {
			LoginInfoDto user = Tools.getUser();
			if (user == null) {
				throw new MyException("000041");
			}
			// 最高管理员修改项目
			if (user != null && ("," + user.getRoleId()).indexOf("," + Const.SUPER + ",") >= 0) {
				return;
			}

			// 自己的项目
			if (user.getId().equals(project.getUserId())) {
				return;
			}

			// 项目成员
			ProjectUser pu = user.getProjects().get(project.getId());
			if (pu == null) {
					throw new MyException("000042");
			}
		} else {
			String needPassword = project.getPassword();
			canVisit(needPassword, password, visitCode);
		}
	}

	protected Object getParam(String key, String def) {
		String value = request.getParameter(key);
		return value == null ? def : value;
	}

}