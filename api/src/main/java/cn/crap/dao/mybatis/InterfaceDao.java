package cn.crap.dao.mybatis;

import cn.crap.model.Interface;
import cn.crap.model.InterfaceCriteria;
import cn.crap.model.InterfaceWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InterfaceDao extends BaseDao<InterfaceWithBLOBs>{
    int countByExample(InterfaceCriteria example);

    int deleteByExample(InterfaceCriteria example);

    int deleteByPrimaryKey(String id);

    int insert(InterfaceWithBLOBs record);

    int insertSelective(InterfaceWithBLOBs record);

    List<InterfaceWithBLOBs> selectByExampleWithBLOBs(InterfaceCriteria example);

    List<Interface> selectByExample(InterfaceCriteria example);

    InterfaceWithBLOBs selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") InterfaceWithBLOBs record, @Param("example") InterfaceCriteria example);

    int updateByExampleWithBLOBs(@Param("record") InterfaceWithBLOBs record, @Param("example") InterfaceCriteria example);

    int updateByExample(@Param("record") Interface record, @Param("example") InterfaceCriteria example);

    int updateByPrimaryKeySelective(InterfaceWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(InterfaceWithBLOBs record);

    int updateByPrimaryKey(Interface record);
}