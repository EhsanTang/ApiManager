package cn.crap.inter.service.table;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.crap.dto.LoginDto;
import cn.crap.framework.base.IBaseService;
import cn.crap.model.User;

public interface IUserService extends IBaseService<User>{

	void login(LoginDto model, User user, HttpServletRequest request, HttpServletResponse response);

}
