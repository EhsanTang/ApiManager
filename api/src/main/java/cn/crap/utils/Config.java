package cn.crap.utils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

public class Config {
	
	public static String redisIp;
	public static int redisPort;
	public static String redisPwd;
	public static int redisPoolSize;
	public static String redisKeyPrefix;
	public static int cacheTime;
	public static int loginInforTime;
	public static int fileSize;
	public static String imageType;
	public static String fileType;
	
	public static String getByName(String name){
		if( redisIp != null){
			return redisIp;
		}
		// 类初始化需要访问的变量需要使用xml方式读取
		XMLConfiguration config;
		try {
			config = new XMLConfiguration("config.xml");
			HierarchicalConfiguration propertys = (HierarchicalConfiguration) config.subset("bean");
			for(int i=0; i< propertys.getRoot().getChildren().size(); i++){
				if(propertys.getString("property(" + i + ")[@name]").equals(name)){
					return propertys.getString("property(" + i + ")[@value]");
				}
			}
			return "";
		} catch (ConfigurationException e) {
			e.printStackTrace();
			return "";
		}
		
	}
	
	public static int getIntByName(String name){
		return Integer.parseInt(getByName(name));
	}


	public static String getRedisIp(){
		if(redisIp != null)
			return redisIp;
		return getByName("redisIp");
	}

	public static void setRedisIp(String redisIp) {
		Config.redisIp = redisIp;
	}


	public static int getRedisPort(){
		if(redisPort > 0)
			return redisPort;
		return Integer.parseInt(getByName("redisPort"));
	}


	public static void setRedisPort(int redisPort) {
		Config.redisPort = redisPort;
	}


	public static String getRedisPwd(){
		if( redisPwd != null)
			return redisPwd;
		return getByName("redisPwd");
	}


	public static void setRedisPwd(String redisPwd) {
		Config.redisPwd = redisPwd;
	}


	public static int getRedisPoolSize(){
		if(redisPoolSize > 0)
			return redisPoolSize;
		return Integer.parseInt(getByName("redisPoolSize"));
	}


	public static void setRedisPoolSize(int redisPoolSize) {
		Config.redisPoolSize = redisPoolSize;
	}


	public static String getRedisKeyPrefix() {
		return redisKeyPrefix;
	}


	public static void setRedisKeyPrefix(String redisKeyPrefix) {
		Config.redisKeyPrefix = redisKeyPrefix;
	}


	public static int getCacheTime() {
		return cacheTime;
	}


	public static void setCacheTime(int cacheTime) {
		Config.cacheTime = cacheTime;
	}


	public static int getLoginInforTime() {
		return loginInforTime;
	}


	public static void setLoginInforTime(int loginInforTime) {
		Config.loginInforTime = loginInforTime;
	}

	public static int getFileSize() {
		return fileSize;
	}

	public static void setFileSize(int fileSize) {
		Config.fileSize = fileSize;
	}

	public static String getImageType() {
		return imageType;
	}

	public static void setImageType(String imageType) {
		Config.imageType = imageType;
	}

	public static String getFileType() {
		return fileType;
	}

	public static void setFileType(String fileType) {
		Config.fileType = fileType;
	}
	
	
}
