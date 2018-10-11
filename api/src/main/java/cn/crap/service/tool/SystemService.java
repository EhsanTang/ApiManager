package cn.crap.service.tool;

import cn.crap.utils.HttpPostGet;
import cn.crap.utils.Tools;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author Ehsan
 * @date 2018/10/11 22:32
 */
@Service("systemService")
public class SystemService {
    private final static String CSS_FILE_URLS[] = new String[]{"base.css", "crapApi.css", "setting.css", "admin.css"};
    private final static String JS_FILE_URLS[] = new String[]{"app.js", "const.js", "services.js", "userRouter.js", "router.js",
            "userCtrls.js", "visitorControllers.js", "visitorRouter.js", "core.js", "global.js",  "crapApi.js", "json.js", "editor.js"};
    private final static String JS_COMPRESS_URL = "http://tool.oschina.net/action/jscompress/js_compress?munge=0&linebreakpos=5000";
    private final static String CSS_COMPRESS_URL = "http://tool.oschina.net/action/jscompress/css_compress?linebreakpos=5000";

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

    private String compress(String compressUrl, String baseFileUrl, String fileUrl) throws Exception {
        String cssSource = Tools.readFile(baseFileUrl + fileUrl);
        String compressText = HttpPostGet.postBody(compressUrl, cssSource, null);
        JSONObject jsonObject = JSONObject.fromObject(compressText);
        String compressResult = jsonObject.getString("result");
        Tools.staticize(compressResult, baseFileUrl + fileUrl);
        return compressResult;
    }
}
