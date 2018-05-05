package cn.crap.service.tool;

import cn.crap.dto.LoginInfoDto;
import cn.crap.service.ICacheService;
import cn.crap.beans.Config;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

@Service("userCache")
public class UserCache implements ICacheService<LoginInfoDto> {
	private static Cache<String, LoginInfoDto> cache;
	public static final String CACHE_PREFIX= "user:";

	@Autowired
	private Config config;

	public Cache<String, LoginInfoDto> getCache(){
	    if (cache == null) {
            cache = CacheBuilder.newBuilder()
                    .initialCapacity(10)
                    .concurrencyLevel(5)
                    .expireAfterWrite(config.getLoginInforTime(), TimeUnit.SECONDS)
                    .build();
        }
        return cache;
	}
	
	@Override
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

    @Override
    public boolean del(String userId){
		getCache().invalidate(assembleKey(userId));
        return true;
    }

	@Override
    public boolean flushDB(){
		getCache().invalidateAll();
	    return true;
    }

	private String assembleKey(String userId) {
		return CACHE_PREFIX + userId;
	}
}
