package cn.crap.service;

import cn.crap.dao.mybatis.ProjectMetaDao;
import cn.crap.enu.TableId;
import cn.crap.model.ProjectMetaPO;
import cn.crap.query.ProjectMetaQuery;
import cn.crap.utils.IConst;
import cn.crap.utils.ILogConst;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProjectMetaService extends NewBaseService<ProjectMetaPO, ProjectMetaQuery> implements ILogConst, IConst {
    private ProjectMetaDao projectMetaDao;

    @Resource
    public void ProjectMetaDao(ProjectMetaDao projectMetaDao) {
        this.projectMetaDao = projectMetaDao;
        super.setBaseDao(projectMetaDao, TableId.PROJECT_META);
    }
}
