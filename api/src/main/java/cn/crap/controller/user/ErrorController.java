package cn.crap.controller.user;

import cn.crap.adapter.ErrorAdapter;
import cn.crap.dto.ErrorDto;
import cn.crap.enu.MyError;
import cn.crap.enu.SettingEnum;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.Error;
import cn.crap.model.Project;
import cn.crap.query.ErrorQuery;
import cn.crap.service.ErrorService;
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

@Controller
@RequestMapping("/user/error")
public class ErrorController extends BaseController{
    @Autowired
    private ErrorService errorService;

    /**
     * 错误码列表
     * @return
     * @throws MyException
     */
    @RequestMapping("/list.do")
    @ResponseBody
    @AuthPassport
    public JsonResult list(@ModelAttribute ErrorQuery query) throws MyException {
        Project project = getProject(query);
        checkPermission(project, READ);

        Page page = new Page(query);
        List<Error> models = errorService.query(query);
        page.setAllRow(errorService.count(query));
        List<ErrorDto> dtoList = ErrorAdapter.getDto(models);

        return new JsonResult().data(dtoList).page(page)
                .others(Tools.getMap("crumbs", Tools.getCrumbs("错误码:" + project.getName(), "void")));
    }

    @RequestMapping("/detail.do")
    @ResponseBody
    @AuthPassport
    public JsonResult detail(String id, String projectId) throws MyException {
        Error model;
        if (id != null) {
            model = errorService.getById(id);
            checkPermission(projectCache.get(model.getProjectId()), READ);
        } else {
            model = new Error();
            model.setProjectId(projectId);
            checkPermission(projectCache.get(projectId), READ);
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
                boolean existSameErrorCode = errorService.count(new ErrorQuery().setProjectId(projectId).setEqualErrorCode(errorCode)) > 0;
                if (existSameErrorCode) {
                    return new JsonResult(MyError.E000002);
                }
            }
            checkPermission(dbError.getProjectId(), MOD_ERROR);

            Error newModel = ErrorAdapter.getModel(dto);
            newModel.setProjectId(null);
            errorService.update(newModel);
            return new JsonResult(1, dto);
        }

        boolean existSameErrorCode = errorService.count(new ErrorQuery().setProjectId(projectId).setEqualErrorCode(errorCode)) > 0;
        if (existSameErrorCode) {
            return new JsonResult(MyError.E000002);
        }
        if (errorService.count(new ErrorQuery().setProjectId(projectId)) > settingCache.getInteger(SettingEnum.MAX_ERROR)){
            return new JsonResult(MyError.E000072);
        }

        checkPermission(projectId, ADD_ERROR);
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
        checkPermission(model.getProjectId(), DEL_ERROR);

        errorService.delete(id);
        return new JsonResult(1, null);
    }

}
