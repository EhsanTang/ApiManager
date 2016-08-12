package cn.crap.dao;

import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.inter.dao.ILogDao;
import cn.crap.model.Log;

@Repository("logDao")
public class LogDao extends BaseDao<Log>
		implements ILogDao {

}