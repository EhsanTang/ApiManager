package cn.crap.controller.admin;

import cn.crap.adapter.UserAdapter;
import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.UserDto;
import cn.crap.enumer.LoginType;
import cn.crap.enumer.MyError;
import cn.crap.enumer.UserStatus;
import cn.crap.enumer.UserType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.mybatis.User;
import cn.crap.model.mybatis.UserCriteria;
import cn.crap.service.custom.CustomProjectService;
import cn.crap.service.custom.CustomUserService;
import cn.crap.service.mybatis.ProjectUserService;
import cn.crap.service.mybatis.RoleService;
import cn.crap.service.mybatis.UserService;
import cn.crap.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController extends BaseController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomProjectService customProjectService;
    @Autowired
    private ProjectUserService projectUserService;
    @Autowired
    private CustomUserService customUserService;

    @RequestMapping("/user/list.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_USER)
    public JsonResult list(String userName, String email, String trueName, Integer currentPage) {
        Page page = new Page(currentPage);
        UserCriteria userCriteria = new UserCriteria();
        UserCriteria.Criteria criteria = userCriteria.createCriteria();

        if (userName != null){
            criteria.andUserNameLike("%" + userName +"%");
        }
        if (trueName != null){
            criteria.andTrueNameLike("%" + trueName+"%");
        }
        if (email != null){
            criteria.andEmailLike("%" + email+"%");
        }
        userCriteria.setOrderByClause(TableField.SORT.SEQUENCE_DESC);
        userCriteria.setLimitStart(page.getStart());
        userCriteria.setMaxResults(page.getSize());

        page.setAllRow(userService.countByExample(userCriteria));
        return new JsonResult(1, UserAdapter.getDto(userService.selectByExample(userCriteria)), page);
    }

    @RequestMapping("/user/detail.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_USER)
    public JsonResult detail(String id) {
        User user = new User();
        if (id != null) {
            user = userService.getById(id);
        }
        return new JsonResult().data(UserAdapter.getDto(user));
    }

    @RequestMapping("/user/addOrUpdate.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_USER)
    public JsonResult add(@ModelAttribute UserDto userDto, String password) throws MyException {
        // 邮箱错误
        if (MyString.isEmpty(userDto.getEmail()) || !Tools.checkEmail(userDto.getEmail())) {
            throw new MyException(MyError.E000032);
        }

        User user = UserAdapter.getModel(userDto);
        if (MyString.isNotEmpty(password)){
            user.setPassword(password);
        }
        if (MyString.isEmpty(userDto.getId())){
            return addUser(user);
        }else{
            return updateUser(user);
        }
    }

    private JsonResult addUser(@ModelAttribute User user) throws MyException {
        if (user.getUserName().isEmpty() || !Tools.checkUserName(user.getUserName()) || ADMIN.equals(user.getUserName())) {
            throw new MyException(MyError.E000028);
        }

        // 判断是否重名
        UserCriteria userCriteria = new UserCriteria();
        UserCriteria.Criteria criteria = userCriteria.createCriteria().andUserNameEqualTo(user.getUserName());
        int userSize = userService.countByExample(userCriteria);
        if (userSize > 0) {
            throw new MyException(MyError.E000015);
        }

        if (MyString.isEmpty(user.getPassword())) {
            throw new MyException(MyError.E000061);
        }

        LoginInfoDto loginUser = LoginUserHelper.getUser();
        // 如果不是最高管理员，不允许修改权限、角色、类型
        if (!Tools.isSuperAdmin(loginUser.getRoleId())) {
            user.setAuth("");
            user.setAuthName("");
            user.setRoleId("");
            user.setRoleName("");
            user.setType(UserType.USER.getType());// 普通用户
        }
        user.setStatus(UserStatus.INVALID.getType());
        user.setPasswordSalt(Tools.getChar(20));
        user.setLoginType(LoginType.COMMON.getValue());
        user.setPassword(MD5.encrytMD5(user.getPassword(), user.getPasswordSalt()));

        userService.insert(user);
        return new JsonResult(1, UserAdapter.getDto(user));
    }

    private JsonResult updateUser(@ModelAttribute User user) throws MyException {
        Assert.notNull(user,"user不能为空");
        Assert.notNull(user.getId(), "user.id不能为空");
        // 判断是否重名
        
        if (customUserService.countByNameExceptUserId(user.getUserName(), user.getId()) > 0) {
            throw new MyException(MyError.E000015);
        }

        if (user.getUserName().isEmpty() || !Tools.checkUserName(user.getUserName())) {
            throw new MyException(MyError.E000028);
        }

        User dbUser = userService.getById(user.getId());
        if (dbUser == null) {
            throw new MyException(MyError.E000013);
        }

        LoginInfoDto loginUser = LoginUserHelper.getUser();
        // 超级管理员账号不能修改其它超级管理员账号信息，但是用户名为admin的超级管理员能修改其他超级管理员的信息
        if (Tools.isSuperAdmin(dbUser.getRoleId())) {
            if (!dbUser.getId().equals(loginUser.getId()) && !loginUser.getUserName().equals("admin")) {
                throw new MyException(MyError.E000053);
            }
        }

        // admin 用户名不允许修改
        if (dbUser.getUserName().equals("admin") && !user.getUserName().equals("admin")) {
            throw new MyException(MyError.E000055);
        }

        // 普通管理员不能修改管理员信息
        if (!Tools.isSuperAdmin(loginUser.getRoleId())) {
            if (!dbUser.getId().equals(loginUser.getId()) && dbUser.getType() == UserType.ADMIN.getType()) {
                throw new MyException(MyError.E000054);
            }
        }

        // 如果不是最高管理员，不允许修改权限、角色、类型
        if (!Tools.isSuperAdmin(loginUser.getRoleId())) {
            user.setAuth(null);
            user.setAuthName(null);
            user.setRoleId(null);
            user.setRoleName(null);
            user.setType(null);
        }

        // 修改了用户邮箱，状态修改改为为验证
        if (MyString.isEmpty(dbUser.getEmail()) || !user.getEmail().equals(dbUser.getEmail())) {
            user.setStatus(UserStatus.INVALID.getType());
            user.setEmail(user.getEmail());
            userCache.add(user.getId(), new LoginInfoDto(user, roleService, customProjectService, projectUserService));
        }

        // 如果前端设置了密码，则修改密码，否者使用旧密码，登陆类型设置为允许普通登陆
        if (!MyString.isEmpty(user.getPassword())) {
            user.setPasswordSalt(Tools.getChar(20));
            user.setPassword(MD5.encrytMD5(user.getPassword(), user.getPasswordSalt()));
            user.setLoginType(LoginType.COMMON.getValue());
        }

        UserCriteria example = new UserCriteria();
        example.createCriteria().andEmailEqualTo(user.getEmail()).andLoginTypeEqualTo(user.getLoginType()).andIdNotEqualTo(dbUser.getId());
        int userSize = userService.countByExample(example);
        if (userSize > 0){
            throw new MyException(MyError.E000062);
        }

        userService.update(user);
        user.setPassword(null);
        return new JsonResult(1, UserAdapter.getDto(user));
    }
}
