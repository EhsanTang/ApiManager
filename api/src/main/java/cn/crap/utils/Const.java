package cn.crap.utils;
public class Const {
//	public final static String SESSION_ADMIN = "sessionAdminName";
//	public final static String SESSION_IMGCODE_TIMES = "sessionImgCodeTryTimes";
//	public final static String SESSION_ADMIN_AUTH = "sessionAdminAuthor";
//	public final static String SESSION_ADMIN_ID = "sessionAdminId";
//	public final static String SESSION_ADMIN_TRUENAME = "sessionAdminTrueName";
//	public final static String SESSION_ADMIN_ROLEIDS = "sessionAdminRoleIds";
//	public final static String SESSION_IMG_CODE = "sessionImgCode";
//	public final static String SESSION_TEMP_PASSWORD = "sessionTempPassword";
	
	
	
	public static final String NULL = "_NULL";
	public final static String NULL_ID = "NULL"; //当新增数据时，前段传递的id=NULL
	public static final String NOT_NULL = "NOT_NULL";
	public static final String BLANK = "_BLANK"; 
	public static final String DEF_MODULEID = "defaultModuleId";
	public static final String SUPER = "super"; 
	public static final String MODULEID = "MODULEID"; 
	public static final String PROJECTID = "PROJECTID"; 
	public static final String AUTH_USER = "USER";
	public static final String AUTH_MENU = "MENU";
	public static final String AUTH_SETTING = "SETTING";
	public static final String AUTH_COMMENT = "COMMENT";
	public static final String AUTH_ADMIN = "ADMIN";// 管理员
	public static final String AUTH_ROLE = "ROLE";
	public static final String AUTH_LOG = "LOG";
	public static final String AUTH_PROJECT = "PROJECT_";
	// 缓存
	public static final String CACHE_USER= ":user";// 用户登录信息
	public static final String CACHE_IMGCODE = ":imgCode";
	public static final String CACHE_IMGCODE_TIMES = ":imgCodeTimes";
	public static final String CACHE_TEMP_PWD = ":tempPwd";
	public static final String CACHE_SEARCH_WORDS = ":searchWords";
	public static final String CACHE_MODULEIDS = ":moduleIds:";
	public static final String CACHE_ALL_DATACENTER = ":all:datacenter:";
	public static final String CACHE_ERROR_TIP = ":errorTip:";
	public static final String CACHE_TUIJIAN_OPEN_MODULEIDS = ":openTuijianModuleIds:";
	public static final String CACHE_ARTICLE_CATEGORY = ":article:category:";
	public static final String CACHE_WEBPAGE = ":webPageDetail:";
	public static final String CACHE_COMMENTLIST = ":commentList:";
	public static final String CACHE_COMMENT_PAGE = ":commentList:page:";
	public static final String CACHE_AUTHORIZE = ":authorize:";
	public static final String CACHE_FINDPWD= ":findPwd:";
	public static final String CACHE_MONITOR_INTERFACES = ":monitor:interfaces";
	public static final String CACHE_MONITOR_INTERFACES_HAS_SEND_EMAIL = ":monitor:interfaces:hasSendEmail";// 是否已经发送了告警邮件
	public static final String CACHE_MONITOR_INTERFACES_EMAIL_TIMES = ":monitor:interfaces:emailTimes";// 发送邮件次数
	public static final String CACHE_PROJECT = ":project:";
	public static final String CACHE_MODULE = "cache:model:";
	public static final String CACHE_SETTING = "cache:setting";
	public static final String CACHE_USER_MODEL= "cache:user:model";// 用户登录信息
	public static final String CACHE_SETTINGLIST = "cache:settingList";
	
	
	//常量
	public static final String COOKIE_USERID = "cookieUserId";
	public static final String COOKIE_USERNAME = "cookieUserName";
	public static final String COOKIE_PASSWORD = "cookiePassword";
	public static final String COOKIE_TOKEN= "token";
	public static final String COOKIE_UUID = "uuid";
	public static final String COOKIE_REMBER_PWD = "cookieRemberPwd";
	public static final String COOKIE_PROJECTID = "cookieProjectId"; // 访问的项目ID
	public static final String MODULE = "MODULE";
	public static final String DIRECTORY = "DIRECTORY";
	public static final String SOURCE = "SOURCE";
	public static final String SEPARATOR = "SEPARATOR";
	public static final String ADMIN_MODULE= "0";
	public static final String WEB_MODULE= "web";
	public static final String LEVEL_PRE = "- - ";
	public static final String REGISTER = "register";
	public static final String GITHUB = "gitHub:";
	public static final String DOMAIN = "DOMAIN";
	// url
	public static final String FRONT_ARTICLE_URL = "#%s/article/list/%s/ARTICLE/%s/NULL";
	public static final String FRONT_PROJECT_URL = "#/%s/module/list";
	
	//系统设置
	public static final String SETTING_SECRETKEY = "SECRETKEY";
	public final static String SETTING_VERIFICATIONCODE = "VERIFICATIONCODE";
	public final static String SETTING_VISITCODE = "VISITCODE";
	public static final String SETTING_COMMENTCODE = "COMMENTCODE";
	public static final String SETTING_LUCENE_DIR = "LUCENE_DIR";
	public static final String SETTING_TITLE = "TITLE";
	
	//SOLR
	public static final String SOLR_URL = "SOLR_URL";
	public static final String SOLR_QUEUESIZE  = "SOLR_QUEUESIZE";
	public static final String SOLR_THREADCOUNT = "SOLR_THREADCOUNT";
	public static final String SEARCH_TYPE = "SEARCH_TYPE";
}
