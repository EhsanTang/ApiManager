package cn.crap.dao.mybatis;

import cn.crap.model.ProjectUserPO;
import cn.crap.query.ProjectUserQuery;

public interface ProjectUserDao extends NewBaseDao<ProjectUserPO, ProjectUserQuery> {

    /**
     * 更新项目用户排序：和项目排序一致，查询时直接使用
     * @param po
     * @return
     */
    int batchUpdateByProjectId(ProjectUserPO po);

}