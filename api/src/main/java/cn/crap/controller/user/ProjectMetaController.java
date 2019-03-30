package cn.crap.controller.user;

import cn.crap.adapter.ProjectMetaAdapter;
import cn.crap.dto.ProjectMetaDTO;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.ProjectMetaPO;
import cn.crap.query.ProjectMetaQuery;
import cn.crap.service.ProjectMetaService;
import cn.crap.service.ProjectService;
import cn.crap.service.UserService;
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
	private UserService userService;
	@Autowired
	private ProjectMetaService projectMetaService;

	@RequestMapping("/list.do")
	@ResponseBody
    @AuthPassport
	public JsonResult list(@ModelAttribute ProjectMetaQuery query) throws MyException{
		Assert.notNull(query.getProjectId());
        Page page= new Page(query);
        checkPermission( projectCache.get(query.getProjectId()));

		List<ProjectMetaPO> projectMetas = projectMetaService.select(query, page);
        page.setAllRow(projectMetaService.count(query));

        List<ProjectMetaDTO> dto = ProjectMetaAdapter.getDto(projectMetas);
        return new JsonResult(1, dto, page);
	}	
	
	@RequestMapping("/detail.do")
	@ResponseBody
    @AuthPassport
	public JsonResult detail(@RequestParam String id) throws MyException{
		ProjectMetaPO projectMeta = projectMetaService.get(id);
		checkPermission(projectMeta.getProjectId());
        ProjectMetaDTO projectMetaDto = ProjectMetaAdapter.getDto(projectMeta);
		return JsonResult.of().success().data(projectMetaDto);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
    @AuthPassport
	public JsonResult addOrUpdate(@ModelAttribute ProjectMetaDTO projectMeta) throws Exception{
	    Assert.notNull(projectMeta.getId());
        checkPermission(projectMeta.getId());

        ProjectMetaPO model = ProjectMetaAdapter.getModel(projectMeta);
        projectMetaService.update(model);
		return new JsonResult(1,projectMeta);
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
    @AuthPassport
	public JsonResult delete(@RequestParam String id) throws Exception{
		ProjectMetaPO projectMeta = projectMetaService.get(id);
		checkPermission(projectMeta.getProjectId());
		projectMetaService.delete(projectMeta.getId());
		return new JsonResult(1,null);
	}
}
