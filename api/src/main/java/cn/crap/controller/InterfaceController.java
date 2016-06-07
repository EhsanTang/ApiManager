package cn.crap.controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.crap.dto.ErrorDto;
import cn.crap.dto.ParamDto;
import cn.crap.dto.ResponseParamDto;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.IErrorService;
import cn.crap.inter.service.IInterfaceService;
import cn.crap.inter.service.IModuleService;
import cn.crap.model.Error;
import cn.crap.model.Interface;
import cn.crap.model.Module;
import cn.crap.utils.Cache;
import cn.crap.utils.Const;
import cn.crap.utils.DateFormartUtil;
import cn.crap.utils.Html2Pdf;
import cn.crap.utils.HttpPostGet;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Scope("prototype")
@Controller
@RequestMapping("/interface")
public class InterfaceController extends BaseController<Interface>{

	@Autowired
	private IInterfaceService interfaceService;
	@Autowired
	private IModuleService moduleService;
	@Autowired
	private IErrorService errorService;

	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(@ModelAttribute Interface interFace,
			@RequestParam(defaultValue = "1") Integer currentPage){
		return interfaceService.getInterfaceList(page, map, interFace, currentPage);
	}

	@RequestMapping("/detail.do")
	@ResponseBody
	@AuthPassport
	public JsonResult detail(@ModelAttribute Interface interFace) {
		if(!interFace.getId().equals(Const.NULL_ID)){
			model= interfaceService.get(interFace.getId());
		}else{
			model = new Interface();
			model.setModuleId(interFace.getModuleId());
		}
		return new JsonResult(1, model);
	}
	

	@RequestMapping("/detail/pdf.do")
	public String pdf(@ModelAttribute Interface interFace) throws Exception {
		interFace = interfaceService.get(interFace.getId());
		request.setAttribute("model", interFace);
		Object params = JSONArray.toArray(JSONArray.fromObject(interFace.getParam()),ParamDto.class);
		request.setAttribute("header", params);
		Object responseParam = JSONArray.toArray(JSONArray.fromObject(interFace.getResponseParam()),ResponseParamDto.class);
		request.setAttribute("responseParam", responseParam);
		Object errors = JSONArray.toArray(JSONArray.fromObject(interFace.getErrors()),ErrorDto.class);
		request.setAttribute("errors", errors);
		return "/WEB-INF/views/interFacePdf.jsp";
	}
	
	@RequestMapping("/download/pdf.do")
	@ResponseBody
	public void download(@ModelAttribute Interface interFace,HttpServletRequest req) throws Exception {
		interFace = interfaceService.get(interFace.getId());
		String displayFilename = "CrapApi|"+interFace.getInterfaceName()+".pdf";
        byte[] buf = new byte[1024 * 1024 * 10];  
        int len = 0; 
        ServletOutputStream ut = null;
        BufferedInputStream br = null;  
        response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "must-revalidate, no-transform");  
        response.setDateHeader("Expires", 0L);  
 
        String userAgent = req.getHeader("User-Agent");  
        boolean isIE = (userAgent != null) && (userAgent.toLowerCase().indexOf("msie") != -1); 
        response.setContentType("application/x-download"); 
        if (isIE) {  
            displayFilename = URLEncoder.encode(displayFilename, "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + displayFilename + "\"");  
        } else {  
            displayFilename = new String(displayFilename.getBytes("UTF-8"), "ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + displayFilename);  
        } 
        br = new BufferedInputStream(new FileInputStream(Html2Pdf.createPdf(req,interFace.getId())));
        ut = response.getOutputStream();  
        while ((len = br.read(buf)) != -1)  
             ut.write(buf, 0, len);
        br.close();
	}
	
	@RequestMapping("/copy.do")
	@ResponseBody
	@AuthPassport
	public JsonResult copy(@ModelAttribute Interface interFace) throws MyException {
		if(interfaceService.getCount(Tools.getMap("url",interFace.getUrl()))>0){
			throw new MyException("000004");
		}
		interFace.setId(null);
		interfaceService.save(interFace);
		return new JsonResult(1, interFace);
	}
	
	@RequestMapping("/webList.do")
	@ResponseBody
	public JsonResult webList(@ModelAttribute Interface interFace,
			@RequestParam(defaultValue = "1") Integer currentPage,String password,String visitCode) throws MyException{
		Module module = moduleService.get(interFace.getModuleId());
		Tools.canVisitModule(module.getPassword(), password, visitCode, request);
		return interfaceService.getInterfaceList(page, map,interFace, currentPage);
	}

	@RequestMapping("/webDetail.do")
	@ResponseBody
	public JsonResult webDetail(@ModelAttribute Interface interFace,String password,String visitCode) throws MyException {
		interFace = interfaceService.get(interFace.getId());
		if(interFace!=null){
			Tools.canVisitModule(Cache.getModule(interFace.getModuleId()).getPassword(), password, visitCode, request);
			/**
			 * 查询相同模块下，相同接口名的其它版本号
			 */
			List<Interface> versions = interfaceService.findByMap(
					Tools.getMap("moduleId",interFace.getModuleId(),"interfaceName",interFace.getInterfaceName(),"version|<>",interFace.getVersion()), null, null);
			return new JsonResult(1, interFace, null, 
					Tools.getMap("versions", versions, "crumbs",
							Tools.getCrumbs( Cache.getModuleName(interFace.getModuleId()), "web.do#/webInterface/list/"+interFace.getModuleId() +"/" +Cache.getModuleName(interFace.getModuleId())
							,interFace.getInterfaceName() , "void")));
		}else{
			throw new MyException("000012");
		}
	}
	
