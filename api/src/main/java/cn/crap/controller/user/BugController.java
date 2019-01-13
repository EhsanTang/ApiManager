package cn.crap.controller.user;

import cn.crap.adapter.BugAdapter;
import cn.crap.dto.BugDto;
import cn.crap.enu.*;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.Bug;
import cn.crap.model.Module;
import cn.crap.model.Project;
import cn.crap.query.BugQuery;
import cn.crap.service.BugService;
import cn.crap.service.MenuService;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user/bug")
public class BugController extends BaseController{
    // TODO 权限目前只要项目成员即可操作

    @Autowired
    private BugService bugService;
    @Autowired
    MenuService customMenuService;

    /**
     * bug管理系统下拉选择框
     * @param request
     * @param type
     * @param def
     * @param tag
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "pick.do")
    @AuthPassport
    public String pickUp(HttpServletRequest request, String type, String def,
                          String tag) throws Exception {
        String pickContent = customMenuService.pick("true", type, null, def, "true");
        request.setAttribute("tag", tag);
        request.setAttribute("pickContent", pickContent);
        request.setAttribute("type", type);
        return "WEB-INF/views/bugPick.jsp";
    }

    /**
     * 修改缺陷状态（严重程度、优先级等）
     * @param type 待修改的状态类型
     * @param value 新的状态
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "changeBug.do")
    @ResponseBody
    @AuthPassport
    public JsonResult changeBug(String id, @RequestParam(defaultValue = "") String type, String value) throws Exception{
        // TODO 修改日志
        Bug dbBug = bugService.get(id);
        checkPermission(dbBug.getProjectId(), READ);

        Bug bug = bugService.getChangeBugPO(id, type, value);
        boolean update = bugService.update(bug);

        dbBug = bugService.get(id);
        Module module = moduleCache.get(dbBug.getModuleId());
        Project project = projectCache.get(dbBug.getProjectId());

        BugDto dto = BugAdapter.getDto(dbBug, module, project);
        return new JsonResult(update).data(dto);
    }


    /**
     * bug列表
     * @return
     * @throws MyException
     */
    @RequestMapping("/list.do")
    @ResponseBody
    @AuthPassport
    public JsonResult list(@ModelAttribute BugQuery query) throws MyException {
        Project project = getProject(query);
        checkPermission(project, READ);

        Page page = new Page(query);
        List<Bug> models = bugService.select(query);
        page.setAllRow(bugService.count(query));
        List<BugDto> dtoList = BugAdapter.getDto(models);

        return new JsonResult().data(dtoList).page(page);
    }

    @RequestMapping("/detail.do")
    @ResponseBody
    @AuthPassport
    public JsonResult detail(String id, String projectId) throws MyException {
        Bug model;
        Module module = null;
        Project project = null;
        if (id != null) {
            model = bugService.get(id);
            module = moduleCache.get(model.getModuleId());
            project = projectCache.get(model.getProjectId());
            checkPermission(projectCache.get(model.getProjectId()), READ);
        } else {
            model = new Bug();
            model.setProjectId(projectId);
            project = projectCache.get(model.getProjectId());
            checkPermission(project, READ);
        }
        return new JsonResult(1, BugAdapter.getDto(model, module, project));
    }

    @RequestMapping("/addOrUpdate.do")
    @ResponseBody
    @AuthPassport
    public JsonResult addOrUpdate(@ModelAttribute BugDto dto) throws MyException {
        String projectId = dto.getProjectId();
        Assert.notNull(projectId, "projectId can't be null");

        if (!MyString.isEmpty(dto.getId())) {
            Bug dbBug = bugService.get(dto.getId());
            // checkPermission(dbBug.getProjectId(), MOD_ERROR);

            Bug newModel = BugAdapter.getModel(dto);
            bugService.update(newModel);
            return new JsonResult(1, dto);
        }

        dto.setStatus(BugStatus.NEW.getByteValue());
        dto.setAssignedTo("tt");
        dto.setCreatedBy("tt");
        dto.setName("name");
        dto.setPriority(BugPriority.URGENT.getByteValue());
        dto.setSeverity(BugSeverity.BLOCK.getByteValue());
        dto.setTester("tt");
        dto.setTracer("tt");
        dto.setTraceType(BugTraceType.FUNCTION.getByteValue());
        // checkPermission(projectId, ADD_ERROR);
        bugService.insert(BugAdapter.getModel(dto));
        return new JsonResult(1, dto);
    }

    @RequestMapping("/delete.do")
    @ResponseBody
    @AuthPassport
    public JsonResult delete(String id) throws MyException {
        throwExceptionWhenIsNull(id, "id");

        Bug model = bugService.get(id);
        if (model == null) {
            throw new MyException(MyError.E000063);
        }
        checkPermission(model.getProjectId(), DEL_ERROR);
        bugService.delete(id);
        return new JsonResult(1, null);
    }

}
