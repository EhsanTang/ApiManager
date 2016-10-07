package cn.crap.controller.user;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.dto.LoginInfoDto;
import cn.crap.enumeration.UserType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.ICacheService;
import cn.crap.inter.service.IErrorService;
import cn.crap.inter.service.IProjectService;
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
	@Autowired
	private IProjectService projectService;

	/**
	 * @return 
	 * @throws Exception 
	 * */
	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(@ModelAttribute Error error,@RequestParam(defaultValue="1") Integer currentPage,
			@RequestParam(defaultValue="false") boolean myself){
		Page page= new Page(15);
		page.setCurrentPage(currentPage);
		
		// 普通用户，管理员我的项目菜单只能查看自己的项目
		LoginInfoDto user = Tools.getUser();
		List<String> projectIds = null;
		if( Tools.getUser().getType() == UserType.USER.getType() || myself){
			projectIds = projectService.getProjectIdByUid(user.getId());
			projectIds.add("NULL");
		}
		
		Map<String,Object> map = Tools.getMap("projectId|in", projectIds,"errorCode|like",error.getErrorCode(),"errorMsg|like",error.getErrorMsg(),"projectId",error.getProjectId());
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
			hasPermission(cacheService.getProject(model.getProjectId()));
		}else{
			model=new Error();
			model.setProjectId(error.getProjectId());
		}
		return new JsonResult(1,model);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute Error error) throws MyException{
		
		hasPermission(cacheService.getProject(error.getProjectId()));
				
		if(!MyString.isEmpty(error.getId())){
			// 不允许修改项目
			error.setProjectId( errorService.get(error.getId()).getProjectId() );
			errorService.update(error);
		}else{
			if(errorService.getCount(Tools.getMap("errorCode",error.getErrorCode(),"projectId",error.getProjectId()))==0){
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
		hasPermission(cacheService.getProject(error.getProjectId()));
		errorService.delete(error);
		return new JsonResult(1,null);
	}
	
}
