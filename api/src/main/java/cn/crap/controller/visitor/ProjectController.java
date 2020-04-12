package cn.crap.controller.visitor;

import cn.crap.adapter.ProjectAdapter;
import cn.crap.dto.ProjectDTO;
import cn.crap.enu.ProjectShowType;
import cn.crap.model.ProjectPO;
import cn.crap.query.ProjectQuery;
import cn.crap.service.ProjectService;
import cn.crap.utils.LoginUserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.dto.LoginInfoDto;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.utils.Page;

import java.util.List;
@Controller("forntProjectController")
@RequestMapping("/visitor/project")
public class ProjectController extends BaseController{
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@ModelAttribute ProjectQuery query, @RequestParam(defaultValue="3") Integer projectShowType) throws MyException{
		
		Page page= new Page(query);
		LoginInfoDto user =  LoginUserHelper.getUser();
		String userId = user.getId();
		List<ProjectPO> models = null;
		// 我创建 & 加入的项目
		if (ProjectShowType.CREATE_JOIN.getType() == projectShowType) {
			page.setAllRow(projectService.count(userId, false, query.getName()));
			models = projectService.query(userId, false, query.getName(), page);
		}

		// 我加入的项目
		else if (ProjectShowType.JOIN.getType() == projectShowType) {
			page.setAllRow(projectService.count(userId, true, query.getName()));
			models = projectService.query(userId, true, query.getName(), page);
		}

        List<ProjectDTO> projectDtos = ProjectAdapter.getDTOS(models, null);
        page.setAllRow(projectService.count(query));

        return new JsonResult().data(projectDtos).page(page);
		
	}
}
