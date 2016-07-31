package cn.crap.controller.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.enumeration.DataCeneterType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.ICacheService;
import cn.crap.inter.service.IDataCenterService;
import cn.crap.inter.service.IErrorService;
import cn.crap.model.Error;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Scope("prototype")
@Controller
@RequestMapping("/front/error")
public class FrontErrorController extends BaseController<Error>{

	@Autowired
	private ICacheService cacheService;
	@Autowired
	private IErrorService errorService;
	@Autowired
	private IDataCenterService dataCenterService;

	/**
	 * 前端错误码列表，只查询公开的顶级项目错误码（错误码定义在顶级项目中）
	 * 非公开的项目，错误码只能在项目主页中查看
	 * @param error
	 * @param currentPage
	 * @return
	 */
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@ModelAttribute Error error,@RequestParam(defaultValue="1") Integer currentPage){
		page.setCurrentPage(currentPage);
		
		// 如果错误码模块Id为空，则查询所有公开的顶级项目
		map = Tools.getMap(  "errorCode|like",error.getErrorCode(),  "errorMsg|like",error.getErrorMsg());
		if( MyString.isEmpty(error.getModuleId()) || error.getModuleId().equals("0") ){
			map.put( "moduleId|in",  dataCenterService.getList(Byte.valueOf("2"), DataCeneterType.MODULE.name(), "")  );
		}else{
			map.put( "moduleId", error.getModuleId() );
		}
		
		return new JsonResult(1,errorService.findByMap(map,page,"errorCode asc"),page,
				Tools.getMap("crumbs", Tools.getCrumbs("错误码:"+cacheService.getModuleName(error.getModuleId()), "void")));
	}

}
