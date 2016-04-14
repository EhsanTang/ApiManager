package cn.crap.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.crap.framework.base.IBaseDao;
import cn.crap.framework.base.BaseService;
import cn.crap.inter.service.IUserService;
import cn.crap.model.User;

@Service
public class UserService extends BaseService<User>
		implements IUserService {

	@Resource(name="userDao")
	public void setDao(IBaseDao<User> dao) {
		super.setDao(dao, new User());
	}
}
