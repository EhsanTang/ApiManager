package cn.crap.controller.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.framework.JsonResult;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.ILogService;
import cn.crap.model.Log;
import cn.crap.utils.Const;
import cn.crap.utils.Tools;

@Scope("prototype")
@Controller
@RequestMapping("/log")
public class BackLogController extends BaseController<Log>{

	@Autowired
	private ILogService logService;
	
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@ModelAttribute Log log,@RequestParam(defaultValue="1") Integer currentPage){
		page.setCurrentPage(currentPage);
		map = Tools.getMap("modelName",log.getModelName());
		return new JsonResult(1,logService.findByMap(map,page,null),page);
	}
	
	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult detail(@ModelAttribute Log log){
		if(!log.getId().equals(Const.NULL_ID)){
			model= logService.get(log.getId());
		}else{
			model=new Log();
		}
		return new JsonResult(1,model);
	}
	
		
	@RequestMapping("/delete.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_LOG)
	public JsonResult delete(@ModelAttribute Log log, @RequestParam(defaultValue="false") boolean recover){
		// 恢复数据
		if(recover){
			logService.recover(log);
		}else{
			logService.delete(log);// 删除日志
		}
		return new JsonResult(1,null);
	}
}
