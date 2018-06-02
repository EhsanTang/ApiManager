package cn.crap.controller.admin;

import cn.crap.adapter.LogAdapter;
import cn.crap.dto.LogDto;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.mybatis.Log;
import cn.crap.model.mybatis.LogCriteria;
import cn.crap.service.custom.CustomLogService;
import cn.crap.service.mybatis.LogService;
import cn.crap.utils.IConst;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Ehsan
 */
@Controller
@RequestMapping("/log")
public class LogController extends BaseController {

    @Autowired
    private LogService logService;
    @Autowired
    private CustomLogService customLogService;

    @RequestMapping("/list.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_LOG)
    public JsonResult list(String identy, Integer currentPage, String modelName) {
        Page page = new Page(currentPage);
        LogCriteria example = new LogCriteria();
        LogCriteria.Criteria criteria = example.createCriteria();
        if(MyString.isNotEmpty(identy)){
            criteria.andIdentyEqualTo(identy);
        }
        example.setOrderByClause(TableField.SORT.CREATE_TIME_DES);
        if (MyString.isNotEmpty(modelName)){
            criteria.andModelNameEqualTo(modelName);
        }

        example.setOrderByClause(TableField.SORT.CREATE_TIME_DES);
        example.setLimitStart(page.getStart());
        example.setMaxResults(page.getSize());

        page.setAllRow(logService.countByExample(example));
        List<LogDto> logDtoList = LogAdapter.getDto(logService.selectByExample(example));
        return new JsonResult().success().data(logDtoList).page(page);
    }

    @RequestMapping("/detail.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_LOG)
    public JsonResult detail(@ModelAttribute Log log) {
        Log model;
        if (!log.getId().equals(IConst.NULL_ID)) {
            model = logService.getById(log.getId());
        } else {
            model = new Log();
        }
        return new JsonResult(1, LogAdapter.getDto(model));
    }

    @RequestMapping("/recover.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_LOG)
    public JsonResult recover(@ModelAttribute Log log) throws MyException {
        customLogService.recover(log);
        return new JsonResult(1, null);
    }
}
