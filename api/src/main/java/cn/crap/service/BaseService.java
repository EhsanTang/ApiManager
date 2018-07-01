package cn.crap.service;

import cn.crap.dao.mybatis.BaseDao;
import cn.crap.enumer.TableId;
import cn.crap.framework.IdGenerator;
import cn.crap.framework.MyException;
import cn.crap.model.BasePo;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;

@Service
public class BaseService<PO extends BasePo, DAO> {

    private BaseDao<PO> baseDao;
    private TableId tableId;
    public void setBaseDao(BaseDao<PO> baseDao, TableId tableId) {
        this.baseDao = baseDao;
        this.tableId = tableId;
    }

    public PO getById(String id) {
        if (id == null){
            return null;
        }
        return baseDao.selectByPrimaryKey(id);
    }

    public boolean insert(PO po) throws MyException {
        if (po == null) {
            return false;
        }
        po.setId(IdGenerator.getId(tableId));
        if (po.getSequence() == null){
            po.setSequence(0);
        }
        po.setCreateTime(new Date());
        return baseDao.insertSelective(po) > 0;
    }

    public boolean update(PO po) {
        if (po == null) {
            return false;
        }
        po.setCreateTime(null);
        // TODO 添加更新时间
        return baseDao.updateByPrimaryKeySelective(po) > 0 ? true : false;
    }

    public boolean delete(String id) throws MyException{
        Assert.notNull(id, "id can't be null");
        return baseDao.deleteByPrimaryKey(id) > 0 ? true : false;
    }

}
