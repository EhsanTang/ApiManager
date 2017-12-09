package cn.crap.controller.user;

import java.util.List;

import cn.crap.adapter.ErrorAdapter;
import cn.crap.dto.ErrorDto;
import cn.crap.model.mybatis.ErrorCriteria;
import cn.crap.service.mybatis.custom.CustomErrorService;
import cn.crap.service.mybatis.imp.MybatisErrorService;
import cn.crap.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.service.ICacheService;
import cn.crap.model.mybatis.Error;

// TODO jsonResult 优化，错误码提示国际化，html页面国际化
@Controller
@RequestMapping("/user/error")
public class ErrorController extends BaseController{
    @Autowired
    private ICacheService cacheService;
    @Autowired
    private MybatisErrorService errorService;
    @Autowired
    private CustomErrorService customErrorService;

    /**
     * @return
     * @throws MyException
     * @throws Exception
     */
    @RequestMapping("/list.do")
    @ResponseBody
    @AuthPassport
    public JsonResult list(String projectId, String errorCode, String errorMsg, @RequestParam(defaultValue = "1") Integer currentPage) throws MyException {
        hasPermission(cacheService.getProject(projectId), view);

        if (MyString.isEmpty(projectId)) {
            throw new MyException("000020");
        }

        Page page = new Page(15, currentPage);

        List<Error> models = customErrorService.pageByProjectIdCodeMsg(projectId, errorCode, errorMsg, page);
        List<ErrorDto> dtoList = ErrorAdapter.getDto(models);

        return new JsonResult(1, dtoList, page).others(Tools.getMap("crumbs", Tools.getCrumbs("错误码:" + cacheService.getProject(projectId).getName(), "void")));
    }

    @RequestMapping("/detail.do")
    @ResponseBody
    @AuthPassport
    public JsonResult detail(String id, String projectId) throws MyException {
        Error model;
        if (id != null) {
            model = errorService.selectByPrimaryKey(id);
            hasPermission(cacheService.getProject(model.getProjectId()), view);
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

        // update
        if (!MyString.isEmpty(dto.getId())) {
            Error model = errorService.selectByPrimaryKey(dto.getId());
            hasPermission(cacheService.getProject(model.getProjectId()), modError);

            Error newModel = ErrorAdapter.getModel(dto);
            newModel.setProjectId(null);
            errorService.update(newModel);
            return new JsonResult(1, dto);
        }

        // add
        boolean existSameErrorCode = customErrorService.countByProjectIdAndErrorCode(projectId, errorCode) > 0;
        if (!existSameErrorCode) {
            hasPermission(cacheService.getProject(dto.getProjectId()), addError);
            errorService.insert(ErrorAdapter.getModel(dto));
        } else {
            return new JsonResult(new MyException("000002"));
        }
        return new JsonResult(1, dto);
    }

    @RequestMapping("/delete.do")
    @ResponseBody
    public JsonResult delete(String id) throws MyException {
        Assert.notNull(id, "id can't be null");

        Error model = errorService.selectByPrimaryKey(id);
        if (model == null) {
            throw new MyException("000063");
        }
        hasPermission(cacheService.getProject(model.getProjectId()), delError);

        errorService.delete(id);
        return new JsonResult(1, null);
    }

}
