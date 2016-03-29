package cn.crap.dao;

import org.springframework.stereotype.Repository;

import cn.crap.framework.base.GenericDaoImpl;
import cn.crap.inter.RoleInfoDao;
import cn.crap.model.RoleInfoModel;

@Repository("roleInfoDao")
public class RoleInfoDaoImpl extends GenericDaoImpl<RoleInfoModel, String>
		implements RoleInfoDao {

}