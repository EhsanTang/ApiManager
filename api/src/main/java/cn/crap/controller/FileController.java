package cn.crap.controller;

import java.io.File;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.crap.framework.base.BaseController;
import cn.crap.utils.DateFormartUtil;
import cn.crap.utils.Tools;

@Controller
public class FileController extends BaseController {
	private HashMap<String, String> extMap = new HashMap<String, String>();
	public FileController(){
		extMap.put("image", ",gif,jpg,jpeg,png,bmp,");
		//extMap.put("file", ",doc,docx,xls,xlsx,ppt,pdf,htm,html,txt,zip,rar,gz,bz2,");	
	}
	@RequestMapping(value="/file/upload.do")
	@ResponseBody
	public void upload(@RequestParam(value = "img", required = false) MultipartFile file) {
		String result = "";
	    String realFileName = file.getOriginalFilename();    
	    String destDir = Tools.getServicePath(request);
	    String saveUrl ="";
	    String suffix = realFileName.substring(realFileName.lastIndexOf(".") + 1).toLowerCase();
	    JSONObject obj = new JSONObject();
	    //拦截信息
	    if(file.getSize()>2*1024*1024){
	    	obj.put("error", 1);
	    	result = "[ERROR]文件超过最大限制，请上传小于2M的图片";
	    }else if(extMap.get("image").indexOf(suffix)<0){
	    	 //检查扩展名
	    	obj.put("error", 1);
	    	result = "[ERROR]上传文件扩展名是不允许的扩展名";
	    }else{
	    	//检查扩展名
	    	if(extMap.get("image").indexOf(suffix)>=0){
	    		saveUrl +="resources/upload/images";
	    	}
		  	saveUrl +="/"+DateFormartUtil.getDateByFormat(DateFormartUtil.YYYY_MM_DD)+"/";
		    realFileName = DateFormartUtil.getDateByFormat(DateFormartUtil.HHmmss)+Tools.getChar(6)+"."+suffix;
	      //保存  
	        try {
	        	 if(!new File(destDir+saveUrl).exists()){  
	        		 new File(destDir+saveUrl).mkdirs();  
		 	     }  
	        	File targetFile = new File(destDir+saveUrl,realFileName);  
	 	        file.transferTo(targetFile);
	        	result = saveUrl+realFileName;
	 	        obj.put("error", 0);
	 	        obj.put("state", "SUCCESS");
	 	        obj.put("url", saveUrl+realFileName);
	        } catch (Exception e) {  
	        	obj.put("error", 1);
	        	e.printStackTrace();
	        	result = "[ERROR]上传失败"; 
	        } 
	        
	    }
	    if(request.getParameter("isEditor")!=null && request.getParameter("isEditor").toString().equals("false")){
	       if(result.indexOf("[ERROR]")<0){
	    	   printMsg("<script>parent.uploadImgCallBack('[OK]上传成功','"+result+"')</script>");
	       }else{
	    	   printMsg("<script>parent.uploadImgCallBack('[ERROR]上传失败','"+result+"')</script>");
	       }
       }else{
    	   obj.put("message", result);
           printMsg(obj.toString());
       }
	}
	
}
