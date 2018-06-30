package cn.crap.dao.mybatis;

/**
 * dao基础类
 * @param <PO>
 */
public interface BaseDao<PO>{
    int deleteByPrimaryKey(String id);
    int insertSelective(PO record);
    PO selectByPrimaryKey(String id);
    int updateByPrimaryKeySelective(PO record);
}