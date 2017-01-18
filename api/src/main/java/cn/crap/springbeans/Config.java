package cn.crap.springbeans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.crap.utils.MyString;

@Component
public class Config{
	
	@Value("${github.clientID}")
	private String clientID;
	
	@Value("${github.clientSecret}")
	private String clientSecret;
	
	@Value("${web.domain}")
	private String domain;
	
	@Value("${web.redisIp}")
	private String redisIp;
	
	@Value("${web.redisPort}")
	private int redisPort;
	
	@Value("${web.redisPwd}")
	private String redisPwd;
	
	@Value("${web.redisPoolSize}")
	private int redisPoolSize;
	
	@Value("${web.redisKeyPrefix}")
	private String redisKeyPrefix;
	
	@Value("${web.cacheTime}")
	private int cacheTime;
	
	@Value("${web.loginInforTime}")
	private int loginInforTime;
	
	@Value("${web.fileSize}")
	private int fileSize;
	
	@Value("${web.imageType}")
	private String imageType;
	
	@Value("${web.fileType}")
	private String fileType;
	
	@Value("${web.monitorThreadNum}")
	private int monitorThreadNum;
	
	@Value("${web.monitorCacheTime}")
	private int monitorCacheTime;
	
	@Value("${web.monitorTryTimes}")
	private int monitorTryTimes;
	
	@Value("${web.monitorEmailSendIndex}")
	private int monitorEmailSendIndex;
	
	@Value("${web.showRecommendProject}")
	private boolean showRecommendProject;
	
	@Value("${web.recommendProjectMenuName}")
	private String recommendProjectMenuName;
	
	@Value("${web.showArticle}")
	private boolean showArticle;
	
	@Value("${web.articleMenuName}")
	private String articleMenuName;
	
	@Value("${web.subMenuSize}")
	private int subMenuSize;
	
	@Value("${web.openRegister}")
	private boolean openRegister;
	
	@Value("${web.privateProjectNeedCreateIndex}")
	private boolean privateProjectNeedCreateIndex;
	
	@Value("${web.luceneSearchNeedLogin}")
	private boolean luceneSearchNeedLogin;
	
	@Value("${web.baidu}")
	private String baidu;
	
	@Value("${web.canRepeatUrl}")
	private boolean canRepeatUrl;
	
	@Value("${mail.username}")
	private String mail;
	
	
	public String getBaidu() {
		if(MyString.isEmpty(baidu))
			return "";
		return baidu;
	}

	public boolean isPrivateProjectNeedCreateIndex() {
		return privateProjectNeedCreateIndex;
	}

	public boolean isLuceneSearchNeedLogin() {
		return luceneSearchNeedLogin;
	}

	public boolean isOpenRegister() {
		return openRegister;
	}

	public String getClientID() {
		return clientID;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public String getDomain() {
		return domain;
	}

	public String getRedisIp() {
		return redisIp;
	}

	public int getRedisPort() {
		return redisPort;
	}

	public String getRedisPwd() {
		return redisPwd;
	}

	public int getRedisPoolSize() {
		return redisPoolSize;
	}

	public String getRedisKeyPrefix() {
		return redisKeyPrefix;
	}

	public int getCacheTime() {
		return cacheTime;
	}

	public int getLoginInforTime() {
		return loginInforTime;
	}

	public int getFileSize() {
		return fileSize;
	}

	public String getImageType() {
		return imageType;
	}

	public String getFileType() {
		return fileType;
	}

	public int getMonitorThreadNum() {
		if(monitorThreadNum > 1000){
			return 1000;
		}
		return monitorThreadNum;
	}
	
	public int getMonitorCacheTime() {
		return monitorCacheTime;
	}
	
	public int getMonitorTryTimes(){
		if(monitorTryTimes > 100)
			return 100;
		if(monitorTryTimes<1)
			return 1;
		return monitorTryTimes;
	}
	
	public int getMonitorEmailSendIndex(){
		if(monitorEmailSendIndex<2){
			return 2;
		}
		if(monitorEmailSendIndex>10){
			return 10;
		}
		return monitorEmailSendIndex;
	}

	public boolean isShowRecommendProject() {
		return showRecommendProject;
	}

	public String getRecommendProjectMenuName() {
		return recommendProjectMenuName;
	}

	public boolean isShowArticle() {
		return showArticle;
	}

	public String getArticleMenuName() {
		return articleMenuName;
	}

	public int getSubMenuSize() {
		return subMenuSize;
	}
	
	public String getMail(){
		return mail;
	}

	public boolean isCanRepeatUrl() {
		return canRepeatUrl;
	}
	
	
}
