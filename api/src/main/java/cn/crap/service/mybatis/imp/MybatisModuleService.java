package cn.crap.service.mybatis.imp;

import cn.crap.dao.mybatis.ModuleMapper;
import cn.crap.enumeration.TableId;
import cn.crap.framework.IdGenerator;
import cn.crap.model.mybatis.Module;
import cn.crap.model.mybatis.ModuleCriteria;
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
public class MybatisModuleService {
    @Autowired
    private ModuleMapper moduleMapper;

    public List<Module> selectByExample(ModuleCriteria example) {
        return moduleMapper.selectByExample(example);
    }

    public int countByExample(ModuleCriteria example) {
        return moduleMapper.countByExample(example);
    }

    public Module selectByPrimaryKey(String id) {
        if (id == null){
            return null;
        }
        return moduleMapper.selectByPrimaryKey(id);
    }

    public boolean insert(Module model) {
        if (model == null) {
            return false;
        }
        model.setId(IdGenerator.getId(TableId.MODULE));
        if (model.getSequence() == null){
            ModuleCriteria example = new ModuleCriteria();
            example.setOrderByClause(TableField.SORT.SEQUENCE_DESC);
            example.setMaxResults(1);
            List<Module>  models = this.selectByExample(example);
            if (models.size() > 0){
                model.setSequence(models.get(0).getSequence() + 1);
            }else{
                model.setSequence(0);
            }
        }
        model.setCreateTime(new Date());
        return moduleMapper.insertSelective(model) > 0;
    }

    public boolean update(Module model) {
        if (model == null) {
            return false;
        }
        return moduleMapper.updateByPrimaryKeySelective(model) > 0 ? true : false;
    }

    public boolean delete(String id) {
        Assert.notNull(id, "id can't be null");
        return moduleMapper.deleteByPrimaryKey(id) > 0 ? true : false;
    }

}
