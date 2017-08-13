package cn.crap.dao.imp;

import cn.crap.dao.IDebugDao;
import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.model.Debug;

@Repository("debugDao")
public class DebugDao extends BaseDao<Debug> implements IDebugDao {
}