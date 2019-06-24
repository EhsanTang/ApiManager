package cn.crap.beans;

import cn.crap.utils.MyInteger;
import cn.crap.utils.MyString;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class Config implements EnvironmentAware {

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
    public static String baidu;
    public static boolean canRepeatUrl;
    public static String mail;
    public static String accessKeyId;
    public static String accessKeySecret;
    public static String endPoint;
    public static String bucketName;
    public static String imgPrefix;
    private static Environment env;

    public static void init() {
        try {
            Config.clientID = env.getProperty(Config.getConfigClientID());
            Config.clientSecret = env.getProperty(Config.getConfigClientSecret());
            Config.oschinaClientID = env.getProperty(Config.getConfigOschinaClientID());
            Config.oschinaClientSecret = env.getProperty(Config.getConfigOschinaClientSecret());
            Config.cacheTime = MyInteger.getInt(env.getProperty(Config.getConfigCacheTime()), 3600);
            Config.loginInforTime = MyInteger.getInt(env.getProperty(Config.getConfigLoginInforTime()), 3600);
            Config.fileSize = MyInteger.getInt(env.getProperty(Config.getConfigFileSize()), 2);
            Config.imageType = env.getProperty(Config.getConfigImageType());
            Config.fileType = env.getProperty(Config.getConfigFileType());
            Config.openRegister = MyString.getBoolean(env.getProperty(Config.getConfigOpenRegister()), true);
            Config.luceneSearchNeedLogin = MyString.getBoolean(env.getProperty(Config.getConfigLuceneSearchNeedLogin()), true);
            Config.canRepeatUrl = MyString.getBoolean(env.getProperty(Config.getCanCanRepeatUrl()), true);
            Config.baidu = env.getProperty(Config.getConfigBaidu());
            Config.mail = env.getProperty(Config.getConfigMail());
            Config.accessKeyId = env.getProperty(Config.getConfigAccessKeyId());
            Config.accessKeySecret = env.getProperty(Config.getConfigAccessKeySecret());
            Config.endPoint = env.getProperty(Config.getConfigEndPoint());
            Config.bucketName = env.getProperty(Config.getConfigBucketName());
            Config.imgPrefix = env.getProperty(Config.getConfigImgPrefix());
        } catch (Exception e) {
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

    private static String getConfigDomain() {
        return "web.domain";
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

    @Override
    public void setEnvironment(Environment env) {
        Config.env = env;
    }

}
