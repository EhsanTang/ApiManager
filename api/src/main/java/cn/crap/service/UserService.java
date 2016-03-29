package cn.crap.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.crap.framework.base.GenericDao;
import cn.crap.framework.base.GenericServiceImpl;
import cn.crap.inter.IUserService;
import cn.crap.model.User;

@Service
public class UserService extends GenericServiceImpl<User, String>
		implements IUserService {

	@Resource(name="userDao")
	public void setDao(GenericDao<User, String> dao) {
		super.setDao(dao);
	}
}
