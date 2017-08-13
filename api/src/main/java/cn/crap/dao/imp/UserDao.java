package cn.crap.dao.imp;

import cn.crap.dao.IUserDao;
import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.model.User;

@Repository("userDao")
public class UserDao extends BaseDao<User>
		implements IUserDao {

}