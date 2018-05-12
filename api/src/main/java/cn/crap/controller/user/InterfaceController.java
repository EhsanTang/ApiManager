package cn.crap.controller.user;

import cn.crap.adapter.InterfaceAdapter;
import cn.crap.dto.InterfaceDto;
import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.SearchDto;
import cn.crap.enumer.MonitorType;
import cn.crap.enumer.MyError;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.mybatis.Error;
import cn.crap.model.mybatis.*;
import cn.crap.service.ISearchService;
import cn.crap.service.custom.CustomErrorService;
import cn.crap.service.custom.CustomInterfaceService;
import cn.crap.service.mybatis.InterfaceService;
import cn.crap.beans.Config;
import cn.crap.utils.*;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user/interface")
public class InterfaceController extends BaseController{

	@Autowired
	private CustomInterfaceService customInterfaceService;
	@Autowired
	private InterfaceService mybatisInterfaceService;
	@Autowired
	private ISearchService luceneService;
	@Autowired
	private CustomErrorService customErrorService;
	@Autowired
	private Config config;
	
	
	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(@RequestParam String moduleId, String interfaceName, String url,
			 Integer currentPage) throws MyException{
		Page page= new Page(currentPage);
		checkUserPermissionByModuleId(moduleId, VIEW);

		InterfaceCriteria example = new InterfaceCriteria();
		InterfaceCriteria.Criteria criteria = example.createCriteria().andModuleIdEqualTo(moduleId);
		if (!MyString.isEmpty(interfaceName)){
			criteria.andInterfaceNameLike("%" + interfaceName + "%");
		}
		if (!MyString.isEmpty(url)){
			criteria.andFullUrlLike("%" + url + "%");
		}

		List<InterfaceDto> interfaces = InterfaceAdapter.getDto(mybatisInterfaceService.selectByExampleWithBLOBs(example), null);
		JsonResult result = new JsonResult(1, interfaces, page);
		result.putOthers("crumbs", Tools.getCrumbs("接口列表:"+ moduleCache.get(moduleId).getName(),"void"))
				.putOthers("module", moduleCache.get(moduleId));

		return result;
		
	}

	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult detail(String id, String moduleId) throws MyException {
		InterfaceWithBLOBs model;
		Module module = null;
		if(id != null){
			model= mybatisInterfaceService.getById(id);
			module = moduleCache.get(model.getModuleId());
			checkUserPermissionByProject(model.getProjectId(), VIEW);
		}else{
			model = new InterfaceWithBLOBs();
			module = moduleCache.get(moduleId);
			model.setModuleId( moduleId);
			model.setProjectId(module.getProjectId());
			model.setParam("form=[]");
			model.setResponseParam("[]");
			model.setHeader("[]");
			model.setParamRemark("[]");
			if(!MyString.isEmpty(module.getTemplateId())){
				InterfaceWithBLOBs template = mybatisInterfaceService.getById(module.getTemplateId());
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
		return new JsonResult(1, InterfaceAdapter.getDto(model, module, false));
	}
	
	/**
	 * @param interFace
	 * @return
	 * @throws MyException
	 * @throws IOException
	 */
	@RequestMapping("/copy.do")
	@ResponseBody
	public JsonResult copy(@ModelAttribute InterfaceDto interFace) throws MyException, IOException {
		//判断是否拥有该模块的权限
		checkUserPermissionByProject(interFace.getProjectId(), ADD_INTER);
		Module module = moduleCache.get(interFace.getModuleId());

		if(!config.isCanRepeatUrl()){
			InterfaceCriteria example = new InterfaceCriteria();
			InterfaceCriteria.Criteria criteria = example.createCriteria();
			criteria.andModuleIdEqualTo(interFace.getModuleId()).andFullUrlEqualTo(module.getUrl() + interFace.getUrl());
			if (mybatisInterfaceService.countByExample(example) > 0){
				throw new MyException(MyError.E000004);
			}
		}
		interFace.setId(null);
		interFace.setFullUrl(module.getUrl() + interFace.getUrl());
		mybatisInterfaceService.insert(InterfaceAdapter.getModel(interFace));

		luceneService.add(InterfaceAdapter.getSearchDto(interFace));
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
	public JsonResult getRequestExam(@ModelAttribute InterfaceDto interFace) {
		customInterfaceService.getInterFaceRequestExam(interFace);
		return new JsonResult(1, interFace);
	}

	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport
	public JsonResult addOrUpdate(@ModelAttribute InterfaceDto interFace) throws IOException, MyException {
		Assert.notNull(interFace.getProjectId(), "projectId can't be null");

		if(MyString.isEmpty(interFace.getUrl())) {
			return new JsonResult(MyError.E000005);
		}
		interFace.setUrl(interFace.getUrl().trim());
		
		/**
		 * 根据选着的错误码id，组装json字符串
		 */
		String errorIds = interFace.getErrorList();
		List<Error> errors  = customErrorService.queryByProjectIdAndErrorCode(interFace.getProjectId(), Tools.getIdsFromField(errorIds));
		interFace.setErrors(JSONArray.fromObject(errors).toString());

		LoginInfoDto user = LoginUserHelper.getUser();
		interFace.setUpdateBy("userName："+user.getUserName()+" | trueName："+ user.getTrueName());

		//请求示例为空，则自动添加
		if(MyString.isEmpty(interFace.getRequestExam())){
			customInterfaceService.getInterFaceRequestExam(interFace);
		}
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
			String oldModuleId = mybatisInterfaceService.getById(interFace.getId()).getModuleId();
			String projectId = moduleCache.get(oldModuleId).getProjectId();
			Project project = projectCache.get(interFace.getProjectId() );

			// 接口只能在同一个项目下的模块中移动
			if( !projectId.equals(project.getId())){
				throw new MyException(MyError.E000047);
			}
			// 判断是否有修改模块的权限
			checkUserPermissionByProject(project, MOD_INTER);
			
			//同一模块下不允许 url 重复
			if( !config.isCanRepeatUrl() && customInterfaceService.countByFullUrl(interFace.getModuleId(),
					module.getUrl() +interFace.getUrl(), interFace.getId()) >0 ){
				throw new MyException(MyError.E000004);
			}
			
			interFace.setFullUrl(module.getUrl() + interFace.getUrl());
			customInterfaceService.update(InterfaceAdapter.getModel(interFace), "接口", "");
			if(interFace.getId().equals(interFace.getProjectId())){
				throw new MyException(MyError.E000027);
			}
			luceneService.update(InterfaceAdapter.getSearchDto(interFace));
			
		} else {
			checkUserPermissionByProject(projectCache.get(interFace.getProjectId() ), ADD_INTER);
			if(!config.isCanRepeatUrl() && customInterfaceService.countByFullUrl(interFace.getModuleId(),module.getUrl() + interFace.getUrl(), null)>0){
				return new JsonResult(MyError.E000004);
			}
			interFace.setFullUrl(module.getUrl() + interFace.getUrl());
			mybatisInterfaceService.insert(InterfaceAdapter.getModel(interFace));
			luceneService.add(InterfaceAdapter.getSearchDto(interFace));
		}
		return new JsonResult(1, interFace);
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
			InterfaceWithBLOBs interFace = mybatisInterfaceService.getById( tempId );
			checkUserPermissionByProject(interFace.getProjectId(), DEL_INTER);
			customInterfaceService.delete(interFace.getId(), "接口", "");
			luceneService.delete(new SearchDto(interFace.getId()));
		}
		return new JsonResult(1, null);
	}

	@RequestMapping("/changeSequence.do")
	@ResponseBody
	public JsonResult changeSequence(@RequestParam String id,@RequestParam String changeId) throws MyException {
		InterfaceWithBLOBs change = mybatisInterfaceService.getById(changeId);
		InterfaceWithBLOBs model = mybatisInterfaceService.getById(id);
		checkUserPermissionByProject(model.getProjectId(), MOD_INTER);
		checkUserPermissionByProject(change.getProjectId(), MOD_INTER);
		
		int modelSequence = model.getSequence();
		
		model.setSequence(change.getSequence());
		change.setSequence(modelSequence);

		mybatisInterfaceService.update(model);
		mybatisInterfaceService.update(change);
		return new JsonResult(1, null);
	}
}
