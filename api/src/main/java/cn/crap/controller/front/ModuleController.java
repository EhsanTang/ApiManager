package cn.crap.controller.front;

import cn.crap.adapter.ModuleAdapter;
import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.ModuleDto;
import cn.crap.enumer.ProjectType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.model.mybatis.ModuleCriteria;
import cn.crap.model.mybatis.Project;
import cn.crap.service.imp.MybatisModuleService;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;
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
	private MybatisModuleService moduleService;

	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@RequestParam String projectId,String password, String visitCode) throws MyException{
		if( MyString.isEmpty(projectId) ){
			throw new MyException("000020");
		}
		
		// 如果是私有项目，必须登录才能访问，公开项目需要查看是否需要密码
		Project project = projectCache.get(projectId);
		isPrivateProject(password, visitCode, project);

		ModuleCriteria example = new ModuleCriteria();
		example.createCriteria().andProjectIdEqualTo(projectId);

		List<ModuleDto> moduleDtoList = ModuleAdapter.getDto(moduleService.selectByExample(example));

		return new JsonResult(1, moduleDtoList, null,
				Tools.getMap("crumbs", Tools.getCrumbs( project.getName(), "void"),  "project", project) );
	}	
	@RequestMapping("/menu.do")
	@ResponseBody
	public JsonResult menu(@RequestParam String projectId) throws MyException{
		if( MyString.isEmpty(projectId) ){
			throw new MyException("000020");
		}
		// 如果是私有项目，必须登录才能访问，公开项目需要查看是否需要密码
		Project project = projectCache.get(projectId);
		if(project.getType() == ProjectType.PRIVATE.getType()){
			LoginInfoDto user = Tools.getUser();
			if (user == null) {
				throw new MyException("000041");
			}
			// 最高管理员修改项目
			// 自己的项目
			if ( ("," + user.getRoleId()).indexOf("," + Const.SUPER + ",") < 0 && !user.getId().equals(project.getUserId())
					&& user.getProjects().get(project.getId()) == null) {
				throw new MyException("000042");
			}
		}
		
		Project returnProject = new Project();
		BeanUtils.copyProperties(project, returnProject);
		returnProject.setPassword("");

		ModuleCriteria example = new ModuleCriteria();
		example.createCriteria().andProjectIdEqualTo(projectId);

		List<ModuleDto> moduleDtoList = ModuleAdapter.getDto(moduleService.selectByExample(example));
		return new JsonResult(1, moduleDtoList, null, Tools.getMap("project",  returnProject) );
	}	
}
