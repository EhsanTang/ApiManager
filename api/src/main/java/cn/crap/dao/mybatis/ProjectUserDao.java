package cn.crap.dao.mybatis;

import cn.crap.model.mybatis.ProjectUser;
import cn.crap.model.mybatis.ProjectUserCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProjectUserDao {
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