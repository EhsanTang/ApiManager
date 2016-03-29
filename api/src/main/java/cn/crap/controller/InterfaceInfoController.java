package cn.crap.controller;

import java.util.List;

import net.sf.json.JSONArray;

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
import cn.crap.inter.InterfaceInfoService;
import cn.crap.inter.ModuleInfoService;
import cn.crap.model.ErrorInfoModel;
import cn.crap.model.InterfaceInfoModel;
import cn.crap.model.ModuleInfoModel;
import cn.crap.utils.Const;
import cn.crap.utils.DateFormartUtil;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Scope("prototype")
@Controller
@RequestMapping("/interfaceInfo")
public class InterfaceInfoController extends BaseController<InterfaceInfoModel> {

	@Autowired
	private InterfaceInfoService interfaceInfoService;
	@Autowired
	private ModuleInfoService moduleInfoService;
	@Autowired
	private ErrorInfoService errorInfoService;

	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@ModelAttribute InterfaceInfoModel interFaceInfo,
			@RequestParam(defaultValue = "1") Integer currentPage) {
		page.setCurrentPage(currentPage);
		map = Tools.getMap("moduleId", interFaceInfo.getModuleId(),
				"interfaceName|like", interFaceInfo.getInterfaceName(),"url|like", interFaceInfo.getUrl()==null?"":interFaceInfo.getUrl().trim());
		List<InterfaceInfoModel> interfaces = interfaceInfoService.findByMap(
				map, page, null);
		map.clear();
		List<ModuleInfoModel> modules = null;
		// 搜索接口时，module为空
		if (interFaceInfo.getModuleId() != null) {
			map = Tools.getMap("parentId", interFaceInfo.getModuleId());
			modules = moduleInfoService.findByMap(map, null, null);
		}
		map.put("interfaces", interfaces);
		map.put("modules", modules);
		return new JsonResult(1, map, page);
	}

	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult detail(@ModelAttribute InterfaceInfoModel interFaceInfo,
			String currentId) {
		interFaceInfo = interfaceInfoService.get(interFaceInfo.getId());
		if (interFaceInfo == null) {
			interFaceInfo = new InterfaceInfoModel();
			interFaceInfo.setModuleId(currentId);
			interFaceInfo.setModuleName(moduleInfoService.get(currentId)
					.getModuleName());
		}
		return new JsonResult(1, interFaceInfo);
	}

	@RequestMapping("/webDetail.do")
	@ResponseBody
	public JsonResult webDetail(@ModelAttribute InterfaceInfoModel interFaceInfo) {
		interFaceInfo = interfaceInfoService.get(interFaceInfo.getId());
		return new JsonResult(1, interFaceInfo);
	}

	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_INTERFACE)
	public JsonResult addOrUpdate(
			@ModelAttribute InterfaceInfoModel interFaceInfo) {
		if(interFaceInfo.getUrl()==null||interFaceInfo.getUrl().trim().equals(""))
			return new JsonResult(new BiyaoBizException("000005"));
		interFaceInfo.setUrl(interFaceInfo.getUrl().trim());
		String errorIds = interFaceInfo.getErrorList();
		if (errorIds != null && !errorIds.equals("")) {
			errorIds = Tools.getIdsFromField(errorIds);
			map = Tools.getMap("errorCode|in", "'0'," + errorIds);

			ModuleInfoModel module = moduleInfoService.get(interFaceInfo
					.getModuleId());
			while (module != null && !module.getParentId().equals("0")) {
				module = moduleInfoService.get(module.getParentId());
			}
			map.put("moduleId", module.getModuleId());
			List<ErrorInfoModel> errors = errorInfoService.findByMap(map, null,
					null);
			interFaceInfo.setErrors(JSONArray.fromObject(errors).toString());
		}else{
			interFaceInfo.setErrors("[]");
		}
		interFaceInfo.setUpdateBy("userName："+request.getSession().getAttribute(Const.SESSION_ADMIN).toString()+" | trueName："+
				request.getSession().getAttribute(Const.SESSION_ADMIN_TRUENAME).toString());
		interFaceInfo.setUpdateTime(DateFormartUtil.getDateByFormat(DateFormartUtil.YYYY_MM_DD_HH_mm));
		
		if (!MyString.isEmpty(interFaceInfo.getId())) {
			interfaceInfoService.update(interFaceInfo);
		} else {
			interFaceInfo.setId(null);
			InterfaceInfoModel example = new InterfaceInfoModel();
			example.setUrl(interFaceInfo.getUrl());
			if(interfaceInfoService.findByExample(example).size()>0){
				return new JsonResult(new BiyaoBizException("000004"));
			}
			interfaceInfoService.save(interFaceInfo);
		}
		return new JsonResult(1, interFaceInfo);
	}

	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(@ModelAttribute InterfaceInfoModel interFaceInfo) throws BiyaoBizException {
		interFaceInfo = interfaceInfoService.get(interFaceInfo.getId());
		Tools.hasAuth(Const.AUTH_INTERFACE, request.getSession(), interFaceInfo.getModuleId());
		interfaceInfoService.delete(interFaceInfo);
		return new JsonResult(1, null);
	}

}
