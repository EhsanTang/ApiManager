package cn.crap.controller.admin;

import cn.crap.adapter.HotSearchAdapter;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.HotSearch;
import cn.crap.query.HotSearchQuery;
import cn.crap.service.HotSearchService;
import cn.crap.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HotSearchController extends BaseController {

    @Autowired
    private HotSearchService hotSearchService;

    @RequestMapping("/admin/hotSearch/list.do")
    @ResponseBody
    @AuthPassport(authority = C_HOT_SEARCH)
    public JsonResult list(@ModelAttribute HotSearchQuery query) throws MyException {
        Page page = new Page(query);

        page.setAllRow(hotSearchService.count(query));
        return new JsonResult(1, HotSearchAdapter.getDto(hotSearchService.query(query)), page);
    }

    @RequestMapping("/admin/hotSearch/detail.do")
    @ResponseBody
    @AuthPassport(authority = C_HOT_SEARCH)
    public JsonResult detail(String id) {
        HotSearch hotSearch = new HotSearch();
        if (id != null) {
            hotSearch = hotSearchService.getById(id);
        }
        return new JsonResult().data(HotSearchAdapter.getDto(hotSearch));
    }


    @RequestMapping("/admin/hotSearch/delete.do")
    @ResponseBody
    @AuthPassport(authority = C_HOT_SEARCH)
    public JsonResult delete(@RequestParam String id) throws MyException {
        hotSearchService.delete(id);
        return SUCCESS;
    }
}
