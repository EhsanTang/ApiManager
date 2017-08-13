package cn.crap.controller.admin;

import java.util.List;
import java.util.Map;

import cn.crap.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.dto.LoginInfoDto;
import cn.crap.enumeration.LoginType;
import cn.crap.enumeration.UserStatus;
import cn.crap.enumeration.UserType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.service.IModuleService;
import cn.crap.service.IProjectService;
import cn.crap.service.IProjectUserService;
import cn.crap.service.IRoleService;
import cn.crap.service.IUserService;
import cn.crap.model.User;
import cn.crap.springbeans.Config;

@Controller
public class UserController extends BaseController<User> {

    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IModuleService dataCenterService;
    @Autowired
    private Config config;
    @Autowired
    private IProjectService projectService;
    @Autowired
    private IProjectUserService projectUserService;

    @RequestMapping("/user/list.do")
    @ResponseBody
    @AuthPassport(authority = Const.AUTH_USER)
    public JsonResult list(@ModelAttribute User user, @RequestParam(defaultValue = "1") Integer currentPage) {
        Page page = new Page(15);
        page.setCurrentPage(currentPage);
        Map<String, Object> map = Tools.getMap("trueName|like", user.getTrueName(),
                "userName|like", user.getUserName(), "email|like", user.getEmail());
        return new JsonResult(1, userService.findByMap(map, page, null), page);
    }

    @RequestMapping("/user/detail.do")
    @ResponseBody
    @AuthPassport(authority = Const.AUTH_USER)
    public JsonResult detail(@ModelAttribute User user) {
        if (!user.getId().equals(Const.NULL_ID)) {
            user = userService.get(user.getId());
        } else {
            user = new User();
        }
        user.setPassword("");
        return new JsonResult(1, user);
    }


    @RequestMapping("/user/addOrUpdate.do")
    @ResponseBody
    @AuthPassport(authority = Const.AUTH_USER)
    public JsonResult add(@ModelAttribute User user) throws MyException {
        // 邮箱错误
        if (MyString.isEmpty(user.getEmail()) || !Tools.checkEmail(user.getEmail())) {
            throw new MyException("000032");
        }

        if (MyString.isEmpty(user.getId())){
            return addUser(user);
        }else{
            return updateUser(user);
        }
    }

    private JsonResult addUser(@ModelAttribute User user) throws MyException {
        if (user.getUserName().isEmpty() || !Tools.checkUserName(user.getUserName()) || Const.ADMIN.equals(user.getUserName())) {
            throw new MyException("000028");
        }

        // 判断是否重名
        List<User> users = userService.findByMap(Tools.getMap("userName", user.getUserName()), null, null);
        if (users.size() > 0) {
            throw new MyException("000015");
        }

        if (MyString.isEmpty(user.getPassword())) {
            throw new MyException("000061");
        }


        LoginInfoDto loginUser = Tools.getUser();
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

        return new JsonResult(1, user);
    }

    private JsonResult updateUser(@ModelAttribute User user) throws MyException {
        // 判断是否重名
        List<User> users = userService.findByMap(Tools.getMap("userName", user.getUserName()), null, null);
        if (users.size() > 0 && !users.get(0).getId().equals(user.getId())) {
            throw new MyException("000015");
        }

        if (user.getUserName().isEmpty() || !Tools.checkUserName(user.getUserName())) {
            throw new MyException("000028");
        }

        User dbUser = userService.get(user.getId());
        if (dbUser == null) {
            throw new MyException("000013");
        }

        LoginInfoDto loginUser = Tools.getUser();
        // 超级管理员账号不能修改其它超级管理员账号信息，但是用户名为admin的超级管理员能修改其他超级管理员的信息
        if (Tools.isSuperAdmin(dbUser.getRoleId())) {
            if (!dbUser.getId().equals(loginUser.getId()) && !loginUser.getUserName().equals("admin")) {
                throw new MyException("000053");
            }
        }

        // admin 用户名不允许修改
        if (dbUser.getUserName().equals("admin") && !user.getUserName().equals("admin")) {
            throw new MyException("000055");
        }

        // 普通管理员不能修改管理员信息
        if (!Tools.isSuperAdmin(loginUser.getRoleId())) {
            if (!dbUser.getId().equals(loginUser.getId()) && dbUser.getType() == UserType.ADMIN.getType()) {
                throw new MyException("000054");
            }
        }

        // 如果不是最高管理员，不允许修改权限、角色、类型
        if (Tools.isSuperAdmin(loginUser.getRoleId())) {
            dbUser.setAuth(user.getAuth());
            dbUser.setAuthName(user.getAuthName());
            dbUser.setRoleId(user.getRoleId());
            dbUser.setRoleName(user.getRoleName());
            dbUser.setType(user.getType());// 普通用户
        }

        // 修改了用户邮箱，状态修改改为为验证
        if (MyString.isEmpty(dbUser.getEmail()) || !user.getEmail().equals(dbUser.getEmail())) {
            dbUser.setStatus(UserStatus.INVALID.getType());
            dbUser.setEmail(user.getEmail());
            cacheService.setObj(Const.CACHE_USER + user.getId(),
                    new LoginInfoDto(user, roleService, projectService, projectUserService), config.getLoginInforTime());
        }

        // 如果前端设置了密码，则修改密码，否者使用旧密码，登陆类型设置为允许普通登陆
        if (!MyString.isEmpty(user.getPassword())) {
            dbUser.setPasswordSalt(Tools.getChar(20));
            dbUser.setPassword(MD5.encrytMD5(user.getPassword(), dbUser.getPasswordSalt()));
            dbUser.setLoginType(LoginType.COMMON.getValue());

            users = userService.findByMap(Tools.getMap("email", dbUser.getEmail(), TableField.USER.LOGIN_TYPE, LoginType.COMMON.getValue()), null, null);
            if (users.size() == 1){
                if (!users.get(0).getId().equals(dbUser.getId())){
                    throw new MyException("000062"); // 邮箱已经被其他用户绑定，不能通过密码登陆
                }
            }
        }

        userService.update(dbUser);
        return new JsonResult(1, user);
    }

/*	@RequestMapping("/user/delete.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_USER)
	public JsonResult delete(@ModelAttribute User user){
		userService.delete(user);
		return new JsonResult(1,null);
	}*/
}
