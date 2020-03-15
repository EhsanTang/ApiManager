package cn.crap.service;

import cn.crap.dao.mybatis.ProjectUserDao;
import cn.crap.enu.TableId;
import cn.crap.model.ProjectUserPO;
import cn.crap.query.ProjectUserQuery;
import cn.crap.utils.IConst;
import cn.crap.utils.ILogConst;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProjectUserService extends NewBaseService<ProjectUserPO, ProjectUserQuery> implements ILogConst, IConst {
    private ProjectUserDao projectUserDao;

    @Resource
    public void ProjectUserDao(ProjectUserDao projectUserDao) {
        this.projectUserDao = projectUserDao;
        super.setBaseDao(projectUserDao, TableId.PROJECT_USER);
    }

    public boolean batchUpdateByProjectId(String projectId, Long sequence, String projectName){
        if (projectId == null || sequence == null){
            return false;
        }
        ProjectUserPO userPO = new ProjectUserPO();
        userPO.setSequence(sequence);
        userPO.setProjectId(projectId);
        userPO.setProjectName(projectName);
        return projectUserDao.batchUpdateByProjectId(userPO) > 0;
    }
}