	/**
	 * 根据参数生成请求示例
	 * @param interFace
	 * @return
	 */
	@RequestMapping("/getRequestExam.do")
	@ResponseBody
	public JsonResult getRequestExam(@ModelAttribute Interface interFace) {
		interfaceService.getInterFaceRequestExam(interFace);
		return new JsonResult(1, interFace);
	}

	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport(authority=Const.AUTH_INTERFACE)
	public JsonResult addOrUpdate(
			@ModelAttribute Interface interFace) {
		if(MyString.isEmpty(interFace.getUrl()))
			return new JsonResult(new MyException("000005"));
		interFace.setUrl(interFace.getUrl().trim());
		
		/**
		 * 根据选着的错误码id，组装json字符串
		 */
		String errorIds = interFace.getErrorList();
		if (errorIds != null && !errorIds.equals("")) {
			map = Tools.getMap("errorCode|in", Tools.getIdsFromField(errorIds));

			Module module = moduleService.get(interFace
					.getModuleId());
			while (module != null && !module.getParentId().equals("0")) {
				module = moduleService.get(module.getParentId());
			}
			map.put("moduleId", module.getId());
			List<Error> errors = errorService.findByMap(map, null,
					null);
			interFace.setErrors(JSONArray.fromObject(errors).toString());
		}else{
			interFace.setErrors("[]");
		}
		
		/**
		 * 如果参数为空，则需要设置一个大小为0的空json数组
		 */
		if(MyString.isEmpty(interFace.getParam())){
			interFace.setParam("[]");
		}
		if(MyString.isEmpty(interFace.getResponseParam())){
			interFace.setResponseParam("[]");
		}
		
		interFace.setUpdateBy("userName："+request.getSession().getAttribute(Const.SESSION_ADMIN).toString()+" | trueName："+
				request.getSession().getAttribute(Const.SESSION_ADMIN_TRUENAME).toString());
		interFace.setUpdateTime(DateFormartUtil.getDateByFormat(DateFormartUtil.YYYY_MM_DD_HH_mm));
		//请求示例为空，则自动添加
		if(MyString.isEmpty(interFace.getRequestExam())){
			interfaceService.getInterFaceRequestExam(interFace);
		}
		if (!MyString.isEmpty(interFace.getId())) {
			interfaceService.update(interFace);
		} else {
			interFace.setId(null);
			if(interfaceService.getCount(Tools.getMap("url",interFace.getUrl()))>0){
				return new JsonResult(new MyException("000004"));
			}
			interfaceService.save(interFace);
		}
		return new JsonResult(1, interFace);
	}

	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(@ModelAttribute Interface interFace) throws MyException {
		interFace = interfaceService.get(interFace.getId());
		Tools.hasAuth(Const.AUTH_INTERFACE, request.getSession(), interFace.getModuleId());
		interfaceService.delete(interFace, "删除接口", "");
		return new JsonResult(1, null);
	}

	@RequestMapping("/changeSequence.do")
	@ResponseBody
	@AuthPassport
	@Override
	public JsonResult changeSequence(@RequestParam String id,@RequestParam String changeId) {
		Interface change = interfaceService.get(changeId);
		model = interfaceService.get(id);
		int modelSequence = model.getSequence();
		
		model.setSequence(change.getSequence());
		change.setSequence(modelSequence);
		
		interfaceService.update(model);
		interfaceService.update(change);
		return new JsonResult(1, null);
	}
	
	@RequestMapping("/debug.do")
	@ResponseBody
	public JsonResult debug(@RequestParam String params, @RequestParam String headers,
			@RequestParam String debugMethod,@RequestParam String url) throws Exception {
		JSONArray jsonParams = JSONArray.fromObject(params);
		JSONArray jsonHeaders = JSONArray.fromObject(headers);
		Map<String,String> httpParams = new HashMap<String,String>();
		for(int i=0;i<jsonParams.size();i++){
			JSONObject param = jsonParams.getJSONObject(i);
			for(Object paramKey:param.keySet()){
				httpParams.put(paramKey.toString(), param.getString(paramKey.toString()));
			}
		}
		
		Map<String,String> httpHeaders = new HashMap<String,String>();
		for(int i=0;i<jsonHeaders.size();i++){
			JSONObject param = jsonHeaders.getJSONObject(i);
			for(Object paramKey:param.keySet()){
				httpHeaders.put(paramKey.toString(), param.getString(paramKey.toString()));
			}
		}
		try{
			switch(debugMethod){
			case "POST":
				return new JsonResult(1, Tools.getMap("debugResult",HttpPostGet.post(url, httpParams, httpHeaders)));
			case "GET":
				return new JsonResult(1, Tools.getMap("debugResult",HttpPostGet.get(url, httpParams, httpHeaders)));
			case "PUT":
				return new JsonResult(1, Tools.getMap("debugResult",HttpPostGet.put(url, httpParams, httpHeaders)));
			case "DELETE":
				return new JsonResult(1, Tools.getMap("debugResult",HttpPostGet.delete(url, httpParams, httpHeaders)));
			case "HEAD":
				return new JsonResult(1, Tools.getMap("debugResult",HttpPostGet.head(url, httpParams, httpHeaders)));
			case "OPTIONS":
				return new JsonResult(1, Tools.getMap("debugResult",HttpPostGet.options(url, httpParams, httpHeaders)));
			case "TRACE":
				return new JsonResult(1, Tools.getMap("debugResult",HttpPostGet.trace(url, httpParams, httpHeaders)));
			default:
				return new JsonResult(1, Tools.getMap("debugResult","不支持的请求方法："+debugMethod));
		}
		}catch(Exception e){
			return new JsonResult(1, Tools.getMap("debugResult","调试出错\r\nmessage:"+e.getMessage()));
		}
	}

}
