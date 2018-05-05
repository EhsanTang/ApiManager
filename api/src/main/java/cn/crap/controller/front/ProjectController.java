package cn.crap.controller.front;

import cn.crap.adapter.ProjectAdapter;
import cn.crap.service.custom.CustomProjectService;
import cn.crap.utils.LoginUserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.dto.LoginInfoDto;
import cn.crap.enumer.ProjectStatus;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.model.mybatis.Project;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;

import java.util.List;
@Controller("forntProjectController")
@RequestMapping("/front/project")
public class ProjectController extends BaseController{
	@Autowired
	private CustomProjectService customProjectService;
	
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(Integer currentPage, @RequestParam(defaultValue="false") boolean myself, String name) throws MyException{
		
		Page page= new Page(currentPage);
		LoginInfoDto user =  LoginUserHelper.tryGetUser();
		
		if(user != null && myself){
			page.setAllRow(customProjectService.countProjectByUserIdName(user.getId(), name));
			return new JsonResult(1, customProjectService.pageProjectByUserIdName(user.getId(), name, page), page);
		}
		// 未登陆用户，查看推荐的项目
		else{
			page.setAllRow(customProjectService.countProjectByStatusName(ProjectStatus.RECOMMEND.getStatus(), name));
			List<Project> projects = customProjectService.pageProjectByStatusName(ProjectStatus.RECOMMEND.getStatus(), name, page);

			return new JsonResult(1, ProjectAdapter.getDto(projects, null), page);
		}
		
	}
}
