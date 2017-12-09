package cn.crap.service.mybatis.custom;

import cn.crap.dao.mybatis.ProjectMapper;
import cn.crap.dao.mybatis.ProjectUserMapper;
import cn.crap.dao.mybatis.custom.CustomProjectMapper;
import cn.crap.model.mybatis.Project;
import cn.crap.model.mybatis.ProjectCriteria;
import cn.crap.model.mybatis.ProjectUserCriteria;
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
}
