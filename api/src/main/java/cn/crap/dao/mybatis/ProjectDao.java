package cn.crap.dao.mybatis;

import cn.crap.model.mybatis.Project;
import cn.crap.model.mybatis.ProjectCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectDao {
    int countByExample(ProjectCriteria example);

    int deleteByExample(ProjectCriteria example);

    int deleteByPrimaryKey(String id);

    int insert(Project record);

    int insertSelective(Project record);

    List<Project> selectByExample(ProjectCriteria example);

    Project selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Project record, @Param("example") ProjectCriteria example);

    int updateByExample(@Param("record") Project record, @Param("example") ProjectCriteria example);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);
}