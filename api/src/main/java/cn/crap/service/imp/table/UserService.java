package cn.crap.service.imp.table;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.crap.dto.LoginDto;
import cn.crap.dto.LoginInfoDto;
import cn.crap.framework.base.BaseService;
import cn.crap.framework.base.IBaseDao;
import cn.crap.dao.IUserDao;
import cn.crap.service.IProjectService;
import cn.crap.service.IProjectUserService;
import cn.crap.service.IRoleService;
import cn.crap.service.IUserService;
import cn.crap.service.ICacheService;
import cn.crap.model.User;
import cn.crap.springbeans.Config;
import cn.crap.utils.Aes;
import cn.crap.utils.Const;
import cn.crap.utils.MyCookie;

@Service
public class UserService extends BaseService<User>
		implements IUserService {
	@Autowired
	private ICacheService cacheService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private Config config;
	@Autowired
	private IProjectService projectService;
	@Autowired
	private IProjectUserService projectUserService;
	
	@Resource(name="userDao")
	IUserDao userDao;
	
	@Resource(name="userDao")
	public void setDao(IBaseDao<User> dao) {
		super.setDao(dao);
	}

	@Override
	@Transactional
	public User get(String id){
		User model = userDao.get(id);
		if(model == null)
			 return new User();
		return model;
	}
	
	@Override
	public void login(LoginDto model, User user, HttpServletRequest request, HttpServletResponse response) {
		String token  = Aes.encrypt(user.getId());
		MyCookie.addCookie(Const.COOKIE_TOKEN, token, response);
		// 将用户信息存入缓存
		cacheService.setObj(Const.CACHE_USER + user.getId(), new LoginInfoDto(user, roleService, projectService, projectUserService), config.getLoginInforTime());
		MyCookie.addCookie(Const.COOKIE_USERID, user.getId(), response);
		MyCookie.addCookie(Const.COOKIE_USERNAME, model.getUserName(), response);
		MyCookie.addCookie(Const.COOKIE_REMBER_PWD, model.getRemberPwd() , response);
		
		// 如果选择了记住密码，或者remberPwd==null，则记住密码
		if (model.getRemberPwd() == null || model.getRemberPwd().equalsIgnoreCase("yes")) {
			MyCookie.addCookie(Const.COOKIE_PASSWORD, model.getPassword(), true, response);
		} else {
			MyCookie.deleteCookie(Const.COOKIE_PASSWORD, request, response);
		}
		model.setSessionAdminName(model.getUserName());
	}
}
