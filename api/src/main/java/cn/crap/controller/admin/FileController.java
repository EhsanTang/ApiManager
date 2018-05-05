package cn.crap.controller.admin;

import java.io.File;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.beans.Config;
import cn.crap.utils.DateFormartUtil;
import cn.crap.utils.Tools;

@Controller
public class FileController extends BaseController{
	@Autowired
	private Config config;
	
	@RequestMapping(value="/file/upload.do")
	@ResponseBody
	@AuthPassport
	public void upload(@RequestParam(value = "img", required = false) MultipartFile file,@RequestParam(defaultValue="") String callBack,
			String property) {
		String result = "";
	    String realFileName = file.getOriginalFilename();    
	    String destDir = Tools.getServicePath();
	    String saveUrl ="";
	    String suffix = realFileName.substring(realFileName.lastIndexOf(".") + 1).toLowerCase();
	    JSONObject obj = new JSONObject();
	    /**
	     * 文件大小拦截，不能超过20M
	     */
	    if(file.getSize() > 1024*1024 * config.getFileSize()){
	    	obj.put("error", 1);
	    	result = "[ERROR]文件超过最大限制，请上传小于" +config.getFileSize() +"M的文件";
	    }else if( config.getImageType().indexOf(suffix)<0 && config.getFileType().indexOf(suffix)<0 ){
	    	 //检查扩展名
	    	obj.put("error", 1);
	    	result = "[ERROR]上传文件格式不对";
	    }else{
	    	//检查扩展名
	    	if( config.getImageType().indexOf(suffix)>=0 ){
	    		saveUrl +="resources/upload/images";
	    	}else{
	    		saveUrl +="resources/upload/files";
	    	}
		  	saveUrl +="/"+DateFormartUtil.getDateByFormat(DateFormartUtil.YYYY_MM_DD)+"/";
		  	String version = ".CAV."+Tools.getChar(6)+".1";
		  	// 如果文件包含版本号：.CAV.文件标识.版本号 //版本号CrapApi Version
		  	try{
		  		if( realFileName.contains(".CAV.")){
		  			String str[] = realFileName.split("\\.");
		  			version = ".CAV." + str[str.length-3] + "." +  (Long.parseLong(str[str.length-2])+1);
			  	}
		  	}catch(Exception e){
		  		e.printStackTrace();
		  	}
		  	
		    realFileName = DateFormartUtil.getDateByFormat(DateFormartUtil.HHmmss)+Tools.getChar(6)+version+"."+suffix;
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
	    if(!callBack.equals("")){
	       if(result.indexOf("[ERROR]")<0){
	    	   printMsg("<script>parent."+callBack+"('[OK]上传成功','"+result+"','"+property+"')</script>");
	       }else{
	    	   printMsg("<script>parent.uploadImgCallBack('[ERROR]上传失败','"+result+"')</script>");
	       }
       }else{
    	   obj.put("message", result);
           printMsg(obj.toString());
       }
	}
	
}
