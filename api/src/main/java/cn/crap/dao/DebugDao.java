package cn.crap.dao;

import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.inter.dao.IDebugDao;
import cn.crap.model.Debug;

@Repository("debugDao")
public class DebugDao extends BaseDao<Debug> implements IDebugDao {
}