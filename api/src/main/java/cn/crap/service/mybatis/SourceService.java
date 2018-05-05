package cn.crap.service.mybatis;

import cn.crap.dao.mybatis.SourceDao;
import cn.crap.enumer.TableId;
import cn.crap.framework.IdGenerator;
import cn.crap.model.mybatis.Source;
import cn.crap.model.mybatis.SourceCriteria;
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
public class SourceService {
    @Autowired
    private SourceDao mapper;

    public List<Source> selectByExample(SourceCriteria example) {
        return mapper.selectByExample(example);
    }

    public int countByExample(SourceCriteria example) {
        return mapper.countByExample(example);
    }

    public Source getById(String id) {
        if (id == null){
            return null;
        }
        return mapper.selectByPrimaryKey(id);
    }

    public boolean insert(Source model) {
        if (model == null) {
            return false;
        }
        model.setId(IdGenerator.getId(TableId.SOURCE));
        if (model.getSequence() == null){
            SourceCriteria example = new SourceCriteria();
            example.setOrderByClause(TableField.SORT.SEQUENCE_DESC);
            example.setMaxResults(1);
            List<Source>  models = this.selectByExample(example);
            if (models.size() > 0){
                model.setSequence(models.get(0).getSequence() + 1);
            }else{
                model.setSequence(0);
            }
        }
        model.setCreateTime(new Date());
        return mapper.insertSelective(model) > 0;
    }

    public boolean update(Source model) {
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
