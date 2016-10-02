package cn.crap.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config{
	
	@Value("${github.clientID}")
	private String clientID;
	
	@Value("${github.clientSecret}")
	private String clientSecret;
	
	@Value("${web.domain}")
	private String domain;
	
	@Value("${web.redisIp}")
	public String redisIp;
	
	@Value("${web.redisPort}")
	public int redisPort;
	
	@Value("${web.redisPwd}")
	public String redisPwd;
	
	@Value("${web.redisPoolSize}")
	public int redisPoolSize;
	
	@Value("${web.redisKeyPrefix}")
	public String redisKeyPrefix;
	
	@Value("${web.cacheTime}")
	public int cacheTime;
	
	@Value("${web.loginInforTime}")
	public int loginInforTime;
	
	@Value("${web.fileSize}")
	public int fileSize;
	
	@Value("${web.imageType}")
	public String imageType;
	
	@Value("${web.fileType}")
	public String fileType;
	
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

	
}
