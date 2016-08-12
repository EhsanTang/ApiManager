//package cn.crap.utils;
//
//import java.util.List;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.CopyOnWriteArrayList;
//
//import javax.servlet.ServletContext;
//
//import cn.crap.inter.service.IModuleService;
//import cn.crap.inter.service.ISettingService;
//import cn.crap.model.Module;
//import cn.crap.model.Setting;
//
///**
// * @author Ehsan
// *
// */
//public class Cache {
//	/************将setting添加至内存及application范围内*****************/
//	private static ConcurrentHashMap<String,Setting> settingMap=new ConcurrentHashMap<String,Setting>();// 单个获取
//	private static CopyOnWriteArrayList<Setting> settings = new CopyOnWriteArrayList<Setting>(); // 批量获取，同时满足排序要求
//	
//	public static Setting getSetting(String key){
//		return settingMap.get(key);
//	}
//	public static List<Setting> getSetting(){
//		return settings;
//	}
//	public static void setSetting(List<Setting> setting,ServletContext sc){
//		settings.clear();
//		settings.addAll(setting);
//		for(Setting s:setting){
//			setSetting(s,sc);
//		}
//	}
//	public static void setSetting(Setting setting, ServletContext sc){
//		if(settingMap.containsKey(setting.getKey()))
//			settingMap.remove(setting.getKey());
//		settingMap.put(setting.getKey(), setting);
//		for(int i = 0; i< settings.size(); i++){
//			if(settings.get(i).getKey().equals(setting.getKey())){
//				settings.remove(i);
//				settings.add(i, setting);
//			}
//		}
//		sc.setAttribute(setting.getKey(), setting.getValue());
//	}
//	
//	
//	/*************将module放入缓存***********/
//	private static ConcurrentHashMap<String,Module> moduleMap=new ConcurrentHashMap<String,Module>();
//	public static Module getModule(String moduleId){
//		if(moduleId != null && moduleId.equals("0")){
//			return new Module("0", "顶级项目");
//		}
//		if(MyString.isEmpty(moduleId)){
//			return new Module();
//		}
//		return moduleMap.get(moduleId) == null? new Module() : moduleMap.get(moduleId);
//	}
//	public static String getModuleName(String moduleId){
//		String name = getModule(moduleId).getModuleName();
//		if(MyString.isEmpty(name))
//			name = "无";
//		return name;
//	}
//	public static void setModule(List<Module> modules){
//		for(Module module:modules){
//			setModule(module);
//		}
//	}
//	public static void setModule(Module module){
//		moduleMap.put(module.getId(), module);
//	}
//	
//	/************刷新缓存*******************/
//	public static void clear(ISettingService settingService,IModuleService moduleService, ServletContext context) {
//		List<Setting> sets = settingService.findByMap(null, null, null);
//		Cache.setSetting(sets, context);
//		List<Module> modules = moduleService.findByMap(null, null, null);
//		Cache.setModule(modules);
//	}
//		
//}