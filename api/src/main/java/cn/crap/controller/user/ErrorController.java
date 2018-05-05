package cn.crap.controller.user;

import cn.crap.adapter.ErrorAdapter;
import cn.crap.dto.ErrorDto;
import cn.crap.enumer.MyError;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.mybatis.Error;
import cn.crap.service.custom.CustomErrorService;
import cn.crap.service.mybatis.ErrorService;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

// TODO jsonResult 优化，html页面国际化
@Controller
@RequestMapping("/user/error")
public class ErrorController extends BaseController{
    @Autowired
    private ErrorService errorService;
    @Autowired
    private CustomErrorService customErrorService;

    /**
     * 错误码列表
     * @param projectId
     * @param errorCode
     * @param errorMsg
     * @param currentPage
     * @return
     * @throws MyException
     */
    @RequestMapping("/list.do")
    @ResponseBody
    @AuthPassport
    public JsonResult list(String projectId, String errorCode, String errorMsg,  Integer currentPage) throws MyException {
        throwExceptionWhenIsNull(projectId, "projectId");
        checkUserPermissionByProject(projectId, VIEW);

        Page page = new Page(currentPage);
        List<Error> models = customErrorService.queryByProjectId(projectId, errorCode, errorMsg, page);
        List<ErrorDto> dtoList = ErrorAdapter.getDto(models);

        return new JsonResult(1, dtoList, page).others(Tools.getMap("crumbs", Tools.getCrumbs("错误码:" + projectCache.get(projectId).getName(), "void")));
    }

    @RequestMapping("/detail.do")
    @ResponseBody
    @AuthPassport
    public JsonResult detail(String id, String projectId) throws MyException {
        Error model;
        if (id != null) {
            model = errorService.getById(id);
            checkUserPermissionByProject(model.getProjectId(), VIEW);
        } else {
            model = new Error();
            model.setProjectId(projectId);
        }
        return new JsonResult(1, ErrorAdapter.getDto(model));
    }

    @RequestMapping("/addOrUpdate.do")
    @ResponseBody
    public JsonResult addOrUpdate(@ModelAttribute ErrorDto dto) throws MyException {
        String projectId = dto.getProjectId();
        String errorCode = dto.getErrorCode();

        Assert.notNull(projectId, "projectId can't be null");
        Assert.notNull(errorCode, "errorCode can't be null");

        if (!MyString.isEmpty(dto.getId())) {
            // 错误码重复及权限检查
            Error dbError = errorService.getById(dto.getId());
            if (!dbError.getErrorCode().equals(dto.getErrorCode())){
                boolean existSameErrorCode = customErrorService.countByProjectIdAndErrorCode(projectId, errorCode) > 0;
                if (existSameErrorCode) {
                    return new JsonResult(MyError.E000002);
                }
            }
            checkUserPermissionByProject(dbError.getProjectId(), MOD_ERROR);

            Error newModel = ErrorAdapter.getModel(dto);
            newModel.setProjectId(null);
            errorService.update(newModel);
            return new JsonResult(1, dto);
        }

        boolean existSameErrorCode = customErrorService.countByProjectIdAndErrorCode(projectId, errorCode) > 0;
        if (existSameErrorCode) {
            return new JsonResult(MyError.E000002);
        }

        checkUserPermissionByProject(projectId, ADD_ERROR);
        errorService.insert(ErrorAdapter.getModel(dto));
        return new JsonResult(1, dto);
    }

    @RequestMapping("/delete.do")
    @ResponseBody
    public JsonResult delete(String id) throws MyException {
        throwExceptionWhenIsNull(id, "id");

        Error model = errorService.getById(id);
        if (model == null) {
            throw new MyException(MyError.E000063);
        }
        checkUserPermissionByProject(model.getProjectId(), DEL_ERROR);

        errorService.delete(id);
        return new JsonResult(1, null);
    }

}
