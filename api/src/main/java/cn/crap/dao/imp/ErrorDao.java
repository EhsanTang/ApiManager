package cn.crap.dao.imp;

import cn.crap.dao.IErrorDao;
import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.model.Error;

@Repository("errorDao")
public class ErrorDao extends BaseDao<Error> implements IErrorDao {

}