package cn.crap.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;

import cn.crap.inter.service.IModuleService;
import cn.crap.inter.service.ISettingService;
import cn.crap.model.Module;
import cn.crap.model.Setting;

public class Cache {
	private static long lastUpdatedTime = 0;
	private static boolean forceRefresh = true;
	/************将setting添加至内存及application范围内*****************/
	private static Map<String,Setting> settingMap=new HashMap<String,Setting>();
	public static Setting getSetting(String key){
		return settingMap.get(key);
	}
	public static void setSetting(List<Setting> setting,ServletContext sc){
		for(Setting s:setting){
			setSetting(s,sc);
		}
	}
	public static void setSetting(Setting setting, ServletContext sc){
		if(settingMap.containsKey(setting.getKey()))
			settingMap.remove(setting.getKey());
		settingMap.put(setting.getKey(), setting);
		sc.setAttribute(setting.getKey(), setting.getValue());
	}
	/*************将module放入缓存***********/
	private static Map<String,Module> moduleMap=new HashMap<String,Module>();
	public static Module getModule(String moduleId){
		return moduleMap.get(moduleId);
	}
	public static void setModule(List<Module> modules){
		for(Module module:modules){
			setModule(module);
		}
	}
	public static void setModule(Module module){
		if(moduleMap.containsKey(module.getModuleId()))
			moduleMap.remove(module.getModuleId());
		moduleMap.put(module.getModuleId(), module);
	}

		
	//首页产品，产品存application范围内
	public synchronized static void updateCache(ISettingService settingService,IModuleService moduleService, ServletContext context) {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastUpdatedTime >= 30 * 60 * 1000) {// 30分钟钟更新一次
			lastUpdatedTime = currentTime;
			
			if (forceRefresh) {
				/********* 将setting添加至application ************/
				List<Setting> sets = settingService.findByMap(null, null, null);
				Cache.setSetting(sets, context);
				forceRefresh = false;
				List<Module> modules = moduleService.findByMap(null, null, null);
				Cache.setModule(modules);
				forceRefresh = false;
			}
		}
	}
	public static void clear(ISettingService settingService,IModuleService moduleService, ServletContext context) {
		lastUpdatedTime = 0;
		forceRefresh = true;
		updateCache(settingService,moduleService, context);
	}

	public static long getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public static void setLastUpdatedTime(long lastUpdatedTime) {
		Cache.lastUpdatedTime = lastUpdatedTime;
	}
}
