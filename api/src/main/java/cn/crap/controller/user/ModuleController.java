package cn.crap.controller.user;

import cn.crap.adapter.Adapter;
import cn.crap.adapter.ModuleAdapter;
import cn.crap.dto.ModuleDTO;
import cn.crap.enu.LogType;
import cn.crap.enu.MyError;
import cn.crap.enu.ProjectPermissionEnum;
import cn.crap.enu.SettingEnum;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.*;
import cn.crap.query.ModuleQuery;
import cn.crap.service.InterfaceService;
import cn.crap.service.LogService;
import cn.crap.service.ModuleService;
import cn.crap.utils.ILogConst;
import cn.crap.utils.LoginUserHelper;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
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
	private ModuleService moduleService;
	@Autowired
	private LogService logService;
	@Autowired
	private InterfaceService interfaceService;

	
	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(@ModelAttribute ModuleQuery query) throws MyException{
			throwExceptionWhenIsNull(query.getProjectId(), "projectId");
			Page page= new Page(query);
			ProjectPO project = projectCache.get(query.getProjectId());
			checkPermission(project, ProjectPermissionEnum.READ);

            List<ModuleDTO> moduleDtos = ModuleAdapter.getDto(moduleService.select(query), project);
            page.setAllRow(moduleService.count(query));
            return new JsonResult().data(moduleDtos).page(page);
		}

	@RequestMapping("/detail.do")
	@ResponseBody
	@AuthPassport
	public JsonResult detail(String id, String projectId) throws MyException{
		ModulePO module;
        ProjectPO project;
		Interface templeteInterface = null;
		if(id != null){
			module= moduleService.get(id);
			project = projectCache.get(module.getProjectId());
			checkPermission(project, ProjectPermissionEnum.READ);

            if (module.getTemplateId() != null) {
                templeteInterface = interfaceService.getById(module.getTemplateId());
            }
		}else{
		    project = projectCache.get(projectId);
			checkPermission(project, ProjectPermissionEnum.READ);
			module=new ModulePO();
			module.setStatus(Byte.valueOf("1"));
			module.setProjectId(projectId);
			module.setSequence(System.currentTimeMillis());
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
	@AuthPassport
	public JsonResult addOrUpdate(@ModelAttribute ModuleDTO moduleDto) throws Exception{
		Assert.notNull(moduleDto.getProjectId());
		// 系统数据，不允许修改名称等
		String id = moduleDto.getId();

		if (MyString.isEmpty(moduleDto.getCategory())){
            moduleDto.setCategory("默认分类");
		}

        ModulePO module = ModuleAdapter.getModel(moduleDto);
		ProjectPO projectPO = projectCache.get(moduleDto.getProjectId());
		if(id != null){
			checkPermission(projectPO, ProjectPermissionEnum.MOD_MODULE);
            moduleService.update(module, true);
            // 更新该模块下的所有接口的fullUrl
			interfaceService.updateFullUrlByModuleId(module.getUrl(), id);
		}else{
			Integer maxModule = settingCache.getInt(SettingEnum.MAX_MODULE);
			Integer totalModuleNum = moduleService.count(new ModuleQuery().setProjectId(moduleDto.getProjectId()));
			if (totalModuleNum > maxModule){
                throw new MyException(MyError.E000071, maxModule + "");
            }
			module.setProjectId(moduleDto.getProjectId());
			checkPermission(projectPO, ProjectPermissionEnum.ADD_MODULE);
			module.setUserId(projectPO.getUserId());
			module.setVersionNum(0);
			moduleService.insert(module);
		}
		moduleCache.del(module.getId());
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
	@AuthPassport
	public JsonResult setTemplate(String id) throws Exception{
		InterfaceWithBLOBs inter = interfaceService.getById(id);
		
		ModulePO module = moduleService.get(inter.getModuleId());
		checkPermission(projectCache.get( inter.getProjectId() ), ProjectPermissionEnum.MOD_MODULE);
		if (module == null){
			throw new MyException(MyError.E000073);
		}
		module.setTemplateId( inter.getIsTemplate() ? "-1" : inter.getId() );
		moduleService.update(module);
		
		interfaceService.deleteTemplateByModuleId(module.getId());
		if(!inter.getIsTemplate()){
			inter.setIsTemplate(true);
			interfaceService.update(inter);
		}
		
		moduleCache.del(module.getId());
		return new JsonResult(1,module);
	}
	
	
	
	@RequestMapping("/delete.do")
	@ResponseBody
	@AuthPassport
	public JsonResult delete(@ModelAttribute ModulePO module) throws Exception{
		// 系统数据，不允许删除
		if(module.getId().equals("web")) {
            throw new MyException(MyError.E000009);
        }

        ModulePO dbModule = moduleCache.get(module.getId());
		checkPermission(projectCache.get( dbModule.getProjectId() ), ProjectPermissionEnum.DEL_MODULE);

		moduleService.delete(module.getId());
		moduleCache.del(module.getId());
		Log log = Adapter.getLog(dbModule.getId(), L_MODULE_CHINESE, dbModule.getName(), LogType.DELTET, dbModule.getClass(), dbModule);
		logService.insert(log);


		return new JsonResult(1,null);
	}
	
	@RequestMapping("/changeSequence.do")
	@ResponseBody
	@AuthPassport
	public JsonResult changeSequence(@RequestParam String id,@RequestParam String changeId) throws MyException {
		ModulePO change = moduleService.get(changeId);
		ModulePO model = moduleService.get(id);
		
		checkPermission(projectCache.get( change.getProjectId() ), ProjectPermissionEnum.MOD_MODULE);
		checkPermission(projectCache.get( model.getProjectId() ), ProjectPermissionEnum.MOD_MODULE);
		
		long modelSequence = model.getSequence();
		model.setSequence(change.getSequence());
		change.setSequence(modelSequence);
		
		moduleService.update(model);
		moduleService.update(change);

		return new JsonResult(1, null);
	}

}
