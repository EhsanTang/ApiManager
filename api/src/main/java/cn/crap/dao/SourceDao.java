package cn.crap.dao;

import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.inter.dao.ISourceDao;
import cn.crap.model.Source;

@Repository("sourceDao")
public class SourceDao extends BaseDao<Source>
		implements ISourceDao {

}