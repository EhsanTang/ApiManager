package cn.crap.framework;

import cn.crap.adapter.SettingAdapter;
import cn.crap.dto.SettingDto;
import cn.crap.enumer.SettingEnum;
import cn.crap.model.Setting;
import cn.crap.service.SettingService;
import cn.crap.service.tool.SettingCache;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ehsan
 * @date 2018/6/30 23:07
 */
@Service
public class InitSystem {
    @Resource
    private SettingCache settingCache;
    private SettingService settingService;

    @Resource
    public void InitSystem(SettingService settingService){
        this.settingService = settingService;
        init();
    }

    public void init(){
        System.out.println("初始化系统数据中....");
        initSetting();
    }
    /**
     * 初始化系统配置
     */
    public void initSetting(){
        List<SettingDto> settingDtos = settingCache.getAll();
        Map<String, SettingDto> settingDtoMap = new HashMap<>();
        for (SettingDto settingDto : settingDtos){
            settingDtoMap.put(settingDto.getKey(), settingDto);
        }

        // 新增加且没有写入数据库的配置，并储存至数据库
        for (SettingEnum settingEnum : SettingEnum.values()){
            try {
                String key = settingEnum.getKey();
                if (!settingDtoMap.containsKey(key)) {
                    settingService.insert(settingEnum.getSetting());
                    settingCache.del(key);
                    continue;
                }

                SettingDto settingDto = settingDtoMap.get(key);
                boolean needUpdate = ((!settingDto.getStatus().equals(settingEnum.getStatus())) ||
                        (!settingDto.getType().equals(settingEnum.getType())));
                if (needUpdate){
                    Setting setting = SettingAdapter.getModel(settingDto);
                    setting.setStatus(settingEnum.getStatus());
                    setting.setType(settingEnum.getType());
                    setting.setCanDelete(null);

                    settingService.update(setting);
                    settingCache.del(key);
                    continue;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
