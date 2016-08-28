package cn.crap.controller.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.dto.LoginInfoDto;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.ICacheService;
import cn.crap.inter.service.IDataCenterService;
import cn.crap.inter.service.IInterfaceService;
import cn.crap.inter.service.IRoleService;
import cn.crap.inter.service.IUserService;
import cn.crap.model.DataCenter;
import cn.crap.utils.Aes;
import cn.crap.utils.Config;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Scope("prototype")
@Controller
public class BackModuleController extends BaseController<DataCenter>{

	@Autowired
	private IDataCenterService moduleService;
	@Autowired
	private ICacheService cacheService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IInterfaceService interfaceService;
	
	@RequestMapping("/module/detail.do")
	@ResponseBody
	public JsonResult detail(@ModelAttribute DataCenter module) throws MyException{
		if(!module.getId().equals(Const.NULL_ID)){
			model= moduleService.get(module.getId());
			Tools.hasAuth("", module.getId());
		}else{
			model=new DataCenter();
			if(MyString.isEmpty((module.getType())))
				model.setType("MODULE");
			else
				model.setType(module.getType());
			model.setStatus(Byte.valueOf("1"));
			model.setParentId(module.getParentId());
		}
		return new JsonResult(1,model);
	}
	
	@RequestMapping("/module/addOrUpdate.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute DataCenter module) throws Exception{
		
		// 修改
		if(!MyString.isEmpty(module.getId())){
			if(module.getId().equals(Const.PRIVATE_MODULE)){
				throw new MyException("000009");
			}
			
			DataCenter oldDataCenter = cacheService.getModule(module.getId());
			// 如果为模块，则必须拥有新旧模块的权限
			if(module.getType().equals(Const.MODULE) || oldDataCenter.getType().equals(Const.MODULE)){
				module.setType("MODULE");// 不允许修改数据类型
				// 如果用户为管理员，则用户必须拥有新旧模块的父模块权限
				if(Tools.getUser().getType() == 100){
					Tools.hasAuth(Const.AUTH_MODULE,  module.getParentId());
					Tools.hasAuth(Const.AUTH_MODULE,  oldDataCenter.getParentId());
				}
				// 普通用户
				else{
					// 需要拥有新旧模块的权限（防止修改他人的模块，或将自己的模块转移到其他人的模块下）
					Tools.hasAuth(Const.AUTH_MODULE,  module.getId());
					Tools.hasAuth(Const.AUTH_MODULE,  oldDataCenter.getId());
					
					// 如果父模块不为私有模块，则还需要拥有该父模块的权限
					if(!module.getParentId().equals(Const.PRIVATE_MODULE)){
						Tools.hasAuth(Const.AUTH_MODULE,  module.getParentId());
					}
					// 用户不能讲模块状态设置为3
					if(module.getStatus() == 3){
						throw new MyException("000022");
					}
				}
				
			}else{
				Tools.hasAuth(Const.AUTH_SOURCE,  "");
			}
		}
		// 新增
		else{
			if(module.getType().equals(Const.MODULE)){
				// 如果父模块不为私有模块，则还需要拥有该父模块的权限
				if(!module.getParentId().equals(Const.PRIVATE_MODULE)){
					Tools.hasAuth(Const.AUTH_MODULE,  module.getParentId());
				}
			}else{
				Tools.hasAuth(Const.AUTH_SOURCE,  "");
			}
		}
		
		if(!MyString.isEmpty(module.getId())){
			moduleService.update(module);
		}else{
			module.setId(null);
			module.setUserId(Tools.getUser().getId());
			moduleService.save(module);
		}
		cacheService.delObj("cache:model:"+module.getId());
		
		/**
		 * 刷新用户权限
		 */
		LoginInfoDto user = Tools.getUser();
		String token  = Aes.encrypt(user.getId());
		// 将用户信息存入缓存
		cacheService.setObj(Const.CACHE_USER + token, new LoginInfoDto(userService.get(user.getId()), roleService, moduleService), Config.getLoginInforTime());
		return new JsonResult(1,module);
	}
	
	@RequestMapping("/module/delete.do")
	@ResponseBody
	public JsonResult delete(@ModelAttribute DataCenter module) throws Exception{
		// 系统私有模块，不允许删除
		if(module.getId().equals(Const.PRIVATE_MODULE)){
			throw new MyException("000009");
		}
		
		DataCenter oldDataCenter = cacheService.getModule(module.getId());
		
		// 管理员，必须拥有父模块的权限，方可删除模块
		if(Tools.getUser().getType() == 100){
			if(oldDataCenter.getType().equals(Const.MODULE)){
				Tools.hasAuth(Const.AUTH_MODULE,  oldDataCenter.getParentId());
			}else{
				Tools.hasAuth(Const.AUTH_SOURCE,  "");
			}
		}
		// 普通用户，必须拥有该模块的权限，方可删除
		else{
			Tools.hasAuth(Const.AUTH_MODULE,  oldDataCenter.getId());
		}
		
		// 只有子模块和接口数量为0，才允许删除模块
		if(moduleService.getCount(Tools.getMap("parentId", oldDataCenter.getId())) > 0){
			throw new MyException("000023");
		}
		if(interfaceService.getCount(Tools.getMap("moduleId", oldDataCenter.getId())) >0 ){
			throw new MyException("000024");
		}
		
		
		cacheService.delObj("cache:model:"+module.getId());
		moduleService.delete(module);
		return new JsonResult(1,null);
	}
	
	@RequestMapping("/back/module/changeSequence.do")
	@ResponseBody
	@AuthPassport
	public JsonResult changeSequence(@RequestParam String id,@RequestParam String changeId) throws MyException {
		DataCenter change = moduleService.get(changeId);
		model = moduleService.get(id);
		
		if(Tools.getUser().getType() == 100){
			Tools.hasAuth(Const.AUTH_MODULE,  model.getParentId());
		}else{
			Tools.hasAuth(Const.AUTH_MODULE,  changeId);
			Tools.hasAuth(Const.AUTH_MODULE, id);
		}
		
		int modelSequence = model.getSequence();
		
		model.setSequence(change.getSequence());
		change.setSequence(modelSequence);
		
		moduleService.update(model);
		moduleService.update(change);

		return new JsonResult(1, null);
	}

}
