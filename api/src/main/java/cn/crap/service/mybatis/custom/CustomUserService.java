package cn.crap.service.mybatis.custom;

import cn.crap.dto.LoginDto;
import cn.crap.dto.LoginInfoDto;
import cn.crap.model.mybatis.User;
import cn.crap.service.imp.tool.UserCache;
import cn.crap.service.mybatis.imp.MybatisProjectService;
import cn.crap.service.mybatis.imp.MybatisProjectUserService;
import cn.crap.service.mybatis.imp.MybatisRoleService;
import cn.crap.springbeans.Config;
import cn.crap.utils.Aes;
import cn.crap.utils.Const;
import cn.crap.utils.MyCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO 重新生成所有的ID，保证ID有序
@Service
public class CustomUserService {
    @Autowired
    private UserCache userCache;
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

    public void login(LoginDto model, User user) {
        String token  = Aes.encrypt(user.getId());
        MyCookie.addCookie(Const.COOKIE_TOKEN, token);
        // 将用户信息存入缓存
        userCache.add(user.getId(), new LoginInfoDto(user, roleService, customProjectService, projectUserService));
        MyCookie.addCookie(Const.COOKIE_USERID, user.getId());
        MyCookie.addCookie(Const.COOKIE_USERNAME, model.getUserName());
        MyCookie.addCookie(Const.COOKIE_REMBER_PWD, model.getRemberPwd());

        // 如果选择了记住密码，或者remberPwd==null，则记住密码
        if (model.getRemberPwd() == null || model.getRemberPwd().equalsIgnoreCase("yes")) {
            MyCookie.addCookie(Const.COOKIE_PASSWORD, model.getPassword(), true);
        } else {
            MyCookie.deleteCookie(Const.COOKIE_PASSWORD);
        }
        model.setSessionAdminName(model.getUserName());
    }

}
