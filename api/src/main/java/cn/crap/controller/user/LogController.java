package cn.crap.controller.user;

import cn.crap.adapter.LogAdapter;
import cn.crap.enu.ProjectPermissionEnum;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.model.Log;
import cn.crap.query.LogQuery;
import cn.crap.service.LogService;
import cn.crap.utils.IConst;
import cn.crap.utils.Page;
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
@RequestMapping("/user/log")
public class LogController extends BaseController {
    @Autowired
    private LogService logService;

    @RequestMapping("/list.do")
    @ResponseBody
    public JsonResult list(@ModelAttribute LogQuery query) throws Exception{
        Page page = new Page(query);

        page.setAllRow(logService.count(query));
        List<Log> logList = logService.query(query);

        if (logList.size() > 0){
            checkPermission(logService.getProjectIdByLog(logList.get(0)), ProjectPermissionEnum.MY_DATE);
        }

        return new JsonResult().success().data(LogAdapter.getDto(logList)).page(page);
    }

    @RequestMapping("/detail.do")
    @ResponseBody
    public JsonResult detail(@ModelAttribute Log log) throws Exception{
        Log model;
        if (!log.getId().equals(IConst.NULL_ID)) {
            model = logService.getById(log.getId());
            checkPermission(logService.getProjectIdByLog(model), ProjectPermissionEnum.MY_DATE);
        } else {
            model = new Log();
        }
        return new JsonResult(1, LogAdapter.getDto(model));
    }

    @RequestMapping("/recover.do")
    @ResponseBody
    public JsonResult recover(@ModelAttribute Log log) throws MyException {
        log = logService.getById(log.getId());;
        checkPermission(logService.getProjectIdByLog(log), ProjectPermissionEnum.MY_DATE);
        logService.recover(log);
        return new JsonResult(1, null);
    }
}
