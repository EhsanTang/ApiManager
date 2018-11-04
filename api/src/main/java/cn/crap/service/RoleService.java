package cn.crap.service;

import cn.crap.dao.mybatis.RoleDao;
import cn.crap.enu.TableId;
import cn.crap.framework.MyException;
import cn.crap.model.Role;
import cn.crap.model.RoleCriteria;
import cn.crap.model.RoleWithBLOBs;
import cn.crap.utils.TableField;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Automatic generation by tools
 * service
 */
@Service
public class RoleService extends BaseService<RoleWithBLOBs, RoleDao> {
    private RoleDao roleDao;

    @Resource
    public void RoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
        super.setBaseDao(roleDao, TableId.ROLE);
    }

    public List<Role> selectByExample(RoleCriteria example) {
        return roleDao.selectByExample(example);
    }

    public List<RoleWithBLOBs> selectByExampleWithBLOBs(RoleCriteria example) {
        return roleDao.selectByExampleWithBLOBs(example);
    }


    public int countByExample(RoleCriteria example) {
        return roleDao.countByExample(example);
    }

    @Override
    public boolean insert(RoleWithBLOBs model) throws MyException{
        if (model == null) {
            return false;
        }
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
        return super.insert(model);
    }

}
