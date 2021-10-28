package cn.crap.utils;

import cn.crap.dto.LoginInfoDto;
import cn.crap.enu.AttributeEnum;
import cn.crap.enu.SettingEnum;
import cn.crap.service.tool.SettingCache;

/**
 * @author Ehsan
 * @date 2020/7/19 13:24
 */
public class VipUtil {

    /**
     * 获取postwoman插件项目最大数量
     * @param settingCache
     * @param user
     * @return
     */
    public static int getPostWomanPlugProjectNum(SettingCache settingCache, LoginInfoDto user){
        Integer defPostWomanNum = settingCache.getInt(SettingEnum.POST_WOMAN_PROJECT_NUM);
        String vpiUserPostWomanNum = user.getAttribute(AttributeEnum.VIP_POST_WOMAN_PROJECT_NUM);

        if (vpiUserPostWomanNum != null){
            Integer defVipPostWomanNum = settingCache.getInt(SettingEnum.POST_WOMAN_VIP_PROJECT_NUM);
            return MyInteger.getInt(vpiUserPostWomanNum, defVipPostWomanNum, defVipPostWomanNum, 100);
        }

        return defPostWomanNum;
    }

    /**
     * 获取插件最大允许的接口数来能
     * @param settingCache
     * @param user
     * @return
     */
    public static int getPostWomanPlugInterNum(SettingCache settingCache, LoginInfoDto user){
        Integer defPostWomanInterNum = settingCache.getInt(SettingEnum.POST_WOMAN_INTER_NUM);
        String vpiUserPostWomanInterNum = user.getAttribute(AttributeEnum.VIP_POST_WOMAN_INTER_NUM);

        if (vpiUserPostWomanInterNum != null){
            Integer defVipPostWomanNum = settingCache.getInt(SettingEnum.POST_WOMAN_VIP_INTER_NUM);
            return MyInteger.getInt(vpiUserPostWomanInterNum, defVipPostWomanNum, defVipPostWomanNum, 500);
        }
        return defPostWomanInterNum;
    }
}
