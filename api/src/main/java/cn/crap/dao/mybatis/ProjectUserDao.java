package cn.crap.dao.mybatis;

import cn.crap.model.ProjectUser;
import cn.crap.model.ProjectUserCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectUserDao extends BaseDao<ProjectUser>{
    int countByExample(ProjectUserCriteria example);

    int deleteByExample(ProjectUserCriteria example);

    int deleteByPrimaryKey(String id);

    int insert(ProjectUser record);

    int insertSelective(ProjectUser record);

    List<ProjectUser> selectByExample(ProjectUserCriteria example);

    ProjectUser selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ProjectUser record, @Param("example") ProjectUserCriteria example);

    int updateByExample(@Param("record") ProjectUser record, @Param("example") ProjectUserCriteria example);

    int updateByPrimaryKeySelective(ProjectUser record);

    int updateByPrimaryKey(ProjectUser record);
}