package cn.crap.service;

import cn.crap.dao.mybatis.UserDao;
import cn.crap.dto.LoginDto;
import cn.crap.dto.LoginInfoDto;
import cn.crap.enumer.TableId;
import cn.crap.framework.MyException;
import cn.crap.model.User;
import cn.crap.model.UserCriteria;
import cn.crap.query.UserQuery;
import cn.crap.service.tool.UserCache;
import cn.crap.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService extends BaseService<User, UserDao> {
    @Autowired
    private UserCache userCache;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectUserService projectUserService;
    @Autowired
    private RoleService roleService;
    private UserDao userDao;

    @Resource
    public void UserDao(UserDao userDao) {
        this.userDao = userDao;
        super.setBaseDao(userDao, TableId.USER);
    }

    public boolean insert(User user) throws MyException{
        if (user == null) {
            return false;
        }
        if (user.getAvatarUrl() == null){
            user.setAvatarUrl(Tools.getAvatar());
        }
        return super.insert(user);
    }

    /**
     * 查询用户
     * @param query
     * @return
     * @throws MyException
     */
    public List<User> query(UserQuery query) throws MyException {
        Assert.notNull(query);

        Page page = new Page(query);
        UserCriteria example = getUserCriteria(query);
        example.setLimitStart(page.getStart());
        example.setMaxResults(page.getSize());
        example.setOrderByClause(query.getSort() == null ? TableField.SORT.CREATE_TIME_DES : query.getSort());

        return userDao.selectByExample(example);
    }

    /**
     * 查询用户数量
     * @param query
     * @return
     * @throws MyException
     */
    public int count(UserQuery query) throws MyException {
        Assert.notNull(query);

        UserCriteria example = getUserCriteria(query);
        return userDao.countByExample(example);
    }

    private UserCriteria getUserCriteria(UserQuery query) throws MyException {
        UserCriteria example = new UserCriteria();
        UserCriteria.Criteria criteria = example.createCriteria();

        if (query.getStatus() != null) {
            criteria.andStatusEqualTo(query.getStatus());
        }
        if (query.getThirdlyId() != null){
            criteria.andThirdlyIdEqualTo(query.getThirdlyId());
        }
        if (query.getEqualEmail() != null){
            criteria.andEmailEqualTo(query.getEqualEmail());
        }
        if (query.getEqualUserName() != null){
            criteria.andUserNameEqualTo(query.getEqualUserName());
        }
        if (query.getEmail() != null){
            criteria.andEmailLike("%" + query.getEmail() + "%");
        }
        if (query.getUserName() != null){
            criteria.andUserNameLike("%" + query.getUserName() + "%");
        }
        if (query.getTrueName() != null){
            criteria.andTrueNameLike("%" + query.getTrueName() + "%");
        }
        if (query.getLoginType() != null){
            criteria.andLoginTypeEqualTo(query.getLoginType());
        }
        return example;
    }

    public void login(LoginDto model, User user) throws MyException{
        String token  = Aes.encrypt(user.getId());
        MyCookie.addCookie(IConst.COOKIE_TOKEN, token);
        // 将用户信息存入缓存
        userCache.add(user.getId(), new LoginInfoDto(user, roleService, projectService, projectUserService));
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
        return userDao.countByExample(userCriteria);
    }

    public List<User> selectByExample(UserCriteria userExample){
        return userDao.selectByExample(userExample);
    }

    public int countByExample(UserCriteria userExample){
        return userDao.countByExample(userExample);
    }
}
