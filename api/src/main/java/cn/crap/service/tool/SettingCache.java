package cn.crap.service.tool;

import cn.crap.adapter.SettingAdapter;
import cn.crap.dto.SettingDto;
import cn.crap.enu.SettingEnum;
import cn.crap.model.Setting;
import cn.crap.service.SettingService;
import cn.crap.beans.Config;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service("settingCache")
public class SettingCache{
	private static List<SettingDto> settingDtos = null;
	private static Cache<String, SettingDto> cache;
	public static final String CACHE_PREFIX = "setting";

	@Autowired
	private SettingService customSettingService;

	public Cache<String, SettingDto> getCache(){
	    if (cache == null) {
            cache = CacheBuilder.newBuilder()
                    .initialCapacity(10)
                    .concurrencyLevel(5)
                    .expireAfterWrite(Config.cacheTime, TimeUnit.SECONDS)
                    .build();
        }
        return cache;
	}
	
	public Integer getInteger(SettingEnum settingEnum){
		try {
			return Integer.parseInt(get(settingEnum.getKey()).getValue());
		}catch (Exception e){
			return Integer.parseInt(settingEnum.getValue());
		}
	}

	
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

    
    public boolean del(String key){
		getCache().invalidate(CACHE_PREFIX + key);
		settingDtos = null;
        return true;
    }

	
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

    public boolean equalse(SettingEnum settingEnum, String value){
    	if (value == null || settingEnum == null || this.get(settingEnum.getKey()) == null
				|| this.get(settingEnum.getKey()).getValue() == null){
			return false;
		}
		return this.get(settingEnum.getKey()).getValue().equals(value);
	}
}
