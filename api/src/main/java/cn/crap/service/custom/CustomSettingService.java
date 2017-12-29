package cn.crap.service.custom;

import cn.crap.dao.mybatis.SettingDao;
import cn.crap.model.mybatis.Setting;
import cn.crap.model.mybatis.SettingCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class CustomSettingService {
    @Autowired
    private SettingDao mapper;

    public Setting getByKey(String key){
        Assert.notNull(key);
        SettingCriteria example = new SettingCriteria();
        SettingCriteria.Criteria criteria = example.createCriteria();
        criteria.andMkeyEqualTo(key);

        List<Setting> settings = mapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(settings)){
            return settings.get(0);
        }

        return null;
    }

    public List<Setting> getAll(){
        SettingCriteria example = new SettingCriteria();
        return mapper.selectByExample(example);
    }

}
