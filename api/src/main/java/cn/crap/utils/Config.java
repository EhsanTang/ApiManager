package cn.crap.utils;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

public class Config {
	public static XMLConfiguration config;
	private static long delayReflashTime = 1000*60*10;		//每隔10分钟检查一次文件是否变化。
	static {
		try {
			config = new XMLConfiguration("config.xml");
		} catch (org.apache.commons.configuration.ConfigurationException e) {
			e.printStackTrace();
		}
		FileChangedReloadingStrategy fileChangedReloadingStrategy = new FileChangedReloadingStrategy();
	    fileChangedReloadingStrategy.setRefreshDelay(delayReflashTime);
		config.setReloadingStrategy(fileChangedReloadingStrategy);
	}
	
	public static String redisIp;
	public static int redisPort;
	public static String redisPwd;
	public static int redisPoolSize;
	public static String redisKeyPrefix;
	public static int cacheTime;
	
	public static String getRedisIp() {
		return config.getString("redis.redisIp");
	}
	
	public static int getRedisPort() {
		return config.getInt("redis.redisPort");
	}
	
	public static String getRedisPwd() {
		return config.getString("redis.redisPwd");
	}
	
	public static int getRedisPoolSize() {
		return config.getInt("redis.redisPoolSize");
	}
	
	public static String getRedisKeyPrefix() {
		return config.getString("redis.redisKeyPrefix");
	}
	
	public static int getCacheTime() {
		return config.getInt("redis.cacheTime");
	}
	
}
