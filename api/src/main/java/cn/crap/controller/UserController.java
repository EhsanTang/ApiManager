package cn.crap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.IUserService;
import cn.crap.model.User;
import cn.crap.utils.Const;
import cn.crap.utils.MD5;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Scope("prototype")
@Controller
@RequestMapping("/user")
public class UserController extends BaseController<User>{

	@Autowired
	private IUserService userService;

	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@ModelAttribute User user,@RequestParam(defaultValue="1") Integer currentPage){
		page.setCurrentPage(currentPage);
		map = Tools.getMap("trueName|like",user.getTrueName());
		return new JsonResult(1,userService.findByMap(map,page,null),page);
	}
	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult detail(@ModelAttribute User user){
		if(!user.getId().equals(Const.NULL_ID)){
			user= userService.get(user.getId());
		}else{
			user=new User();
		}
		user.setPassword("");
		return new JsonResult(1,user);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_USER)
	public JsonResult addOrUpdate(@ModelAttribute User user) throws MyException{
		// 判断是否重名
		List<User> users = userService.findByMap(Tools.getMap("userName", user.getUserName()), null, null);
		if(users.size()>0 && !users.get(0).getId().equals(user.getId())){
			throw new MyException("000015");
		}
		
		// 如果前端设置了密码，则修改密码，否者使用就密码
		if(!MyString.isEmpty(user.getPassword())){
			user.setPassword(MD5.encrytMD5(user.getPassword()));
		}
		
		User temp = null;
		if(!MyString.isEmpty(user.getId())){
			temp = userService.get(user.getId());
		}
		
		// 如果不是最高管理员，不允许修改权限、角色
		String roleIds = MyString.getValueFromSession(request, Const.SESSION_ADMIN_ROLEIDS);
		if((","+roleIds).indexOf(","+Const.SUPER+",") < 0){
			if(temp != null){
				user.setAuth(temp.getAuth());
				user.setAuthName(temp.getAuthName());
				user.setRoleId(temp.getRoleId());
				user.setRoleName(temp.getRoleName());
			}else{
				user.setAuth("");
				user.setAuthName("");
				user.setRoleId("");
				user.setRoleName("");
			}
			
		}
		
		// 如果temp不为空，表示修改用户信息
		if(temp != null){
			// 如果密码为空，则设置为旧密码
			if(MyString.isEmpty(user.getPassword())){
				user.setPassword(temp.getPassword());
			}
			userService.update(user);
		}else{
			user.setStatus(Byte.valueOf("1"));
			user.setId(null);
			userService.save(user);
		}
		user.setPassword("");
		return new JsonResult(1,user);
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_USER)
	public JsonResult delete(@ModelAttribute User user){
		userService.delete(user);
		return new JsonResult(1,null);
	}

	@RequestMapping("/changeSequence.do")
	@ResponseBody
	@AuthPassport
	@Override
	public JsonResult changeSequence(@RequestParam String id,@RequestParam String changeId) {
		return null;
	}
}
