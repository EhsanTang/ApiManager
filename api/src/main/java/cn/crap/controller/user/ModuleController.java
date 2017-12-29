package cn.crap.controller.user;

import cn.crap.adapter.ModuleAdapter;
import cn.crap.dto.LoginInfoDto;
import cn.crap.enumer.ArticleType;
import cn.crap.enumer.LogType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.mybatis.InterfaceWithBLOBs;
import cn.crap.model.mybatis.Module;
import cn.crap.service.custom.*;
import cn.crap.service.imp.*;
import cn.crap.springbeans.Config;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user/module")
public class ModuleController extends BaseController{

	@Autowired
	private MybatisModuleService moduleService;
	@Autowired
	private MybatisRoleService roleService;
	@Autowired
	private CustomArticleService articleService;
	@Autowired
	private MybatisInterfaceService interfaceService;
	@Autowired
	private CustomProjectService customProjectService;
	@Autowired
	private MybatisProjectUserService projectUserService;
	@Autowired
	private CustomSourceService customSourceService;
	@Autowired
	private MybatisUserService userService;
	@Autowired
	private Config config;
	@Autowired
	private CustomModuleService customModuleService;
	@Autowired
	private CustomLogService customLogService;
	@Autowired
	private CustomInterfaceService customInterfaceService;
	
	
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@RequestParam String projectId, @RequestParam(defaultValue="1") int currentPage) throws MyException{
			Page<Module> page= new Page(15, currentPage);

			hasPermission(projectCache.get(projectId), view);

			page = customModuleService.queryByProjectId(projectId, page);
			return new JsonResult(1, ModuleAdapter.getDto(page.getList()), page);
		}

	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult detail(@ModelAttribute Module module) throws MyException{
		Module model;
		if(!module.getId().equals(Const.NULL_ID)){
			model= moduleService.selectByPrimaryKey(module.getId());
//			if(!MyString.isEmpty(model.getTemplateId())){
//				Interface inter = interfaceService.get(model.getTemplateId());
//				if(inter != null)
//					model.setTemplateName(inter.getInterfaceName());
//			}getInterfaceName
			hasPermission(projectCache.get(model.getProjectId()), view);
		}else{
			hasPermission(projectCache.get(module.getProjectId()), view);
			model=new Module();
			model.setStatus(Byte.valueOf("1"));
			model.setProjectId(module.getProjectId());
		}
		return new JsonResult(1,model);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute Module module) throws Exception{
		// 系统数据，不允许删除 TODO web移至单独表
		if(module.getId().equals("web"))
			throw new MyException("000009");
				
		if(!MyString.isEmpty(module.getId())){
			module.setProjectId(moduleCache.get(module.getId()).getProjectId());
			hasPermission(projectCache.get( module.getProjectId() ), modModule);
			// 更新该模块下的所有接口的fullUrl
			customInterfaceService.updateFullUrlByModuleId(module.getUrl(), module.getId());
			moduleService.update(module);
			Module dbModule = moduleService.selectByPrimaryKey(module.getId());
			customLogService.saveLog("模块", JSONObject.fromObject(dbModule).toString(), "", LogType.UPDATE, Module.class);
		}else{
			hasPermission(projectCache.get( module.getProjectId() ), addModule);
			module.setUserId(Tools.getUser().getId());
			module.setVersion(0);
			moduleService.insert(module);
		}
		moduleCache.del(module.getId());
		
		/**
		 * 刷新用户权限
		 */
		LoginInfoDto user = Tools.getUser();
		// 将用户信息存入缓存
		userCache.add(user.getId(), new LoginInfoDto(userService.selectByPrimaryKey(user.getId()), roleService, customProjectService, projectUserService));
		return new JsonResult(1,module);
	}
	
	/**
	 * 设置模块接口
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/setTemplate.do")
	@ResponseBody
	public JsonResult setTemplate(String id) throws Exception{
		InterfaceWithBLOBs inter = interfaceService.selectByPrimaryKey(id);
		
		Module module = moduleCache.get(inter.getModuleId());
		hasPermission(projectCache.get( module.getProjectId() ), modModule);
		
		module.setTemplateId( inter.getIsTemplate() ? null: inter.getId() );
		moduleService.update(module);
		
		customInterfaceService.deleteTemplateByModuleId(module.getId());
		if(!inter.getIsTemplate()){
			inter.setIsTemplate(true);;
			interfaceService.update(inter);
		}
		
		moduleCache.del(module.getId());
		return new JsonResult(1,module);
	}
	
	
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(@ModelAttribute Module module) throws Exception{
		// 系统数据，不允许删除
		if(module.getId().equals("web"))
			throw new MyException("000009");
				
		Module dbModule = moduleCache.get(module.getId());
		hasPermission(projectCache.get( dbModule.getProjectId() ), delModule);
		
		if(customInterfaceService.countByModuleId(dbModule.getId()) >0 ){
			throw new MyException("000024");
		}
		
		if(articleService.countByModuleIdAndType(dbModule.getId(), ArticleType.ARTICLE.name()) >0 ){
			throw new MyException("000034");
		}
		
		if(customSourceService.countByModuleId(dbModule.getId()) >0 ){
			throw new MyException("000035");
		}
		
		if(articleService.countByModuleIdAndType(dbModule.getId(),  ArticleType.DICTIONARY.name()) >0 ){
			throw new MyException("000036");
		}
		
		moduleCache.del(module.getId());
		moduleService.delete(module.getId());

		customLogService.saveLog("模块", JSONObject.fromObject(dbModule).toString(), "", LogType.DELTET, Module.class);
		return new JsonResult(1,null);
	}
	
	@RequestMapping("/changeSequence.do")
	@ResponseBody
	@AuthPassport
	public JsonResult changeSequence(@RequestParam String id,@RequestParam String changeId) throws MyException {
		Module change = moduleService.selectByPrimaryKey(changeId);
		Module model = moduleService.selectByPrimaryKey(id);
		
		hasPermission(projectCache.get( change.getProjectId() ), modModule);
		hasPermission(projectCache.get( model.getProjectId() ), modModule);
		
		int modelSequence = model.getSequence();
		model.setSequence(change.getSequence());
		change.setSequence(modelSequence);
		
		moduleService.update(model);
		moduleService.update(change);

		return new JsonResult(1, null);
	}

}
