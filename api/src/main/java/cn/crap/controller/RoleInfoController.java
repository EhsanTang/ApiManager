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
import cn.crap.model.RoleInfoModel;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Scope("prototype")
@Controller
@RequestMapping("/roleInfo")
public class RoleInfoController extends BaseController<RoleInfoModel>{

	@Autowired
	private RoleInfoService roleInfoService;
	@Autowired
	private MenuInfoService menuInfoService;
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@ModelAttribute RoleInfoModel role,@RequestParam(defaultValue="1") Integer currentPage){
		page.setCurrentPage(currentPage);
		map = Tools.getMap("roleName|like",role.getRoleName());
		return new JsonResult(1,roleInfoService.findByMap(map,page,null),page);
	}
	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult detail(@ModelAttribute RoleInfoModel role){
		role= roleInfoService.get(role.getRoleId());
		if(role==null){
			role=new RoleInfoModel();
		}
		return new JsonResult(1,role);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_ROLE)
	public JsonResult addOrUpdate(@ModelAttribute RoleInfoModel role){
		String auths = role.getAuth();
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
			role.setAuthName(sb.toString());
		}else{
			role.setAuthName("");
		}
		if(!MyString.isEmpty(role.getRoleId())){
			roleInfoService.update(role);
		}else{
			role.setRoleId(null);
			roleInfoService.save(role);
		}
		return new JsonResult(1,role);
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_ROLE)
	public JsonResult delete(@ModelAttribute RoleInfoModel role){
		roleInfoService.delete(role);
		return new JsonResult(1,null);
	}


}
