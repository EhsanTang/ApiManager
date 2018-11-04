package cn.crap.service;

import cn.crap.dao.custom.CustomHotSearchDao;
import cn.crap.dao.mybatis.HotSearchDao;
import cn.crap.enu.TableId;
import cn.crap.framework.MyException;
import cn.crap.model.HotSearch;
import cn.crap.model.HotSearchCriteria;
import cn.crap.query.HotSearchQuery;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;


@Service
public class HotSearchService extends BaseService<HotSearch, HotSearchDao> {
    @Autowired
    private CustomHotSearchDao customHotSearchDao;
    private HotSearchDao hotSearchDao;

    @Resource
    public void HotSearchDao(HotSearchDao hotSearchDao) {
        this.hotSearchDao = hotSearchDao;
        super.setBaseDao(hotSearchDao, TableId.HOT_SEARCH);
    }

    /**
     * 查询热搜
     * @param query
     * @return
     * @throws MyException
     */
    public List<HotSearch> query(HotSearchQuery query) throws MyException {
        Assert.notNull(query);

        Page page = new Page(query);
        HotSearchCriteria example = getHotSearchCriteria(query);
        example.setLimitStart(page.getStart());
        example.setMaxResults(page.getSize());
        example.setOrderByClause(query.getSort() == null ? TableField.SORT.TIMES_DESC : query.getSort());

        return hotSearchDao.selectByExample(example);
    }

    /**
     * 查询热搜数量
     * @param query
     * @return
     * @throws MyException
     */
    public int count(HotSearchQuery query) throws MyException {
        Assert.notNull(query);

        HotSearchCriteria example = getHotSearchCriteria(query);
        return hotSearchDao.countByExample(example);
    }

    private HotSearchCriteria getHotSearchCriteria(HotSearchQuery query) throws MyException {
        HotSearchCriteria example = new HotSearchCriteria();
        HotSearchCriteria.Criteria criteria = example.createCriteria();
        return example;
    }
    
    public List<String> queryTop10() {
        return customHotSearchDao.queryTop10();
    }

    public HotSearch tryGetByKeyWord(String keyword) {
        if (MyString.isEmpty(keyword)) {
            return null;
        }
        HotSearchCriteria example = new HotSearchCriteria();
        example.createCriteria().andKeywordEqualTo(keyword);

        List<HotSearch> hotSearches = hotSearchDao.selectByExample(example);
        if (!CollectionUtils.isEmpty(hotSearches)) {
            return hotSearches.get(0);
        }
        return null;
    }
}
