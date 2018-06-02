package cn.crap.service.mybatis;

import cn.crap.dao.mybatis.DebugDao;
import cn.crap.framework.IdGenerator;
import cn.crap.enumer.TableId;
import cn.crap.model.mybatis.Debug;
import cn.crap.model.mybatis.DebugCriteria;
import cn.crap.utils.MyString;
import cn.crap.utils.TableField;
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
public class DebugService {
    @Autowired
    private DebugDao mapper;

    public List<Debug> selectByExample(DebugCriteria example) {
        return mapper.selectByExample(example);
    }

    public int countByExample(DebugCriteria example) {
        return mapper.countByExample(example);
    }

    public Debug getById(String id) {
        if (id == null){
            return null;
        }
        return mapper.selectByPrimaryKey(id);
    }

    public boolean insert(Debug model) {
        if (model == null) {
            return false;
        }
        if (MyString.isEmpty(model.getId())) {
            model.setId(IdGenerator.getId(TableId.DEBUG));
        }
        if (model.getSequence() == null){
            DebugCriteria example = new DebugCriteria();
            example.setOrderByClause(TableField.SORT.SEQUENCE_DESC);
            example.setMaxResults(1);
            List<Debug>  models = this.selectByExample(example);
            if (models.size() > 0){
                model.setSequence(models.get(0).getSequence() + 1);
            }else{
                model.setSequence(0);
            }
        }
        model.setCreateTime(new Date());
        return mapper.insertSelective(model) > 0;
    }

    public boolean update(Debug model) {
        if (model == null) {
            return false;
        }
        return mapper.updateByPrimaryKeySelective(model) > 0 ? true : false;
    }

    public boolean delete(String id) {
        Assert.notNull(id, "id can't be null");
        return mapper.deleteByPrimaryKey(id) > 0 ? true : false;
    }

}
