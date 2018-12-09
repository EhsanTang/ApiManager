package cn.crap.framework.base;

import cn.crap.dto.LoginInfoDto;
import cn.crap.enu.InterfaceContentType;
import cn.crap.enu.MyError;
import cn.crap.enu.ProjectType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.ThreadContext;
import cn.crap.model.Module;
import cn.crap.model.Project;
import cn.crap.model.ProjectUser;
import cn.crap.query.BaseQuery;
import cn.crap.service.tool.*;
import cn.crap.utils.*;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Enumeration;

public abstract class BaseController implements IAuthCode, IConst, ISetting {
    protected final static String ERROR_VIEW = "/WEB-INF/views/result.jsp";
    protected final static int SIZE = 15;
    protected Logger log = Logger.getLogger(getClass());
    protected final static JsonResult SUCCESS = new JsonResult();
    @Resource(name = "projectCache")
    protected ProjectCache projectCache;
    @Resource(name = "moduleCache")
    protected ModuleCache moduleCache;
    @Resource(name = "stringCache")
    protected StringCache stringCache;
    @Resource(name = "settingCache")
    protected SettingCache settingCache;
    @Resource(name = "userCache")
    protected UserCache userCache;
    @Resource(name = "objectCache")
    protected ObjectCache objectCache;

    protected Project getProject(BaseQuery query){
        Assert.isTrue(query.getProjectId() != null || query.getModuleId() != null, "projectId & moduleId 不能同时为空");

        if (query.getModuleId() != null){
            Module module = moduleCache.get(query.getModuleId());
            return projectCache.get(module.getProjectId());
        }

        if (query.getProjectId()!= null){
            return projectCache.get(query.getProjectId());
        }
        return null;
    }

    protected String getProjectId(String projectId, String moduleId){
        Assert.isTrue(projectId != null || moduleId != null, "projectId & moduleId 不能同时为空");
        if (moduleId != null){
            String moduleProjectId = moduleCache.get(moduleId).getProjectId();
            return moduleProjectId == null ? projectId : moduleProjectId;
        }
        return projectId;
    }

    protected Project getProject(String projectId, String moduleId){
        Assert.isTrue(projectId != null || moduleId != null, "projectId & moduleId 不能同时为空");
        if (moduleId != null){
            projectId = moduleCache.get(moduleId).getProjectId();
        }
        return projectCache.get(projectId);
    }

    protected Module getModule(BaseQuery query){
        if (query.getModuleId() != null){
            Module module = moduleCache.get(query.getModuleId());
            return module.getId() == null ? null : module;
        }
        return null;
    }

    /**
     * 检查是否是crapDebug的项目，crapDebug插件的项目不允许修改、删除
     * @param userId
     * @param projectId
     * @throws MyException
     */
    protected void checkCrapDebug(String userId, String projectId) throws MyException{
        String debugProjectId = MD5.encrytMD5(userId, "").substring(0, 20) + "-debug";
        if (debugProjectId.equals(projectId)){
            throw new MyException(MyError.E000067);
        }
    }
    /**
     * @param param 待校验参数
     * @param tip 前端提示文案
     * @param myError
     * @throws MyException
     */
    protected void throwExceptionWhenIsNull(String param, String tip, MyError myError) throws MyException{
        if (myError == null){
            myError = MyError.E000020;
        }
        if (MyString.isEmpty(param)) {
            throw new MyException(myError, tip);
        }
    }

    protected void throwExceptionWhenIsNull(String param, String tip) throws MyException{
        throwExceptionWhenIsNull(param, tip, null);
    }


    @ExceptionHandler({Exception.class})
    @ResponseBody
    public JsonResult expHandler(HttpServletRequest request, Exception ex) {
        if (ex instanceof MyException) {
            return new JsonResult((MyException) ex);
        }

        if (ex instanceof NullPointerException) {
            log.error("异常, params:" + getAllStrParam(request), ex);
            return new JsonResult(new MyException(MyError.E000051));
        } else {
            log.error("异常, params:" + getAllStrParam(request), ex);
            ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();
            ex.printStackTrace(new PrintStream(outPutStream));
            String errorStackTrace = outPutStream.toString();
            try {
                outPutStream.close();
            } catch (IOException e) {
            }

            String exceptionDetail[] = errorStackTrace.split("Caused by:");
            String errorReason = "";
            if (exceptionDetail.length > 0) {
                errorReason = exceptionDetail[exceptionDetail.length - 1].split("\n")[0];
            }

            /**
             * field is too long
             * 字段超过最大长度异常处理
             */
            if (ex instanceof DataIntegrityViolationException) {
                if (errorReason.contains("com.mysql.jdbc.MysqlDataTruncation")) {
                    int index = errorStackTrace.indexOf("insert into") + 11;
                    String table = errorStackTrace.substring(index, index + 100).split(" ")[1].trim();
                    return new JsonResult(new MyException(MyError.E000052, "（字段：" + table + "." + errorReason.split("'")[1] + "）"));
                }
            }
            return new JsonResult(new MyException(MyError.E000001, ex.getMessage() + "，详细错误：" + errorReason));
        }
    }

