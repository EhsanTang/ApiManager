package cn.crap.service.imp;

import cn.crap.dao.mybatis.RoleDao;
import cn.crap.framework.IdGenerator;
import cn.crap.enumer.TableId;
import cn.crap.model.mybatis.Role;
import cn.crap.model.mybatis.RoleCriteria;
import cn.crap.model.mybatis.RoleWithBLOBs;
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
public class MybatisRoleService {
    @Autowired
    private RoleDao mapper;

    public List<Role> selectByExample(RoleCriteria example) {
        return mapper.selectByExample(example);
    }

    public List<RoleWithBLOBs> selectByExampleWithBLOBs(RoleCriteria example) {
        return mapper.selectByExampleWithBLOBs(example);
    }


    public int countByExample(RoleCriteria example) {
        return mapper.countByExample(example);
    }

    public Role selectByPrimaryKey(String id) {
        if (id == null){
            return null;
        }
        return mapper.selectByPrimaryKey(id);
    }

    public boolean insert(RoleWithBLOBs model) {
        if (model == null) {
            return false;
        }
        model.setId(IdGenerator.getId(TableId.ROLE));
        if (model.getSequence() == null){
            RoleCriteria example = new RoleCriteria();
            example.setOrderByClause(TableField.SORT.SEQUENCE_DESC);
            example.setMaxResults(1);
            List<Role>  models = this.selectByExample(example);
            if (models.size() > 0){
                model.setSequence(models.get(0).getSequence() + 1);
            }else{
                model.setSequence(0);
            }
        }
        model.setCreateTime(new Date());
        return mapper.insertSelective(model) > 0;
    }

    public boolean update(RoleWithBLOBs model) {
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
