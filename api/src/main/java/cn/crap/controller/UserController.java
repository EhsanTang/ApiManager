package cn.crap.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.framework.JsonResult;
import cn.crap.framework.Pick;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.IMenuService;
import cn.crap.inter.IRoleService;
import cn.crap.inter.IUserService;
import cn.crap.model.Role;
import cn.crap.model.User;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Scope("prototype")
@Controller
@RequestMapping("/user")
public class UserController extends BaseController<User>{

	@Autowired
	private IUserService userService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IMenuService menuService;

	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@ModelAttribute User user,@RequestParam(defaultValue="1") Integer currentPage){
		page.setCurrentPage(currentPage);
		map = Tools.getMap("trueName|like",user.getTrueName());
		return new JsonResult(1,userService.findByMap(map,page,null),page);
	}
	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult detail(@ModelAttribute User user,String currentId){
		user= userService.get(user.getUserId());
		if(user==null){
			user=new User();
		}
		return new JsonResult(1,user);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_USER)
	public JsonResult addOrUpdate(@ModelAttribute User user){
		try{
		String roleIds = user.getRoleId();
		if (roleIds != null&&!roleIds.equals("")) {
			roleIds = Tools.getIdsFromField(roleIds);
			map = Tools.getMap("roleId|in", "'0'," + roleIds);
			List<Role> roles = roleService.findByMap(map, null,null);
			StringBuilder sb = new StringBuilder();
			for(Role role:roles){
				sb.append(","+role.getRoleName());
			}
			if(roleIds.indexOf(Const.SUPER)>=0){
				sb.append(","+Const.SUPER);
			}
			user.setRoleName(sb.toString().replaceFirst(",", ""));
		}else{
			user.setRoleName("");
		}
		String auths = user.getAuth();
		if(auths !=null && !auths.equals("")){
			List<Pick> picks = new ArrayList<Pick>();
			menuService.pick(picks, "", "AUTH", "");
			StringBuilder sb = new StringBuilder();
			for(Pick pick:picks){
				for(String auth:auths.split(",")){
					if(pick.getValue().equals(auth)){
						sb.append(pick.getName()+"，");
					}
				}
			}
			user.setAuthName(sb.toString());
		}else{
			user.setAuthName("");
		}
		if(!MyString.isEmpty(user.getUserId())){
			userService.update(user);
		}else{
			user.setUserId(null);
			userService.save(user);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return new JsonResult(1,user);
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_USER)
	public JsonResult delete(@ModelAttribute User user){
		userService.delete(user);
		return new JsonResult(1,null);
	}


}
