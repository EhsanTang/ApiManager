package cn.crap.dao.imp;

import cn.crap.dao.ILogDao;
import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.model.Log;

@Repository("logDao")
public class LogDao extends BaseDao<Log>
		implements ILogDao {

}