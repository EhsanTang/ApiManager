package cn.crap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.framework.BiyaoBizException;
import cn.crap.framework.JsonResult;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.IErrorService;
import cn.crap.inter.service.IModuleService;
import cn.crap.model.Error;
import cn.crap.model.Module;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Scope("prototype")
@Controller
@RequestMapping("/error")
public class ErrorController extends BaseController{

	@Autowired
	private IErrorService errorService;
	@Autowired
	private IModuleService moduleService;
	/**
	 * MenuDemo
	 * @return 
	 * @throws Exception 
	 * */
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@ModelAttribute Error error,@RequestParam(defaultValue="1") Integer currentPage){
		page.setCurrentPage(currentPage);
		map = Tools.getMap("errorCode|like",error.getErrorCode(),"errorMsg|like",error.getErrorMsg(),"moduleId",error.getModuleId());
		return new JsonResult(1,errorService.findByMap(map,page,"errorCode asc"),page);
	}
	
	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult detail(@ModelAttribute Error error,String currentId){
		error= errorService.get(error.getErrorId());
		if(error==null){
			error=new Error();
			error.setModuleId(currentId);
		}
		return new JsonResult(1,error);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_ERROR)
	public JsonResult addOrUpdate(@ModelAttribute Error error){
		try{
			if(error.getModuleId()!=null){
				Module module = moduleService.get(error.getModuleId());
				error.setModuleName(module==null?"":module.getModuleName());
			}
			if(!MyString.isEmpty(error.getErrorId())){
				errorService.update(error);
			}else{
				error.setErrorId(null);
				if(errorService.getCount(Tools.getMap("errorCode",error.getErrorCode(),"moduleId",error.getModuleId()))==0){
					errorService.save(error);
				}else{
					return new JsonResult(new BiyaoBizException("000002"));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return new JsonResult(1,error);
	}
	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(@ModelAttribute Error error) throws BiyaoBizException{
		error = errorService.get(error.getErrorId());
		Tools.hasAuth(Const.AUTH_ERROR, request.getSession(), error.getModuleId());
		errorService.delete(error);
		return new JsonResult(1,null);
	}

}
