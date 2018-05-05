package cn.crap.controller.user;

import cn.crap.adapter.ProjectAdapter;
import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.ProjectDto;
import cn.crap.enumer.*;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.mybatis.Project;
import cn.crap.model.mybatis.ProjectCriteria;
import cn.crap.service.ISearchService;
import cn.crap.service.custom.CustomErrorService;
import cn.crap.service.custom.CustomModuleService;
import cn.crap.service.custom.CustomProjectService;
import cn.crap.service.custom.CustomProjectUserService;
import cn.crap.service.mybatis.*;
import cn.crap.beans.Config;
import cn.crap.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user/project")
public class ProjectController extends BaseController {
@Autowired
private ProjectService projectService;
@Autowired
private ISearchService luceneService;
@Autowired
private CustomErrorService customErrorService;
@Autowired
private UserService userService;
@Autowired
private CustomModuleService customModuleService;
@Autowired
private CustomProjectUserService customProjectUserService;
	@Autowired
	private CustomProjectService customProjectService;
	@Autowired
	private ProjectUserService projectUserService;
	@Autowired
	private RoleService roleService;

	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(@ModelAttribute Project project, Integer currentPage,
						   @RequestParam(defaultValue="false") boolean myself) throws MyException{
		Page page= new Page(currentPage);
		LoginInfoDto user = LoginUserHelper.getUser();
		String userId = user.getId();
		List<Project> models = null;
		List<ProjectDto> dtos = null;

        // 普通用户，管理员我的项目菜单只能查看自己的项目
		if( user.getType() == UserType.USER.getType() || myself){
			page.setAllRow(customProjectService.countProjectByUserIdName(userId, project.getName()));
			models = customProjectService.pageProjectByUserIdName(userId, project.getName(), page);
		}else{
			ProjectCriteria example = new ProjectCriteria();
			ProjectCriteria.Criteria criteria = example.createCriteria();
			if (project.getName() != null){
				criteria.andNameLike("%" + project.getName() +"%");
			}
			example.setLimitStart(page.getStart());
			example.setMaxResults(page.getSize());
			example.setOrderByClause(TableField.SORT.SEQUENCE_DESC);
			page.setAllRow(projectService.countByExample(example));
			models = projectService.selectByExample(example);
		}

		dtos = ProjectAdapter.getDto(models, userService);
		return new JsonResult(1,dtos, page);
	}

	@RequestMapping("/detail.do")
	@ResponseBody
	@AuthPassport
	public JsonResult detail(@ModelAttribute Project project) throws MyException{
		Project model;
		if(project.getId() != null){
			model= projectCache.get(project.getId());
			checkUserPermissionByProject(model);
		}else{
			model=new Project();
		}
		return new JsonResult(1,ProjectAdapter.getDto(model, null));
	}


	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute ProjectDto project) throws Exception{
		String userId = LoginUserHelper.getUser().getId();
		String projectId = project.getId();

		// 私有项目不能建立索引
		if (project.getType() == ProjectType.PRIVATE.getType()){
			project.setLuceneSearch(LuceneSearchType.No.getByteValue());
		}

		// 修改
		if(!MyString.isEmpty(projectId)){
            Project dbProject = projectCache.get(projectId);
			checkUserPermissionByProject(dbProject);

			// 普通用户不能推荐项目，将项目类型修改为原有类型
			if( LoginUserHelper.getUser().getType() == UserType.USER.getType()){
				project.setStatus(null);
			}
			customProjectService.update(ProjectAdapter.getModel(project));

            // 需要重建索引
            projectCache.del(projectId);
            if (!project.getType().equals(dbProject.getType()) || !project.getLuceneSearch().equals(dbProject.getLuceneSearch())){
                luceneService.rebuildByProjectId(projectId);
            }
		}
		// 新增
		else{
			Project model = ProjectAdapter.getModel(project);
			model.setUserId(userId);
			model.setPassword(project.getPassword());
			// 普通用户不能推荐项目
			if( LoginUserHelper.getUser().getType() == UserType.USER.getType()){
				project.setStatus(Byte.valueOf(ProjectStatus.COMMON.getStatus()+""));
			}
			projectService.insert(model);
		}

		// 清楚缓存
		projectCache.del(projectId);

		// 刷新用户权限 将用户信息存入缓存
		userCache.add(userId, new LoginInfoDto(userService.getById(userId), roleService, customProjectService, projectUserService));
		return new JsonResult(1,project);
	}


	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(@ModelAttribute Project project) throws Exception{
		// 系统数据，不允许删除
		if(project.getId().equals("web"))
			throw new MyException(MyError.E000009);


		Project model= projectCache.get(project.getId());
		checkUserPermissionByProject(model);


		// 只有子模块数量为0，才允许删除项目
		if(customModuleService.countByProjectId(model.getId()) > 0){
			throw new MyException(MyError.E000023);
		}

		// 只有错误码数量为0，才允许删除项目
		if(customErrorService.countByProjectId(model.getId()) > 0){
			throw new MyException(MyError.E000033);
		}

		// 只有项目成员数量为0，才允许删除项目
		if(customProjectUserService.countByProjectId(model.getId())>0){
			throw new MyException(MyError.E000038);
		}

		projectCache.del(project.getId());
		customProjectService.delete(project.getId());
		return new JsonResult(1,null);
	}

	@RequestMapping("/changeSequence.do")
	@ResponseBody
	@AuthPassport
	public JsonResult changeSequence(@RequestParam String id,@RequestParam String changeId) throws MyException {
		Project change = projectCache.get(changeId);
		Project model = projectCache.get(id);

		checkUserPermissionByProject(change);
		checkUserPermissionByProject(model);

		int modelSequence = model.getSequence();
		model.setSequence(change.getSequence());
		change.setSequence(modelSequence);

		projectService.update(model);
		projectService.update(change);

		return new JsonResult(1, null);
	}

	@ResponseBody
	@RequestMapping("/rebuildIndex.do")
	@AuthPassport
	public JsonResult rebuildIndex(@RequestParam String projectId) throws Exception {
		Project model= projectCache.get(projectId);
		checkUserPermissionByProject(model);
		return new JsonResult(1, luceneService.rebuildByProjectId(projectId));
	}
}
