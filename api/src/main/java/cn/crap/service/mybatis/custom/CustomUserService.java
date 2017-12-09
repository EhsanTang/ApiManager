package cn.crap.service.mybatis.custom;

import cn.crap.dao.mybatis.UserMapper;
import cn.crap.dto.LoginDto;
import cn.crap.dto.LoginInfoDto;
import cn.crap.enumeration.TableId;
import cn.crap.framework.IdGenerator;
import cn.crap.model.mybatis.User;
import cn.crap.model.mybatis.UserCriteria;
import cn.crap.service.ICacheService;
import cn.crap.service.mybatis.custom.CustomProjectService;
import cn.crap.service.mybatis.imp.MybatisProjectService;
import cn.crap.service.mybatis.imp.MybatisProjectUserService;
import cn.crap.service.mybatis.imp.MybatisRoleService;
import cn.crap.springbeans.Config;
import cn.crap.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

// TODO 重新生成所有的ID，保证ID有序
@Service
public class CustomUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ICacheService cacheService;
    @Autowired
    private Config config;
    @Autowired
    private MybatisProjectService projectService;
    @Autowired
    private CustomProjectService customProjectService;
    @Autowired
    private MybatisProjectUserService projectUserService;
    @Autowired
    private MybatisRoleService roleService;

    public void login(LoginDto model, User user, HttpServletRequest request, HttpServletResponse response) {
        String token  = Aes.encrypt(user.getId());
        MyCookie.addCookie(Const.COOKIE_TOKEN, token, response);
        // 将用户信息存入缓存
        cacheService.setObj(Const.CACHE_USER + user.getId(), new LoginInfoDto(user, roleService, customProjectService, projectUserService), config.getLoginInforTime());
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
