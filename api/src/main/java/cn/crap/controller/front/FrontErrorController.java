package cn.crap.controller.front;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.ICacheService;
import cn.crap.inter.service.IErrorService;
import cn.crap.model.Error;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;

@Controller
@RequestMapping("/front/error")
public class FrontErrorController extends BaseController<Error>{

	@Autowired
	private ICacheService cacheService;
	@Autowired
	private IErrorService errorService;

	/**
	 * 前端错误码列表，只查询公开的顶级项目错误码（错误码定义在顶级项目中）
	 * 非公开的项目，错误码只能在项目主页中查看
	 * @param error
	 * @param currentPage
	 * @return
	 * @throws MyException 
	 */
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@RequestParam String errorCode,@RequestParam String errorMsg, @RequestParam String moduleId, @RequestParam(defaultValue="1") Integer currentPage) throws MyException{
		Page page= new Page(15);
		page.setCurrentPage(currentPage);
		page.setSize(20);
		
		// 不允许查看根路径下所有项目
		Map<String,Object> map = Tools.getMap(  "errorCode|like", errorCode,  "errorMsg|like", errorMsg);
		if( !Tools.moduleIdIsLegal(moduleId) ){
			throw new MyException("000020");
		}
		map.put( "moduleId", moduleId );
		
		return new JsonResult(1,errorService.findByMap(map,page,"errorCode asc"),page,
				Tools.getMap("crumbs", Tools.getCrumbs("错误码:"+cacheService.getModuleName(moduleId), "void")));
	}

}
