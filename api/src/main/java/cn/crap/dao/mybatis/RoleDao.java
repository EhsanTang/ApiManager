package cn.crap.dao.mybatis;

import cn.crap.model.Role;
import cn.crap.model.RoleCriteria;
import cn.crap.model.RoleWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleDao extends BaseDao<RoleWithBLOBs>{
    int countByExample(RoleCriteria example);

    int deleteByExample(RoleCriteria example);

    int deleteByPrimaryKey(String id);

    int insert(RoleWithBLOBs record);

    int insertSelective(RoleWithBLOBs record);

    List<RoleWithBLOBs> selectByExampleWithBLOBs(RoleCriteria example);

    List<Role> selectByExample(RoleCriteria example);

    RoleWithBLOBs selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RoleWithBLOBs record, @Param("example") RoleCriteria example);

    int updateByExampleWithBLOBs(@Param("record") RoleWithBLOBs record, @Param("example") RoleCriteria example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleCriteria example);

    int updateByPrimaryKeySelective(RoleWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(RoleWithBLOBs record);

    int updateByPrimaryKey(Role record);
}