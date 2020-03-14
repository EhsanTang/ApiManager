package cn.crap.dao.mybatis;

import cn.crap.model.UserPO;
import cn.crap.model.UserCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao extends BaseDao<UserPO>{
    int countByExample(UserCriteria example);

    int deleteByExample(UserCriteria example);

    int deleteByPrimaryKey(String id);

    int insert(UserPO record);

    int insertSelective(UserPO record);

    List<UserPO> selectByExample(UserCriteria example);

    UserPO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UserPO record, @Param("example") UserCriteria example);

    int updateByExample(@Param("record") UserPO record, @Param("example") UserCriteria example);

    int updateByPrimaryKeySelective(UserPO record);

    int updateByPrimaryKey(UserPO record);
}