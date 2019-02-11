package cn.crap.dao.mybatis;

import cn.crap.query.BaseQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DAO基类，用于替换BaseDao
 */

public interface NewBaseDao<PO, Query extends BaseQuery>{
    int insert(PO po);

    int delete(@Param("id")String id);

    int update(PO po);

    PO get(@Param("id")String id);

    int count(@Param("query") Query query);

    List<PO> select(@Param("query") Query query);


}