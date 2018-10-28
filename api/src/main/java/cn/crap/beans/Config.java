package cn.crap.beans;

import org.springframework.stereotype.Component;

@Component
public class Config{
	public static String clientID;
	public static String clientSecret;
    public static String oschinaClientID;
	public static String oschinaClientSecret;
    public static String domain;
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

    public static String getConfigClientID() {
        return "github.clientID";
    }

    public static String getConfigClientSecret() {
        return "github.clientSecret";
    }

    public static String getConfigOschinaClientID() {
        return "git.oschina.clientID";
    }

    public static String getConfigOschinaClientSecret() {
        return "git.oschina.clientSecret";
    }

    public static String getConfigDomain() {
        return "web.domain";
    }

    public static String getConfigCacheTime() {
        return "web.cacheTime";
    }

    public static String getConfigLoginInforTime() {
        return "web.loginInforTime";
    }

    public static String getConfigFileSize() {
        return "web.fileSize";
    }

    public static String getConfigImageType() {
        return "web.imageType";
    }

    public static String getConfigFileType() {
        return "web.fileType";
    }

    public static String getConfigOpenRegister() {
        return "web.openRegister";
    }

    public static String getConfigLuceneSearchNeedLogin() {
        return "web.luceneSearchNeedLogin";
    }

    public static String getConfigBaidu() {
        return "web.baidu";
    }

    public static String getCanCanRepeatUrl() {
        return "web.canRepeatUrl";
    }

    public static String getConfigMail() {
        return "mail.username";
    }

    public static String getConfigAccessKeyId() {
        return "aliyun.oss.accessKeyId";
    }

    public static String getConfigAccessKeySecret() {
        return "aliyun.oss.accessKeySecret";
    }

    public static String getConfigEndPoint() {
        return "aliyun.oss.endPoint";
    }

    public static String getConfigBucketName() {
        return "aliyun.oss.bucketName";
    }

    public static String getConfigImgPrefix() {
        return "aliyun.oss.imgPrefix";
    }
}
