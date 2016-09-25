package cn.crap.controller.back;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.SearchDto;
import cn.crap.enumeration.DataCeneterType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.ICacheService;
import cn.crap.inter.service.IDataCenterService;
import cn.crap.inter.service.IErrorService;
import cn.crap.inter.service.IInterfaceService;
import cn.crap.inter.service.ISearchService;
import cn.crap.model.DataCenter;
import cn.crap.model.Error;
import cn.crap.model.Interface;
import cn.crap.utils.Const;
import cn.crap.utils.DateFormartUtil;
import cn.crap.utils.MyCookie;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;
import net.sf.json.JSONArray;

@Controller
@RequestMapping("/back/interface")
public class BackInterfaceController extends BaseController<Interface>{

	@Autowired
	private IInterfaceService interfaceService;
	@Autowired
	private IDataCenterService dataCenterService;
	@Autowired
	private IErrorService errorService;
	@Autowired
	private ICacheService cacheService;
	@Autowired
	private ISearchService luceneService;
	
	
	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(@ModelAttribute Interface interFace,
			@RequestParam(defaultValue = "1") Integer currentPage){
		Page page= new Page(15);
		
		List<String> moduleIds = null;
		// 如果用户为普通用户，则只能查看自己的模块
		LoginInfoDto user = Tools.getUser();
		if(user != null && user.getType() == 1){
			moduleIds = dataCenterService.getList(  null, DataCeneterType.MODULE.name(), Tools.getUser().getId() );
		}
		
		return interfaceService.getInterfaceList(page, moduleIds, interFace, currentPage);
	}

	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult detail(@ModelAttribute Interface interFace) throws MyException {
		Interface model;
		if(!interFace.getId().equals(Const.NULL_ID)){
			model= interfaceService.get(interFace.getId());
			Tools.hasAuth("", model.getModuleId() );
		}else{
			model = new Interface();
			model.setModuleId(interFace.getModuleId());
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
		Tools.hasAuth("", interFace.getModuleId());
		
		if(interfaceService.getCount(Tools.getMap("fullUrl", interFace.getModuleUrl()+interFace.getUrl()))>0){
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
			Map<String,Object> map = Tools.getMap("errorCode|in", Tools.getIdsFromField(errorIds));

			DataCenter dc = dataCenterService.get(interFace.getModuleId());
			while (!MyString.isEmpty(dc.getId()) && !dc.getParentId().equals("0") && !dc.getParentId().equals(Const.PRIVATE_MODULE)) {
				dc = dataCenterService.get(dc.getParentId());
			}
			map.put("moduleId", dc.getId());
			List<Error> errors = errorService.findByMap(map, null,
					null);
			interFace.setErrors(JSONArray.fromObject(errors).toString());
		}else{
			interFace.setErrors("[]");
		}
		
		String token = MyCookie.getCookie(Const.COOKIE_TOKEN, false, request);
		LoginInfoDto user = (LoginInfoDto) cacheService.getObj(Const.CACHE_USER + token);
		interFace.setUpdateBy("userName："+user.getUserName()+" | trueName："+ user.getTrueName());
		interFace.setUpdateTime(DateFormartUtil.getDateByFormat(DateFormartUtil.YYYY_MM_DD_HH_mm));
		
		//请求示例为空，则自动添加
		if(MyString.isEmpty(interFace.getRequestExam())){
			interfaceService.getInterFaceRequestExam(interFace);
		}
		
		if (!MyString.isEmpty(interFace.getId())) {
			// 判断是否有修改模块的权限
			Tools.hasAuth(Const.AUTH_INTERFACE, interfaceService.get(interFace.getId()).getModuleId());
			
			if( interfaceService.getCount(Tools.getMap("fullUrl",interFace.getModuleUrl()+interFace.getUrl(),"id|!=",interFace.getId())) >0 ){
				throw new MyException("000004");
			}
			interfaceService.update(interFace, "接口", "");
			interFace.setFullUrl(interFace.getModuleUrl()+interFace.getUrl());
			luceneService.update(interFace.toSearchDto(cacheService));
			
		} else {
			// 判断是否有新建模块的权限
			Tools.hasAuth(Const.AUTH_INTERFACE, interFace.getModuleId());
			
			interFace.setId(null);
			if(interfaceService.getCount(Tools.getMap("fullUrl",interFace.getModuleUrl()+interFace.getUrl()))>0){
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
		Tools.hasAuth(Const.AUTH_INTERFACE, interFace.getModuleId());
		interfaceService.delete(interFace, "接口", "");
		luceneService.delete(new SearchDto(interFace.getId()));
		return new JsonResult(1, null);
	}

	@RequestMapping("/changeSequence.do")
	@ResponseBody
	public JsonResult changeSequence(@RequestParam String id,@RequestParam String changeId) throws MyException {
		Interface change = interfaceService.get(changeId);
		Interface model = interfaceService.get(id);
		Tools.hasAuth(Const.AUTH_INTERFACE, model.getModuleId());
		
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
