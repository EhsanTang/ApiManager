package cn.crap.controller.back;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.dto.LoginInfoDto;
import cn.crap.enumeration.DataCeneterType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.ICacheService;
import cn.crap.inter.service.IDataCenterService;
import cn.crap.inter.service.IErrorService;
import cn.crap.model.Error;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Scope("prototype")
@Controller
@RequestMapping("/back/error")
public class BackErrorController extends BaseController<Error>{
	@Autowired
	private ICacheService cacheService;
	@Autowired
	private IErrorService errorService;
	@Autowired
	private IDataCenterService dataCenterService;

	/**
	 * @return 
	 * @throws Exception 
	 * */
	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(@ModelAttribute Error error,@RequestParam(defaultValue="1") Integer currentPage){
		page.setCurrentPage(currentPage);
		
		// 如果用户为普通用户，则只能查看自己的模块
		LoginInfoDto user = Tools.getUser();
		List<String> moduleIds = null;
		if(user != null && user.getType() == 1){
			moduleIds = dataCenterService.getList(  null, DataCeneterType.MODULE.name(), Tools.getUser().getId() );
			moduleIds.add("NULL");
		}
		
		map = Tools.getMap("moduleId|in", moduleIds,"errorCode|like",error.getErrorCode(),"errorMsg|like",error.getErrorMsg(),"moduleId",error.getModuleId());
		return new JsonResult(1,errorService.findByMap(map,page,"errorCode asc"),page,
				Tools.getMap("crumbs", Tools.getCrumbs("错误码:"+cacheService.getModuleName(error.getModuleId()), "void")));
	}
	
	@RequestMapping("/detail.do")
	@ResponseBody
	@AuthPassport
	public JsonResult detail(@ModelAttribute Error error) throws MyException{
		if(!error.getId().equals(Const.NULL_ID)){
			model= errorService.get(error.getId());
			Tools.hasAuth(Const.AUTH_ERROR, model.getModuleId());
		}else{
			model=new Error();
			model.setModuleId(error.getModuleId());
		}
		return new JsonResult(1,model);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute Error error) throws MyException{
		
		// 修改错误码需要判断是否有原有模块的权限、是否有新模块的权限，新增错误码需要判断是否有该模块的权限
		if(!MyString.isEmpty(error.getId())){
			Error oldError = errorService.get(error.getId());
			Tools.hasAuth(Const.AUTH_ERROR, oldError.getModuleId());
			Tools.hasAuth(Const.AUTH_ERROR, error.getModuleId());
		}else{
			Tools.hasAuth(Const.AUTH_ERROR, error.getModuleId());
		}
		
		try{
			if(!MyString.isEmpty(error.getId())){
				errorService.update(error);
			}else{
				error.setId(null);
				if(errorService.getCount(Tools.getMap("errorCode",error.getErrorCode(),"moduleId",error.getModuleId()))==0){
					errorService.save(error);
				}else{
					return new JsonResult(new MyException("000002"));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return new JsonResult(1,error);
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(@ModelAttribute Error error) throws MyException{
		error = errorService.get(error.getId());
		Tools.hasAuth(Const.AUTH_ERROR, error.getModuleId());
		errorService.delete(error);
		return new JsonResult(1,null);
	}
	
}
