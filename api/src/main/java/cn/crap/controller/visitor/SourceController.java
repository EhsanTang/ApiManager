package cn.crap.controller.visitor;

import cn.crap.adapter.SourceAdapter;
import cn.crap.dto.SourceDto;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.model.ModulePO;
import cn.crap.model.ProjectPO;
import cn.crap.model.Source;
import cn.crap.query.SourceQuery;
import cn.crap.service.SourceService;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller("visitorSourceController")
@RequestMapping("/visitor/source")
public class SourceController extends BaseController {

    @Autowired
    private SourceService sourceService;

    @RequestMapping("/detail.do")
    @ResponseBody
    public JsonResult webDetail(String id, String password, String visitCode) throws MyException {
        Source model = sourceService.getById(id);
        ProjectPO project = projectCache.get(model.getProjectId());
        // 如果是私有项目，必须登录才能访问，公开项目需要查看是否需要密码
        checkFrontPermission(password, visitCode, project);
        return new JsonResult(1, model);
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public JsonResult webList(@ModelAttribute SourceQuery query, String password, String visitCode) throws MyException {
        ModulePO module = moduleCache.get(query.getModuleId());
        ProjectPO project = projectCache.get(module.getProjectId());
        // 如果是私有项目，必须登录才能访问，公开项目需要查看是否需要密码
        checkFrontPermission(password, visitCode, project);

        Page page = new Page(query);
        List<Source> sources = sourceService.query(query);
        page.setAllRow(sourceService.count(query));
        List<SourceDto> sourceDtoList = SourceAdapter.getDto(sources);

        return new JsonResult().data(sourceDtoList).page(page).others(Tools.getMap("crumbs", Tools.getCrumbs("模块:" + module.getName(), "void")));
    }
}
