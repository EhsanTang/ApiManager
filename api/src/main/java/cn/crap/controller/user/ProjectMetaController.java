package cn.crap.controller.user;

import cn.crap.adapter.ProjectMetaAdapter;
import cn.crap.dto.ProjectMetaDTO;
import cn.crap.enu.ArticleStatus;
import cn.crap.enu.ProjectPermissionEnum;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.ModulePO;
import cn.crap.model.ProjectPO;
import cn.crap.model.ProjectMetaPO;
import cn.crap.query.ProjectMetaQuery;
import cn.crap.service.ProjectMetaService;
import cn.crap.service.ProjectService;
import cn.crap.service.UserService;
import cn.crap.service.tool.ProjectCache;
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
@RequestMapping("/user/projectMeta")
public class ProjectMetaController extends BaseController{

	@Autowired
	private ProjectService projectService;
    @Autowired
    private ProjectCache projectCache;
	@Autowired
	private UserService userService;
	@Autowired
	private ProjectMetaService projectMetaService;

	@RequestMapping("/list.do")
	@ResponseBody
    @AuthPassport
	public JsonResult list(@ModelAttribute ProjectMetaQuery query) throws MyException{
		Assert.notNull(query.getProjectId());
        checkPermission( projectCache.get(query.getProjectId()), ProjectPermissionEnum.READ);

		List<ProjectMetaPO> projectMetas = projectMetaService.select(query);
        Page page= new Page(query);
        page.setAllRow(projectMetaService.count(query));

        List<ProjectMetaDTO> dto = ProjectMetaAdapter.getDto(projectMetas);
        return new JsonResult(1, dto, page);
	}	
	
	@RequestMapping("/detail.do")
	@ResponseBody
    @AuthPassport
	public JsonResult detail(String id, @ModelAttribute ProjectMetaQuery query) throws MyException{
        ProjectMetaPO projectMeta;
        ProjectPO project;
        ModulePO module = null;
        if (id != null) {
            projectMeta = projectMetaService.get(id);
            project = projectCache.get(projectMeta.getProjectId());
            module = moduleCache.get(projectMeta.getModuleId());
        } else {
            project = projectCache.get(query.getProjectId());

            projectMeta = new ProjectMetaPO();
            projectMeta.setProjectId(query.getProjectId());
            projectMeta.setType(query.getType());
            projectMeta.setSequence(System.currentTimeMillis());
        }

		checkPermission(project, ProjectPermissionEnum.READ);
        ProjectMetaDTO projectMetaDto = ProjectMetaAdapter.getDto(projectMeta, module);
		return JsonResult.of().success().data(projectMetaDto);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
    @AuthPassport
	public JsonResult addOrUpdate(@ModelAttribute ProjectMetaDTO projectMeta) throws Exception{
	    Assert.notNull(projectMeta.getProjectId());

        ProjectMetaPO model = ProjectMetaAdapter.getModel(projectMeta);
        if (projectMeta.getId() != null) {
            ProjectMetaPO dbProjectMetaPO = projectMetaService.get(projectMeta.getId());
            checkPermission(dbProjectMetaPO.getProjectId(), ProjectPermissionEnum.MOD_ENV);
            projectMetaService.update(model);
        } else {
            model.setStatus(ArticleStatus.COMMON.getStatus());
            checkPermission(projectMeta.getProjectId(), ProjectPermissionEnum.ADD_ENV);
            projectMetaService.insert(model);
        }

		return new JsonResult(1,projectMeta);
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
    @AuthPassport
	public JsonResult delete(@RequestParam String id) throws Exception{
		ProjectMetaPO projectMeta = projectMetaService.get(id);
		checkPermission(projectMeta.getProjectId(), ProjectPermissionEnum.DEL_ENV);
		projectMetaService.delete(projectMeta.getId());
		return new JsonResult(1,null);
	}
}
