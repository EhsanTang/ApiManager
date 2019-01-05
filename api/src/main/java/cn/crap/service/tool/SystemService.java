package cn.crap.service.tool;

import cn.crap.beans.Config;
import cn.crap.enu.SettingEnum;
import cn.crap.model.Setting;
import cn.crap.service.SettingService;
import cn.crap.utils.HttpPostGet;
import cn.crap.utils.ISetting;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;
import com.google.common.collect.Maps;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * @author Ehsan
 * @date 2018/10/11 22:32
 */
@Service
public class SystemService {
    @Autowired
    private SettingService settingService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Logger log = Logger.getLogger(getClass());

    private final static String CSS_FILE_URLS[] = new String[]{"base.css", "crapApi.css", "setting.css", "admin.css", "index.css"};
    private final static String JS_FILE_URLS[] = new String[]{"app.js", "const.js", "services.js", "userRouter.js", "router.js",
            "userCtrls.js", "visitorControllers.js", "visitorRouter.js", "core.js", "global.js", "validateAndRefresh.js", "crapApi.js", "json.js", "editor.js"};
    private final static String JS_COMPRESS_URL = "http://tool.oschina.net/action/jscompress/js_compress?munge=0&linebreakpos=5000";
    private final static String CSS_COMPRESS_URL = "http://tool.oschina.net/action/jscompress/css_compress?linebreakpos=5000";

    private static final LinkedHashMap<Integer, String> CHANGE_SQL_MAP = Maps.newLinkedHashMap();
    static {
        //	v8.0.5 修改，允许模块为空，2018-11-17
        CHANGE_SQL_MAP.put(1, "ALTER TABLE `interface` CHANGE `moduleId` `moduleId` VARCHAR(50) NULL  DEFAULT ''  COMMENT '所属模块ID'");
        CHANGE_SQL_MAP.put(2, "ALTER TABLE `log` CHANGE `content` `content` LONGTEXT NOT NULL");
        CHANGE_SQL_MAP.put(3, "ALTER TABLE `interface` CHANGE `version` `version` VARCHAR(20)  NULL  DEFAULT '1.0'  COMMENT '版本号'");
        CHANGE_SQL_MAP.put(4, "ALTER TABLE `article` CHANGE `moduleId` `moduleId` VARCHAR(50) NULL  DEFAULT 'top'");
        CHANGE_SQL_MAP.put(5, "ALTER TABLE `source` CHANGE `moduleId` `moduleId` VARCHAR(50)  NULL  DEFAULT '0'  COMMENT '模块ID'");
        CHANGE_SQL_MAP.put(6, "ALTER TABLE `article` ADD `attributes` VARCHAR(200)  NULL  DEFAULT ''  AFTER `projectId`");
    }

    /**
     * 清理日志
     */
    public void cleanLog(){
        jdbcTemplate.execute("DELETE FROM log WHERE createTime < DATE_SUB(NOW(),INTERVAL 30 day)");
    }
    /**
     * 数据库更新
     */
    public void updateDataBase(){
        Setting setting = settingService.getByKey(SettingEnum.DATABASE_CHANGE_LOG.getKey());
        Integer lastSqIndex = 0;
        try {
            if (setting != null && MyString.isNotEmpty(setting.getValue())) {
                lastSqIndex = Integer.parseInt(setting.getValue());
            }
        }catch (Exception e){
            log.error("检查数据库更新，获取最后一条执行序号失败", e);
        }

        Iterator iterator = CHANGE_SQL_MAP.keySet().iterator();
        while (iterator.hasNext()) {
            String sql = null;
            Integer sqlIndex = null;
            try {
                sqlIndex = (Integer) iterator.next();
                sql = CHANGE_SQL_MAP.get(sqlIndex);

                if (sqlIndex <= lastSqIndex) {
                    log.warn("检查数据库更新，序号:" + sqlIndex + "，sql已经执行过，跳过sql:" + sql);
                    continue;
                }

                lastSqIndex = sqlIndex;
                log.warn("检查数据库更新，序号:" + sqlIndex + "，执行sql:" + sql);
                jdbcTemplate.execute(sql);
            }catch (Throwable e){
                log.error("执行sql失败，sqlIndex:" + sqlIndex + ", sql:" + sql, e);
            }
        }
        setting.setValue(lastSqIndex + "");
        settingService.update(setting);
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
