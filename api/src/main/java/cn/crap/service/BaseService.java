package cn.crap.service;

import cn.crap.dao.mybatis.BaseDao;
import cn.crap.enu.TableId;
import cn.crap.framework.IdGenerator;
import cn.crap.framework.MyException;
import cn.crap.model.BasePO;
import cn.crap.utils.IConst;
import cn.crap.utils.MyString;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;

@Deprecated
@Service
public class BaseService<PO extends BasePO, DAO> {
    protected Logger log = Logger.getLogger("service");

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

        // 兼容CrapDebug系统
        if (MyString.isEmpty(po.getId())){
            po.setId(IdGenerator.getId(tableId));
        }

        if (po.getSequence() == null){
            po.setSequence(System.currentTimeMillis());
        }

        if (po.getSequence() < 0){
            po.setSequence(System.currentTimeMillis());
        }
        /**
         * 不能超过mysql最大限制
         */
        if (po.getSequence() > IConst.C_MAX_SEQUENCE){
            po.setSequence(IConst.C_MAX_SEQUENCE);
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
