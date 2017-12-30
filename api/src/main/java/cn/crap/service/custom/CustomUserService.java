package cn.crap.service.custom;

import cn.crap.dao.mybatis.UserDao;
import cn.crap.dto.LoginDto;
import cn.crap.dto.LoginInfoDto;
import cn.crap.model.mybatis.User;
import cn.crap.model.mybatis.UserCriteria;
import cn.crap.service.tool.UserCache;
import cn.crap.service.mybatis.ProjectUserService;
import cn.crap.service.mybatis.RoleService;
import cn.crap.utils.Aes;
import cn.crap.utils.IConst;
import cn.crap.utils.MyCookie;
import cn.crap.utils.MyString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO 重新生成所有的ID，保证ID有序
@Service
public class CustomUserService {
    @Autowired
    private UserCache userCache;
    @Autowired
    private CustomProjectService customProjectService;
    @Autowired
    private ProjectUserService projectUserService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserDao dao;

    public void login(LoginDto model, User user) {
        String token  = Aes.encrypt(user.getId());
        MyCookie.addCookie(IConst.COOKIE_TOKEN, token);
        // 将用户信息存入缓存
        userCache.add(user.getId(), new LoginInfoDto(user, roleService, customProjectService, projectUserService));
        MyCookie.addCookie(IConst.C_COOKIE_USERID, user.getId());
        MyCookie.addCookie(IConst.COOKIE_USERNAME, model.getUserName());
        MyCookie.addCookie(IConst.COOKIE_REMBER_PWD, model.getRemberPwd());

        // 如果选择了记住密码，或者remberPwd==null，则记住密码
        if (model.getRemberPwd() == null || model.getRemberPwd().equalsIgnoreCase("yes")) {
            MyCookie.addCookie(IConst.COOKIE_PASSWORD, model.getPassword(), true);
        } else {
            MyCookie.deleteCookie(IConst.COOKIE_PASSWORD);
        }
        model.setSessionAdminName(model.getUserName());
    }

    /**
     * 根据用户名称查询数量
     * @param name
     * @param expectUserId 可以为空
     * @return
     */
    public int countByNameExceptUserId(String name, String expectUserId){
        UserCriteria userCriteria = new UserCriteria();
        UserCriteria.Criteria criteria = userCriteria.createCriteria().andUserNameEqualTo(name);
        if (MyString.isNotEmpty(expectUserId)){
            criteria.andIdNotEqualTo(expectUserId);
        }
        return dao.countByExample(userCriteria);
    }
}
