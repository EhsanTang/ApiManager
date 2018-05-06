package cn.crap.utils;

/**
 * @author Ehsan
 * TODO 所有变量必须以 C_开头，局部变量提取至使用的class
 */
public interface IConst {
	String NULL = "_NULL";
	String C_TRUE = "true";
	public final static String NULL_ID = "NULL"; //当新增数据时，前段传递的id=NULL
	String NOT_NULL = "NOT_NULL";
	String BLANK = "_BLANK"; 
	String ALL = "_ALL"; 
	String DEF_MODULEID = "defaultModuleId";
	String C_SUPER = "super";
	String MODULEID = "MODULEID"; 
	String PROJECTID = "PROJECTID"; 
	String C_AUTH_USER = "USER";
	String C_HOT_SEARCH = "HOTS_EARCH";
	String C_AUTH_MENU = "MENU";
	String C_AUTH_SETTING = "SETTING";
	String AUTH_COMMENT = "COMMENT";
	String C_AUTH_ADMIN = "ADMIN";// 管理员
	String C_AUTH_ROLE = "ROLE";
	String C_AUTH_LOG = "LOG";
	String C_AUTH_PROJECT = "PROJECT_";
	String C_DELETE_PASSWORD = "delete";

	// 缓存
    String C_CACHE_LEFT_MENU = "cache:leftMenu";

	String CACHE_IMGCODE = ":imgCode";
	String CACHE_IMGCODE_TIMES = ":imgCodeTimes";
	String CACHE_TEMP_PWD = ":tempPwd";
	String CACHE_MODULEIDS = ":moduleIds:";
	String C_CACHE_ERROR_TIP = ":errorTip:";
	String CACHE_TUIJIAN_OPEN_MODULEIDS = ":openTuijianModuleIds:";
	String CACHE_ARTICLE_CATEGORY = ":article:category:";
	String CACHE_AUTHORIZE = ":authorize:";
	String CACHE_OSCHINA_AUTHORIZE = ":oschina:authorize:";
	String CACHE_FINDPWD= ":findPwd:";
	String CACHE_MONITOR_INTERFACES = ":monitor:interfaces";
	String CACHE_MONITOR_INTERFACES_HAS_SEND_EMAIL = ":monitor:interfaces:hasSendEmail";// 是否已经发送了告警邮件
	String CACHE_MONITOR_INTERFACES_EMAIL_TIMES = ":monitor:interfaces:emailTimes";// 发送邮件次数


	/**
	 * cookie前端名称要尽量短，且不易识别
	 */
	String C_COOKIE_USERID = "cookieUserId";
	String COOKIE_USERNAME = "cookieUserName";
	String COOKIE_PASSWORD = "cookiePassword";
	String COOKIE_TOKEN= "token";
	String COOKIE_UUID = "uuid";
	String COOKIE_REMBER_PWD = "cookieRemberPwd";
	String COOKIE_PROJECTID = "cookieProjectId"; // 访问的项目ID
	String MODULE = "MODULE";
	String DIRECTORY = "DIRECTORY";
	String SOURCE = "SOURCE";
	String SEPARATOR = "SEPARATOR";
	String ADMIN_MODULE= "0";
	String C_WEB_MODULE = "web";
	String LEVEL_PRE = "- - ";
	String REGISTER = "register";
	String GITHUB = "gitHub:";
	String OSCHINA = "oschina:";
	String DOMAIN = "DOMAIN";
	String ADMIN = "admin";
	// url
	String FRONT_PROJECT_URL = "project.do#/%s/module/list";
	
	//系统设置
	String SETTING_OPEN_REGISTER = "openRegister";
	String SETTING_GITHUB_ID = "githubClientID";
}
