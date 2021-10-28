package cn.crap.service;

import cn.crap.dao.mybatis.ProjectUserDao;
import cn.crap.enu.TableId;
import cn.crap.framework.MyException;
import cn.crap.model.ProjectUserPO;
import cn.crap.query.ProjectUserQuery;
import cn.crap.utils.IConst;
import cn.crap.utils.ILogConst;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProjectUserService extends NewBaseService<ProjectUserPO, ProjectUserQuery> implements ILogConst, IConst {
    private ProjectUserDao projectUserDao;

    @Resource
    public void ProjectUserDao(ProjectUserDao projectUserDao) {
        this.projectUserDao = projectUserDao;
        super.setBaseDao(projectUserDao, TableId.PROJECT_USER);
    }

    public ProjectUserPO getByProjectUniKey(String userId, String projectUniKey) throws MyException {
        List<ProjectUserPO> projectPOS = this.select(new ProjectUserQuery().setUserId(userId).setProjectUniKey(projectUniKey));
        if (CollectionUtils.isEmpty(projectPOS)){
            return null;
        }
        return projectPOS.get(0);
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

    public boolean deleteByProjectId(String projectId){
        if (projectId == null){
            return false;
        }
        return projectUserDao.deleteByProjectId(projectId) > 0;
    }

}
