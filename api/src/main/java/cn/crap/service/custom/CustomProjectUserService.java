package cn.crap.service.custom;

import cn.crap.dao.mybatis.ProjectUserDao;
import cn.crap.model.mybatis.*;
import cn.crap.utils.Page;
import cn.crap.utils.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class CustomProjectUserService {
    @Autowired
    private ProjectUserDao mapper;

    public int countByProjectId(String projectId){
        Assert.notNull(projectId);
        ProjectUserCriteria example = new ProjectUserCriteria();
        example.createCriteria().andProjectIdEqualTo(projectId);

        return mapper.countByExample(example);
    }

    public List<ProjectUser> queryByProjectId(String projectId, Page page){
        Assert.notNull(projectId, "projectId can't be null");
        Assert.notNull(page, "page can't be null");
        ProjectUserCriteria example = new ProjectUserCriteria();
        example.createCriteria().andProjectIdEqualTo(projectId);

        example.setLimitStart(page.getStart());
        example.setMaxResults(page.getSize());
        example.setOrderByClause(TableField.SORT.SEQUENCE_DESC);

        return (mapper.selectByExample(example));
    }

}
