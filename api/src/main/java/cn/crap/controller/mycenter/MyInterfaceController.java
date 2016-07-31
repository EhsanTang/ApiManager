package cn.crap.controller.mycenter;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.dto.SearchDto;
import cn.crap.enumeration.DataCeneterType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.dao.ICacheDao;
import cn.crap.inter.service.ICacheService;
import cn.crap.inter.service.IDataCenterService;
import cn.crap.inter.service.IErrorService;
import cn.crap.inter.service.IInterfaceService;
import cn.crap.model.DataCenter;
import cn.crap.model.Error;
import cn.crap.model.Interface;
import cn.crap.model.User;
import cn.crap.utils.Const;
import cn.crap.utils.DateFormartUtil;
import cn.crap.utils.GetBeanBySetting;
import cn.crap.utils.MyCookie;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;
import net.sf.json.JSONArray;

@Scope("prototype")
@Controller
@RequestMapping("/myCenter/interface")
public class MyInterfaceController extends BaseController<Interface>{

	@Autowired
	private IInterfaceService interfaceService;
	@Autowired
	private IDataCenterService dataCenterService;
	@Autowired
	private IErrorService errorService;
	@Autowired
	private ICacheService cacheService;
	
	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(@ModelAttribute Interface interFace,
			@RequestParam(defaultValue = "1") Integer currentPage){
		String token = MyCookie.getCookie(Const.COOKIE_TOKEN, false, request);
		User user = (User) cacheService.getObj(Const.CACHE_USER + token);
		page.setCurrentPage(currentPage);
		
		List<String> moduleIds = dataCenterService.getList(  null, DataCeneterType.MODULE.name(), user.getId() );
		map = Tools.getMap("moduleId", interFace.getModuleId(),
				"interfaceName|like", interFace.getInterfaceName(),
				"url|like", interFace.getUrl()==null?"":interFace.getUrl().trim(),
				"moduleId|in", moduleIds);
		
		List<Interface> interfaces = interfaceService.findByMap(
				map, " new Interface(id,moduleId,interfaceName,version,createTime,updateBy,updateTime)", page, null);
		map.clear();
		
		List<DataCenter> modules = null;
		// 搜索接口时，module为空
		if (interFace.getModuleId() != null) {
			map = Tools.getMap("parentId", interFace.getModuleId(), "type", "MODULE", "moduleId|in", moduleIds);
			modules = dataCenterService.findByMap(map, null, null);
		}
		
		map.put("interfaces", interfaces);
		map.put("modules", modules);
		return new JsonResult(1, map, page, 
				Tools.getMap("crumbs", Tools.getCrumbs("接口列表:"+cacheService.getModuleName(interFace.getModuleId()),"void"),
						"module",cacheService.getModule(interFace.getModuleId())));
	}

	@RequestMapping("/detail.do")
	@ResponseBody
	@AuthPassport
	public JsonResult detail(@ModelAttribute Interface interFace) throws MyException {
		String token = MyCookie.getCookie(Const.COOKIE_TOKEN, false, request);
		User user = (User) cacheService.getObj(Const.CACHE_USER + token);
		
		if(!interFace.getId().equals(Const.NULL_ID)){
			model= interfaceService.get(interFace.getId());
			if(! cacheService.getModule( model.getModuleId() ).equals(user.getId())){
				throw new MyException("000003");
			}
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
	@AuthPassport
	public JsonResult copy(@ModelAttribute Interface interFace) throws MyException, IOException {
		//TODO 判断是否拥有该模块的权限
		if(interfaceService.getCount(Tools.getMap("url",interFace.getUrl()))>0){
			throw new MyException("000004");
		}
		interFace.setId(null);
		interfaceService.save(interFace);
		GetBeanBySetting.getSearchService().update(interFace.toSearchDto());
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
			map = Tools.getMap("errorCode|in", Tools.getIdsFromField(errorIds));

			DataCenter dc = dataCenterService.get(interFace
					.getModuleId());
			while (dc != null && !dc.getParentId().equals("0")) {
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
		User user = (User) cacheService.getObj(Const.CACHE_USER + token);
		interFace.setUpdateBy("userName："+user.getUserName()+" | trueName："+ user.getTrueName());
		interFace.setUpdateTime(DateFormartUtil.getDateByFormat(DateFormartUtil.YYYY_MM_DD_HH_mm));
		//请求示例为空，则自动添加
		if(MyString.isEmpty(interFace.getRequestExam())){
			interfaceService.getInterFaceRequestExam(interFace);
		}
		if (!MyString.isEmpty(interFace.getId())) {
			if( interfaceService.getCount(Tools.getMap("url",interFace.getUrl(),"id|!=",interFace.getId())) >0 ){
				throw new MyException("000004");
			}
			interfaceService.update(interFace, "接口", "");
			GetBeanBySetting.getSearchService().add(interFace.toSearchDto());
		} else {
			interFace.setId(null);
			if(interfaceService.getCount(Tools.getMap("url",interFace.getUrl()))>0){
				return new JsonResult(new MyException("000004"));
			}
			interfaceService.save(interFace);
			GetBeanBySetting.getSearchService().add(interFace.toSearchDto());
		}
		return new JsonResult(1, interFace);
	}

	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(@ModelAttribute Interface interFace) throws MyException, IOException {
		interFace = interfaceService.get(interFace.getId());
		Tools.hasAuth(Const.AUTH_INTERFACE, interFace.getModuleId());
		interfaceService.delete(interFace, "接口", "");
		GetBeanBySetting.getSearchService().delete(new SearchDto(interFace.getId()));
		return new JsonResult(1, null);
	}

	@RequestMapping("/changeSequence.do")
	@ResponseBody
	public JsonResult changeSequence(@RequestParam String id,@RequestParam String changeId) throws MyException {
		Interface change = interfaceService.get(changeId);
		model = interfaceService.get(id);
		Tools.hasAuth(Const.AUTH_INTERFACE, model.getModuleId());
		
		int modelSequence = model.getSequence();
		
		model.setSequence(change.getSequence());
		change.setSequence(modelSequence);
		
		interfaceService.update(model);
		interfaceService.update(change);
		return new JsonResult(1, null);
	}
}
