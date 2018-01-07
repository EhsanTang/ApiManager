package cn.crap.service.mybatis;

import cn.crap.dao.mybatis.HotSearchDao;
import cn.crap.enumer.TableId;
import cn.crap.framework.IdGenerator;
import cn.crap.model.mybatis.HotSearch;
import cn.crap.model.mybatis.HotSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * Automatic generation by tools
 * service
 */
@Service
public class HotSearchService {
    @Autowired
    private HotSearchDao dao;

    public List<HotSearch> selectByExample(HotSearchCriteria example) {
        return dao.selectByExample(example);
    }

    public int countByExample(HotSearchCriteria example) {
        return dao.countByExample(example);
    }

    public HotSearch getById(String id) {
        if (id == null){
            return null;
        }
        return dao.selectByPrimaryKey(id);
    }

    public boolean insert(HotSearch model) {
        if (model == null) {
            return false;
        }
        model.setId(IdGenerator.getId(TableId.HOT_SEARCH));
        model.setCreateTime(new Date());
        return dao.insertSelective(model) > 0;
    }

    public boolean update(HotSearch model) {
        if (model == null) {
            return false;
        }
        return dao.updateByPrimaryKeySelective(model) > 0 ? true : false;
    }

    public boolean delete(String id) {
        Assert.notNull(id, "id can't be null");
        return dao.deleteByPrimaryKey(id) > 0 ? true : false;
    }

}
