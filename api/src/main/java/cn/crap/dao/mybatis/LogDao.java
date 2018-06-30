package cn.crap.dao.mybatis;

import cn.crap.model.Log;
import cn.crap.model.LogCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogDao extends BaseDao<Log>{
    int countByExample(LogCriteria example);

    int deleteByExample(LogCriteria example);

    int deleteByPrimaryKey(String id);

    int insert(Log record);

    int insertSelective(Log record);

    List<Log> selectByExampleWithBLOBs(LogCriteria example);

    List<Log> selectByExample(LogCriteria example);

    Log selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Log record, @Param("example") LogCriteria example);

    int updateByExampleWithBLOBs(@Param("record") Log record, @Param("example") LogCriteria example);

    int updateByExample(@Param("record") Log record, @Param("example") LogCriteria example);

    int updateByPrimaryKeySelective(Log record);

    int updateByPrimaryKeyWithBLOBs(Log record);

    int updateByPrimaryKey(Log record);
}