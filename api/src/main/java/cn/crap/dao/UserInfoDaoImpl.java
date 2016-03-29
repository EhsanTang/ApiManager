package cn.crap.dao;

import org.springframework.stereotype.Repository;

import cn.crap.framework.base.GenericDaoImpl;
import cn.crap.inter.UserInfoDao;
import cn.crap.model.UserInfoModel;

@Repository("userInfoDao")
public class UserInfoDaoImpl extends GenericDaoImpl<UserInfoModel, String>
		implements UserInfoDao {

}