package cn.crap.dao;

import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.inter.dao.IUserDao;
import cn.crap.model.User;

@Repository("userDao")
public class UserDao extends BaseDao<User>
		implements IUserDao {

}