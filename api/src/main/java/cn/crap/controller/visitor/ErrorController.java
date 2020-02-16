package cn.crap.controller.visitor;

import cn.crap.adapter.ErrorAdapter;
import cn.crap.dto.ErrorDto;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.model.Error;
import cn.crap.model.ProjectPO;
import cn.crap.query.ErrorQuery;
import cn.crap.service.ErrorService;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller("visitorErrorController")
@RequestMapping("/visitor/error")
public class ErrorController extends BaseController{
	@Autowired
	private ErrorService errorService;

	/**
	 * 前端错误码列表，只查询公开的顶级项目错误码（错误码定义在顶级项目中）
	 * 非公开的项目，错误码只能在项目主页中查看
	 * @return
	 * @throws MyException 
	 */
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@ModelAttribute ErrorQuery query, String password, String visitCode) throws MyException{
		ProjectPO project = projectCache.get(query.getProjectId());

		checkFrontPermission(password, visitCode, project);
		
		Page page= new Page(query);
		page.setAllRow(errorService.count(query));
		List<Error> models = errorService.query(query);
		List<ErrorDto> dtos = ErrorAdapter.getDto(models);

		return new JsonResult().data(dtos).page(page).
                others(Tools.getMap("crumbs", Tools.getCrumbs("错误码:"+project.getName(), "void")));
	}

}
