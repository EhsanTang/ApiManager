package cn.crap.service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crap.beans.Config;
import cn.crap.dto.LoginDto;
import cn.crap.dto.LoginInfoDto;
import cn.crap.framework.base.BaseService;
import cn.crap.framework.base.IBaseDao;
import cn.crap.inter.service.ICacheService;
import cn.crap.inter.service.IDataCenterService;
import cn.crap.inter.service.IRoleService;
import cn.crap.inter.service.IUserService;
import cn.crap.model.User;
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
	private IDataCenterService dataCenterService;
	@Autowired
	private Config config;
	
	@Resource(name="userDao")
	public void setDao(IBaseDao<User> dao) {
		super.setDao(dao, new User());
	}
	
	@Override
	public void login(LoginDto model, User user, HttpServletRequest request, HttpServletResponse response) {
		String token  = Aes.encrypt(user.getId());
		MyCookie.addCookie(Const.COOKIE_TOKEN, token, response);
		// 将用户信息存入缓存
		cacheService.setObj(Const.CACHE_USER + user.getId(), new LoginInfoDto(user, roleService, dataCenterService), config.getLoginInforTime());
		MyCookie.addCookie(Const.COOKIE_USERID, user.getId(), response);
		MyCookie.addCookie(Const.COOKIE_USERNAME, model.getUserName(), response);
		MyCookie.addCookie(Const.COOKIE_REMBER_PWD, model.getRemberPwd() , response);
		
		if (model.getRemberPwd().equals("YES")) {
			MyCookie.addCookie(Const.COOKIE_PASSWORD, model.getPassword(), true, response);
		} else {
			MyCookie.deleteCookie(Const.COOKIE_PASSWORD, request, response);
		}
		model.setSessionAdminName(model.getUserName());
	}
}
