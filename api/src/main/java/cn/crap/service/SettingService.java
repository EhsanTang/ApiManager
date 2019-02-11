package cn.crap.service;

import cn.crap.dao.mybatis.SettingDao;
import cn.crap.enu.SettingStatus;
import cn.crap.enu.TableId;
import cn.crap.framework.MyException;
import cn.crap.model.Setting;
import cn.crap.model.SettingCriteria;
import cn.crap.query.SettingQuery;
import cn.crap.utils.Page;
import cn.crap.utils.TableField;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SettingService extends BaseService<Setting, SettingDao> {
    private SettingDao settingDao;

    @Resource
    public void SettingDao(SettingDao settingDao) {
        this.settingDao = settingDao;
        super.setBaseDao(settingDao, TableId.SETTING);
    }

    @Override
    public boolean insert(Setting setting) throws MyException{
        if (setting == null) {
            return false;
        }
        return super.insert(setting);
    }

    /**
     * 查询设置
     * @param query
     * @return
     * @throws MyException
     */
    public List<Setting> query(SettingQuery query) throws MyException {
        Assert.notNull(query);

        Page page = new Page(query);
        SettingCriteria example = getSettingCriteria(query);
        example.setLimitStart(page.getStart());
        example.setMaxResults(page.getSize());
        example.setOrderByClause(query.getSort() == null ? TableField.SORT.SEQUENCE_DESC : query.getSort());

        return settingDao.selectByExample(example);
    }

    /**
     * 查询设置数量
     * @param query
     * @return
     * @throws MyException
     */
    public int count(SettingQuery query) throws MyException {
        Assert.notNull(query);

        SettingCriteria example = getSettingCriteria(query);
        return settingDao.countByExample(example);
    }

    private SettingCriteria getSettingCriteria(SettingQuery query) throws MyException {
        SettingCriteria example = new SettingCriteria();
        SettingCriteria.Criteria criteria = example.createCriteria();
        if (query.getKey() != null) {
            criteria.andMkeyLike("%" + query.getKey() + "%");
        }
        if (query.getRemark() != null) {
            criteria.andRemarkLike("%" + query.getRemark() + "%");
        }
        criteria.andStatusGreaterThan(SettingStatus.DELETE.getStatus());
        return example;
    }

    public Setting getByKey(String key){
        Assert.notNull(key);
        SettingCriteria example = new SettingCriteria();
        SettingCriteria.Criteria criteria = example.createCriteria();
        criteria.andMkeyEqualTo(key);

        List<Setting> settings = settingDao.selectByExample(example);
        if (!CollectionUtils.isEmpty(settings)){
            return settings.get(0);
        }

        return null;
    }

    public List<Setting> getAll(){
        SettingCriteria example = new SettingCriteria();
        return settingDao.selectByExample(example);
    }

}
