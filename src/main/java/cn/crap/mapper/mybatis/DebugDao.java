package cn.crap.mapper.mybatis;

import cn.crap.model.Debug;
import cn.crap.model.DebugCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DebugDao extends BaseDao<Debug> {
    int countByExample(DebugCriteria example);

    int deleteByExample(DebugCriteria example);

    int deleteByPrimaryKey(String id);

    int insert(Debug record);

    int insertSelective(Debug record);

    List<Debug> selectByExample(DebugCriteria example);

    Debug selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Debug record, @Param("example") DebugCriteria example);

    int updateByExample(@Param("record") Debug record, @Param("example") DebugCriteria example);

    int updateByPrimaryKeySelective(Debug record);

    int updateByPrimaryKey(Debug record);
}