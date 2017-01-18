package cn.crap.controller.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.SearchDto;
import cn.crap.enumeration.MonitorType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.table.IErrorService;
import cn.crap.inter.service.table.IInterfaceService;
import cn.crap.inter.service.tool.ICacheService;
import cn.crap.inter.service.tool.ISearchService;
import cn.crap.model.Error;
import cn.crap.model.Interface;
import cn.crap.model.Module;
import cn.crap.springbeans.Config;
import cn.crap.utils.Const;
import cn.crap.utils.DateFormartUtil;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;
import net.sf.json.JSONArray;

@Controller
@RequestMapping("/user/interface")
public class InterfaceController extends BaseController<Interface>{

	@Autowired
	private IInterfaceService interfaceService;
	@Autowired
	private IErrorService errorService;
	@Autowired
	private ICacheService cacheService;
	@Autowired
	private ISearchService luceneService;
	@Autowired
	private Config config;
	
	
	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(@ModelAttribute Interface interFace,
			@RequestParam(defaultValue = "1") Integer currentPage) throws MyException{
		Page page= new Page(15);
		page.setCurrentPage(currentPage);
		hasPermission(cacheService.getProject(interFace.getProjectId()), view);
		
		List<Interface> interfaces = interfaceService.findByMap( 
				Tools.getMap("moduleId", interFace.getModuleId(), "interfaceName|like", interFace.getInterfaceName(), "fullUrl|like", interFace.getUrl()), 
				" new Interface(id,moduleId,interfaceName,version,createTime,updateBy,updateTime,remark,sequence,template)", page, null);
		
		return new JsonResult(1, interfaces, page, 
				Tools.getMap("crumbs", Tools.getCrumbs("接口列表:"+cacheService.getModuleName(interFace.getModuleId()),"void"),
						"module",cacheService.getModule(interFace.getModuleId())));
		
	}

	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult detail(@RequestParam String id, String moduleId) throws MyException {
		Interface model;
		if(!id.equals(Const.NULL_ID)){
			model= interfaceService.get(id);
			hasPermission(cacheService.getProject(model.getProjectId()), view);
		}else{
			model = new Interface();
			model.setModuleId( moduleId);
			Module module = cacheService.getModule(moduleId);
			if(!MyString.isEmpty(module.getTemplateId())){
				Interface template = interfaceService.get(module.getTemplateId());
				// 根据模板初始化接口
				if(template != null){
					model.setHeader(template.getHeader());
					model.setParam(template.getParam());
					model.setMethod(template.getMethod());
					model.setVersion(template.getVersion());
					model.setParamRemark(template.getParamRemark());
					model.setResponseParam(template.getResponseParam());
					model.setErrorList(template.getErrorList());
					model.setErrors(template.getErrors());
					model.setFalseExam(template.getFalseExam());
					model.setTrueExam(template.getTrueExam());
					model.setStatus(template.getStatus());
				}
			}
			
		}
		return new JsonResult(1, model);
	}
	
	/**
	 * @param interFace
	 * @return
	 * @throws MyException
	 * @throws IOException
	 */
	@RequestMapping("/copy.do")
	@ResponseBody
	public JsonResult copy(@ModelAttribute Interface interFace) throws MyException, IOException {
		//判断是否拥有该模块的权限
		hasPermission(cacheService.getProject(interFace.getProjectId()), addInter);
		if(!config.isCanRepeatUrl() && interfaceService.getCount(Tools.getMap("moduleId", interFace.getModuleId(), "fullUrl", interFace.getModuleUrl()+interFace.getUrl()))>0){
			throw new MyException("000004");
		}
		interFace.setId(null);
		interFace.setFullUrl(interFace.getModuleUrl()+interFace.getUrl());
		interfaceService.save(interFace);
		luceneService.add(interFace.toSearchDto(cacheService));
		return new JsonResult(1, interFace);
	}
	
