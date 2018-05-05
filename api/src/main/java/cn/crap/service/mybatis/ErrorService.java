package cn.crap.service.mybatis;

import cn.crap.dao.mybatis.ErrorDao;
import cn.crap.enumer.TableId;
import cn.crap.framework.IdGenerator;
import cn.crap.model.mybatis.Error;
import cn.crap.model.mybatis.ErrorCriteria;
import cn.crap.utils.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@Service
public class ErrorService {
    @Autowired
    private ErrorDao mapper;

    public List<Error> selectByExample(ErrorCriteria example) {
        return mapper.selectByExample(example);
    }

    public int countByExample(ErrorCriteria example) {
        return mapper.countByExample(example);
    }

    public Error getById(String id) {
        if (id == null){
            return null;
        }
        return mapper.selectByPrimaryKey(id);
    }

    public boolean insert(Error model) {
        if (model == null) {
            return false;
        }
        model.setId(IdGenerator.getId(TableId.ERROR));
        if (model.getSequence() == null){
            ErrorCriteria example = new ErrorCriteria();
            example.setOrderByClause(TableField.SORT.SEQUENCE_DESC);
            example.setMaxResults(1);
            List<Error>  models = this.selectByExample(example);
            if (models.size() > 0){
                model.setSequence(models.get(0).getSequence() + 1);
            }else{
                model.setSequence(0);
            }
        }
        model.setCreateTime(new Date());
        return mapper.insertSelective(model) > 0;
    }

    public boolean update(Error model) {
        if (model == null) {
            return false;
        }
        return mapper.updateByPrimaryKeySelective(model) > 0 ? true : false;
    }

    public boolean delete(String id) {
        Assert.notNull(id, "id ????");
        return mapper.deleteByPrimaryKey(id) > 0 ? true : false;
    }

}
