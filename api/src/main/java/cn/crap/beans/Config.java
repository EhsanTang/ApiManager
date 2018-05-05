package cn.crap.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.crap.utils.MyString;

@Component
public class Config{
	
	@Value("${github.clientID}")
	private String clientID;
	
	@Value("${github.clientSecret}")
	private String clientSecret;
	
	@Value("${git.oschina.clientID}")
	private String oschinaClientID;
	
	@Value("${git.oschina.clientSecret}")
	private String oschinaClientSecret;
	
	@Value("${web.domain}")
	private String domain;
	
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

	// 该功能暂时关闭
//	@Value("${web.monitorThreadNum}")
//	private int monitorThreadNum;
//
//	@Value("${web.monitorCacheTime}")
//	private int monitorCacheTime;
//
//	@Value("${web.monitorTryTimes}")
//	private int monitorTryTimes;
//
//	@Value("${web.monitorEmailSendIndex}")
//	private int monitorEmailSendIndex;

	@Value("${web.openRegister}")
	private boolean openRegister;
	
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

//	public int getMonitorThreadNum() {
//		if(monitorThreadNum > 1000){
//			return 1000;
//		}
//		return monitorThreadNum;
//	}
//
//	public int getMonitorCacheTime() {
//		return monitorCacheTime;
//	}
//
//	public int getMonitorTryTimes(){
//		if(monitorTryTimes > 100)
//			return 100;
//		if(monitorTryTimes<1)
//			return 1;
//		return monitorTryTimes;
//	}
//
//	public int getMonitorEmailSendIndex(){
//		if(monitorEmailSendIndex<2){
//			return 2;
//		}
//		if(monitorEmailSendIndex>10){
//			return 10;
//		}
//		return monitorEmailSendIndex;
//	}
	
	public String getMail(){
		return mail;
	}

	public boolean isCanRepeatUrl() {
		return canRepeatUrl;
	}

	public String getOschinaClientID() {
		return oschinaClientID;
	}

	public String getOschinaClientSecret() {
		return oschinaClientSecret;
	}
	
	
}
