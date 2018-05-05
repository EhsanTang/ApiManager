package cn.crap.controller.front;

import cn.crap.adapter.ErrorAdapter;
import cn.crap.dto.ErrorDto;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.model.mybatis.Error;
import cn.crap.model.mybatis.Project;
import cn.crap.service.custom.CustomErrorService;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller("frontErrorController")
@RequestMapping("/front/error")
public class ErrorController extends BaseController{
	@Autowired
	private CustomErrorService customErrorService;

	/**
	 * 前端错误码列表，只查询公开的顶级项目错误码（错误码定义在顶级项目中）
	 * 非公开的项目，错误码只能在项目主页中查看
	 * @param currentPage
	 * @return
	 * @throws MyException 
	 */
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(String errorCode,String errorMsg, @RequestParam String projectId,
						   @RequestParam(defaultValue = "1") Integer currentPage, String password, String visitCode) throws MyException{
		Project project = projectCache.get(projectId);

		checkFrontPermission(password, visitCode, project);
		
		Page page= new Page(20, currentPage);

		List<Error> models = customErrorService.queryByProjectId(projectId, errorCode, errorMsg, page);
		List<ErrorDto> dtos = ErrorAdapter.getDto(models);

		return new JsonResult().data(dtos).page(page).
                others(Tools.getMap("crumbs", Tools.getCrumbs("错误码:"+project.getName(), "void")));
	}

}
