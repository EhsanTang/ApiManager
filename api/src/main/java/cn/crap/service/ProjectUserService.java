package cn.crap.service;

import cn.crap.dao.mybatis.ProjectUserDao;
import cn.crap.enumer.TableId;
import cn.crap.framework.MyException;
import cn.crap.model.ProjectUser;
import cn.crap.model.ProjectUserCriteria;
import cn.crap.query.ProjectUserQuery;
import cn.crap.utils.IConst;
import cn.crap.utils.ILogConst;
import cn.crap.utils.Page;
import cn.crap.utils.TableField;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProjectUserService extends BaseService<ProjectUser, ProjectUserDao> implements ILogConst, IConst {
    private ProjectUserDao projectUserDao;
    @Resource
    public void ProjectUserDao(ProjectUserDao projectUserDao) {
        this.projectUserDao = projectUserDao;
        super.setBaseDao(projectUserDao, TableId.PROJECT_USER);
    }

    @Override
    public boolean insert(ProjectUser model) throws MyException{
        if (model == null) {
            return false;
        }
        if (model.getSequence() == null){
            List<ProjectUser>  models = this.query(new ProjectUserQuery().setProjectId(model.getProjectId()).setPageSize(1));
            if (models.size() > 0){
                model.setSequence(models.get(0).getSequence() + 1);
            }else{
                model.setSequence(0);
            }
        }
        return super.insert(model);
    }

    public int count(ProjectUserQuery query) throws MyException{
        Assert.notNull(query.getProjectId(), "projectId can't be null");

        ProjectUserCriteria example = getProjectUserCriteria(query);
        return projectUserDao.countByExample(example);
    }

    public List<ProjectUser> query(ProjectUserQuery query) throws MyException{
        Page page = new Page(query);

        ProjectUserCriteria example = getProjectUserCriteria(query);
        example.setLimitStart(page.getStart());
        example.setMaxResults(page.getSize());
        example.setOrderByClause(query.getSort() == null ? TableField.SORT.SEQUENCE_DESC : query.getSort());

        return (projectUserDao.selectByExample(example));
    }

    private ProjectUserCriteria getProjectUserCriteria(ProjectUserQuery query) throws MyException {
        ProjectUserCriteria example = new ProjectUserCriteria();
        ProjectUserCriteria.Criteria criteria = example.createCriteria();
        if (query.getStatus() != null) {
            criteria.andStatusEqualTo(query.getStatus());
        }
        if (query.getUserId() != null) {
            criteria.andUserIdEqualTo(query.getUserId());
        }
        if (query.getProjectId() != null){
            criteria.andProjectIdEqualTo(query.getProjectId());
        }
        return example;
    }

}
