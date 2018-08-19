package cn.crap.controller.admin;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

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

import javax.servlet.http.HttpServletRequest;

@Controller
public class FileController extends BaseController{
	@Autowired
	private Config config;
	
	@RequestMapping(value="/file/upload.do")
	@ResponseBody
	@AuthPassport
	public void upload(@RequestParam(value = "img", required = false) MultipartFile file,@RequestParam(defaultValue="") String callBack,
			String property) {
        String result = upload(file);
	    if(!callBack.equals("")){
	       if(result.indexOf("[ERROR]")<0){
	    	   printMsg("<script>parent."+callBack+"('[OK]上传成功','"+result+"','"+property+"')</script>");
	       }else{
	    	   printMsg("<script>parent.uploadImgCallBack('[ERROR]上传失败','"+result+"')</script>");
	       }
       }else{
			if(result.indexOf("[ERROR]")<0){
				printMsg("{\"errno\": 0,\"data\": [\"" + result + "\"]}");
			}else {
				printMsg("{\"errno\": 1,\"errorMessage\": \"" + result + "\"}");

			}
       }
	}

    @RequestMapping(value="/markdown/upload.do")
    @ResponseBody
    @AuthPassport
    public void markdown(@RequestParam(value = "editormd-image-file", required = false) MultipartFile file, HttpServletRequest request) {
        String result = upload(file);
        if (result.indexOf("[ERROR]")<0){
            printMsg("{\"success\": 1,\"url\": \"" + result + "\"}");
        }else {
            printMsg("{\"success\": 0,\"message\": \"" + result + "\"}");
        }
    }


    private String upload(MultipartFile file) {
        String result = "";
        String realFileName = file.getOriginalFilename();
        String destDir = Tools.getServicePath();
        String saveUrl ="";
        String suffix = realFileName.substring(realFileName.lastIndexOf(".") + 1).toLowerCase();
        /**
         * 文件大小拦截，不能超过20M
         */
        if(file.getSize() > 1024*1024 * config.getFileSize()){
            result = "[ERROR]文件超过最大限制，请上传小于" +config.getFileSize() +"M的文件";
        }else if( config.getImageType().indexOf(suffix)<0 && config.getFileType().indexOf(suffix)<0 ){
            result = "[ERROR]上传文件格式不对";
        }else{
            //检查扩展名
            if( config.getImageType().indexOf(suffix)>=0 ){
                saveUrl +="resources/upload/images";
            }else{
                saveUrl +="resources/upload/files";
            }
              saveUrl +="/"+ DateFormartUtil.getDateByFormat(DateFormartUtil.YYYY_MM_DD)+"/";
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
            } catch (Exception e) {
                e.printStackTrace();
                result = "[ERROR]上传失败";
            }
        }
        return result;
    }
}
