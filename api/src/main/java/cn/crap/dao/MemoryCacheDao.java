package cn.crap.dao;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import cn.crap.inter.dao.ICacheDao;

@Repository("memoryCacheDao")
public class MemoryCacheDao implements ICacheDao {
	private static ConcurrentHashMap<String,Long> cacheTime=new ConcurrentHashMap<String,Long>();// 缓存到期时间
	private static ConcurrentHashMap<String,Object> objectCache=new ConcurrentHashMap<String,Object>();// 缓存
	private static ConcurrentHashMap<String,HashMap<String,Object>> objectMapCache=new ConcurrentHashMap<String,HashMap<String,Object>>();// 缓存
	private static ConcurrentHashMap<String,String> stringCache=new ConcurrentHashMap<String,String>();// 缓存
	
	// 清除无用的key
	private void removeKey(String key){
		cacheTime.remove(key);
		objectCache.remove(key);
		objectMapCache.remove(key);
		stringCache.remove(key);
		if(cacheTime.size()>10000){
			Set<String> timeKeys= cacheTime.keySet();
			for(String timeKey:timeKeys){
				if(cacheTime.get(timeKey) < System.currentTimeMillis()){
					cacheTime.remove(timeKey);
					objectCache.remove(timeKey);
					objectMapCache.remove(timeKey);
					stringCache.remove(timeKey);
				}
			}
		}
	}
	
	@Override
	public boolean flushDB() {
		cacheTime.clear();
		objectCache.clear();
		objectMapCache.clear();
		stringCache.clear();
		return false;
	} 
	
	
	@Override
	public String getStr(String key){
		// 缓存过期
		if( cacheTime.get(key) != null && cacheTime.get(key) < System.currentTimeMillis()){
			removeKey(key);
			return null;
		}
		return  stringCache.get(key);
	}
	
	@Override
	public boolean setStr(String key, String value, int expireTime){
		// 如果为-1，存储一年
		if(expireTime == -1) expireTime = 360 * 24 * 60 *60;
		stringCache.put(key, value);
		cacheTime.put(key, System.currentTimeMillis() + expireTime*1000);
		return true;
	}
	
	@Override
	public Object getObj(String key){
		// 缓存过期
		if( cacheTime.get(key) != null && cacheTime.get(key) < System.currentTimeMillis()){
			removeKey(key);
			return null;
		}
		return  objectCache.get(key);
	}
	
	@Override
	public Object getObj(String key, String field){
		// 缓存过期
		if( cacheTime.get(key+"_"+field) != null && cacheTime.get(key+"_"+field) < System.currentTimeMillis()){
			removeKey(key);
			return null;
		}
		if(objectMapCache.containsKey(key)){
			return objectMapCache.get(key).get(field);
		}
		return null;
	}
	
	@Override
	public boolean setObj(String key, Object value, int expireTime){
		// 如果为-1，存储一年
		if(expireTime == -1) expireTime = 360 * 24 * 60 *60;
		objectCache.put(key, value);
		cacheTime.put(key, System.currentTimeMillis() + expireTime*1000);
		return true;
	}
	
	@Override
	public boolean setObj(String key,String field, Object value, int expireTime){
		// 如果为-1，存储一年
		if(expireTime == -1) expireTime = 360 * 24 * 60 *60;
		HashMap<String,Object> map;
		if(objectMapCache.containsKey(key)){
			map = objectMapCache.get(key);
			map.put(field, value);
		}else{
			map = new HashMap<String,Object>();
			map.put(field, value);
			objectMapCache.put(key, map);
		}
		cacheTime.put(key+"_"+field, System.currentTimeMillis() + expireTime*1000);
		return true;
	}

	@Override
	public boolean delStr(String key) {
		stringCache.remove(key);
		cacheTime.remove(key);
		return false;
	}

	@Override
	public boolean delObj(String key) {
		objectCache.remove(key);
		objectMapCache.remove(key);
		cacheTime.remove(key);
		return false;
	} 
	
	@Override
	public boolean delObj(String key, String field) {
		if(objectMapCache.containsKey(key)){
			objectMapCache.get(key).remove(field);
		}
		cacheTime.remove(key+"_" +field);
		return false;
	}

}
