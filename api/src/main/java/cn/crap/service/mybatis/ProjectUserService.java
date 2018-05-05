package cn.crap.service.mybatis;

import cn.crap.dao.mybatis.ProjectUserDao;
import cn.crap.framework.IdGenerator;
import cn.crap.enumer.TableId;
import cn.crap.model.mybatis.ProjectUser;
import cn.crap.model.mybatis.ProjectUserCriteria;
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
public class ProjectUserService {
    @Autowired
    private ProjectUserDao mapper;

    public List<ProjectUser> selectByExample(ProjectUserCriteria example) {
        return mapper.selectByExample(example);
    }

    public int countByExample(ProjectUserCriteria example) {
        return mapper.countByExample(example);
    }

    public ProjectUser getById(String id) {
        if (id == null){
            return null;
        }
        return mapper.selectByPrimaryKey(id);
    }

    public boolean insert(ProjectUser model) {
        if (model == null) {
            return false;
        }
        model.setId(IdGenerator.getId(TableId.PROJECT_USER));
        if (model.getSequence() == null){
            ProjectUserCriteria example = new ProjectUserCriteria();
            example.setOrderByClause(TableField.SORT.SEQUENCE_DESC);
            example.setMaxResults(1);
            List<ProjectUser>  models = this.selectByExample(example);
            if (models.size() > 0){
                model.setSequence(models.get(0).getSequence() + 1);
            }else{
                model.setSequence(0);
            }
        }
        model.setCreateTime(new Date());
        return mapper.insertSelective(model) > 0;
    }

    public boolean update(ProjectUser model) {
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
