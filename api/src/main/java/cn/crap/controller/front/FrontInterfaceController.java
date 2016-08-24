package cn.crap.controller.front;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
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
import cn.crap.enumeration.DataCeneterType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.ICacheService;
import cn.crap.inter.service.IDataCenterService;
import cn.crap.inter.service.IInterfaceService;
import cn.crap.model.DataCenter;
import cn.crap.model.Interface;
import cn.crap.utils.Config;
import cn.crap.utils.Const;
import cn.crap.utils.Html2Pdf;
import cn.crap.utils.HttpPostGet;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Scope("prototype")
@Controller
@RequestMapping("/front/interface")
public class FrontInterfaceController extends BaseController<Interface>{

	@Autowired
	private IInterfaceService interfaceService;
	@Autowired
	private IDataCenterService dataCenterService;
	@Autowired
	private ICacheService cacheService;
	
	@RequestMapping("/detail/pdf.do")
	public String pdf(@ModelAttribute Interface interFace) throws Exception {
		interFace = interfaceService.get(interFace.getId());
		request.setAttribute("model", interFace);
		if(interFace.getParam().startsWith("form=")){
			request.setAttribute("formParams", JSONArray.toArray(JSONArray.fromObject(interFace.getParam().substring(5)),ParamDto.class));
		}else{
			request.setAttribute("customParams", interFace.getParam());
		}
		request.setAttribute("headers", JSONArray.toArray(JSONArray.fromObject(interFace.getHeader()),ParamDto.class));
		
		Object responseParam = JSONArray.toArray(JSONArray.fromObject(interFace.getResponseParam()),ResponseParamDto.class);
		request.setAttribute("responseParam", responseParam);
		Object errors = JSONArray.toArray(JSONArray.fromObject(interFace.getErrors()),ErrorDto.class);
		request.setAttribute("errors", errors);
		request.setAttribute("MAIN_COLOR", cacheService.getSetting("MAIN_COLOR").getValue());
		return "/WEB-INF/views/interFacePdf.jsp";
	}
	
	@RequestMapping("/download/pdf.do")
	@ResponseBody
	public void download(@ModelAttribute Interface interFace,HttpServletRequest req) throws Exception {
		interFace = interfaceService.get(interFace.getId());
		String displayFilename = "CrapApi"+System.currentTimeMillis()+".pdf";
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
	
	
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult webList(@ModelAttribute Interface interFace,
			@RequestParam(defaultValue = "1") Integer currentPage,String password,String visitCode) throws MyException{
		// 查询公开和推荐的接口
		if(!Tools.moduleIdIsLegal(interFace.getModuleId())){
			@SuppressWarnings("unchecked")
			List<String> moduleIds = (List<String>) cacheService.getObj(Const.CACHE_TUIJIAN_OPEN_MODULEIDS);
			if(moduleIds == null){
				List<Byte> statuss = new ArrayList<Byte>();
				statuss.add(Byte.valueOf("1"));
				statuss.add(Byte.valueOf("3"));
				moduleIds = dataCenterService.getListByStatuss(statuss, DataCeneterType.MODULE.name(), null);
				cacheService.setObj(Const.CACHE_TUIJIAN_OPEN_MODULEIDS, moduleIds, Config.getCacheTime());
			}
			return interfaceService.getInterfaceList(page, moduleIds ,interFace, currentPage);
		}else{
			DataCenter dc = dataCenterService.get(interFace.getModuleId());
			Tools.canVisitModule(dc.getPassword(), password, visitCode, request);
			
			return interfaceService.getInterfaceList(page, null,interFace, currentPage);
		}
		
	}

	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult webDetail(@ModelAttribute Interface interFace,String password,String visitCode) throws MyException {
		interFace = interfaceService.get(interFace.getId());
		if(interFace!=null){
			Tools.canVisitModule(cacheService.getModule(interFace.getModuleId()).getPassword(), password, visitCode, request);
			/**
			 * 查询相同模块下，相同接口名的其它版本号
			 */
			List<Interface> versions = interfaceService.findByMap(
					Tools.getMap("moduleId",interFace.getModuleId(),"interfaceName",interFace.getInterfaceName(),"version|<>",interFace.getVersion()), null, null);
			return new JsonResult(1, interFace, null, 
					Tools.getMap("versions", versions, "crumbs",
							Tools.getCrumbs( cacheService.getModuleName(interFace.getModuleId()), "#/"+interFace.getProjectId() +"/interface/list/" +interFace.getModuleId() +"/" +cacheService.getModuleName(interFace.getModuleId())
							,interFace.getInterfaceName() , "void"), "module",cacheService.getModule(interFace.getModuleId()) ));
		}else{
			throw new MyException("000012");
		}
	}
	
	@RequestMapping("/debug.do")
	@ResponseBody
	public JsonResult debug(@RequestParam String params, @RequestParam String headers, @RequestParam(defaultValue="") String customParams,
			@RequestParam String debugMethod,@RequestParam String url, String moduleUrl) throws Exception {
		if(!MyString.isEmpty(moduleUrl))
			url = moduleUrl + url;
		
		JSONArray jsonParams = JSONArray.fromObject(params);
		JSONArray jsonHeaders = JSONArray.fromObject(headers);
		Map<String,String> httpParams = new HashMap<String,String>();
		for(int i=0;i<jsonParams.size();i++){
			JSONObject param = jsonParams.getJSONObject(i);
			for(Object paramKey:param.keySet()){
				if(url.contains("{"+paramKey.toString()+"}")){
					url = url.replace("{"+paramKey.toString()+"}", param.getString(paramKey.toString()));
				}else{
					httpParams.put(paramKey.toString(), param.getString(paramKey.toString()));
				}
				
			}
		}
		
		Map<String,String> httpHeaders = new HashMap<String,String>();
		for(int i=0;i<jsonHeaders.size();i++){
			JSONObject param = jsonHeaders.getJSONObject(i);
			for(Object paramKey:param.keySet()){
				httpHeaders.put(paramKey.toString(), param.getString(paramKey.toString()));
			}
		}
		// 如果自定义参数不为空，则表示需要使用post发送自定义包体
		if(!MyString.isEmpty(customParams)){
			return new JsonResult(1, Tools.getMap("debugResult",HttpPostGet.postBody(url, customParams, httpHeaders)));
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
