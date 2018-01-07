package cn.crap.service.tool;

import cn.crap.model.mybatis.Module;
import cn.crap.service.ICacheService;
import cn.crap.service.mybatis.ModuleService;
import cn.crap.beans.Config;
import cn.crap.utils.MyString;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service("moduleCache")
public class ModuleCache implements ICacheService<Module> {
	private static Cache<String, Module> cache;
	public static final String CACHE_PREFIX = "module:";

	@Autowired
	private Config config;
	@Autowired
	private ModuleService moduleService;

	public Cache<String, Module> getCache(){
		if (cache == null) {
			cache = CacheBuilder.newBuilder()
					.initialCapacity(10)
					.concurrencyLevel(5)
					.expireAfterWrite(config.getCacheTime(), TimeUnit.SECONDS)
					.build();
		}
		return cache;
	}
	
	@Override
	public Module get(String moduleId){
		if(MyString.isEmpty(moduleId)){
			return new Module();
		}
		String cacheKey = assembleKey(moduleId);
		Module module = getCache().getIfPresent(cacheKey);
		if(module == null){
			module = moduleService.getById(moduleId);
			if(module == null) {
				module = new Module();
			}
			getCache().put(cacheKey, module);
		}
		//内存缓存时拷贝对象，防止在Controller中将密码修改为空时导致问题
		Module p = new Module();
		BeanUtils.copyProperties(module, p);
		return p;
	}

    @Override
    public boolean del(String moduleId){
		getCache().invalidate(assembleKey(moduleId));
        return true;
    }
	

	@Override
    public boolean flushDB(){
		getCache().invalidateAll();
	    return true;
    }

	private String assembleKey(String moduleId) {
		return CACHE_PREFIX + moduleId;
	}
}
