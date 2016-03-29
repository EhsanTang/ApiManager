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
import cn.crap.inter.ErrorInfoService;
import cn.crap.inter.ModuleInfoService;
import cn.crap.model.ErrorInfoModel;
import cn.crap.model.ModuleInfoModel;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Scope("prototype")
@Controller
@RequestMapping("/errorInfo")
public class ErrorInfoController extends BaseController<ErrorInfoModel>{

	@Autowired
	private ErrorInfoService errorInfoService;
	@Autowired
	private ModuleInfoService moduleInfoService;
	/**
	 * MenuInfoDemo
	 * @return 
	 * @throws Exception 
	 * */
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@ModelAttribute ErrorInfoModel errorInfo,@RequestParam(defaultValue="1") Integer currentPage){
		page.setCurrentPage(currentPage);
		map = Tools.getMap("errorCode|like",errorInfo.getErrorCode(),"errorMsg|like",errorInfo.getErrorMsg(),"moduleId",errorInfo.getModuleId());
		return new JsonResult(1,errorInfoService.findByMap(map,page,"errorCode asc"),page);
	}
	
	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult detail(@ModelAttribute ErrorInfoModel errorInfo,String currentId){
		errorInfo= errorInfoService.get(errorInfo.getErrorId());
		if(errorInfo==null){
			errorInfo=new ErrorInfoModel();
			errorInfo.setModuleId(currentId);
		}
		return new JsonResult(1,errorInfo);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_ERROR)
	public JsonResult addOrUpdate(@ModelAttribute ErrorInfoModel errorInfo){
		try{
			if(errorInfo.getModuleId()!=null){
				ModuleInfoModel module = moduleInfoService.get(errorInfo.getModuleId());
				errorInfo.setModuleName(module==null?"":module.getModuleName());
			}
			if(!MyString.isEmpty(errorInfo.getErrorId())){
				errorInfoService.update(errorInfo);
			}else{
				errorInfo.setErrorId(null);
				if(errorInfoService.getCount(Tools.getMap("errorCode",errorInfo.getErrorCode(),"moduleId",errorInfo.getModuleId()))==0){
					errorInfoService.save(errorInfo);
				}else{
					return new JsonResult(new BiyaoBizException("000002"));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return new JsonResult(1,errorInfo);
	}
	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(@ModelAttribute ErrorInfoModel errorInfo) throws BiyaoBizException{
		errorInfo = errorInfoService.get(errorInfo.getErrorId());
		Tools.hasAuth(Const.AUTH_ERROR, request.getSession(), errorInfo.getModuleId());
		errorInfoService.delete(errorInfo);
		return new JsonResult(1,null);
	}

}
