package cn.crap.dao.mybatis;

import cn.crap.model.Error;
import cn.crap.model.ErrorCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ErrorDao extends BaseDao<Error>{
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