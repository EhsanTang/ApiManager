package cn.crap.controller.user;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import cn.crap.adapter.InterfaceAdapter;
import cn.crap.dto.InterfaceDto;
import cn.crap.dto.InterfacePDFDto;
import cn.crap.model.mybatis.Project;
import cn.crap.service.mybatis.custom.CustomErrorService;
import cn.crap.service.mybatis.custom.CustomInterfaceService;
import cn.crap.service.mybatis.imp.MybatisInterfaceService;
import cn.crap.service.mybatis.imp.MybatisRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.SearchDto;
import cn.crap.enumeration.MonitorType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.service.ICacheService;
import cn.crap.service.ISearchService;
import cn.crap.model.mybatis.Error;
import cn.crap.model.mybatis.*;
import cn.crap.springbeans.Config;
import cn.crap.utils.Const;
import cn.crap.utils.DateFormartUtil;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;
import net.sf.json.JSONArray;

@Controller
@RequestMapping("/user/interface")
public class InterfaceController extends BaseController{

	@Autowired
	private CustomInterfaceService customInterfaceService;
	@Autowired
	private MybatisInterfaceService mybatisInterfaceService;
	@Autowired
	private ICacheService cacheService;
	@Autowired
	private ISearchService luceneService;
	@Autowired
	private CustomErrorService customErrorService;
	@Autowired
	private Config config;
	
	
	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(@RequestParam String projectId, @RequestParam String moduleId, String interfaceName, String url,
			@RequestParam(defaultValue = "1") Integer currentPage) throws MyException{
		Page page= new Page(15, currentPage);
		hasPermission(cacheService.getProject(projectId), view);

		InterfaceCriteria example = new InterfaceCriteria();
		InterfaceCriteria.Criteria criteria = example.createCriteria().andModuleIdEqualTo(moduleId);
		if (!MyString.isEmpty(interfaceName)){
			criteria.andInterfaceNameLike("%" + interfaceName + "%");
		}
		if (!MyString.isEmpty(url)){
			criteria.andFullUrlLike("%" + url + "%");
		}

		List<InterfaceDto> interfaces = InterfaceAdapter.getDto(mybatisInterfaceService.selectByExample(example));
		JsonResult result = new JsonResult(1, interfaces, page);
		result.putOthers("crumbs", Tools.getCrumbs("接口列表:"+cacheService.getModuleName(moduleId),"void"))
				.putOthers("module",cacheService.getModule(moduleId));

		return result;
		
	}

	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult detail(@RequestParam String id, String moduleId) throws MyException {
		InterfaceWithBLOBs model;
		if(!id.equals(Const.NULL_ID)){
			model= mybatisInterfaceService.selectByPrimaryKey(id);
			hasPermission(cacheService.getProject(model.getProjectId()), view);
		}else{
			model = new InterfaceWithBLOBs();
			model.setModuleId( moduleId);
			Module module = cacheService.getModule(moduleId);
			if(!MyString.isEmpty(module.getTemplateId())){
				InterfaceWithBLOBs template = mybatisInterfaceService.selectByPrimaryKey(module.getTemplateId());
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
	public JsonResult copy(@ModelAttribute InterfaceWithBLOBs interFace) throws MyException, IOException {
		//判断是否拥有该模块的权限
		hasPermission(cacheService.getProject(interFace.getProjectId()), addInter);
		Module module = cacheService.getModule(interFace.getModuleId());

		if(!config.isCanRepeatUrl()){
			InterfaceCriteria example = new InterfaceCriteria();
			InterfaceCriteria.Criteria criteria = example.createCriteria();
			criteria.andModuleIdEqualTo(interFace.getModuleId()).andFullUrlEqualTo(module.getUrl() + interFace.getUrl());
			if (mybatisInterfaceService.countByExample(example) > 0){
				throw new MyException("000004");
			}
		}
		interFace.setId(null);
		interFace.setFullUrl(module.getUrl() + interFace.getUrl());
		mybatisInterfaceService.insert(interFace);

		luceneService.add(InterfaceAdapter.getSearchDto(cacheService, interFace));
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
	public JsonResult getRequestExam(@ModelAttribute InterfaceWithBLOBs interFace) {
		customInterfaceService.getInterFaceRequestExam(interFace);
		return new JsonResult(1, interFace);
	}

	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport
	public JsonResult addOrUpdate(@ModelAttribute InterfaceWithBLOBs interFace) throws IOException, MyException {
		Assert.notNull(interFace.getProjectId(), "projectId can't be null");

		if(MyString.isEmpty(interFace.getUrl())) {
			return new JsonResult(new MyException("000005"));
		}
		interFace.setUrl(interFace.getUrl().trim());
		
		/**
		 * 根据选着的错误码id，组装json字符串
		 */
		String errorIds = interFace.getErrorList();
		List<Error> errors  = customErrorService.queryByProjectIdAndErrorCode(interFace.getProjectId(), Tools.getIdsFromField(errorIds));
		interFace.setErrors(JSONArray.fromObject(errors).toString());

		LoginInfoDto user = (LoginInfoDto) Tools.getUser();
		interFace.setUpdateBy("userName："+user.getUserName()+" | trueName："+ user.getTrueName());
		interFace.setUpdateTime(new Date());
		
		//请求示例为空，则自动添加
		if(MyString.isEmpty(interFace.getRequestExam())){
			customInterfaceService.getInterFaceRequestExam(interFace);
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

		Module module = cacheService.getModule(interFace.getModuleId());
		if (!MyString.isEmpty(interFace.getId())) {
			String oldModuleId = mybatisInterfaceService.selectByPrimaryKey(interFace.getId()).getModuleId();
			String projectId = cacheService.getModule(oldModuleId).getProjectId();
			Project project = cacheService.getProject( interFace.getProjectId() );

			// 接口只能在同一个项目下的模块中移动
			if( !projectId.equals(project.getId())){
				throw new MyException("000047");
			}
			// 判断是否有修改模块的权限
			hasPermission(project, modInter);
			
			//同一模块下不允许 url 重复
			if( !config.isCanRepeatUrl() && customInterfaceService.countByFullUrl(interFace.getModuleId(),
					module.getUrl() +interFace.getUrl(), interFace.getId()) >0 ){
				throw new MyException("000004");
			}
			
			interFace.setFullUrl(module.getUrl() + interFace.getUrl());
			customInterfaceService.update(interFace, "接口", "");
			// TODO 添加日志
			if(interFace.getId().equals(interFace.getProjectId())){
				throw new MyException("000027");
			}
			luceneService.update(InterfaceAdapter.getSearchDto(cacheService, interFace));
			
		} else {
			hasPermission(cacheService.getProject( interFace.getProjectId() ), addInter);
			if(!config.isCanRepeatUrl() && customInterfaceService.countByFullUrl(interFace.getModuleId(),module.getUrl() + interFace.getUrl(), null)>0){
				return new JsonResult(new MyException("000004"));
			}
			interFace.setFullUrl(module.getUrl() + interFace.getUrl());
			mybatisInterfaceService.insert(interFace);
			luceneService.add(InterfaceAdapter.getSearchDto(cacheService, interFace));
		}
		return new JsonResult(1, interFace);
	}

	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(String id, String ids) throws MyException, IOException{
		if( MyString.isEmpty(id) && MyString.isEmpty(ids)){
			throw new MyException("000029");
		}
		if( MyString.isEmpty(ids) ){
			ids = id;
		}
		
		for(String tempId : ids.split(",")){
			if(MyString.isEmpty(tempId)){
				continue;
			}
			InterfaceWithBLOBs interFace = mybatisInterfaceService.selectByPrimaryKey( tempId );
			hasPermission(cacheService.getProject( interFace.getProjectId() ), delInter);
			customInterfaceService.delete(interFace.getId(), "接口", "");
			luceneService.delete(new SearchDto(interFace.getId()));
		}
		return new JsonResult(1, null);
	}

	@RequestMapping("/changeSequence.do")
	@ResponseBody
	public JsonResult changeSequence(@RequestParam String id,@RequestParam String changeId) throws MyException {
		InterfaceWithBLOBs change = mybatisInterfaceService.selectByPrimaryKey(changeId);
		InterfaceWithBLOBs model = mybatisInterfaceService.selectByPrimaryKey(id);
		hasPermission(cacheService.getProject( model.getProjectId() ), modInter);
		hasPermission(cacheService.getProject( change.getProjectId() ), modInter);
		
		int modelSequence = model.getSequence();
		
		model.setSequence(change.getSequence());
		change.setSequence(modelSequence);

		mybatisInterfaceService.update(model);
		mybatisInterfaceService.update(change);
		return new JsonResult(1, null);
	}
	public HttpServletResponse getResponse(){
		return response;
	}
}
