package cn.crap.service.mybatis;

import cn.crap.dao.mybatis.InterfaceDao;
import cn.crap.framework.IdGenerator;
import cn.crap.enumer.TableId;
import cn.crap.model.mybatis.Interface;
import cn.crap.model.mybatis.InterfaceCriteria;
import cn.crap.model.mybatis.InterfaceWithBLOBs;
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
public class InterfaceService {
    @Autowired
    private InterfaceDao mapper;

    public List<Interface> selectByExample(InterfaceCriteria example) {
        return mapper.selectByExample(example);
    }

    public List<InterfaceWithBLOBs> selectByExampleWithBLOBs(InterfaceCriteria example) {
        return mapper.selectByExampleWithBLOBs(example);
    }

    public int countByExample(InterfaceCriteria example) {
        return mapper.countByExample(example);
    }

    public InterfaceWithBLOBs getById(String id) {
        if (id == null){
            return null;
        }
        return mapper.selectByPrimaryKey(id);
    }

    public boolean insert(InterfaceWithBLOBs model) {
        if (model == null) {
            return false;
        }
        model.setId(IdGenerator.getId(TableId.INTERFACE));
        if (model.getSequence() == null){
            InterfaceCriteria example = new InterfaceCriteria();
            example.setOrderByClause(TableField.SORT.SEQUENCE_DESC);
            example.setMaxResults(1);
            List<Interface>  models = this.selectByExample(example);
            if (models.size() > 0){
                model.setSequence(models.get(0).getSequence() + 1);
            }else{
                model.setSequence(0);
            }
        }
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        return mapper.insertSelective(model) > 0;
    }

    public boolean update(InterfaceWithBLOBs model) {
        if (model == null) {
            return false;
        }
        model.setUpdateTime(new Date());
        model.setCreateTime(null);
        return mapper.updateByPrimaryKeySelective(model) > 0 ? true : false;
    }

    public boolean delete(String id) {
        Assert.notNull(id, "id can't be null");
        return mapper.deleteByPrimaryKey(id) > 0 ? true : false;
    }

}