    /**
     * 返回响应结果
     *
     * @param message
     */
    protected void printMsg(String message) {
        printMsg(message, InterfaceContentType.HTML);
    }

    protected void printMsg(String message, InterfaceContentType contentType) {
        if (contentType == null){
            contentType = InterfaceContentType.JSON;
        }
        if (MyString.isEmpty(message)){
            message = " ";
        }
        ThreadContext.response().setHeader("Content-Type", contentType.getType());
        ThreadContext.response().setCharacterEncoding("utf-8");
        try {
            PrintWriter out = ThreadContext.response().getWriter();
            out.write(message);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 权限校验
     * @param project
     * @throws MyException
     */
    protected void checkPermission(Project project) throws MyException {
        PermissionUtil.checkPermission(project, MY_DATE);
    }

    protected void checkPermission(Project project, int type) throws MyException {
        PermissionUtil.checkPermission(project, type);
    }

    protected void checkPermission(String projectId, int type) throws MyException {
        PermissionUtil.checkPermission(projectCache.get(projectId), type);
    }

    protected void checkPermission(String projectId) throws MyException {
        PermissionUtil.checkPermission(projectCache.get(projectId), MY_DATE);
    }

    /**
     * 初次输入浏览密码是需要验证码，然后记录至缓存中，第二次访问若缓存中有密码，则不需要检查验证码是否争取
     *
     * @param projectPassword 如果为空，则表示不需要密码
     * @param inputPassword
     * @param visitCode
     * @throws MyException
     */
    public void verifyPassword(String projectId, String projectPassword, String inputPassword, String visitCode) throws MyException {
        String COOKIE_PROJECT_PASSWORD = "cpp" + projectPassword;
        if (MyString.isEmpty(projectPassword)) {
            return;
        }

        String tempPassword = MyCookie.getCookie(COOKIE_PROJECT_PASSWORD, true);
        /**
         * 兼容旧版未加密的项目密码
         * 优先使用缓存密码校验
         */
        if (projectPassword.length() < 20 && MyString.equals(tempPassword, projectPassword)) {
            return;
        }

        /**
         * 升级后的项目密码以md5+盐的方式存储
         */
        if (MyString.equals(MD5.encrytMD5(tempPassword, projectId), projectPassword)) {
            return;
        }

        /**
         * 兼容旧版未加密的项目密码
         */
        if (projectPassword.length() < 20 && MyString.notEquals(inputPassword, projectPassword)) {
            throw new MyException(MyError.E000007);
        }

        if (projectPassword.length() >= 20 && MyString.notEquals(MD5.encrytMD5(inputPassword, projectId), projectPassword)) {
            throw new MyException(MyError.E000007);
        }

        /**
         * 如果开启了验证码，则需要校验图形验证码是否正确，防止暴力破解
         */
        if (settingCache.get(S_VISITCODE).getValue().equals("true")) {
            String imgCode = "";
            try{
                imgCode = Tools.getImgCode();
            }catch (MyException e){
                throw new MyException(MyError.E000007);
            }
            if (MyString.notEquals(imgCode, visitCode)) {
                throw new MyException(MyError.E000007);
            }
        }

        /**
         * 将密码记入缓存，方便下次使用
         * 有效时间为12小时
         */
        MyCookie.addCookie(COOKIE_PROJECT_PASSWORD, inputPassword, true, 60 * 60 * 12);
    }

    /**
     * 前端页面检查权限
     * private project need login
     * public project need check password,
     *
     * @param inputPassword
     * @param visitCode
     * @param project
     * @throws MyException
     */
    protected void checkFrontPermission(String inputPassword, String visitCode, Project project) throws MyException {
        // 如果是私有项目，必须登录才能访问，公开项目需要查看是否需要密码
        if (project.getType() == ProjectType.PRIVATE.getType()) {
            LoginInfoDto user = LoginUserHelper.getUser(MyError.E000041);

            // 最高管理员修改项目
            if (user != null && ("," + user.getRoleId()).indexOf("," + IConst.C_SUPER + ",") >= 0) {
                return;
            }

            // 自己的项目
            if (user.getId().equals(project.getUserId())) {
                return;
            }

            // 项目成员
            ProjectUser pu = user.getProjects().get(project.getId());
            if (pu == null) {
                throw new MyException(MyError.E000042);
            }
        } else {
            String projectPassword = project.getPassword();
            verifyPassword(project.getId(), projectPassword, inputPassword, visitCode);
        }
    }

    protected Object getParam(String key, String def) {
        String value = ThreadContext.request().getParameter(key);
        return value == null ? def : value;
    }

    private String getAllStrParam(HttpServletRequest request){
        try {
            StringBuilder sb = new StringBuilder("[");
            Enumeration paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = (String) paramNames.nextElement();
                sb.append(paramName + "=");
                boolean isFirst = true;
                for (String paramValue : request.getParameterValues(paramName)) {
                    sb.append((isFirst ? "" : ",") + paramValue);
                    isFirst = false;
                }
                sb.append(";");
            }
            sb.append("]");
            return sb.toString();
        }catch (Exception e){
            log.error("获取请求参数异常", e);
            return "";
        }
    }
}