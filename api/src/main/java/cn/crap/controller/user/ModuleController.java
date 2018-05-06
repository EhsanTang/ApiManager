package cn.crap.controller.user;

import cn.crap.adapter.Adapter;
import cn.crap.adapter.ModuleAdapter;
import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.ModuleDto;
import cn.crap.enumer.ArticleType;
import cn.crap.enumer.LogType;
import cn.crap.enumer.MyError;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.mybatis.InterfaceWithBLOBs;
import cn.crap.model.mybatis.Log;
import cn.crap.model.mybatis.Module;
import cn.crap.model.mybatis.Project;
import cn.crap.service.custom.*;
import cn.crap.service.mybatis.*;
import cn.crap.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user/module")
public class ModuleController extends BaseController implements ILogConst{

	@Autowired
	private ModuleService moduleService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private CustomArticleService articleService;
	@Autowired
	private InterfaceService interfaceService;
	@Autowired
	private CustomProjectService customProjectService;
	@Autowired
	private ProjectUserService projectUserService;
	@Autowired
	private CustomSourceService customSourceService;
	@Autowired
	private UserService userService;
	@Autowired
	private CustomModuleService customModuleService;
	@Autowired
	private LogService logService;
	@Autowired
	private CustomInterfaceService customInterfaceService;
	
	
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@RequestParam String projectId, @RequestParam(defaultValue="1") int currentPage, String name) throws MyException{
			Page<Module> page= new Page(currentPage);
			checkUserPermissionByProject(projectId, VIEW);

			page = customModuleService.queryByProjectId(projectId, name, page);
			return new JsonResult(1, ModuleAdapter.getDto(page.getList()), page);
		}

	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult detail(String id, String projectId) throws MyException{
		Module module;
        Project project;
		if(id != null){
			module= moduleService.getById(id);
			project = projectCache.get(module.getProjectId());
			checkUserPermissionByProject(project, VIEW);
		}else{
		    project = projectCache.get(projectId);
			checkUserPermissionByProject(project, VIEW);
			module=new Module();
			module.setStatus(Byte.valueOf("1"));
			module.setProjectId(projectId);
		}
		return new JsonResult(1, ModuleAdapter.getDto(module, project));
	}

	/**
	 * 修改模块名等，需要手动重建索引
	 * @param moduleDto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute ModuleDto moduleDto) throws Exception{
		Assert.notNull(moduleDto.getProjectId());

		// 系统数据，不允许修改名称等
		String id = moduleDto.getId();
		if(id != null) {
			moduleDto.setCanDelete(null);
			moduleDto.setProjectId(null);
			moduleDto.setStatus(null);
		}

		if (MyString.isEmpty(moduleDto.getCategory())){
            moduleDto.setCategory("默认分类");
		}

        Module module = ModuleAdapter.getModel(moduleDto);
		if(id != null){
			checkUserPermissionByModuleId(id, MOD_MODULE);

            Module dbModule = moduleService.getById(module.getId());
            Log log = Adapter.getLog(dbModule.getId(), L_MODULE_CHINESE, dbModule.getName(), LogType.UPDATE, dbModule.getClass(), dbModule);
            logService.insert(log);

            moduleService.update(module);
            // 更新该模块下的所有接口的fullUrl
			customInterfaceService.updateFullUrlByModuleId(module.getUrl(), id);
		}else{
			module.setProjectId(moduleDto.getProjectId());
			checkUserPermissionByProject(module.getProjectId(), ADD_MODULE);
			module.setUserId(LoginUserHelper.getUser().getId());
			module.setVersion(0);
			moduleService.insert(module);
		}
		moduleCache.del(module.getId());
		
		/**
		 * 刷新用户权限
		 */
		LoginInfoDto user = LoginUserHelper.getUser();
		// 将用户信息存入缓存
		userCache.add(user.getId(), new LoginInfoDto(userService.getById(user.getId()), roleService, customProjectService, projectUserService));
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
		InterfaceWithBLOBs inter = interfaceService.getById(id);
		
		Module module = moduleService.getById(inter.getModuleId());
		checkUserPermissionByProject(projectCache.get( module.getProjectId() ), MOD_MODULE);
		
		module.setTemplateId( inter.getIsTemplate() ? "-1" : inter.getId() );
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
			throw new MyException(MyError.E000009);
				
		Module dbModule = moduleCache.get(module.getId());
		checkUserPermissionByProject(projectCache.get( dbModule.getProjectId() ), DEL_MODULE);
		
		if(customInterfaceService.countByModuleId(dbModule.getId()) >0 ){
			throw new MyException(MyError.E000024);
		}
		
		if(articleService.countByModuleIdAndType(dbModule.getId(), ArticleType.ARTICLE.name()) >0 ){
			throw new MyException(MyError.E000034);
		}
		
		if(customSourceService.countByModuleId(dbModule.getId()) >0 ){
			throw new MyException(MyError.E000035);
		}
		
		if(articleService.countByModuleIdAndType(dbModule.getId(),  ArticleType.DICTIONARY.name()) >0 ){
			throw new MyException(MyError.E000036);
		}

        Log log = Adapter.getLog(dbModule.getId(), L_MODULE_CHINESE, dbModule.getName(), LogType.DELTET, dbModule.getClass(), dbModule);
        logService.insert(log);

		moduleCache.del(module.getId());
		moduleService.delete(module.getId());

		return new JsonResult(1,null);
	}
	
	@RequestMapping("/changeSequence.do")
	@ResponseBody
	@AuthPassport
	public JsonResult changeSequence(@RequestParam String id,@RequestParam String changeId) throws MyException {
		Module change = moduleService.getById(changeId);
		Module model = moduleService.getById(id);
		
		checkUserPermissionByProject(projectCache.get( change.getProjectId() ), MOD_MODULE);
		checkUserPermissionByProject(projectCache.get( model.getProjectId() ), MOD_MODULE);
		
		int modelSequence = model.getSequence();
		model.setSequence(change.getSequence());
		change.setSequence(modelSequence);
		
		moduleService.update(model);
		moduleService.update(change);

		return new JsonResult(1, null);
	}

}
