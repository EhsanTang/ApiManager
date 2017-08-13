package cn.crap.dao.imp;

import cn.crap.dao.ISourceDao;
import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.model.Source;

@Repository("sourceDao")
public class SourceDao extends BaseDao<Source>
		implements ISourceDao {

}