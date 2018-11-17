package cn.crap.controller.user;

import cn.crap.adapter.Adapter;
import cn.crap.adapter.ModuleAdapter;
import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.ModuleDto;
import cn.crap.enu.ArticleType;
import cn.crap.enu.LogType;
import cn.crap.enu.MyError;
import cn.crap.enu.SettingEnum;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.*;
import cn.crap.query.ArticleQuery;
import cn.crap.query.InterfaceQuery;
import cn.crap.query.ModuleQuery;
import cn.crap.query.SourceQuery;
import cn.crap.service.*;
import cn.crap.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/user/module")
public class ModuleController extends BaseController implements ILogConst{

	@Autowired
	private RoleService roleService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ProjectUserService projectUserService;
	@Autowired
	private SourceService sourceService;
	@Autowired
	private UserService userService;
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private LogService logService;
	@Autowired
	private InterfaceService interfaceService;
	
	
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@ModelAttribute ModuleQuery query) throws MyException{
			throwExceptionWhenIsNull(query.getProjectId(), "projectId");
			Page page= new Page(query);
			Project project = projectCache.get(query.getProjectId());
			checkPermission(project, READ);

            List<ModuleDto> moduleDtos = ModuleAdapter.getDto(moduleService.query(query), project);
            page.setAllRow(moduleService.count(query));
            return new JsonResult().data(moduleDtos).page(page);
		}

	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult detail(String id, String projectId) throws MyException{
		Module module;
        Project project;
		Interface templeteInterface = null;
		if(id != null){
			module= moduleService.getById(id);
			project = projectCache.get(module.getProjectId());
			checkPermission(project, READ);

            if (module.getTemplateId() != null) {
                templeteInterface = interfaceService.getById(module.getTemplateId());
            }
		}else{
		    project = projectCache.get(projectId);
			checkPermission(project, READ);
			module=new Module();
			module.setStatus(Byte.valueOf("1"));
			module.setProjectId(projectId);
		}
		return new JsonResult(1, ModuleAdapter.getDto(module, project, templeteInterface));
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
		LoginInfoDto user = LoginUserHelper.getUser();

		checkCrapDebug(user.getId(), moduleDto.getProjectId());

		// 系统数据，不允许修改名称等
		String id = moduleDto.getId();

		if (MyString.isEmpty(moduleDto.getCategory())){
            moduleDto.setCategory("默认分类");
		}

        Module module = ModuleAdapter.getModel(moduleDto);
		if(id != null){
			checkPermission(moduleDto.getProjectId(), MOD_MODULE);
            moduleService.update(module, true);
            // 更新该模块下的所有接口的fullUrl
			interfaceService.updateFullUrlByModuleId(module.getUrl(), id);
		}else{
			Integer maxModule = settingCache.getInteger(SettingEnum.MAX_MODULE);
			Integer totalModuleNum = moduleService.count(new ModuleQuery().setProjectId(moduleDto.getProjectId()));
			if (totalModuleNum > maxModule){
                throw new MyException(MyError.E000071, maxModule + "");
            }
			module.setProjectId(moduleDto.getProjectId());
			checkPermission(module.getProjectId(), ADD_MODULE);
			module.setUserId(LoginUserHelper.getUser().getId());
			module.setVersion(0);
			moduleService.insert(module);
		}
		moduleCache.del(module.getId());
		
		/**
		 * 刷新用户权限
		 */
        user = LoginUserHelper.getUser();
		// 将用户信息存入缓存
		userCache.add(user.getId(), new LoginInfoDto(userService.getById(user.getId()), roleService, projectService, projectUserService));
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
		checkPermission(projectCache.get( inter.getProjectId() ), MOD_MODULE);
		if (module == null){
			throw new MyException(MyError.E000073);
		}
		module.setTemplateId( inter.getIsTemplate() ? "-1" : inter.getId() );
		moduleService.update(module);
		
		interfaceService.deleteTemplateByModuleId(module.getId());
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
		if(module.getId().equals("web")) {
            throw new MyException(MyError.E000009);
        }

        Module dbModule = moduleCache.get(module.getId());
        LoginInfoDto user = LoginUserHelper.getUser();
        checkCrapDebug(user.getId(), dbModule.getProjectId());
		checkPermission(projectCache.get( dbModule.getProjectId() ), DEL_MODULE);
		
		if(interfaceService.count(new InterfaceQuery().setModuleId(dbModule.getId())) >0 ){
			throw new MyException(MyError.E000024);
		}
		
		if(articleService.count(new ArticleQuery().setModuleId(dbModule.getId()).setType(ArticleType.ARTICLE.name())) >0 ){
			throw new MyException(MyError.E000034);
		}
		
		if(sourceService.count(new SourceQuery().setModuleId(dbModule.getId())) >0 ){
			throw new MyException(MyError.E000035);
		}
		
		if(articleService.count(new ArticleQuery().setModuleId(dbModule.getId()).setType(ArticleType.DICTIONARY.name())) >0 ){
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
		
		checkPermission(projectCache.get( change.getProjectId() ), MOD_MODULE);
		checkPermission(projectCache.get( model.getProjectId() ), MOD_MODULE);
		
		int modelSequence = model.getSequence();
		model.setSequence(change.getSequence());
		change.setSequence(modelSequence);
		
		moduleService.update(model);
		moduleService.update(change);

		return new JsonResult(1, null);
	}

}
