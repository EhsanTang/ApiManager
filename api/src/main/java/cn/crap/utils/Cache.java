package cn.crap.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;

import cn.crap.inter.service.ISettingService;
import cn.crap.model.Setting;

public class Cache {
	private static long lastUpdatedTime = 0;
	private static boolean forceRefresh = true;
	/************将setting添加至内存及application范围内*****************/
	private static Map<String,Setting> settingList=new HashMap<String,Setting>();
	public static Setting getSetting(String key){
		return settingList.get(key);
	}
	public static void setSetting(List<Setting> setting,ServletContext sc){
		boolean useRedis = true;
		for(Setting s:setting){
			try{
				setSetting(s,sc,useRedis);
			}catch(Exception e){
				useRedis = false;
			}
		}
	}
	public static void setSetting(Setting setting){
		setSetting(setting,Tools.getServletContext(),true);
	}
	public static void setSetting(Setting setting, ServletContext sc,boolean useRedis){
		if(settingList.containsKey(setting.getKey()))
			settingList.remove(setting.getKey());
		settingList.put(setting.getKey(), setting);
		sc.setAttribute(setting.getKey(), setting.getValue());
	}
	
	//首页产品，产品存application范围内
	public synchronized static void updateCache(ISettingService settingService, ServletContext context) {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastUpdatedTime >= 30 * 60 * 1000) {// 30分钟钟更新一次
			lastUpdatedTime = currentTime;
			
			if (forceRefresh) {
				/********* 将setting添加至application ************/
				List<Setting> sets = settingService.findByMap(null, null, null);
				Cache.setSetting(sets, context);
				forceRefresh = false;
			}
		}
	}
	public static void clear(ISettingService settingService, ServletContext context) {
		lastUpdatedTime = 0;
		forceRefresh = true;
		updateCache(settingService, context);
	}

	public static long getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public static void setLastUpdatedTime(long lastUpdatedTime) {
		Cache.lastUpdatedTime = lastUpdatedTime;
	}
}
