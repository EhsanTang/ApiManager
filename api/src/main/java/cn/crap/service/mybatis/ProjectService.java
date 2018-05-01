package cn.crap.service.mybatis;

import cn.crap.dao.mybatis.ProjectDao;
import cn.crap.enumer.TableId;
import cn.crap.framework.IdGenerator;
import cn.crap.model.mybatis.Project;
import cn.crap.model.mybatis.ProjectCriteria;
import cn.crap.utils.MD5;
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
public class ProjectService {
    @Autowired
    private ProjectDao mapper;

    public List<Project> selectByExample(ProjectCriteria example) {
        return mapper.selectByExample(example);
    }

    public int countByExample(ProjectCriteria example) {
        return mapper.countByExample(example);
    }

    public Project getById(String id) {
        if (id == null){
            return null;
        }
        return mapper.selectByPrimaryKey(id);
    }

    public boolean insert(Project project) {
        if (project == null) {
            return false;
        }
        project.setId(IdGenerator.getId(TableId.PROJECT));

        if (MyString.isNotEmpty(project.getPassword())){
            project.setPassword(MD5.encrytMD5(project.getPassword(), project.getId()));
        }

        if (project.getSequence() == null){
            ProjectCriteria example = new ProjectCriteria();
            example.setOrderByClause(TableField.SORT.SEQUENCE_DESC);
            example.setMaxResults(1);
            List<Project>  models = this.selectByExample(example);
            if (models.size() > 0){
                project.setSequence(models.get(0).getSequence() + 1);
            }else{
                project.setSequence(0);
            }
        }
        project.setCreateTime(new Date());
        return mapper.insertSelective(project) > 0;
    }

    public boolean update(Project model) {
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
