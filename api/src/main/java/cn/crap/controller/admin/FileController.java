package cn.crap.controller.admin;

import cn.crap.beans.Config;
import cn.crap.enu.SettingEnum;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.utils.DateFormartUtil;
import cn.crap.utils.Tools;
import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;

@Controller
public class FileController extends BaseController{

    @RequestMapping(value="/user/file/upload.do")
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

    @RequestMapping(value="/user/markdown/upload.do")
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
        if(file.getSize() > 1024*1024 * Config.fileSize){
            result = "[ERROR]文件超过最大限制，请上传小于" +Config.fileSize +"M的文件";
        }else if( Config.imageType.indexOf(suffix)<0 && Config.fileType.indexOf(suffix)<0 ){
            result = "[ERROR]上传文件格式不对";
        }else{
            //检查扩展名
            if( Config.imageType.indexOf(suffix)>=0 ){
                saveUrl +="/resources/upload/images";
            }else{
                saveUrl +="/resources/upload/files";
            }
            saveUrl +="/"+ DateFormartUtil.getDateByFormat(DateFormartUtil.YYYY_MM)+"/";
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

            realFileName = DateFormartUtil.getDateByFormat(DateFormartUtil.DDHHmmss)+Tools.getChar(6)+version+"."+suffix;
            //保存
            try {
                if(!new File(destDir+saveUrl).exists()){
                    new File(destDir+saveUrl).mkdirs();
                }

                if (settingCache.equalse(SettingEnum.OPEN_ALIYUN, "true")){
                    if(result.indexOf("[ERROR]")<0){
                        updateFileToAliyun(file, saveUrl+realFileName);
                    }
                }

                File targetFile = new File(destDir+saveUrl,realFileName);
                file.transferTo(targetFile);
                if (Config.imgPrefix.endsWith("/")){
                    result = Config.imgPrefix.substring(0, Config.imgPrefix.length() - 1) + saveUrl + realFileName;
                } else {
                    result = Config.imgPrefix + saveUrl + realFileName;
                }
            } catch (Exception e) {
                e.printStackTrace();
                result = "[ERROR]上传失败";
            }
        }

        return Tools.getRequest().getContextPath() + result;
    }

    @RequestMapping(value="/user/file/uploadAllToAliyun.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_SETTING)
    public void updateAllFileToAliyun(String path){
        OSSClient client = null;
        try {
            String basePath = Tools.getServicePath() + "resources/" + path;
            ClientConfiguration conf = new ClientConfiguration();
            conf.setConnectionTimeout(1000);
            conf.setSocketTimeout(1000);
            client = new OSSClient(Config.endPoint, Config.accessKeyId, Config.accessKeySecret, conf);
            updateFileToAliyun(new File(basePath), client);
        }catch (Exception e){
            throw e;
        }finally {
            if (client != null) {
                client.shutdown();
            }
        }
    }

    public void updateFileToAliyun(MultipartFile file, String dir) {
        if (file != null && !StringUtils.isEmpty(file.getOriginalFilename())){
            OSSClient client = null;
            try {
                ClientConfiguration conf = new ClientConfiguration();
                conf.setConnectionTimeout(1000);
                conf.setSocketTimeout(1000);
                client = new OSSClient(Config.endPoint, Config.accessKeyId, Config.accessKeySecret, conf);
                client.putObject(Config.bucketName, dir.substring(1), file.getInputStream());
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if (client != null) {
                    client.shutdown();
                }
            }
        }
    }

    public static void updateFileToAliyun(File file, OSSClient client){
        if(file != null){
            if(file.isDirectory()){
                File[] fileArray=file.listFiles();
                if(fileArray!=null){

                    for (int i = 0; i < fileArray.length; i++) {
                        //递归调用
                        updateFileToAliyun(fileArray[i], client);
                    }
                }
            }
            else{
                try {
                    String fileUrl = file.getAbsolutePath().replace(Tools.getServicePath(), "");
                    OSSObject object = null;
                    try {
                        client.getObject(Config.bucketName, fileUrl);
                    }catch (Exception e){}

                    if (object == null){
                        client.putObject(Config.bucketName, fileUrl, new FileInputStream(file));
                    } else {
                        System.out.println(fileUrl + "，已经存在");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
