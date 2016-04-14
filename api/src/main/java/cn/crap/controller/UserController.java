package cn.crap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.crap.framework.JsonResult;
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
		if(user.getUserId() != Const.NULL_ID){
			user= userService.get(user.getUserId());
		}else{
			user=new User();
		}
		user.setPassword("");
		return new JsonResult(1,user);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_USER)
	public JsonResult addOrUpdate(@ModelAttribute User user){
		if(!MyString.isEmpty(user.getPassword())){
			user.setPassword(MD5.encrytMD5(user.getPassword()));
		}
		try{
		if(!MyString.isEmpty(user.getUserId())){
			User temp = userService.get(user.getUserId());
			if(MyString.isEmpty(user.getPassword())){
				user.setPassword(temp.getPassword());
			}
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
