package cn.crap.framework.base;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cn.crap.adapter.ProjectUserAdapter;
import cn.crap.beans.Config;
import cn.crap.dto.ProjectUserDto;
import cn.crap.enumer.InterfaceContentType;
import cn.crap.enumer.MyError;
import cn.crap.framework.ThreadContext;
import cn.crap.model.ProjectUser;
import cn.crap.service.tool.*;
import cn.crap.utils.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.dto.LoginInfoDto;
import cn.crap.enumer.ProjectType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.model.Project;

public abstract class BaseController implements IAuthCode, IConst, ISetting {
    protected final static String ERROR_VIEW = "/WEB-INF/views/interFacePdf.jsp";
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
    @Autowired
    protected Config config;

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
        } else if (ex instanceof NullPointerException) {
            log.error(ex.getMessage(), ex);
            return new JsonResult(new MyException(MyError.E000051));
        } else {
            log.error(ex.getMessage(), ex);
            ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();
            ex.printStackTrace(new PrintStream(outPutStream));
            String exceptionDetail[] = outPutStream.toString().split("Caused by:");
            try {
                outPutStream.close();
            } catch (IOException e) {
            }

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
                    return new JsonResult(new MyException(MyError.E000052, "（字段：" + errorReason.split("'")[1] + "）"));
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
     * 用户页面权限检查
     *
     * @param project
     * @throws MyException
     */
    protected void checkUserPermissionByProject(Project project, int type) throws MyException {
        LoginInfoDto user = LoginUserHelper.getUser(MyError.E000069);
        /**
         * 最高管理员修改项目
         * the supper admin can do anything
         */
        if (user != null && ("," + user.getRoleId()).indexOf("," + C_SUPER + ",") >= 0) {
            return;
        }

        /**
         * 修改自己的项目
         * myself project
         */
        if (user.getId().equals(project.getUserId())) {
            return;
        }

        if (type < 0) {
            throw new MyException(MyError.E000022);
        }

        // 项目成员
        ProjectUserDto puDto = ProjectUserAdapter.getDto(user.getProjects().get(project.getId()), null);
        if (puDto == null) {
            throw new MyException(MyError.E000022);
        }

        /**
         * if login user is one member, then he can see the data
         */
        if (type == VIEW) {
            return;
        }

        if (type < puDto.getProjectAuth().length - 1 && puDto.getProjectAuth()[type]) {
            return;
        }

        throw new MyException(MyError.E000022);
    }

    protected void checkUserPermissionByProject(Project project) throws MyException {
        checkUserPermissionByProject(project, MY_DATE);
    }

    protected void checkUserPermissionByProject(String projectId, int type) throws MyException {
        checkUserPermissionByProject(projectCache.get(projectId), type);
    }

    protected void checkUserPermissionByModuleId(String moduleId, int type) throws MyException {
        checkUserPermissionByProject(projectCache.get(moduleCache.get(moduleId).getProjectId()), type);
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
            String imgCode = Tools.getImgCode();
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

}