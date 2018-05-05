package cn.crap.service.mybatis;

import cn.crap.dao.mybatis.SettingDao;
import cn.crap.enumer.TableId;
import cn.crap.framework.IdGenerator;
import cn.crap.model.mybatis.Setting;
import cn.crap.model.mybatis.SettingCriteria;
import cn.crap.utils.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@Service
public class SettingService {
    @Autowired
    private SettingDao settingMapper;

    public List<Setting> selectByExample(SettingCriteria example) {
        return settingMapper.selectByExample(example);
    }

    public int countByExample(SettingCriteria example) {
        return settingMapper.countByExample(example);
    }

    public Setting getById(String id) {
        if (id == null){
            return null;
        }
        return settingMapper.selectByPrimaryKey(id);
    }

    public boolean insert(Setting setting) {
        if (setting == null) {
            return false;
        }
        setting.setId(IdGenerator.getId(TableId.SETTING));
        if (setting.getSequence() == null){
            SettingCriteria example = new SettingCriteria();
            example.setOrderByClause(TableField.SORT.SEQUENCE_DESC);
            example.setMaxResults(1);
            List<Setting>  menus = this.selectByExample(example);
            if (menus.size() > 0){
                setting.setSequence(menus.get(0).getSequence() + 1);
            }else{
                setting.setSequence(0);
            }
        }
        setting.setCreateTime(new Date());
        return settingMapper.insertSelective(setting) > 0;
    }

    public boolean update(Setting setting) {
        if (setting == null) {
            return false;
        }
        return settingMapper.updateByPrimaryKeySelective(setting) > 0 ? true : false;
    }

    public boolean delete(String id) {
        Assert.notNull(id, "id 不能为空");
        return settingMapper.deleteByPrimaryKey(id) > 0 ? true : false;
    }

}
