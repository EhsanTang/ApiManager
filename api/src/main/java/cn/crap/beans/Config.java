package cn.crap.beans;

import cn.crap.service.tool.SystemService;
import cn.crap.utils.MyInteger;
import cn.crap.utils.MyString;

import java.io.InputStream;
import java.util.Properties;

public class Config{
	public static String clientID;
	public static String clientSecret;
    public static String oschinaClientID;
	public static String oschinaClientSecret;
    public static int cacheTime;
    public static int loginInforTime;
    public static int fileSize;
	public static String imageType;
	public static String fileType;
	public static boolean openRegister;
	public static boolean luceneSearchNeedLogin;
	// public static String baidu;
	public static boolean canRepeatUrl;
	public static String mail;
	public static String accessKeyId;
	public static String accessKeySecret;
	public static String endPoint;
	public static String bucketName;
	public static String imgPrefix;

	public static void init(){
	    try {
            Properties properties = new Properties();
            // 使用ClassLoader加载properties配置文件生成对应的输入流
            InputStream in = SystemService.class.getResourceAsStream("/config.properties");
            // 使用properties对象加载输入流
            properties.load(in);
            Config.clientID = properties.getProperty(Config.getConfigClientID());
            Config.clientSecret = properties.getProperty(Config.getConfigClientSecret());
            Config.oschinaClientID = properties.getProperty(Config.getConfigOschinaClientID());
            Config.oschinaClientSecret = properties.getProperty(Config.getConfigOschinaClientSecret());
            Config.cacheTime = MyInteger.getInt(properties.getProperty(Config.getConfigCacheTime()), 3600);
            Config.loginInforTime = MyInteger.getInt(properties.getProperty(Config.getConfigLoginInforTime()), 3600);
            Config.fileSize = MyInteger.getInt(properties.getProperty(Config.getConfigFileSize()), 2);
            Config.imageType = properties.getProperty(Config.getConfigImageType());
            Config.fileType = properties.getProperty(Config.getConfigFileType());
            Config.openRegister = MyString.getBoolean(properties.getProperty(Config.getConfigOpenRegister()), true);
            Config.luceneSearchNeedLogin = MyString.getBoolean(properties.getProperty(Config.getConfigLuceneSearchNeedLogin()), true);
            Config.canRepeatUrl = MyString.getBoolean(properties.getProperty(Config.getCanCanRepeatUrl()), true);
            // Config.baidu = properties.getProperty(Config.getConfigBaidu());
            Config.mail = properties.getProperty(Config.getConfigMail());
            Config.accessKeyId = properties.getProperty(Config.getConfigAccessKeyId());
            Config.accessKeySecret = properties.getProperty(Config.getConfigAccessKeySecret());
            Config.endPoint = properties.getProperty(Config.getConfigEndPoint());
            Config.bucketName = properties.getProperty(Config.getConfigBucketName());
            Config.imgPrefix = properties.getProperty(Config.getConfigImgPrefix());
            if (Config.imgPrefix == null || Config.imgPrefix.equals("")){
                Config.imgPrefix = "";
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private static String getConfigClientID() {
        return "github.clientID";
    }

    private static String getConfigClientSecret() {
        return "github.clientSecret";
    }

    private static String getConfigOschinaClientID() {
        return "git.oschina.clientID";
    }

    private static String getConfigOschinaClientSecret() {
        return "git.oschina.clientSecret";
    }

    private static String getConfigCacheTime() {
        return "web.cacheTime";
    }

    private static String getConfigLoginInforTime() {
        return "web.loginInforTime";
    }

    private static String getConfigFileSize() {
        return "web.fileSize";
    }

    private static String getConfigImageType() {
        return "web.imageType";
    }

    private static String getConfigFileType() {
        return "web.fileType";
    }

    private static String getConfigOpenRegister() {
        return "web.openRegister";
    }

    private static String getConfigLuceneSearchNeedLogin() {
        return "web.luceneSearchNeedLogin";
    }

    private static String getConfigBaidu() {
        return "web.baidu";
    }

    private static String getCanCanRepeatUrl() {
        return "web.canRepeatUrl";
    }

    private static String getConfigMail() {
        return "mail.username";
    }

    private static String getConfigAccessKeyId() {
        return "aliyun.oss.accessKeyId";
    }

    private static String getConfigAccessKeySecret() {
        return "aliyun.oss.accessKeySecret";
    }

    private static String getConfigEndPoint() {
        return "aliyun.oss.endPoint";
    }

    private static String getConfigBucketName() {
        return "aliyun.oss.bucketName";
    }

    private static String getConfigImgPrefix() {
        return "aliyun.oss.imgPrefix";
    }
}
