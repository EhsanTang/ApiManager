package cn.crap.dao.mybatis;

import cn.crap.model.mybatis.Source;
import cn.crap.model.mybatis.SourceCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SourceDao {
    int countByExample(SourceCriteria example);

    int deleteByExample(SourceCriteria example);

    int deleteByPrimaryKey(String id);

    int insert(Source record);

    int insertSelective(Source record);

    List<Source> selectByExample(SourceCriteria example);

    Source selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Source record, @Param("example") SourceCriteria example);

    int updateByExample(@Param("record") Source record, @Param("example") SourceCriteria example);

    int updateByPrimaryKeySelective(Source record);

    int updateByPrimaryKey(Source record);
}