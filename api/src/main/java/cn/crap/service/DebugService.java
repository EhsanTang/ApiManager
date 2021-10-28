package cn.crap.service;

import cn.crap.dao.custom.CustomDebugDao;
import cn.crap.dao.mybatis.DebugDao;
import cn.crap.enu.TableId;
import cn.crap.framework.MyException;
import cn.crap.model.Debug;
import cn.crap.model.DebugCriteria;
import cn.crap.query.DebugQuery;
import cn.crap.utils.IConst;
import cn.crap.utils.Page;
import cn.crap.utils.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DebugService extends BaseService<Debug, DebugDao> {
    @Autowired
    private CustomDebugDao customDebugDao;
    private DebugDao debugDao;

    @Resource
    public void DebugDao(DebugDao debugDao) {
        this.debugDao = debugDao;
        super.setBaseDao(debugDao, TableId.DEBUG);
    }

    /**
     * 查询调试
     * @param query
     * @return
     * @throws MyException
     */
    public List<Debug> query(DebugQuery query) throws MyException {
        Assert.notNull(query);

        DebugCriteria example = getDebugCriteria(query);
        example.setOrderByClause(query.getSort() == null ? TableField.SORT.SEQUENCE_DESC : query.getSort());

        return debugDao.selectByExample(example);
    }

    /**
     * 查询调试数量
     * @param query
     * @return
     * @throws MyException
     */
    public int count(DebugQuery query) throws MyException {
        Assert.notNull(query);

        DebugCriteria example = getDebugCriteria(query);
        return debugDao.countByExample(example);
    }

    private DebugCriteria getDebugCriteria(DebugQuery query) throws MyException {
        DebugCriteria example = new DebugCriteria();
        DebugCriteria.Criteria criteria = example.createCriteria();
        if (query.getStatus() != null) {
            criteria.andStatusEqualTo(query.getStatus());
        }
        if (query.getModuleId() != null) {
            criteria.andModuleIdEqualTo(query.getModuleId());
        }
        if (!CollectionUtils.isEmpty(query.getModuleIds())){
            criteria.andModuleIdIn(query.getModuleIds());
        }
        if (query.getUserId() != null){
            criteria.andUidEqualTo(query.getUserId());
        }

        Page page = new Page(query);
        example.setLimitStart(page.getStart());
        example.setMaxResults(page.getSize());
        return example;
    }

    public void deleteByModelId(String moduleId) {
        Assert.notNull(moduleId);
        customDebugDao.deleteByModuleId(moduleId);
    }

}
