package cn.crap.controller.front;

import java.util.List;
import java.util.Map;

import cn.crap.adapter.ErrorAdapter;
import cn.crap.dto.ErrorDto;
import cn.crap.model.mybatis.Project;
import cn.crap.service.mybatis.custom.CustomErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.service.ICacheService;
import cn.crap.model.mybatis.Error;
import cn.crap.model.mybatis.Project;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;

@Controller("frontErrorController")
@RequestMapping("/front/error")
public class ErrorController extends BaseController{

	@Autowired
	private ICacheService cacheService;
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
			@RequestParam(defaultValue="1") Integer currentPage, String password, String visitCode) throws MyException{
		Project project = cacheService.getProject(projectId);
		// 如果是私有项目，必须登录才能访问，公开项目需要查看是否需要密码
		isPrivateProject(password, visitCode, project);
		
		Page page= new Page(20, currentPage);

		List<Error> models = customErrorService.pageByProjectIdCodeMsg(projectId, errorCode, errorMsg, page);
		List<ErrorDto> dtos = ErrorAdapter.getDto(models);

		return new JsonResult(1,dtos ,page).others(Tools.getMap("crumbs", Tools.getCrumbs("错误码:"+project.getName(), "void")));
	}

}
