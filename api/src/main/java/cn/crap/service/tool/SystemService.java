package cn.crap.service.tool;

import cn.crap.beans.Config;
import cn.crap.model.Setting;
import cn.crap.service.SettingService;
import cn.crap.utils.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author Ehsan
 * @date 2018/10/11 22:32
 */
@Service
public class SystemService {
    @Autowired
    private SettingService settingService;

    private final static String CSS_FILE_URLS[] = new String[]{"base.css", "crapApi.css", "setting.css", "admin.css", "index.css"};
    private final static String JS_FILE_URLS[] = new String[]{"app.js", "const.js", "services.js", "userRouter.js", "router.js",
            "userCtrls.js", "visitorControllers.js", "visitorRouter.js", "core.js", "global.js", "validateAndRefresh.js", "crapApi.js", "json.js", "editor.js"};
    private final static String JS_COMPRESS_URL = "http://tool.oschina.net/action/jscompress/js_compress?munge=0&linebreakpos=5000";
    private final static String CSS_COMPRESS_URL = "http://tool.oschina.net/action/jscompress/css_compress?linebreakpos=5000";

    /**
     * 将config配置加载至静态类
     */
    public void initConfig(){
        try {
            Properties properties = new Properties();
            // 使用ClassLoader加载properties配置文件生成对应的输入流
            InputStream in = SystemService.class.getResourceAsStream("/config.properties");
            // 使用properties对象加载输入流
            properties.load(in);
            Config.clientID = properties.getProperty(Config.getConfigClientID());
            Config.clientSecret = properties.getProperty(Config.getConfigClientSecret());
            Config.oschinaClientID = properties.getProperty(Config.getConfigOschinaClientID());
            Config.oschinaClientSecret = properties.getProperty(Config.getConfigOschinaClientSecret());
            Config.domain = properties.getProperty(Config.getConfigDomain());
            Config.cacheTime = MyInteger.getInt(properties.getProperty(Config.getConfigCacheTime()), 3600);
            Config.loginInforTime = MyInteger.getInt(properties.getProperty(Config.getConfigLoginInforTime()), 3600);
            Config.fileSize = MyInteger.getInt(properties.getProperty(Config.getConfigFileSize()), 2);
            Config.imageType = properties.getProperty(Config.getConfigImageType());
            Config.fileType = properties.getProperty(Config.getConfigFileType());
            Config.openRegister = MyString.getBoolean(properties.getProperty(Config.getConfigOpenRegister()), true);
            Config.luceneSearchNeedLogin = MyString.getBoolean(properties.getProperty(Config.getConfigLuceneSearchNeedLogin()), true);
            Config.canRepeatUrl = MyString.getBoolean(properties.getProperty(Config.getCanCanRepeatUrl()), true);
            Config.baidu = properties.getProperty(Config.getConfigBaidu());
            Config.mail = properties.getProperty(Config.getConfigMail());
            Config.accessKeyId = properties.getProperty(Config.getConfigAccessKeyId());
            Config.accessKeySecret = properties.getProperty(Config.getConfigAccessKeySecret());
            Config.endPoint = properties.getProperty(Config.getConfigEndPoint());
            Config.bucketName = properties.getProperty(Config.getConfigBucketName());
            Config.imgPrefix = properties.getProperty(Config.getConfigImgPrefix());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 合并资源
     */
    public void mergeSource(){
        try {
            String allCss = "";
            String cssBaseFileUrl = Tools.getServicePath() + "resources/css/";
            for (String cssFileUrl : CSS_FILE_URLS) {
                allCss = allCss + Tools.readFile(cssBaseFileUrl + cssFileUrl) + "\n";
            }
            Tools.staticize(allCss, cssBaseFileUrl + "allCss.css");

            String allJs = "";
            String jsBaseFileUrl = Tools.getServicePath() + "resources/js/";
            for (String jsFileUrl : JS_FILE_URLS) {
                allJs = allJs + Tools.readFile(jsBaseFileUrl + jsFileUrl) + "\n";
            }
            Tools.staticize(allJs, jsBaseFileUrl + "allJs.js");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 压缩资源
     * @throws Exception
     */
    public void compressSource() throws Exception{
        String cssBaseFileUrl = Tools.getServicePath() + "resources/css/";
        for (String cssFileUrl : CSS_FILE_URLS){
            compress(CSS_COMPRESS_URL, cssBaseFileUrl, cssFileUrl);
        }

        String jsBaseFileUrl = Tools.getServicePath() + "resources/js/";
        for (String jsFileUrl : JS_FILE_URLS){
            compress(JS_COMPRESS_URL, jsBaseFileUrl, jsFileUrl);
        }
    }

    /**
     * 静态化setting.css文件
     * @throws Exception
     */
    public void updateSettingCss() throws Exception{
        // 更新css模板，静态化css文件
        String cssPath = Tools.getServicePath() + "resources/css/";
        Tools.createFile(cssPath);
        String cssContent = Tools.readFile(cssPath + "setting.tpl");
        for (Setting s : settingService.getAll()) {
            String value = s.getValue();
            if (value != null && (value.toLowerCase().endsWith(".jpg") || value.toLowerCase().endsWith(".png"))) {
                if (!value.startsWith("http://") && !value.startsWith("https://")) {
                    value = Config.domain + "/" + value;
                }
            }
            cssContent = cssContent.replace("[" + s.getMkey() + "]", value);
        }
        cssContent = cssContent.replace("[MAIN_COLOR_HOVER]",
                Tools.getRgba(0.1f, settingService.getByKey(ISetting.S_MAIN_COLOR).getValue()));

        Tools.staticize(cssContent, cssPath + "/setting.css");
    }

    private String compress(String compressUrl, String baseFileUrl, String fileUrl) throws Exception {
        String cssSource = Tools.readFile(baseFileUrl + fileUrl);
        String compressText = HttpPostGet.postBody(compressUrl, cssSource, null);
        JSONObject jsonObject = JSONObject.fromObject(compressText);
        String compressResult = jsonObject.getString("result");
        Tools.staticize(compressResult, baseFileUrl + fileUrl);
        return compressResult;
    }
}