	/**
	 * 根据参数生成请求示例
	 * @param interFace
	 * @return
	 */
	@RequestMapping("/getRequestExam.do")
	@ResponseBody
	@AuthPassport
	public JsonResult getRequestExam(@ModelAttribute Interface interFace) {
		interfaceService.getInterFaceRequestExam(interFace);
		return new JsonResult(1, interFace);
	}

	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport
	public JsonResult addOrUpdate(
			@ModelAttribute Interface interFace) throws IOException, MyException {
		if(MyString.isEmpty(interFace.getUrl()))
			return new JsonResult(new MyException("000005"));
		
		interFace.setUrl(interFace.getUrl().trim());
		
		/**
		 * 根据选着的错误码id，组装json字符串
		 */
		String errorIds = interFace.getErrorList();
		if (errorIds != null && !errorIds.equals("")) {
			List<Error> errors = errorService.findByMap(
					 Tools.getMap("errorCode|in", Tools.getIdsFromField(errorIds), "moduleId", interFace.getProjectId()), null,null);
			interFace.setErrors(JSONArray.fromObject(errors).toString());
		}else{
			interFace.setErrors("[]");
		}
		
		LoginInfoDto user = (LoginInfoDto) Tools.getUser();
		interFace.setUpdateBy("userName："+user.getUserName()+" | trueName："+ user.getTrueName());
		interFace.setUpdateTime(DateFormartUtil.getDateByFormat(DateFormartUtil.YYYY_MM_DD_HH_mm));
		
		//请求示例为空，则自动添加
		if(MyString.isEmpty(interFace.getRequestExam())){
			interfaceService.getInterFaceRequestExam(interFace);
		}
		
		//检查邮件格式是否正确
		if(interFace.getMonitorType() != MonitorType.No.getValue()){
			if(!MyString.isEmpty(interFace.getMonitorEmails())){
				for(String email : interFace.getMonitorEmails().split(";")){
					if( !Tools.checkEmail(email) ){
						throw new MyException("000032");
					}
				}
			}else{
				throw new MyException("000032");
			}
		}
		
		if (!MyString.isEmpty(interFace.getId())) {
			interFace.setModuleId( interfaceService.get(interFace.getId()).getModuleId());
			// 判断是否有修改模块的权限
			hasPermission(cacheService.getProject( interFace.getProjectId() ), modInter);
			
			//同一模块下不允许 url 重复
			if( !config.isCanRepeatUrl() && interfaceService.getCount(Tools.getMap("moduleId",interFace.getModuleId(), "fullUrl",interFace.getModuleUrl() +interFace.getUrl(),"id|!=",interFace.getId())) >0 ){
				throw new MyException("000004");
			}
			
			interFace.setFullUrl(interFace.getModuleUrl()+interFace.getUrl());
			interfaceService.update(interFace, "接口", "");
			if(interFace.getId().equals(interFace.getProjectId())){
				throw new MyException("000027");
			}
			luceneService.update(interFace.toSearchDto(cacheService));
			
		} else {
			hasPermission(cacheService.getProject( interFace.getProjectId() ), addInter);
			if(!config.isCanRepeatUrl() && interfaceService.getCount(Tools.getMap("fullUrl",interFace.getModuleUrl()+interFace.getUrl()))>0){
				return new JsonResult(new MyException("000004"));
			}
			interFace.setFullUrl(interFace.getModuleUrl()+interFace.getUrl());
			interfaceService.save(interFace);
			luceneService.add(interFace.toSearchDto(cacheService));
		}
		return new JsonResult(1, interFace);
	}

	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(@ModelAttribute Interface interFace) throws MyException, IOException {
		interFace = interfaceService.get(interFace.getId());
		hasPermission(cacheService.getProject( interFace.getProjectId() ), delInter);
		interfaceService.delete(interFace, "接口", "");
		luceneService.delete(new SearchDto(interFace.getId()));
		return new JsonResult(1, null);
	}

	@RequestMapping("/changeSequence.do")
	@ResponseBody
	public JsonResult changeSequence(@RequestParam String id,@RequestParam String changeId) throws MyException {
		Interface change = interfaceService.get(changeId);
		Interface model = interfaceService.get(id);
		hasPermission(cacheService.getProject( model.getProjectId() ), modInter);
		hasPermission(cacheService.getProject( change.getProjectId() ), modInter);
		
		int modelSequence = model.getSequence();
		
		model.setSequence(change.getSequence());
		change.setSequence(modelSequence);
		
		interfaceService.update(model);
		interfaceService.update(change);
		return new JsonResult(1, null);
	}
	public HttpServletResponse getResponse(){
		return response;
	}
}
