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
    @Autowired
    private SettingCache settingCache;

    private Logger log = Logger.getLogger(getClass());

    // TODO 按文件夹合并
    private final static String CSS_FILE_URLS[] = new String[]{"base.css", "crapApi.css", "setting.css", "admin.css", "bug.css", "index.css"};
    private final static String JS_FILE_URLS[] = new String[]{"app.js", "const.js", "services.js", "userRouter.js", "router.js",
            "userCtrls.js", "userBugCtrl.js", "userProjectMetaCtrl.js", "userCommonCtrl.js", "commentCtrl.js", "visitorControllers.js", "visitorRouter.js", "core.js", "coreNew.js", "global.js", "validateAndRefresh.js",
            "crapApi.js", "json.js", "editor.js"};
    private final static String JS_COMPRESS_URL = "http://tool.oschina.net/action/jscompress/js_compress?munge=0&linebreakpos=5000";
    private final static String CSS_COMPRESS_URL = "http://tool.oschina.net/action/jscompress/css_compress?linebreakpos=5000";

    private static final LinkedHashMap<Integer, String> CHANGE_SQL_MAP = Maps.newLinkedHashMap();
    static {
        // V8.2.0_0630 修改，允许模块为空，2018-11-17
        CHANGE_SQL_MAP.put(1, "ALTER TABLE `interface` CHANGE `moduleId` `moduleId` VARCHAR(50) NULL  DEFAULT ''  COMMENT '所属模块ID'");
        CHANGE_SQL_MAP.put(2, "ALTER TABLE `log` CHANGE `content` `content` LONGTEXT NOT NULL");
        CHANGE_SQL_MAP.put(3, "ALTER TABLE `interface` CHANGE `version` `version` VARCHAR(20)  NULL  DEFAULT '1.0'  COMMENT '版本号'");
        CHANGE_SQL_MAP.put(4, "ALTER TABLE `article` CHANGE `moduleId` `moduleId` VARCHAR(50) NULL  DEFAULT 'top'");
        CHANGE_SQL_MAP.put(5, "ALTER TABLE `source` CHANGE `moduleId` `moduleId` VARCHAR(50)  NULL  DEFAULT '0'  COMMENT '模块ID'");
        CHANGE_SQL_MAP.put(6, "ALTER TABLE `article` ADD `attributes` VARCHAR(200)  NULL  DEFAULT ''  AFTER `projectId`");
        CHANGE_SQL_MAP.put(7, "ALTER TABLE `comment` CHANGE `articleId` `targetId` VARCHAR(50)  NOT NULL  DEFAULT '' COMMENT '评论对象ID'");
        CHANGE_SQL_MAP.put(8, "ALTER TABLE `comment` ADD `type` VARCHAR(32)  NOT NULL  DEFAULT 'ARTICLE'  COMMENT '评论类型：ARTICLE 文档，BUG 缺陷'  AFTER `avatarUrl`");
        CHANGE_SQL_MAP.put(9, "ALTER TABLE `comment` CHANGE `content` `content` VARCHAR(512)  CHARACTER SET utf8  COLLATE utf8_general_ci  NOT NULL  DEFAULT ''");
        CHANGE_SQL_MAP.put(10, "ALTER TABLE `project_user` ADD `permission` VARCHAR(500)  NULL  DEFAULT ',' COMMENT '权限'");

        // 权限数据结构调整
        CHANGE_SQL_MAP.put(11, "UPDATE project_user SET permission=concat(permission,'addModule,') WHERE addModule=1");
        CHANGE_SQL_MAP.put(12, "UPDATE project_user SET permission=concat(permission,'delModule,') WHERE delModule=1");
        CHANGE_SQL_MAP.put(13, "UPDATE project_user SET permission=concat(permission,'modModule,') WHERE modModule=1");

        CHANGE_SQL_MAP.put(14, "UPDATE project_user SET permission=concat(permission,'addInter,') WHERE addInter=1");
        CHANGE_SQL_MAP.put(15, "UPDATE project_user SET permission=concat(permission,'modInter,') WHERE modInter=1");
        CHANGE_SQL_MAP.put(16, "UPDATE project_user SET permission=concat(permission,'delInter,') WHERE delInter=1");

        CHANGE_SQL_MAP.put(17, "UPDATE project_user SET permission=concat(permission,'addArticle,') WHERE addArticle=1");
        CHANGE_SQL_MAP.put(18, "UPDATE project_user SET permission=concat(permission,'delArticle,') WHERE delArticle=1");
        CHANGE_SQL_MAP.put(19, "UPDATE project_user SET permission=concat(permission,'modArticle,') WHERE modArticle=1");

        CHANGE_SQL_MAP.put(20, "UPDATE project_user SET permission=concat(permission,'addDict,') WHERE addDict=1");
        CHANGE_SQL_MAP.put(21, "UPDATE project_user SET permission=concat(permission,'delDict,') WHERE delDict=1");
        CHANGE_SQL_MAP.put(22, "UPDATE project_user SET permission=concat(permission,'modDict,') WHERE modDict=1");

        CHANGE_SQL_MAP.put(23, "UPDATE project_user SET permission=concat(permission,'addSource,') WHERE addSource=1");
        CHANGE_SQL_MAP.put(24, "UPDATE project_user SET permission=concat(permission,'delSource,') WHERE delSource=1");
        CHANGE_SQL_MAP.put(25, "UPDATE project_user SET permission=concat(permission,'modSource,') WHERE modSource=1");

        CHANGE_SQL_MAP.put(26, "UPDATE project_user SET permission=concat(permission,'addError,') WHERE addError=1");
        CHANGE_SQL_MAP.put(27, "UPDATE project_user SET permission=concat(permission,'delError,') WHERE delError=1");
        CHANGE_SQL_MAP.put(28, "UPDATE project_user SET permission=concat(permission,'modError,') WHERE modError=1");

        CHANGE_SQL_MAP.put(29, "ALTER TABLE `project_user` DROP `addModule`");
        CHANGE_SQL_MAP.put(30, "ALTER TABLE `project_user` DROP `delModule`");
        CHANGE_SQL_MAP.put(31, "ALTER TABLE `project_user` DROP `modModule`");

        CHANGE_SQL_MAP.put(32, "ALTER TABLE `project_user` DROP `addInter`");
        CHANGE_SQL_MAP.put(33, "ALTER TABLE `project_user` DROP `modInter`");
        CHANGE_SQL_MAP.put(34, "ALTER TABLE `project_user` DROP `delInter`");

        CHANGE_SQL_MAP.put(35, "ALTER TABLE `project_user` DROP `addArticle`");
        CHANGE_SQL_MAP.put(36, "ALTER TABLE `project_user` DROP `delArticle`");
        CHANGE_SQL_MAP.put(37, "ALTER TABLE `project_user` DROP `modArticle`");

        CHANGE_SQL_MAP.put(38, "ALTER TABLE `project_user` DROP `addDict`");
        CHANGE_SQL_MAP.put(39, "ALTER TABLE `project_user` DROP `delDict`");
        CHANGE_SQL_MAP.put(40, "ALTER TABLE `project_user` DROP `modDict`");

        CHANGE_SQL_MAP.put(41, "ALTER TABLE `project_user` DROP `addSource`");
        CHANGE_SQL_MAP.put(42, "ALTER TABLE `project_user` DROP `delSource`");
        CHANGE_SQL_MAP.put(43, "ALTER TABLE `project_user` DROP `modSource`");

        CHANGE_SQL_MAP.put(44, "ALTER TABLE `project_user` DROP `addError`");
        CHANGE_SQL_MAP.put(45, "ALTER TABLE `project_user` DROP `delError`");
        CHANGE_SQL_MAP.put(46  , "ALTER TABLE `project_user` DROP `modError`");

        // 废弃 role字段，但是不删除：mybatis需要修改
        CHANGE_SQL_MAP.put(47, "UPDATE user SET auth=concat(auth,',SUPER,') WHERE roleId like '%super%'");
        CHANGE_SQL_MAP.put(48, "DROP TABLE `role`");

        CHANGE_SQL_MAP.put(49, "ALTER TABLE `article` CHANGE `sequence` `sequence` BIGINT(11)  UNSIGNED  NOT NULL  DEFAULT '0'  COMMENT '排序，越大越靠前'");
        CHANGE_SQL_MAP.put(52, "ALTER TABLE `comment` CHANGE `sequence` `sequence` BIGINT(11)  UNSIGNED  NOT NULL  DEFAULT '0'  COMMENT '排序，越大越靠前'");
        CHANGE_SQL_MAP.put(53, "ALTER TABLE `debug` CHANGE `sequence` `sequence` BIGINT(11)  UNSIGNED  NOT NULL  DEFAULT '0'  COMMENT '排序，越大越靠前'");
        CHANGE_SQL_MAP.put(54, "ALTER TABLE `error` CHANGE `sequence` `sequence` BIGINT(11)  UNSIGNED  NOT NULL  DEFAULT '0'  COMMENT '排序，越大越靠前'");
        CHANGE_SQL_MAP.put(55, "ALTER TABLE `interface` CHANGE `sequence` `sequence` BIGINT(11)  UNSIGNED  NOT NULL  DEFAULT '0'  COMMENT '排序，越大越靠前'");
        CHANGE_SQL_MAP.put(56, "ALTER TABLE `log` CHANGE `sequence` `sequence` BIGINT(11)  UNSIGNED  NOT NULL  DEFAULT '0'  COMMENT '排序，越大越靠前'");
        CHANGE_SQL_MAP.put(57, "ALTER TABLE `menu` CHANGE `sequence` `sequence` BIGINT(11)  UNSIGNED  NOT NULL  DEFAULT '0'  COMMENT '排序，越大越靠前'");
        CHANGE_SQL_MAP.put(58, "ALTER TABLE `module` CHANGE `sequence` `sequence` BIGINT(11)  UNSIGNED  NOT NULL  DEFAULT '0'  COMMENT '排序，越大越靠前'");
        CHANGE_SQL_MAP.put(59, "ALTER TABLE `project` CHANGE `sequence` `sequence` BIGINT(11)  UNSIGNED  NOT NULL  DEFAULT '0'  COMMENT '排序，越大越靠前'");
        CHANGE_SQL_MAP.put(60, "ALTER TABLE `project_user` CHANGE `sequence` `sequence` BIGINT(11)  UNSIGNED  NOT NULL  DEFAULT '0'  COMMENT '排序，越大越靠前'");
        CHANGE_SQL_MAP.put(61, "ALTER TABLE `setting` CHANGE `sequence` `sequence` BIGINT(11)  UNSIGNED  NOT NULL  DEFAULT '0'  COMMENT '排序，越大越靠前'");
        CHANGE_SQL_MAP.put(62, "ALTER TABLE `source` CHANGE `sequence` `sequence` BIGINT(11)  UNSIGNED  NOT NULL  DEFAULT '0'  COMMENT '排序，越大越靠前'");
        CHANGE_SQL_MAP.put(63, "ALTER TABLE `user` CHANGE `sequence` `sequence` BIGINT(11)  UNSIGNED  NOT NULL  DEFAULT '0'  COMMENT '排序，越大越靠前'");

        CHANGE_SQL_MAP.put(64, "CREATE TABLE `bug` (\n" +
                "  `id` varchar(50) NOT NULL,\n" +
                "  `name` varchar(100) NOT NULL,\n" +
                "  `content` text NOT NULL,\n" +
                "  `status` tinyint(4) NOT NULL DEFAULT '1',\n" +
                "  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "  `moduleId` varchar(50) DEFAULT 'top',\n" +
                "  `sequence` bigint(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',\n" +
                "  `projectId` varchar(50) NOT NULL DEFAULT '',\n" +
                "  `attributes` varchar(200) DEFAULT '',\n" +
                "  `executor` varchar(50) DEFAULT NULL COMMENT '解决者',\n" +
                "  `executorStr` varchar(50) DEFAULT NULL COMMENT '解决者名称',\n" +
                "  `updateTime` timestamp NULL DEFAULT NULL COMMENT '最后更新时间',\n" +
                "  `creator` varchar(50) NOT NULL DEFAULT '' COMMENT '创建者',\n" +
                "  `creatorStr` varchar(50) DEFAULT NULL COMMENT '创建者名称',\n" +
                "  `tester` varchar(50) DEFAULT NULL COMMENT '验证者',\n" +
                "  `testerStr` varchar(50) DEFAULT NULL COMMENT '验证这名称',\n" +
                "  `priority` tinyint(4) NOT NULL COMMENT '优先级',\n" +
                "  `tracer` varchar(50) DEFAULT NULL COMMENT '抄送人',\n" +
                "  `tracerStr` varchar(50) DEFAULT NULL COMMENT '抄送人名称',\n" +
                "  `deadline` timestamp NULL DEFAULT NULL COMMENT '截止时间',\n" +
                "  `type` tinyint(4) DEFAULT NULL COMMENT '问题类型',\n" +
                "  `severity` tinyint(4) DEFAULT NULL COMMENT '严重程度',\n" +
                "  `updateBy` varchar(50) DEFAULT NULL COMMENT '最后修改人',\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  KEY `idx_project_sequence` (`projectId`,`sequence`),\n" +
                "  KEY `idx_project_creator` (`projectId`,`creator`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");

        CHANGE_SQL_MAP.put(65, "CREATE TABLE `bug_log` (\n" +
                "  `id` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',\n" +
                "  `operator` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '操作人',\n" +
                "  `operatorStr` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '操作人名',\n" +
                "  `senior` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '原用户',\n" +
                "  `type` tinyint(4) unsigned NOT NULL COMMENT '操作类型：1标题，2内容，3状态，4优先级，5严重程度，6问题类型，7模块，8执行人，9测试，10抄送人',\n" +
                "  `junior` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '新用户',\n" +
                "  `remark` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',\n" +
                "  `newValue` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '新值',\n" +
                "  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "  `sequence` bigint(11) unsigned NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',\n" +
                "  `projectId` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',\n" +
                "  `status` int(11) DEFAULT NULL,\n" +
                "  `bugId` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '缺陷ID',\n" +
                "  `originalValue` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '原始值',\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  KEY `idx_bug_sequence` (`bugId`,`sequence`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;");
        CHANGE_SQL_MAP.put(66, "ALTER TABLE `project_user` DROP INDEX `project_user`");
        CHANGE_SQL_MAP.put(67, "ALTER TABLE `project_user` ADD INDEX `idx_project_seq` (`projectId`, `sequence`)");

        CHANGE_SQL_MAP.put(68, "CREATE TABLE `project_meta` (\n" +
                "  `id` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '主键',\n" +
                "  `projectId` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '项目ID',\n" +
                "  `moduleId` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '模块ID',\n" +
                "  `attributes` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '属性，使用;:分割',\n" +
                "  `sequence` bigint(20) DEFAULT NULL,\n" +
                "  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
                "  `status` tinyint(11) NOT NULL DEFAULT '0' COMMENT '状态，1:正常',\n" +
                "  `type` tinyint(4) NOT NULL COMMENT '数据类型，0:环境',\n" +
                "  `name` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '名称',\n" +
                "  `value` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '值',\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  KEY `idx_project_type` (`projectId`,`type`),\n" +
                "  KEY `idx_project_module_type` (`projectId`,`moduleId`,`type`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;");


        CHANGE_SQL_MAP.put(69, "UPDATE `setting` SET `value` = 'resources/images/transparent.png',`remark` = '登陆背景图\\n默认图片：resources/images/bg_web.jpg\\n透明：resources/images/transparent.png' WHERE `mkey` = 'LOGINBG';");
        CHANGE_SQL_MAP.put(70, "UPDATE `setting` SET `value` = 'resources/images/transparent.png',`remark` = '头部标题搜索背景图\\n默认图片：resources/images/bg_web.jpg\\n透明：resources/images/transparent.png' WHERE `mkey` = 'TITLEBG';");
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
                    value = settingCache.getDomain() + "/" + value;
                }
            }
            cssContent = cssContent.replace("[" + s.getMkey() + "]", value);
        }
        cssContent = cssContent.replace("[MAIN_COLOR_OPACITY_1]",
                Tools.getRgba(0.1f, settingService.getByKey(ISetting.S_MAIN_COLOR).getValue()));

        Tools.staticize(cssContent, cssPath + "/setting.css");
    }

    private String compress(String compressUrl, String baseFileUrl, String fileUrl) throws Exception {
        String compressText = null;
        String compressResult = null;
        try {
            String cssSource = Tools.readFile(baseFileUrl + fileUrl);
            compressText = HttpPostGet.postBody(compressUrl, cssSource, null, 5000);
            JSONObject jsonObject = JSONObject.fromObject(compressText);
            compressResult = jsonObject.getString("result");
            Tools.staticize(compressResult, baseFileUrl + fileUrl);
        } catch (Throwable e){
            log.error("压缩js、css异常,compressText:" + compressText,  e);
            throw e;
        }
        return compressResult;
    }
}
