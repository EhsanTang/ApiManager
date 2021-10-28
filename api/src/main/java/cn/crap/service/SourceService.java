package cn.crap.service;

import cn.crap.adapter.Adapter;
import cn.crap.adapter.SourceAdapter;
import cn.crap.dao.mybatis.SourceDao;
import cn.crap.dto.SearchDto;
import cn.crap.enu.LogType;
import cn.crap.enu.TableId;
import cn.crap.framework.MyException;
import cn.crap.model.Log;
import cn.crap.model.Source;
import cn.crap.model.SourceCriteria;
import cn.crap.query.SourceQuery;
import cn.crap.utils.ILogConst;
import cn.crap.utils.Page;
import cn.crap.utils.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SourceService extends BaseService<Source, SourceDao> implements ILogConst, ILuceneService {
    private SourceDao sourceDao;
    @Autowired
    private LogService logService;

    @Resource
    public void SourceDao(SourceDao sourceDao) {
        this.sourceDao = sourceDao;
        super.setBaseDao(sourceDao, TableId.SOURCE);
    }

    public void update(Source model, boolean needAddLog) throws MyException{
        if (needAddLog) {
            Source dbModel = sourceDao.selectByPrimaryKey(model.getId());
            Log log = Adapter.getLog(dbModel.getId(), L_SOURCE_CHINESE, dbModel.getName(), LogType.UPDATE, dbModel.getClass(), dbModel);
            logService.insert(log);
        }
        sourceDao.updateByPrimaryKeySelective(model);
    }

    @Override
    public boolean delete(String id) throws MyException{
        Assert.notNull(id);
        Source dbModel = sourceDao.selectByPrimaryKey(id);
        Log log = Adapter.getLog(dbModel.getId(), L_SOURCE_CHINESE, dbModel.getName(), LogType.DELTET, dbModel.getClass(), dbModel);
        logService.insert(log);

        return super.delete(id);
    }

    /**
     * 查询文件
     *
     * @param query
     * @return
     * @throws MyException
     */
    public List<Source> query(SourceQuery query) throws MyException {
        Assert.notNull(query);

        Page page = new Page(query);
        SourceCriteria example = getSourceCriteria(query);
        example.setLimitStart(page.getStart());
        example.setMaxResults(page.getSize());
        example.setOrderByClause(query.getSort() == null ? TableField.SORT.SEQUENCE_DESC : query.getSort());

        return sourceDao.selectByExample(example);
    }

    /**
     * 查询文件数量
     *
     * @param query
     * @return
     * @throws MyException
     */
    public int count(SourceQuery query) throws MyException {
        Assert.notNull(query);

        SourceCriteria example = getSourceCriteria(query);
        return sourceDao.countByExample(example);
    }

    private SourceCriteria getSourceCriteria(SourceQuery query) throws MyException {
        SourceCriteria example = new SourceCriteria();
        SourceCriteria.Criteria criteria = example.createCriteria();
        if (query.getName() != null) {
            criteria.andNameLike("%" + query.getName() + "%");
        }
        if (query.getStatus() != null) {
            criteria.andStatusEqualTo(query.getStatus());
        }

        if (query.getProjectId() != null) {
            criteria.andProjectIdEqualTo(query.getProjectId());
        }

        if (query.getModuleId() != null) {
            criteria.andModuleIdEqualTo(query.getModuleId());
        }
        return example;
    }

    @Override
    public List<SearchDto> selectOrderById(String projectId, String id, int pageSize){
        Assert.isTrue(pageSize > 0 && pageSize <= 1000);
        SourceCriteria example = new SourceCriteria();
        SourceCriteria.Criteria criteria = example.createCriteria();
        if (projectId != null){
            criteria.andProjectIdEqualTo(projectId);
        }
        example.setMaxResults(pageSize);
        if (id != null){
            criteria.andIdGreaterThan(id);
        }
        example.setOrderByClause(TableField.SORT.ID_ASC);
        return SourceAdapter.getSearchDto(sourceDao.selectByExample(example));
    }
}
