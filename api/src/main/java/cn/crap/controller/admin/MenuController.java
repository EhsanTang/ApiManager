package cn.crap.controller.admin;

import cn.crap.adapter.MenuAdapter;
import cn.crap.dto.MenuDto;
import cn.crap.enu.MyError;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.Menu;
import cn.crap.query.MenuQuery;
import cn.crap.service.MenuService;
import cn.crap.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class MenuController extends BaseController {
    @Autowired
    private MenuService menuService;

    /**
     * 根据父菜单、菜单名、菜单类型及页码获取菜单列表
     *
     * @return
     */
    @RequestMapping("/menu/list.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_MENU)
    public JsonResult list(@ModelAttribute MenuQuery query) throws MyException{
        Page page = new Page(query);

        page.setAllRow(menuService.count(query));
        return new JsonResult(1, MenuAdapter.getDto(menuService.query(query)), page);
    }

    /**
     * 菜单详情
     * @param id
     * @param parentId
     * @param type 菜单类型，当id为null，parentId为null时，表示新增父菜单，需要根据传入的type默认选中菜单类型
     * @return
     */
    @RequestMapping("/menu/detail.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_MENU)
    public JsonResult detail(String id, String parentId, String type) {
        Menu menu = new Menu();
        menu.setParentId(parentId);
        Menu parentMenu = menuService.getById(parentId);
        if (id != null) {
            menu = menuService.getById(id);
        }else{
            menu.setType(parentMenu == null ? type : parentMenu.getType());
        }
        MenuDto menuDto = MenuAdapter.getDto(menu);

        return new JsonResult().data(menuDto);
    }

    /**
     * @return
     */
    @RequestMapping("/menu/addOrUpdate.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_MENU)
    public JsonResult addOrUpdate(@ModelAttribute MenuDto menuDto) throws MyException{
        // 子菜单类型和父菜单类型一致
        Menu parentMenu = menuService.getById(menuDto.getParentId());
        if (parentMenu != null && parentMenu.getId() != null) {
            menuDto.setType(parentMenu.getType());
        }

        if (menuDto.getId() != null) {
            menuService.update(MenuAdapter.getModel(menuDto));
        } else {
            menuService.insert(MenuAdapter.getModel(menuDto));
        }
        // 清除缓存
        objectCache.del(C_CACHE_MENU);
        return new JsonResult().data(menuDto);
    }

    @RequestMapping("/menu/delete.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_MENU)
    public JsonResult delete(@RequestParam String id) throws MyException {
        if (menuService.count(new MenuQuery().setParentId(id)) > 0) {
            throw new MyException(MyError.E000025);
        }
        menuService.delete(id);
        // 清除缓存
        objectCache.del(C_CACHE_MENU);
        return SUCCESS;
    }
}
