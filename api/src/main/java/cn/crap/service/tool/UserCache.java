package cn.crap.service.tool;

import cn.crap.beans.Config;
import cn.crap.dto.LoginInfoDto;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

@Service("userCache")
public class UserCache{
	private static Cache<String, LoginInfoDto> cache;
	public static final String CACHE_PREFIX= "user:";

	public Cache<String, LoginInfoDto> getCache(){
	    if (cache == null) {
            cache = CacheBuilder.newBuilder()
                    .initialCapacity(10)
                    .concurrencyLevel(5)
                    .expireAfterWrite(Config.loginInforTime, TimeUnit.SECONDS)
                    .build();
        }
        return cache;
	}
	
	
	public LoginInfoDto get(String userId){
		Assert.notNull(userId);
		Object obj = getCache().getIfPresent(assembleKey(userId));
		if(obj != null){
			return (LoginInfoDto) obj;
		}
        return null;
	}

	public boolean add(String userId, LoginInfoDto user)
    {
		getCache().put(assembleKey(userId), user);
		return true;
	}

    
    public boolean del(String userId){
		getCache().invalidate(assembleKey(userId));
        return true;
    }

	
    public boolean flushDB(){
		getCache().invalidateAll();
	    return true;
    }

	private String assembleKey(String userId) {
		return CACHE_PREFIX + userId;
	}
}
