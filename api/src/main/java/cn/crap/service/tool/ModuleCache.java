package cn.crap.service.tool;

import cn.crap.beans.Config;
import cn.crap.model.ModulePO;
import cn.crap.service.ModuleService;
import cn.crap.utils.MyString;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service("moduleCache")
public class ModuleCache{
	private static Cache<String, ModulePO> cache;
	public static final String CACHE_PREFIX = "module:";

	@Autowired
	private ModuleService moduleService;

	public Cache<String, ModulePO> getCache(){
		if (cache == null) {
			cache = CacheBuilder.newBuilder()
					.initialCapacity(10)
					.concurrencyLevel(5)
					.expireAfterWrite(Config.cacheTime, TimeUnit.SECONDS)
					.build();
		}
		return cache;
	}
	
	public ModulePO get(String moduleId){
		if(MyString.isEmpty(moduleId)){
			ModulePO module = new ModulePO();
			module.setUrl("");
			return module;
		}

		String cacheKey = assembleKey(moduleId);
		ModulePO module = getCache().getIfPresent(cacheKey);
		if(module != null){
			return module;
		}

		module = moduleService.get(moduleId);
		if(module == null) {
			module = new ModulePO();
			module.setUrl("");
			return module;
		}

		getCache().put(cacheKey, module);
		//内存缓存时拷贝对象，防止在Controller中将密码修改为空时导致问题
		ModulePO p = new ModulePO();
		BeanUtils.copyProperties(module, p);
		return p;
	}

    public boolean del(String moduleId){
		getCache().invalidate(assembleKey(moduleId));
        return true;
    }
	

    public boolean flushDB(){
		getCache().invalidateAll();
	    return true;
    }

	private String assembleKey(String moduleId) {
		return CACHE_PREFIX + moduleId;
	}
}
