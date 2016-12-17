package cn.crap.controller.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.dto.LoginInfoDto;
import cn.crap.enumeration.ArticleType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.table.IArticleService;
import cn.crap.inter.service.table.IInterfaceService;
import cn.crap.inter.service.table.IModuleService;
import cn.crap.inter.service.table.IProjectService;
import cn.crap.inter.service.table.IProjectUserService;
import cn.crap.inter.service.table.IRoleService;
import cn.crap.inter.service.table.ISourceService;
import cn.crap.inter.service.table.IUserService;
import cn.crap.inter.service.tool.ICacheService;
import cn.crap.model.Interface;
import cn.crap.model.Module;
import cn.crap.springbeans.Config;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;

@Controller
@RequestMapping("/user/module")
public class ModuleController extends BaseController<Module>{

	@Autowired
	private IModuleService moduleService;
	@Autowired
	private ICacheService cacheService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IArticleService articleService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IInterfaceService interfaceService;
	@Autowired
	private IProjectService projectService;
	@Autowired
	private IProjectUserService projectUserService;
	@Autowired
	private ISourceService sourceService;
	@Autowired
	private Config config;
	
	
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@ModelAttribute  Module module, @RequestParam(defaultValue="1") int currentPage) throws MyException{
			Page page= new Page(15);
			page.setCurrentPage(currentPage);
			Map<String,Object> map = Tools.getMap("projectId", module.getProjectId());
			
			hasPermission(cacheService.getProject(module.getProjectId()), view);
			
			return new JsonResult(1, moduleService.findByMap(map, page, null), page);
		}	
	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult detail(@ModelAttribute Module module) throws MyException{
		Module model;
		if(!module.getId().equals(Const.NULL_ID)){
			model= moduleService.get(module.getId());
			if(!MyString.isEmpty(model.getTemplateId())){
				Interface inter = interfaceService.get(model.getTemplateId());
				if(inter != null)
					model.setTemplateName(inter.getInterfaceName());
			}
			hasPermission(cacheService.getProject(model.getProjectId()), view);
		}else{
			hasPermission(cacheService.getProject(module.getProjectId()), view);
			model=new Module();
			model.setStatus(Byte.valueOf("1"));
			model.setProjectId(module.getProjectId());
		}
		return new JsonResult(1,model);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute Module module) throws Exception{
		// 系统数据，不允许删除
		if(module.getId().equals("web"))
			throw new MyException("000009");
				
		if(!MyString.isEmpty(module.getId())){
			module.setProjectId(cacheService.getModule(module.getId()).getProjectId());
			hasPermission(cacheService.getProject( module.getProjectId() ), modModule);
			// 更新该模块下的所有接口的fullUrl
			interfaceService.update("update Interface set fullUrl=CONCAT('"+(module.getUrl() == null? "":module.getUrl())+
					"',url) where moduleId ='"+module.getId()+"'", null);
			moduleService.update(module);
		}else{
			hasPermission(cacheService.getProject( module.getProjectId() ), addModule);
			module.setUserId(Tools.getUser().getId());
			moduleService.save(module);
		}
		cacheService.delObj(Const.CACHE_MODULE+module.getId());
		
		/**
		 * 刷新用户权限
		 */
		LoginInfoDto user = Tools.getUser();
		// 将用户信息存入缓存
		cacheService.setObj(Const.CACHE_USER + user.getId(), 
				new LoginInfoDto(userService.get(user.getId()), roleService, projectService, projectUserService), config.getLoginInforTime());
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
		Interface inter = interfaceService.get(id);
		
		Module module = cacheService.getModule(inter.getModuleId());
		hasPermission(cacheService.getProject( module.getProjectId() ), modModule);
		
		module.setTemplateId( inter.isTemplate() ? null: inter.getId() );
		moduleService.update(module);
		
		interfaceService.update("update Interface set isTemplate=0 where moduleId ='"+module.getId()+"'", null);
		if(!inter.isTemplate()){
			inter.setTemplate(true);;
			interfaceService.update(inter);
		}
		
		cacheService.delObj(Const.CACHE_MODULE+module.getId());
		return new JsonResult(1,module);
	}
	
	
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(@ModelAttribute Module module) throws Exception{
		// 系统数据，不允许删除
		if(module.getId().equals("web"))
			throw new MyException("000009");
				
		Module oldDataCenter = cacheService.getModule(module.getId());
		hasPermission(cacheService.getProject( oldDataCenter.getProjectId() ), delModule);
		
		if(interfaceService.getCount(Tools.getMap("moduleId", oldDataCenter.getId())) >0 ){
			throw new MyException("000024");
		}
		
		if(articleService.getCount(Tools.getMap("moduleId", oldDataCenter.getId(), "type", ArticleType.ARTICLE.name())) >0 ){
			throw new MyException("000034");
		}
		
		if(sourceService.getCount(Tools.getMap("moduleId", oldDataCenter.getId())) >0 ){
			throw new MyException("000035");
		}
		
		if(articleService.getCount(Tools.getMap("moduleId", oldDataCenter.getId(), "type", ArticleType.DICTIONARY.name())) >0 ){
			throw new MyException("000036");
		}
		
		
		
		
		cacheService.delObj(Const.CACHE_MODULE+module.getId());
		moduleService.delete(module);
		return new JsonResult(1,null);
	}
	
	@RequestMapping("/changeSequence.do")
	@ResponseBody
	@AuthPassport
	public JsonResult changeSequence(@RequestParam String id,@RequestParam String changeId) throws MyException {
		Module change = moduleService.get(changeId);
		Module model = moduleService.get(id);
		
		hasPermission(cacheService.getProject( change.getProjectId() ), modModule);
		hasPermission(cacheService.getProject( model.getProjectId() ), modModule);
		
		int modelSequence = model.getSequence();
		model.setSequence(change.getSequence());
		change.setSequence(modelSequence);
		
		moduleService.update(model);
		moduleService.update(change);

		return new JsonResult(1, null);
	}

}
