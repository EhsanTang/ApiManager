package cn.crap.dao;

import org.springframework.stereotype.Repository;

import cn.crap.framework.base.GenericDaoImpl;
import cn.crap.inter.IUserDao;
import cn.crap.model.User;

@Repository("userDao")
public class UserDao extends GenericDaoImpl<User, String>
		implements IUserDao {

}