package cn.crap.dao.mybatis;

import cn.crap.model.mybatis.Error;
import cn.crap.model.mybatis.ErrorCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ErrorMapper {
    int countByExample(ErrorCriteria example);

    int deleteByExample(ErrorCriteria example);

    int deleteByPrimaryKey(String id);

    int insert(Error record);

    int insertSelective(Error record);

    List<Error> selectByExample(ErrorCriteria example);

    Error selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Error record, @Param("example") ErrorCriteria example);

    int updateByExample(@Param("record") Error record, @Param("example") ErrorCriteria example);

    int updateByPrimaryKeySelective(Error record);

    int updateByPrimaryKey(Error record);
}