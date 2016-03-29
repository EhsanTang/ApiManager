package cn.crap.dao;

import org.springframework.stereotype.Repository;

import cn.crap.framework.base.GenericDaoImpl;
import cn.crap.inter.ErrorInfoDao;
import cn.crap.model.ErrorInfoModel;

@Repository("errorInfoDao")
public class ErrorInfoDaoImpl extends GenericDaoImpl<ErrorInfoModel, String>
		implements ErrorInfoDao {

}