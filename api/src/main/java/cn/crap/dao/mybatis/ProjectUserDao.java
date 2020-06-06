package cn.crap.dao.mybatis;

import cn.crap.model.ProjectUserPO;
import cn.crap.query.ProjectUserQuery;
import org.apache.ibatis.annotations.Param;

public interface ProjectUserDao extends NewBaseDao<ProjectUserPO, ProjectUserQuery> {

    /**
     * 更新项目用户排序：和项目排序一致，查询时直接使用
     * @param po
     * @return
     */
    int batchUpdateByProjectId(ProjectUserPO po);

    /**
     * 根据项目ID删除用户
     * @param projectId
     * @return
     */
    int deleteByProjectId(@Param("projectId") String projectId);

}