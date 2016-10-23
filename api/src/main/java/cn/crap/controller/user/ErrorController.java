package cn.crap.controller.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.table.IErrorService;
import cn.crap.inter.service.tool.ICacheService;
import cn.crap.model.Error;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;

@Controller
@RequestMapping("/user/error")
public class ErrorController extends BaseController<Error>{
	@Autowired
	private ICacheService cacheService;
	@Autowired
	private IErrorService errorService;

	/**
	 * @return 
	 * @throws MyException 
	 * @throws Exception 
	 * */
	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(@ModelAttribute Error error,@RequestParam(defaultValue="1") Integer currentPage) throws MyException{
		hasPermission(cacheService.getProject(error.getProjectId()), view);
		
		if(MyString.isEmpty(error.getProjectId())){
			throw new MyException("000020");
		}
		
		Page page= new Page(15);
		page.setCurrentPage(currentPage);
		
		Map<String,Object> map = Tools.getMap("errorCode|like",error.getErrorCode(),"errorMsg|like",error.getErrorMsg(),"projectId",error.getProjectId());
		return new JsonResult(1,errorService.findByMap(map,page,"errorCode asc"),page,
				Tools.getMap("crumbs", Tools.getCrumbs("错误码:"+cacheService.getProject(error.getProjectId()).getName(), "void")));
	}
	
	@RequestMapping("/detail.do")
	@ResponseBody
	@AuthPassport
	public JsonResult detail(@ModelAttribute Error error) throws MyException{
		Error model;
		if(!error.getId().equals(Const.NULL_ID)){
			model= errorService.get(error.getId());
			hasPermission(cacheService.getProject(model.getProjectId()), view);
		}else{
			model=new Error();
			model.setProjectId(error.getProjectId());
		}
		return new JsonResult(1,model);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute Error error) throws MyException{
		
		if(!MyString.isEmpty(error.getId())){
			// 不允许修改项目
			error.setProjectId( errorService.get(error.getId()).getProjectId() );
			hasPermission(cacheService.getProject(error.getProjectId()), modError);
			errorService.update(error);
		}else{
			if(errorService.getCount(Tools.getMap("errorCode",error.getErrorCode(),"projectId",error.getProjectId()))==0){
				hasPermission(cacheService.getProject(error.getProjectId()), addError);
				errorService.save(error);
			}else{
				return new JsonResult(new MyException("000002"));
			}
		}
		return new JsonResult(1,error);
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(@ModelAttribute Error error) throws MyException{
		error = errorService.get(error.getId());
		hasPermission(cacheService.getProject(error.getProjectId()), delError);
		errorService.delete(error);
		return new JsonResult(1,null);
	}
	
}
