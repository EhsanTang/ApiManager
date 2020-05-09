package cn.crap.utils;

/**
 * @author Ehsan
 */
public interface IConst {
	Long C_MAX_SEQUENCE = 999999999999999999L;
	String SEPARATOR = ":"; // 分割符
	String NULL = "_NULL";
	String C_TRUE = "true";
	String NULL_ID = "NULL"; //当新增数据时，前段传递的id=NULL
	String NOT_NULL = "NOT_NULL";
	String BLANK = "_BLANK"; 
	String ALL = "_ALL"; 
	String C_SUPER = "SUPER";
	String C_AUTH_USER = "USER";
	String C_HOT_SEARCH = "HOTS_EARCH";
	String C_AUTH_MENU = "MENU";
	String C_AUTH_SETTING = "SETTING";
	String C_AUTH_ADMIN = "ADMIN";// 管理员
	String C_AUTH_ROLE = "ROLE";
	String C_AUTH_PROJECT = "PROJECT_";
	String C_DELETE_PASSWORD = "delete";
	String C_PARAM_FORM = "FORM";
    String C_PARAM_RAW = "RAW";
    String C_PARAM_FORM_PRE = "form=";
    String C_METHOD_POST = "POST";
    String C_METHOD_GET = "GET";
    String C_SEPARATOR = "C_SEPARATOR";
    String C_BUG= "BUG";
    String C_CONTENT_TYPE = "Content-Type";
	String C_STRING = "string";
	String C_CONTENT_TYPE_TIP = "指定参数类型为";
	String C_FORM_DATA_TYPE= "application/x-www-form-urlencoded;charset=UTF-8";
	String C_NEED_VERIFICATION_IMG = "needVerification";



	int ALL_PAGE_SIZE = -100; // 不分页
    long ONE_DAY = 24 * 60 * 60 * 1000;
    long TWO_HOUR = 2 * 60 * 60 * 1000;


    // 缓存
    String C_CACHE_MENU = "cache:menu";

	String CACHE_IMGCODE = ":imgCode";
	String CACHE_IMGCODE_TIMES = ":imgCodeTimes";
	String C_CACHE_ERROR_TIP = ":errorTip:";
	String CACHE_AUTHORIZE = ":authorize:";
	String CACHE_FINDPWD= ":findPwd:";


	/**
	 * cookie前端名称要尽量短，且不易识别
	 */
	String C_COOKIE_USERID = "cookieUserId";
	String COOKIE_USERNAME = "cookieUserName";
	String COOKIE_PASSWORD = "cookiePassword";
	String COOKIE_TOKEN= "token";
	String COOKIE_UUID = "uuid";
	String COOKIE_REMBER_PWD = "cookieRemberPwd";
	String REGISTER = "register";
	String GITHUB = "gitHub:";
	String OSCHINA = "oschina:";
	String ADMIN = "admin";
	// url
	String FRONT_PROJECT_URL = "project.do#/module/list?projectId=%s";
	
	//系统设置
	String SETTING_OPEN_REGISTER = "openRegister";
	String SETTING_GITHUB_ID = "githubClientID";
	String SETTING_GITEE_ID = "giteeClientID";

}
