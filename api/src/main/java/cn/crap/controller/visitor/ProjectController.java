package cn.crap.controller.visitor;

import cn.crap.adapter.ProjectAdapter;
import cn.crap.dto.ProjectDto;
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
import cn.crap.enumer.ProjectStatus;
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
	public JsonResult list(@ModelAttribute ProjectQuery query, @RequestParam(defaultValue="false") boolean myself) throws MyException{
		
		Page page= new Page(query);
		LoginInfoDto user =  LoginUserHelper.tryGetUser();

		if(user != null && myself){
            query.setUserId(user.getId());
		}else{
            // 未登陆用户，查看推荐的项目
            query.setStatus(ProjectStatus.RECOMMEND.getStatus());
		}

        List<ProjectDto> projectDtos = ProjectAdapter.getDto(projectService.query(query), null);
        page.setAllRow(projectService.count(query));

        return new JsonResult().data(projectDtos).page(page);
		
	}
}
