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
import cn.crap.inter.service.IMenuService;
import cn.crap.inter.service.IRoleService;
import cn.crap.model.Role;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Scope("prototype")
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{

	@Autowired
	private IRoleService roleService;
	@Autowired
	private IMenuService menuService;
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@ModelAttribute Role role,@RequestParam(defaultValue="1") Integer currentPage){
		page.setCurrentPage(currentPage);
		map = Tools.getMap("roleName|like",role.getRoleName());
		return new JsonResult(1,roleService.findByMap(map,page,null),page);
	}
	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult detail(@ModelAttribute Role role){
		role= roleService.get(role.getRoleId());
		if(role==null){
			role=new Role();
		}
		return new JsonResult(1,role);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_ROLE)
	public JsonResult addOrUpdate(@ModelAttribute Role role){
		String auths = role.getAuth();
		if(auths !=null && !auths.equals("")){
			List<Pick> picks = new ArrayList<Pick>();
			menuService.pick(picks, "", "AUTH", "","");
			StringBuilder sb = new StringBuilder();
			for(Pick pick:picks){
				for(String auth:auths.split(",")){
					if(pick.getValue().equals(auth)){
						sb.append(pick.getName()+"，");
					}
				}
			}
			role.setAuthName(sb.toString().replaceAll("-", "").replaceAll(" ", ""));
		}else{
			role.setAuthName("");
		}
		if(!MyString.isEmpty(role.getRoleId())){
			roleService.update(role);
		}else{
			role.setRoleId(null);
			roleService.save(role);
		}
		return new JsonResult(1,role);
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_ROLE)
	public JsonResult delete(@ModelAttribute Role role){
		roleService.delete(role);
		return new JsonResult(1,null);
	}


}
