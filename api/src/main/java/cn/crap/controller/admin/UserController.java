package cn.crap.controller.admin;

import cn.crap.adapter.UserAdapter;
import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.UserDTO;
import cn.crap.enu.LoginType;
import cn.crap.enu.MyError;
import cn.crap.enu.UserStatus;
import cn.crap.enu.UserType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.UserPO;
import cn.crap.query.UserQuery;
import cn.crap.service.ProjectService;
import cn.crap.service.ProjectUserService;
import cn.crap.service.UserService;
import cn.crap.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectUserService projectUserService;
    @Autowired
    private UserService customUserService;

    @RequestMapping("/list.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_USER)
    public JsonResult list(@ModelAttribute UserQuery query) throws MyException{
        Page page = new Page(query);

        page.setAllRow(userService.count(query));
        return new JsonResult(1, UserAdapter.getDto(userService.select(query)), page);
    }

    @RequestMapping("/detail.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_USER)
    public JsonResult detail(String id) {
        UserPO user = new UserPO();
        if (id != null) {
            user = userService.get(id);
        }
        return new JsonResult().data(UserAdapter.getDto(user));
    }

    @RequestMapping("/addOrUpdate.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_USER)
    public JsonResult add(@ModelAttribute UserDTO userDto, String password) throws MyException {
        // 邮箱错误
        if (MyString.isEmpty(userDto.getEmail()) || !Tools.checkEmail(userDto.getEmail())) {
            throw new MyException(MyError.E000032);
        }

        UserPO user = UserAdapter.getModel(userDto);
        if (MyString.isNotEmpty(password)){
            user.setPassword(password);
        }

        UserQuery query = new UserQuery().setEqualEmail(userDto.getEmail().toLowerCase());
        if (MyString.isEmpty(userDto.getId())){
            if(userService.count(query) > 0){
                throw new MyException(MyError.E000065, "邮箱已经注册");
            }
            return addUser(user);
        }else{
            UserPO dbUser = userService.get(user.getId());
            if(dbUser.getEmail() == null || !dbUser.getEmail().equalsIgnoreCase(userDto.getEmail())){
                if (userService.count(query) > 0){
                    throw new MyException(MyError.E000065, "邮箱已经注册");
                }
            }
            return updateUser(user, userDto.getAttrKey(), userDto.getAttrVal());
        }
    }

    private JsonResult addUser(@ModelAttribute UserPO user) throws MyException {
        if (user.getUserName().isEmpty() || !Tools.checkUserName(user.getUserName()) || ADMIN.equals(user.getUserName())) {
            throw new MyException(MyError.E000028);
        }

        // 判断是否重名
        UserQuery query = new UserQuery().setEqualUserName(user.getUserName()).setPageSize(1);
        int userSize = userService.count(query);
        if (userSize > 0) {
            throw new MyException(MyError.E000015);
        }

        if (MyString.isEmpty(user.getPassword())) {
            throw new MyException(MyError.E000061);
        }

        LoginInfoDto loginUser = LoginUserHelper.getUser();
        // 如果不是最高管理员，不允许修改权限、类型
        if (!Tools.isSuperAdmin(loginUser.getAuthStr())) {
            user.setAuth("");
            user.setAuthName("");
            user.setType(UserType.USER.getType());// 普通用户
        }
        user.setStatus(UserStatus.INVALID.getType());
        user.setPasswordSalt(Tools.getChar(20));
        user.setLoginType(LoginType.COMMON.getValue());
        user.setPassword(MD5.encrytMD5(user.getPassword(), user.getPasswordSalt()));

        userService.insert(user);
        return new JsonResult(1, UserAdapter.getDto(user));
    }

    private JsonResult updateUser(@ModelAttribute UserPO user, String attrKey, String attrVal) throws MyException {
        Assert.notNull(user,"user不能为空");
        Assert.notNull(user.getId(), "user.id不能为空");
        // 判断是否重名
        
        if (customUserService.countByNameExceptUserId(user.getUserName(), user.getId()) > 0) {
            throw new MyException(MyError.E000015);
        }

        if (user.getUserName().isEmpty() || !Tools.checkUserName(user.getUserName())) {
            throw new MyException(MyError.E000028);
        }

        UserPO dbUser = userService.get(user.getId());
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
        boolean superAdmin = Tools.isSuperAdmin(loginUser.getAuthStr());
        if (!superAdmin) {
            if (!dbUser.getId().equals(loginUser.getId()) && dbUser.getType() == UserType.ADMIN.getType()) {
                throw new MyException(MyError.E000054);
            }
        }

        // 如果不是最高管理员，不允许修改权限、类型
        if (!superAdmin) {
            user.setAuth(null);
            user.setAuthName(null);
            user.setType(null);
            user.setAttributes(null);
        }

        // 修改了用户邮箱，状态修改改为为验证
        if (MyString.isEmpty(dbUser.getEmail()) || !user.getEmail().equals(dbUser.getEmail())) {
            user.setStatus(UserStatus.INVALID.getType());
            dbUser.setEmail(user.getEmail());
            dbUser.setStatus(UserStatus.INVALID.getType());
            userCache.add(user.getId(), new LoginInfoDto(dbUser));
        }

        // 如果前端设置了密码，则修改密码，否者使用旧密码，登录类型设置为允许普通登录
        if (!MyString.isEmpty(user.getPassword())) {
            user.setPasswordSalt(Tools.getChar(20));
            user.setPassword(MD5.encrytMD5(user.getPassword(), user.getPasswordSalt()));
            user.setLoginType(LoginType.COMMON.getValue());
        }

        UserQuery userQuery = new UserQuery();
        userQuery.setEqualEmail(user.getEmail()).setLoginType(user.getLoginType()).setNotEqualId(dbUser.getId());
        int userSize = userService.count(userQuery);
        if (userSize > 0){
            throw new MyException(MyError.E000062);
        }

        userService.update(user);

        if (superAdmin && MyString.isNotEmptyOrNUll(attrKey) && MyString.isNotEmptyOrNUll(attrVal)){
            userService.updateAttribute(user.getId(), attrKey, attrVal, new UserPO());
        }

        user.setPassword(null);


        return new JsonResult(1, UserAdapter.getDto(user));
    }
}
