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
import cn.crap.inter.MenuInfoService;
import cn.crap.inter.RoleInfoService;
import cn.crap.inter.UserInfoService;
import cn.crap.model.RoleInfoModel;
import cn.crap.model.UserInfoModel;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Scope("prototype")
@Controller
@RequestMapping("/userInfo")
public class UserInfoController extends BaseController<UserInfoModel>{

	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private RoleInfoService roleInfoService;
	@Autowired
	private MenuInfoService menuInfoService;

	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@ModelAttribute UserInfoModel user,@RequestParam(defaultValue="1") Integer currentPage){
		page.setCurrentPage(currentPage);
		map = Tools.getMap("trueName|like",user.getTrueName());
		return new JsonResult(1,userInfoService.findByMap(map,page,null),page);
	}
	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult detail(@ModelAttribute UserInfoModel user,String currentId){
		user= userInfoService.get(user.getUserId());
		if(user==null){
			user=new UserInfoModel();
		}
		return new JsonResult(1,user);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_USER)
	public JsonResult addOrUpdate(@ModelAttribute UserInfoModel user){
		try{
		String roleIds = user.getRoleId();
		if (roleIds != null&&!roleIds.equals("")) {
			roleIds = Tools.getIdsFromField(roleIds);
			map = Tools.getMap("roleId|in", "'0'," + roleIds);
			List<RoleInfoModel> roles = roleInfoService.findByMap(map, null,null);
			StringBuilder sb = new StringBuilder();
			for(RoleInfoModel role:roles){
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
			menuInfoService.pick(picks, "", "AUTH", "");
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
			userInfoService.update(user);
		}else{
			user.setUserId(null);
			userInfoService.save(user);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return new JsonResult(1,user);
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_USER)
	public JsonResult delete(@ModelAttribute UserInfoModel user){
		userInfoService.delete(user);
		return new JsonResult(1,null);
	}


}
