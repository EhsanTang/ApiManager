package cn.crap.controller.front;

import cn.crap.adapter.ModuleAdapter;
import cn.crap.adapter.ProjectAdapter;
import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.ModuleDto;
import cn.crap.dto.ProjectDto;
import cn.crap.enumer.MyError;
import cn.crap.enumer.ProjectType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.model.mybatis.Module;
import cn.crap.model.mybatis.ModuleCriteria;
import cn.crap.model.mybatis.Project;
import cn.crap.service.custom.CustomModuleService;
import cn.crap.service.mybatis.ModuleService;
import cn.crap.utils.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller("frontModuleController")
@RequestMapping("/front/module")
public class ModuleController extends BaseController{

	@Autowired
	private ModuleService moduleService;
    @Autowired
    private CustomModuleService customModuleService;

	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(String projectId, String password, String visitCode,
                           @RequestParam(defaultValue="1") int currentPage) throws MyException{
        throwExceptionWhenIsNull(projectId, "projectId");

        // 如果是私有项目，必须登录才能访问，公开项目需要查看是否需要密码
		Project project = projectCache.get(projectId);
		checkFrontPermission(password, visitCode, project);

        Page<Module> page= new Page(currentPage);
        page = customModuleService.queryByProjectId(projectId, null, page);

		List<ModuleDto> moduleDtoList = ModuleAdapter.getDto(page.getList());
		page.setList(null);

		return new JsonResult(1, moduleDtoList, page,
				Tools.getMap("crumbs", Tools.getCrumbs( project.getName(), "void"),
						"project", ProjectAdapter.getDto(project, null)) );
	}

	@RequestMapping("/menu.do")
	@ResponseBody
	public JsonResult menu(@RequestParam String projectId) throws MyException{
		throwExceptionWhenIsNull(projectId, "projectId");

		// 如果是私有项目，必须登录才能访问，公开项目需要查看是否需要密码
		Project project = projectCache.get(projectId);
		if(project.getType() == ProjectType.PRIVATE.getType()){
			LoginInfoDto user = LoginUserHelper.getUser(MyError.E000041);

			// 最高管理员修改项目
			// 自己的项目
			if ( ("," + user.getRoleId()).indexOf("," + IConst.C_SUPER + ",") < 0 && !user.getId().equals(project.getUserId())
					&& user.getProjects().get(project.getId()) == null) {
				throw new MyException(MyError.E000042);
			}
		}
		
		ModuleCriteria example = new ModuleCriteria();
		example.createCriteria().andProjectIdEqualTo(projectId);
        example.setLimitStart(0);
        example.setMaxResults(10);
        example.setOrderByClause(TableField.SORT.SEQUENCE_DESC);

		List<ModuleDto> moduleDtoList = ModuleAdapter.getDto(moduleService.selectByExample(example));
		return new JsonResult(1, moduleDtoList, null, Tools.getMap("project",  ProjectAdapter.getDto(project, null)) );
	}	
}
