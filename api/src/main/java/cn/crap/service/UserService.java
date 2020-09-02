package cn.crap.service;

import cn.crap.dao.mybatis.UserDao;
import cn.crap.dto.LoginDto;
import cn.crap.dto.LoginInfoDto;
import cn.crap.enu.LoginType;
import cn.crap.enu.TableId;
import cn.crap.enu.UserStatus;
import cn.crap.framework.MyException;
import cn.crap.model.UserPO;
import cn.crap.query.UserQuery;
import cn.crap.service.tool.UserCache;
import cn.crap.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class UserService extends NewBaseService<UserPO, UserQuery> implements ILogConst, IConst {
    @Autowired
    private UserCache userCache;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectUserService projectUserService;

    private UserDao userDao;

    @Resource
    public void UserDao(UserDao userDao) {
        this.userDao = userDao;
        super.setBaseDao(userDao, TableId.USER);
    }

    @Override
    public boolean insert(UserPO user) throws MyException{
        if (user == null) {
            return false;
        }
        if (user.getAvatarUrl() == null){
            user.setAvatarUrl(Tools.getAvatar());
        }
        if (user.getAuthName() == null){
            user.setAuthName("");
        }
        if (user.getAuth() == null){
            user.setAuth("");
        }
        if (user.getRoleId() == null){
            user.setRoleId("");
        }
        if (user.getRoleName() == null){
            user.setRoleName("");
        }
        if (user.getTrueName() == null){
            user.setTrueName("");
        }
        if (user.getStatus() == null){
            user.setStatus(UserStatus.INVALID.getType());
        }
        if (user.getLoginType() == null){
            user.setLoginType(LoginType.COMMON.getValue());
        }

        user.setLoginTime(new Date());
        return super.insert(user);
    }


    public void login(LoginDto loginDto, UserPO user) throws MyException{
        String token  = Aes.encrypt(user.getId());
        MyCookie.addCookie(IConst.COOKIE_TOKEN, token);
        // 将用户信息存入缓存
        userCache.add(user.getId(), new LoginInfoDto(user));
        MyCookie.addCookie(IConst.C_COOKIE_USERID, user.getId());
        MyCookie.addCookie(IConst.COOKIE_USERNAME, loginDto.getUserName());
        MyCookie.addCookie(IConst.COOKIE_REMBER_PWD, loginDto.getRemberPwd());

        // 如果选择了记住密码，或者remberPwd==null，则记住密码
        if (loginDto.getRemberPwd() == null || loginDto.getRemberPwd().equalsIgnoreCase("yes")) {
            MyCookie.addCookie(IConst.COOKIE_PASSWORD, loginDto.getPassword(), true);
        } else {
            MyCookie.deleteCookie(IConst.COOKIE_PASSWORD);
        }
        loginDto.setSessionAdminName(loginDto.getUserName());
        loginDto.setAttributes(user.getAttributes());

        UserPO updateLoginTimeUser = new UserPO();
        updateLoginTimeUser.setId(user.getId());
        updateLoginTimeUser.setLoginTime(new Date());
        super.update(updateLoginTimeUser);
    }

    /**
     * 根据用户名称查询数量
     * @param name
     * @param expectUserId 可以为空
     * @return
     */
    public int countByNameExceptUserId(String name, String expectUserId){
        UserQuery query = new UserQuery().setEqualUserName(name).setNotEqualId(expectUserId);
        return userDao.count(query);
    }
}
