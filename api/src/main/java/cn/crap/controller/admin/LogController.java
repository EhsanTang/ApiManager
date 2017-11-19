package cn.crap.controller.admin;

import java.util.List;
import java.util.Map;

import cn.crap.adapter.LogAdapter;
import cn.crap.dto.LogDto;
import cn.crap.model.User;
import cn.crap.model.mybatis.LogCriteria;
import cn.crap.service.mybatis.custom.CustomLogService;
import cn.crap.service.mybatis.imp.MybatisLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.model.mybatis.Log;
import cn.crap.utils.Const;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;

@Controller
@RequestMapping("/log")
public class LogController extends BaseController{

	@Autowired
	private MybatisLogService logService;
	@Autowired
	private CustomLogService customLogService;
	
	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport(authority = Const.AUTH_LOG)
	public JsonResult list(@ModelAttribute Log log,@RequestParam(defaultValue="1") Integer currentPage){
		Page page= new Page(15, currentPage);
		Map<String,Object> map = Tools.getMap("modelName",log.getModelName(),"identy", log.getIdenty());
		LogCriteria example = new LogCriteria();
		example.createCriteria().andModelNameEqualTo(log.getModelName()).andIdNotEqualTo(log.getIdenty());

		page.setAllRow(logService.countByExample(example));
		List<LogDto> logDtoList = LogAdapter.getDto(logService.selectByExample(example));
		return new JsonResult(1, logDtoList, page);
	}
	
	@RequestMapping("/detail.do")
	@ResponseBody
	@AuthPassport(authority = Const.AUTH_LOG)
	public JsonResult detail(@ModelAttribute Log log){
		Log model;
		if(!log.getId().equals(Const.NULL_ID)){
			model= logService.selectByPrimaryKey(log.getId());
		}else{
			model=new Log();
		}
		return new JsonResult(1,model);
	}
	
		
	@RequestMapping("/recover.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_LOG)
	public JsonResult recover(@ModelAttribute Log log) throws MyException{
		customLogService.recover(log);
		return new JsonResult(1,null);
	}
}
