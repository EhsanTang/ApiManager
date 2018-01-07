package cn.crap.controller.admin;

import cn.crap.adapter.HotSearchAdapter;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.mybatis.*;
import cn.crap.service.mybatis.HotSearchService;
import cn.crap.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HotSearchController extends BaseController {

    @Autowired
    private HotSearchService hotSearchService;

    @RequestMapping("/hotSearch/list.do")
    @ResponseBody
    @AuthPassport(authority = C_HOT_SEARCH)
    public JsonResult list(Integer currentPage) {
        Page page = new Page(currentPage);
        HotSearchCriteria example = new HotSearchCriteria();

        example.setOrderByClause(TableField.SORT.TIMES_DESC);
        example.setLimitStart(page.getStart());
        example.setMaxResults(page.getSize());

        page.setAllRow(hotSearchService.countByExample(example));
        return new JsonResult(1, HotSearchAdapter.getDto(hotSearchService.selectByExample(example)), page);
    }

    @RequestMapping("/hotSearch/detail.do")
    @ResponseBody
    @AuthPassport(authority = C_HOT_SEARCH)
    public JsonResult detail(String id) {
        HotSearch hotSearch = new HotSearch();
        if (id != null) {
            hotSearch = hotSearchService.getById(id);
        }
        return new JsonResult().data(HotSearchAdapter.getDto(hotSearch));
    }


    @RequestMapping("/hotSearch/delete.do")
    @ResponseBody
    @AuthPassport(authority = C_HOT_SEARCH)
    public JsonResult delete(@RequestParam String id) throws MyException {
        hotSearchService.delete(id);
        return SUCCESS;
    }
}
