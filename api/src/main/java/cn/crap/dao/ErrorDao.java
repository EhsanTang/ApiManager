package cn.crap.dao;

import org.springframework.stereotype.Repository;

import cn.crap.framework.base.GenericDaoImpl;
import cn.crap.inter.IErrorDao;
import cn.crap.model.Error;

@Repository("errorDao")
public class ErrorDao extends GenericDaoImpl<Error, String>
		implements IErrorDao {

}