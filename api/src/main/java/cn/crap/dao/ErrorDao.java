package cn.crap.dao;

import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.inter.dao.IErrorDao;
import cn.crap.model.Error;

@Repository("errorDao")
public class ErrorDao extends BaseDao<Error> implements IErrorDao {

}