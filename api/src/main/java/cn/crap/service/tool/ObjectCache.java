package cn.crap.service.tool;

import cn.crap.beans.Config;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

@Service("objectCache")
public class ObjectCache{
	private static Cache<String, Object> cache;
	public static final String CACHE_PREFIX = "object";

	public Cache<String, Object> getCache(){
		if (cache == null) {
			cache = CacheBuilder.newBuilder()
					.initialCapacity(10)
					.concurrencyLevel(5)
					.expireAfterWrite(Config.cacheTime, TimeUnit.SECONDS)
					.build();
		}
		return cache;
	}
	
	
	public Object get(String key){
		Assert.notNull(key);
		return getCache().getIfPresent(assembleKey(key));
	}

	public void add(String key, Object object){
		Assert.notNull(key);
		getCache().put(assembleKey(key), object);
	}

    
    public boolean del(String key){
		getCache().invalidate(CACHE_PREFIX + key);
        return true;
    }

	
    public boolean flushDB(){
		cache.invalidateAll();
	    return true;
    }

    private String assembleKey(String key) {
        return CACHE_PREFIX + key;
    }

}
