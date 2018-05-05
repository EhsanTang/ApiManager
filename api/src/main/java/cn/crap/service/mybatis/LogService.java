package cn.crap.service.mybatis;

import cn.crap.dao.mybatis.LogDao;
import cn.crap.enumer.TableId;
import cn.crap.framework.IdGenerator;
import cn.crap.model.mybatis.Log;
import cn.crap.model.mybatis.LogCriteria;
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
public class LogService {
    @Autowired
    private LogDao mapper;

    public List<Log> selectByExample(LogCriteria example) {
        return mapper.selectByExample(example);
    }

    public int countByExample(LogCriteria example) {
        return mapper.countByExample(example);
    }

    public Log getById(String id) {
        if (id == null){
            return null;
        }
        return mapper.selectByPrimaryKey(id);
    }

    public boolean insert(Log model) {
        if (model == null) {
            return false;
        }
        model.setId(IdGenerator.getId(TableId.LOG));
        if (model.getSequence() == null){
            LogCriteria example = new LogCriteria();
            example.setOrderByClause(TableField.SORT.SEQUENCE_DESC);
            example.setMaxResults(1);
            List<Log>  models = this.selectByExample(example);
            if (models.size() > 0){
                model.setSequence(models.get(0).getSequence() + 1);
            }else{
                model.setSequence(0);
            }
        }
        model.setCreateTime(new Date());
        return mapper.insertSelective(model) > 0;
    }

    public boolean update(Log model) {
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
