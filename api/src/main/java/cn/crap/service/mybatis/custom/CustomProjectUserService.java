package cn.crap.service.mybatis.custom;

import cn.crap.dao.mybatis.ProjectMapper;
import cn.crap.dao.mybatis.ProjectUserMapper;
import cn.crap.dao.mybatis.custom.CustomProjectMapper;
import cn.crap.model.mybatis.*;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class CustomProjectUserService {
    @Autowired
    private ProjectUserMapper mapper;

    public int countByProjectId(String projectId){
        Assert.notNull(projectId);
        ProjectUserCriteria example = new ProjectUserCriteria();
        example.createCriteria().andProjectIdEqualTo(projectId);

        return mapper.countByExample(example);
    }

    public Page<ProjectUser> queryByProjectId(String projectId, Page<ProjectUser> page){
        Assert.notNull(projectId, "projectId can't be null");
        Assert.notNull(page, "page can't be null");
        ProjectUserCriteria example = new ProjectUserCriteria();
        example.createCriteria().andProjectIdEqualTo(projectId);

        example.setLimitStart(page.getStart());
        example.setMaxResults(page.getSize());
        example.setOrderByClause(TableField.SORT.SEQUENCE_DESC);

        page.setAllRow(mapper.countByExample(example));
        page.setList(mapper.selectByExample(example));
        return page;
    }

}
