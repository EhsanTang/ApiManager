package cn.crap.service.tool;

import cn.crap.adapter.SettingAdapter;
import cn.crap.dto.SettingDto;
import cn.crap.model.mybatis.Setting;
import cn.crap.service.ICacheService;
import cn.crap.service.custom.CustomSettingService;
import cn.crap.beans.Config;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service("settingCache")
public class SettingCache implements ICacheService<SettingDto> {
	private static List<SettingDto> settingDtos = null;
	private static Cache<String, SettingDto> cache;
	public static final String CACHE_PREFIX = "setting";

	@Autowired
	private Config config;
	@Autowired
	private CustomSettingService customSettingService;

	public Cache<String, SettingDto> getCache(){
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
	public SettingDto get(String key){
		Assert.notNull(key);
		Object obj = getCache().getIfPresent(assembleKey(key));
		if (obj != null) {
			return (SettingDto) obj;
		}

		Setting setting = customSettingService.getByKey(key);
		if (setting == null) {
			return new SettingDto();
		}
		SettingDto settingDto = SettingAdapter.getDto(setting);
		getCache().put(assembleKey(key), settingDto);
		return settingDto;
	}

    @Override
    public boolean del(String key){
		getCache().invalidate(CACHE_PREFIX + key);
		settingDtos = null;
        return true;
    }

	@Override
    public boolean flushDB(){
		getCache().invalidateAll();
		settingDtos = null;
	    return true;
    }

    /**
     * 获取所有的setting
     * @return
     */
    public List<SettingDto> getAll(){
        if (settingDtos != null){
            return settingDtos;
        }

        List<Setting> settings= customSettingService.getAll();
        settingDtos = SettingAdapter.getDto(settings);
        return settingDtos;
    }

    private String assembleKey(String key) {
        return CACHE_PREFIX + key;
    }

}
