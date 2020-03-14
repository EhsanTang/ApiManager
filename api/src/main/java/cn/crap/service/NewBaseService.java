package cn.crap.service;

import cn.crap.dao.mybatis.NewBaseDao;
import cn.crap.enu.TableId;
import cn.crap.framework.IdGenerator;
import cn.crap.framework.MyException;
import cn.crap.model.BasePO;
import cn.crap.query.BaseQuery;
import cn.crap.utils.AttributeUtils;
import cn.crap.utils.IConst;
import cn.crap.utils.MyString;
import cn.crap.utils.TableField;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class NewBaseService<PO extends BasePO, Query extends BaseQuery> {
    protected Logger log = Logger.getLogger("service");

    private NewBaseDao<PO, Query> newBaseDao;
    private TableId tableId;
    public void setBaseDao(NewBaseDao<PO, Query> myBaseDao, TableId tableId) {
        this.newBaseDao = myBaseDao;
        this.tableId = tableId;
    }

    public PO get(String id) {
        Assert.notNull(id);
        return newBaseDao.get(id);
    }

    public boolean insert(PO po) throws MyException{
        Assert.notNull(po);

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
        return newBaseDao.insert(po) > 0;
    }

    public boolean update(PO po){
        Assert.notNull(po);
        Assert.notNull(po.getId());

        po.setCreateTime(null);
        po.setUpdateTime(new Date());
        return newBaseDao.update(po) > 0 ? true : false;
    }

    public boolean delete(String id) throws MyException{
        Assert.notNull(id, "id can't be null");
        return newBaseDao.delete(id) > 0 ? true : false;
    }

    public int count(Query query){
        Assert.notNull(query, "query can't be null");
        return newBaseDao.count(query);
    }

    public List<PO> select(Query query) throws MyException {
        Assert.notNull(query, "query can't be null");
        if (query.getSort() == null){
            query.setSort(TableField.SORT.SEQUENCE_DESC);
        }
        return newBaseDao.select(query);
    }

    /**
     * 更新属性
     * @param id
     * @param attributeKey
     * @param attributeValue
     * @param po 空的更新对象
     */
    public void updateAttribute(String id, String attributeKey, String attributeValue, PO po){
        Assert.notNull(id);
        Assert.notNull(attributeKey);
        Assert.notNull(po);

        if (MyString.isEmpty(attributeKey)) {
            return;
        }
        PO dbPo = get(id);
        Map<String, String> attributeMap = AttributeUtils.getAttributeMap(dbPo.getAttributes());
        attributeMap.put(attributeKey, attributeValue);

        po.setId(id);
        po.setAttributes(AttributeUtils.getAttributeStr(attributeMap));
        update(po);
    }

    /**
     * 删除属性值
     * @param id
     * @param attributeKey
     * @param po 空的更新对象
     */
    public void deleteAttribute(String id, String attributeKey, PO po){
        Assert.notNull(id);
        Assert.notNull(attributeKey);
        Assert.notNull(po);
        if (MyString.isEmpty(attributeKey)) {
            return;
        }

        PO dbPo = get(id);
        Map<String, String> attributeMap = AttributeUtils.getAttributeMap(dbPo.getAttributes());
        attributeMap.remove(attributeKey);

        po.setId(id);
        po.setAttributes(AttributeUtils.getAttributeStr(attributeMap));
        update(po);
    }

}
