package cn.crap.controller.admin;

import java.util.List;
import java.util.Map;

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
import cn.crap.inter.service.table.IModuleService;
import cn.crap.inter.service.table.IProjectService;
import cn.crap.inter.service.table.IProjectUserService;
import cn.crap.inter.service.table.IRoleService;
import cn.crap.inter.service.table.IUserService;
import cn.crap.inter.service.tool.ICacheService;
import cn.crap.model.User;
import cn.crap.springbeans.Config;
import cn.crap.utils.Const;
import cn.crap.utils.MD5;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;

@Controller
public class UserController extends BaseController<User>{

	@Autowired
	private IUserService userService;
	@Autowired
	private ICacheService cacheService;
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
	@AuthPassport(authority=Const.AUTH_USER)
	public JsonResult list(@ModelAttribute User user,@RequestParam(defaultValue="1") Integer currentPage){
		Page page= new Page(15);
		page.setCurrentPage(currentPage);
		Map<String,Object> map = Tools.getMap("trueName|like",user.getTrueName(),
				"userName|like", user.getUserName(), "email|like", user.getEmail());
		return new JsonResult(1,userService.findByMap(map,page,null),page);
	}
	@RequestMapping("/user/detail.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_USER)
	public JsonResult detail(@ModelAttribute User user){
		if(!user.getId().equals(Const.NULL_ID)){
			user= userService.get(user.getId());
		}else{
			user=new User();
		}
		user.setPassword("");
		return new JsonResult(1,user);
	}
	
	@RequestMapping("/user/addOrUpdate.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_USER)
	public JsonResult addOrUpdate(@ModelAttribute User user) throws MyException{
		// 判断是否重名
		List<User> users = userService.findByMap(Tools.getMap("userName", user.getUserName()), null, null);
		if(users.size()>0 && !users.get(0).getId().equals(user.getId())){
			throw new MyException("000015");
		}
	
		if(user.getUserName().isEmpty() || !Tools.checkUserName(user.getUserName())){
			throw new MyException("000028");
		}
		
		
		User temp = null;
		if(!MyString.isEmpty(user.getId())){
			temp = userService.get(user.getId());
		}
		
		LoginInfoDto cacheUser = (LoginInfoDto) Tools.getUser();
		
		// 超级管理员账号不能修改其它超级管理员账号信息，但是用户名为admin的超级管理员能修改其他超级管理员的信息
		if( temp != null && Tools.isSuperAdmin( temp.getRoleId() )){
			if( !temp.getId().equals( cacheUser.getId()) && !cacheUser.getUserName().equals("admin") ){
				throw new MyException("000053");
			}
		}
		
		// admin 用户名不允许修改
		if( temp != null && temp.getUserName().equals("admin") && !user.getUserName().equals("admin")){
			throw new MyException("000055");
		}
		
		// 普通管理员不能修改管理员信息
		if( temp != null && !Tools.isSuperAdmin(cacheUser.getRoleId()) ){
			if( !temp.getId().equals( cacheUser.getId()) && temp.getType() == UserType.ADMIN.getType() ){
				throw new MyException("000054");
			}
		}
		
		// 如果前端设置了密码，则修改密码，否者使用就密码
		if(!MyString.isEmpty(user.getPassword())){
			user.setPassword(MD5.encrytMD5(user.getPassword()));
		}
		
		// 修改了用户邮箱，状态修改改为为验证
		if( temp != null && !MyString.isEmpty(user.getEmail()) && ( MyString.isEmpty(temp.getEmail()) || !user.getEmail().equals(temp.getEmail()) )){
			user.setStatus(Byte.valueOf(UserStatus.邮箱未验证.getName()));
			cacheService.setObj(Const.CACHE_USER + user.getId(), 
					new LoginInfoDto(user, roleService, projectService, projectUserService), config.getLoginInforTime());
		}
		
		// 如果不是最高管理员，不允许修改权限、角色、类型
		if( !Tools.isSuperAdmin(cacheUser.getRoleId()) ){
			if(temp != null){
				user.setAuth(temp.getAuth());
				user.setAuthName(temp.getAuthName());
				user.setRoleId(temp.getRoleId());
				user.setRoleName(temp.getRoleName());
				user.setType(temp.getType());
			}else{
				user.setAuth("");
				user.setAuthName("");
				user.setRoleId("");
				user.setRoleName("");
				user.setType(Byte.valueOf("1"));// 普通用户
			}
			
		}

		if(MyString.isEmpty(user.getEmail())){
			user.setEmail(null);
		}
		
		// 如果temp不为空，表示修改用户信息
		if(temp != null){
			// 如果密码为空，则设置为旧密码
			if(MyString.isEmpty(user.getPassword())){
				// 如果设置了密码，则修改为普通登陆
				if(temp.getLoginType() != LoginType.COMMON.getValue()){
					user.setLoginType(LoginType.COMMON.getValue());
				}
				user.setPassword(temp.getPassword());
			}
			
			userService.update(user);
		}else{
			user.setStatus(Byte.valueOf("1"));
			user.setId(null);
			userService.save(user);
		}
		return new JsonResult(1,user);
	}
	
/*	@RequestMapping("/user/delete.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_USER)
	public JsonResult delete(@ModelAttribute User user){
		userService.delete(user);
		return new JsonResult(1,null);
	}*/
}
