package cn.crap.controller.admin;

import cn.crap.adapter.RoleAdapter;
import cn.crap.dto.RoleDto;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.RoleCriteria;
import cn.crap.model.RoleWithBLOBs;
import cn.crap.service.RoleService;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/admin/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/list.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_ROLE)
    public JsonResult list(String roleName, Integer currentPage) {
        Page page = new Page(currentPage);

        RoleCriteria example = new RoleCriteria();
        if (!MyString.isEmpty(roleName)) {
            example.createCriteria().andRoleNameLike("%" + roleName + "%");
        }
        // TODO   Assert.isNull(query.getCurrentPage(), "WithBLOBs 不支持分页查询");
        List<RoleDto> roleDtos = RoleAdapter.getDto(roleService.selectByExampleWithBLOBs(example));
        return new JsonResult().success().data(roleDtos).page(page);
    }

    @RequestMapping("/detail.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_ROLE)
    public JsonResult detail(String id) {
        RoleWithBLOBs role = new RoleWithBLOBs();
        if (id != null) {
            role = roleService.getById(id);
        }
        return new JsonResult().success().data(RoleAdapter.getDto(role));
    }

    @RequestMapping("/addOrUpdate.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_ROLE)
    public JsonResult addOrUpdate(@ModelAttribute RoleDto roleDto) throws Exception{
        if (roleDto.getId() != null) {
            roleService.update(RoleAdapter.getModel(roleDto));
        } else {
            roleService.insert(RoleAdapter.getModel(roleDto));
        }
        return new JsonResult().data(roleDto);
    }

    @RequestMapping("/delete.do")
    @ResponseBody
    @AuthPassport(authority = C_AUTH_ROLE)
    public JsonResult delete(@RequestParam String id) throws MyException{
        roleService.delete(id);
        return SUCCESS;
    }
}
