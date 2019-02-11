package cn.crap.service;

import cn.crap.dao.mybatis.ErrorDao;
import cn.crap.enu.TableId;
import cn.crap.framework.MyException;
import cn.crap.model.Error;
import cn.crap.model.ErrorCriteria;
import cn.crap.query.ErrorQuery;
import cn.crap.utils.Page;
import cn.crap.utils.TableField;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ErrorService extends BaseService<Error, ErrorDao> {
    private ErrorDao errorDao;
    @Resource
    public void ErrorDao(ErrorDao errorDao) {
        this.errorDao = errorDao;
        super.setBaseDao(errorDao, TableId.ERROR);
    }

    /**
     * 查询错误码
     * @param query
     * @return
     * @throws MyException
     */
    public List<Error> query(ErrorQuery query) throws MyException {
        Assert.notNull(query);

        Page page = new Page(query);
        ErrorCriteria example = getErrorCriteria(query);
        example.setLimitStart(page.getStart());
        example.setMaxResults(page.getSize());
        example.setOrderByClause(query.getSort() == null ? TableField.SORT.SEQUENCE_DESC : query.getSort());

        return errorDao.selectByExample(example);
    }

    /**
     * 查询错误码数量
     * @param query
     * @return
     * @throws MyException
     */
    public int count(ErrorQuery query) throws MyException {
        Assert.notNull(query);

        ErrorCriteria example = getErrorCriteria(query);
        return errorDao.countByExample(example);
    }

    private ErrorCriteria getErrorCriteria(ErrorQuery query) throws MyException {
        ErrorCriteria example = new ErrorCriteria();
        ErrorCriteria.Criteria criteria = example.createCriteria();
        if (query.getProjectId() != null) {
            criteria.andProjectIdEqualTo(query.getProjectId());
        }
        if (query.getEqualErrorCode() != null) {
            criteria.andErrorCodeEqualTo(query.getEqualErrorCode());
        }
        if (query.getErrorCode() != null) {
            criteria.andErrorCodeLike("%" + query.getErrorCode() + "%");
        }
        if (query.getErrorMsg() != null) {
            criteria.andErrorMsgLike("%" + query.getErrorMsg() + "%");
        }
        if (!CollectionUtils.isEmpty(query.getErrorCodeList())){
            criteria.andErrorCodeIn(query.getErrorCodeList());
        }
        return example;
    }
}
