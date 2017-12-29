package cn.crap.controller.admin;

import cn.crap.adapter.RoleAdapter;
import cn.crap.model.mybatis.Role;
import cn.crap.model.mybatis.RoleCriteria;
import cn.crap.model.mybatis.RoleWithBLOBs;
import cn.crap.service.imp.MybatisRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.framework.JsonResult;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{

	@Autowired
	private MybatisRoleService roleService;
	
	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_ROLE)
	public JsonResult list(String roleName, @RequestParam(defaultValue="1") Integer currentPage){
		Page page= new Page(15, currentPage);

		RoleCriteria example = new RoleCriteria();

		if (!MyString.isEmpty(roleName)) {
			example.createCriteria().andRoleNameLike("%" + roleName + "%");
		}

		return new JsonResult(1, RoleAdapter.getDto(roleService.selectByExampleWithBLOBs(example)),page);
	}
	@RequestMapping("/detail.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_ROLE)
	public JsonResult detail(@ModelAttribute Role role){
		Role model;
		if(!role.getId().equals(Const.NULL_ID)){
			model= roleService.selectByPrimaryKey(role.getId());
		}else{
			model=new Role();
		}
		return new JsonResult(1,model);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_ROLE)
	public JsonResult addOrUpdate(@ModelAttribute RoleWithBLOBs role){
		if(!MyString.isEmpty(role.getId())){
			roleService.update(role);
		}else{
			role.setId(null);
			roleService.insert(role);
		}
		return new JsonResult(1,role);
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_ROLE)
	public JsonResult delete(String id){
		roleService.delete(id);
		return new JsonResult(1,null);
	}
}
