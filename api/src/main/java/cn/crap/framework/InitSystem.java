package cn.crap.framework;

import cn.crap.adapter.SettingAdapter;
import cn.crap.beans.Config;
import cn.crap.dto.SettingDto;
import cn.crap.enu.SettingEnum;
import cn.crap.model.Setting;
import cn.crap.service.SettingService;
import cn.crap.service.tool.SettingCache;
import cn.crap.service.tool.SystemService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
    protected Logger log = Logger.getLogger(getClass());

    @Resource
    private SettingCache settingCache;
    @Resource
    private SystemService systemService;
    @Resource
    private SettingService settingService;

    /**
     *  PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器执行一次
     *  PostConstruct在构造函数之后执行,init()方法之前执行
     */
    @PostConstruct
    public void init(){
        log.info("初始化系统数据中....");
        initConfig();
        initSetting();
        mergeSource();
        updateDataBase();
        // updateDataBase，需要重新加载setting
        settingCache.flushDB();
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
                log.error("初始化setting数据异常....", e);
            }
        }
    }

    /**
     * 合并资源
     */
    public void mergeSource(){
        try{
            log.info("合并资源....");
            systemService.mergeSource();
        }catch (Exception e){
            log.error("合并资源文件异常....", e);
        }
    }

    /**
     * 将config配置加载至静态类
     */
    public void initConfig(){
        try{
            log.info("初始化config....");
            Config.init();
        }catch (Exception e){
            log.error("初始化config....", e);
        }
    }

    public void updateDataBase(){
        try{
            log.info("更新数据库结构....");
            systemService.updateDataBase();
        }catch (Throwable e){
            log.error("更新数据库结构失败....", e);
        }
    }
}
