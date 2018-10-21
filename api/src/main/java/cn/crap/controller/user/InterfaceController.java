package cn.crap.controller.user;

import cn.crap.adapter.InterfaceAdapter;
import cn.crap.beans.Config;
import cn.crap.dto.InterfaceDto;
import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.ParamDto;
import cn.crap.dto.SearchDto;
import cn.crap.enumer.InterfaceContentType;
import cn.crap.enumer.InterfaceStatus;
import cn.crap.enumer.MonitorType;
import cn.crap.enumer.MyError;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.Error;
import cn.crap.model.InterfaceWithBLOBs;
import cn.crap.model.Module;
import cn.crap.model.Project;
import cn.crap.query.ErrorQuery;
import cn.crap.query.InterfaceQuery;
import cn.crap.service.ErrorService;
import cn.crap.service.ISearchService;
import cn.crap.service.InterfaceService;
import cn.crap.utils.*;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user/interface")
public class InterfaceController extends BaseController{

	@Autowired
	private InterfaceService interfaceService;
	@Autowired
	private ISearchService luceneService;
	@Autowired
	private ErrorService errorService;
	@Autowired
	private Config config;
	
	
	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(@ModelAttribute InterfaceQuery query, String url) throws MyException{
        Module module = getModule(query);
        Project project = getProject(query);
        checkPermission(project, VIEW);

        InterfaceQuery interfaceQuery = query.setFullUrl(url);
		Page page= new Page(interfaceQuery);

		List<InterfaceDto> interfaces = InterfaceAdapter.getDto(interfaceService.query(interfaceQuery), module, project);
		page.setAllRow(interfaceService.count(interfaceQuery));
		JsonResult result = new JsonResult(1, interfaces, page);
		result.putOthers("module", module);

		return result;
		
	}

	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult detail(String id, String moduleId, String projectId) throws MyException {
		InterfaceWithBLOBs model;
		Module module;
		if(id != null){
			model= interfaceService.getById(id);
			module = moduleCache.get(model.getModuleId());
		}else{
			model = new InterfaceWithBLOBs();
			module = moduleCache.get(moduleId);
			model.setModuleId( moduleId);
			model.setProjectId(module.getProjectId() == null ? projectId : module.getProjectId());
			model.setResponseParam("[]");
			model.setHeader("[]");
			model.setParamRemark("[]");
            model.setErrors("[]");
            model.setMethod(IConst.C_METHOD_GET);
            model.setStatus(InterfaceStatus.ONLINE.getByteValue());
            model.setContentType(InterfaceContentType.JSON.getType());
            model.setParam(IConst.C_PARAM_FORM_PRE + "[]");

            if(!MyString.isEmpty(module.getTemplateId())){
				InterfaceWithBLOBs template = interfaceService.getById(module.getTemplateId());
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
					model.setContentType(template.getContentType());
				}
			}
		}
        checkPermission(model.getProjectId(), VIEW);
		return new JsonResult(1, InterfaceAdapter.getDtoWithBLOBs(model, module, null, false));
	}
	
	/**
	 * @return
	 * @throws MyException
	 * @throws IOException
	 */
	@RequestMapping("/copy.do")
	@ResponseBody
	public JsonResult copy(@RequestParam String id, String interfaceName, String url, @RequestParam String moduleId) throws MyException, IOException {
		InterfaceWithBLOBs interFace = interfaceService.getById(id);

		//判断是否拥有原来项目的权限
		checkPermission(interFace.getProjectId(), VIEW);
		Module module = moduleCache.get(moduleId);
		// 检查新项目的权限
        checkPermission(module.getProjectId(), ADD_INTER);

		if(!config.isCanRepeatUrl()){
			if (interfaceService.count(new InterfaceQuery().setModuleId(moduleId).setEqualFullUrl(module.getUrl() + interFace.getUrl())) > 0){
				throw new MyException(MyError.E000004);
			}
		}

		interFace.setId(null);
		interFace.setUrl(url);
        interFace.setFullUrl((module.getUrl() == null ? "" : module.getUrl()) + url);
        interFace.setInterfaceName(interfaceName);
        interFace.setModuleId(moduleId);
        interFace.setProjectId(module.getProjectId());
        interfaceService.insert(interFace);

        interFace.setId(interFace.getId());
		luceneService.add(InterfaceAdapter.getSearchDto(InterfaceAdapter.getDtoWithBLOBs(interFace, null, null, false)));
		return new JsonResult(1, interFace);
	}
	
	/**
	 * 根据参数生成请求示例
	 * @param interFace
	 * @return

	@RequestMapping("/getRequestExam.do")
	@ResponseBody
	@AuthPassport
	public JsonResult getRequestExam(@ModelAttribute InterfaceDto interFace) {
		interfaceService.getInterFaceRequestExam(interFace);
		return new JsonResult(1, interFace);
	}*/

	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport
	public JsonResult addOrUpdate(@ModelAttribute InterfaceDto interFace, String modifyRemark) throws IOException, MyException {
		Assert.notNull(interFace.getProjectId(), "projectId can't be null");

		if(MyString.isEmpty(interFace.getUrl())) {
			return new JsonResult(MyError.E000005);
		}
		interFace.setUrl(interFace.getUrl().trim());
		if (C_PARAM_FORM.equals(interFace.getParamType())){
		    interFace.setParam(C_PARAM_FORM_PRE + interFace.getParam());
        }
		
		/**
		 * 根据选着的错误码id，组装json字符串
		 */
        ErrorQuery query = new ErrorQuery().setProjectId(interFace.getProjectId())
                .setErrorCodeList(Tools.getIdsFromField(interFace.getErrorList())).setPageSize(100);
        List<Error> errors  = errorService.query(query);
		interFace.setErrors(JSONArray.fromObject(errors).toString());

		LoginInfoDto user = LoginUserHelper.getUser();
		interFace.setUpdateBy("userName："+user.getUserName()+" | trueName："+ user.getTrueName());

		//请求示例为空，则自动添加
		/**if(MyString.isEmpty(interFace.getRequestExam())){
			interfaceService.getInterFaceRequestExam(interFace);
		}**/

        interFace.setMonitorType(MonitorType.No.getValue());
		//检查邮件格式是否正确
		/**if(interFace.getMonitorType() != MonitorType.No.getValue()){
			if(!MyString.isEmpty(interFace.getMonitorEmails())){
				for(String email : interFace.getMonitorEmails().split(";")){
					if( !Tools.checkEmail(email) ){
						throw new MyException(E000032");
					}
				}
			}else{
				throw new MyException(E000032");
			}
		}**/

		Module module = moduleCache.get(interFace.getModuleId());
		if (!MyString.isEmpty(interFace.getId())) {
			String oldModuleId = interfaceService.getById(interFace.getId()).getModuleId();
			String projectId = moduleCache.get(oldModuleId).getProjectId();
			Project project = projectCache.get(interFace.getProjectId() );

			// 接口只能在同一个项目下的模块中移动
			if( !projectId.equals(project.getId())){
				throw new MyException(MyError.E000047);
			}
			// 判断是否有修改模块的权限
			checkPermission(project, MOD_INTER);
			
			//同一模块下不允许 url 重复
            InterfaceQuery interfaceQuery = new InterfaceQuery();
            interfaceQuery.setModuleId(interFace.getModuleId()).setFullUrl(module.getUrl() +interFace.getUrl()).setExceptId(interFace.getId());
			if( !config.isCanRepeatUrl() && interfaceService.count(interfaceQuery) >0 ){
				throw new MyException(MyError.E000004);
			}
			
			interFace.setFullUrl(module.getUrl() + interFace.getUrl());
			interfaceService.update(InterfaceAdapter.getModel(interFace), "接口", modifyRemark);
			if(interFace.getId().equals(interFace.getProjectId())){
				throw new MyException(MyError.E000027);
			}
			luceneService.update(InterfaceAdapter.getSearchDto(interFace));
			
		} else {
			checkPermission(interFace.getProjectId(), ADD_INTER);
            InterfaceQuery interfaceQuery = new InterfaceQuery();
            interfaceQuery.setModuleId(interFace.getModuleId()).setFullUrl(module.getUrl() +interFace.getUrl());
			if(!config.isCanRepeatUrl() && interfaceService.count(interfaceQuery)>0){
				return new JsonResult(MyError.E000004);
			}
			interFace.setFullUrl(module.getUrl() + interFace.getUrl());
			InterfaceWithBLOBs model = InterfaceAdapter.getModel(interFace);
			interfaceService.insert(model);

			interFace.setId(model.getId());
			luceneService.add(InterfaceAdapter.getSearchDto(interFace));
		}
		return new JsonResult(1, interFace);
	}


	@RequestMapping("/debug.do")
	@ResponseBody
	@AuthPassport
	public JsonResult debug(@ModelAttribute InterfaceDto interFace) throws IOException, Exception {
		Assert.notNull(interFace.getMethod(), "请求方法不能为空");
		Assert.notNull(interFace.getFullUrl(), "地址不能为空");
        Assert.isTrue(interFace.getFullUrl().startsWith("http"), "地址必须以 http开头");

        String fullUrl = interFace.getFullUrl();

        List<ParamDto> headerList =JSONArray.toList(JSONArray.fromObject(
                interFace.getHeader() == null ? "[]" : interFace.getHeader()), new ParamDto(), new JsonConfig());

        Map<String, String> httpHeaders = new HashMap<>();
        for (ParamDto paramDto : headerList) {
            httpHeaders.put(paramDto.getName(), paramDto.getDef());
        }
        // 如果自定义参数不为空，则表示需要使用post发送自定义包体
        if (C_PARAM_RAW.equals(interFace.getParamType())) {
            return new JsonResult(1, Tools.getMap("debugResult", HttpPostGet.postBody(fullUrl, interFace.getParam(), httpHeaders)));
        }

        List<ParamDto> paramList = JSONArray.toList(JSONArray.fromObject(
                interFace.getParam() == null ? "[]" : interFace.getParam()), new ParamDto(), new JsonConfig());

        Map<String, String> httpParams = new HashMap<>();
        for (ParamDto paramDto : paramList) {
            if (fullUrl.contains("{" + paramDto.getRealName() + "}")) {
                fullUrl = fullUrl.replace("{" + paramDto.getName() + "}", paramDto.getDef());
            } else {
                httpParams.put(paramDto.getName(), paramDto.getDef());
            }
        }
        try {
            switch (interFace.getMethod()) {
                case "POST":
                    return new JsonResult(1, Tools.getMap("debugResult", HttpPostGet.post(fullUrl, httpParams, httpHeaders)));
                case "GET":
                    return new JsonResult(1, Tools.getMap("debugResult", HttpPostGet.get(fullUrl, httpParams, httpHeaders)));
                case "PUT":
                    return new JsonResult(1, Tools.getMap("debugResult", HttpPostGet.put(fullUrl, httpParams, httpHeaders)));
                case "DELETE":
                    return new JsonResult(1, Tools.getMap("debugResult", HttpPostGet.delete(fullUrl, httpParams, httpHeaders)));
                case "HEAD":
                    return new JsonResult(1, Tools.getMap("debugResult", HttpPostGet.head(fullUrl, httpParams, httpHeaders)));
                case "OPTIONS":
                    return new JsonResult(1, Tools.getMap("debugResult", HttpPostGet.options(fullUrl, httpParams, httpHeaders)));
                case "TRACE":
                    return new JsonResult(1, Tools.getMap("debugResult", HttpPostGet.trace(fullUrl, httpParams, httpHeaders)));
				case "PATCH":
					return new JsonResult(1, Tools.getMap("debugResult", HttpPostGet.patch(fullUrl, httpParams, httpHeaders)));
                default:
                    return new JsonResult(1, Tools.getMap("debugResult", "不支持的请求方法：" + interFace.getMethod()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(1, Tools.getMap("debugResult", "调试出错\r\nmessage:" + e.getMessage()));
        }

	}

	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(String id, String ids) throws MyException, IOException{
		if( MyString.isEmpty(id) && MyString.isEmpty(ids)){
			throw new MyException(MyError.E000029);
		}
		if( MyString.isEmpty(ids) ){
			ids = id;
		}
		
		for(String tempId : ids.split(",")){
			if(MyString.isEmpty(tempId)){
				continue;
			}
			InterfaceWithBLOBs interFace = interfaceService.getById( tempId );
			checkPermission(interFace.getProjectId(), DEL_INTER);
			interfaceService.delete(interFace.getId(), "接口", "");
			luceneService.delete(new SearchDto(interFace.getId()));
		}
		return new JsonResult(1, null);
	}

	@RequestMapping("/changeSequence.do")
	@ResponseBody
	public JsonResult changeSequence(@RequestParam String id,@RequestParam String changeId) throws MyException {
		InterfaceWithBLOBs change = interfaceService.getById(changeId);
		InterfaceWithBLOBs model = interfaceService.getById(id);
		checkPermission(model.getProjectId(), MOD_INTER);
		checkPermission(change.getProjectId(), MOD_INTER);
		
		int modelSequence = model.getSequence();
		
		model.setSequence(change.getSequence());
		change.setSequence(modelSequence);

		interfaceService.update(model);
		interfaceService.update(change);
		return new JsonResult(1, null);
	}
}
