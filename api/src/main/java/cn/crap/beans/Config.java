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

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	
	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getRedisIp() {
		return redisIp;
	}

	public void setRedisIp(String redisIp) {
		this.redisIp = redisIp;
	}

	public int getRedisPort() {
		return redisPort;
	}

	public void setRedisPort(int redisPort) {
		this.redisPort = redisPort;
	}

	public String getRedisPwd() {
		return redisPwd;
	}

	public void setRedisPwd(String redisPwd) {
		this.redisPwd = redisPwd;
	}

	public int getRedisPoolSize() {
		return redisPoolSize;
	}

	public void setRedisPoolSize(int redisPoolSize) {
		this.redisPoolSize = redisPoolSize;
	}

	public String getRedisKeyPrefix() {
		return redisKeyPrefix;
	}

	public void setRedisKeyPrefix(String redisKeyPrefix) {
		this.redisKeyPrefix = redisKeyPrefix;
	}

	public int getCacheTime() {
		return cacheTime;
	}

	public void setCacheTime(int cacheTime) {
		this.cacheTime = cacheTime;
	}

	public int getLoginInforTime() {
		return loginInforTime;
	}

	public void setLoginInforTime(int loginInforTime) {
		this.loginInforTime = loginInforTime;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	
	
}
