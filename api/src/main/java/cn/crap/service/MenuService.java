package cn.crap.service;

import cn.crap.beans.Config;
import cn.crap.dao.mybatis.MenuDao;
import cn.crap.dto.MenuWithSubMenuDto;
import cn.crap.dto.PickDto;
import cn.crap.enumer.TableId;
import cn.crap.framework.MyException;
import cn.crap.model.Menu;
import cn.crap.model.MenuCriteria;
import cn.crap.query.MenuQuery;
import cn.crap.utils.IConst;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService extends BaseService<Menu, MenuDao> {
    private MenuDao menuDao;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private Config config;
    @Autowired
    private ModuleService moduleService;
    @Resource(name = "pickService")
    private IPickService pickService;

    @Resource
    public void MenuDao(MenuDao menuDao) {
        this.menuDao = menuDao;
        super.setBaseDao(menuDao, TableId.MENU);
    }

    @Override
    public boolean insert(Menu menu) throws MyException{
        if (menu == null) {
            return false;
        }
        if (menu.getSequence() == null){
            List<Menu>  menus = this.query(new MenuQuery().setPageSize(1).setParentId(menu.getParentId()));
            if (menus.size() > 0){
                menu.setSequence(menus.get(0).getSequence() + 1);
            }else{
                menu.setSequence(0);
            }
        }
        return super.insert(menu);
    }

    /**
     * 查询菜单
     *
     * @param query
     * @return
     * @throws MyException
     */
    public List<Menu> query(MenuQuery query) throws MyException {
        Assert.notNull(query);

        Page page = new Page(query);
        MenuCriteria example = getMenuCriteria(query);
        example.setLimitStart(page.getStart());
        example.setMaxResults(page.getSize());
        example.setOrderByClause(query.getSort() == null ? TableField.SORT.SEQUENCE_DESC : query.getSort());

        return menuDao.selectByExample(example);
    }

    /**
     * 查询菜单数量
     *
     * @param query
     * @return
     * @throws MyException
     */
    public int count(MenuQuery query) throws MyException {
        Assert.notNull(query);

        MenuCriteria example = getMenuCriteria(query);
        return menuDao.countByExample(example);
    }

    private MenuCriteria getMenuCriteria(MenuQuery query) throws MyException {
        MenuCriteria example = new MenuCriteria();
        MenuCriteria.Criteria criteria = example.createCriteria();
        if (query.getParentId() != null) {
            criteria.andParentIdEqualTo(query.getParentId());
        }
        if (!CollectionUtils.isEmpty(query.getParentIds())) {
            criteria.andParentIdIn(query.getParentIds());
        }
        if (query.getMenuName() != null) {
            criteria.andMenuNameLike("%" + query.getMenuName() + "%");
        }
        if (query.getType() != null){
            criteria.andTypeEqualTo(query.getType());
        }
        return example;
    }


    public List<MenuWithSubMenuDto> getLeftMenu() throws MyException {
        List<Menu> menus = query(new MenuQuery().setParentId("0"));
        List<String> menuIds = new ArrayList<>();
        for (Menu menu : menus) {
            menuIds.add(menu.getId());
        }

        List<Menu> subMenus = query(new MenuQuery().setParentIds(menuIds));
        List<MenuWithSubMenuDto> menuVOs = new ArrayList<>();

        for (Menu menu : menus) {
            MenuWithSubMenuDto menuVO = new MenuWithSubMenuDto();
            menuVO.setMenu(menu);
            menuVO.setSubMenu(new ArrayList<Menu>());
            for (Menu subMenu : subMenus) {
                if (subMenu.getParentId().equals(menu.getId())) {
                    menuVO.getSubMenu().add(subMenu);
                }
            }
            menuVOs.add(menuVO);
        }
        return menuVOs;
    }

    public String pick(String radio, String code, String key, String def, String notNull) throws MyException {

        List<PickDto> picks = pickService.getPickList(code, key);

        // 单选是否可以为空
        if (radio.equals("true") && !MyString.isEmpty(notNull) && notNull.equals("false")) {
            PickDto pick = new PickDto("pick_null", "", "");
            picks.add(0, pick);
        }

        // 组装字符串，返回至前端页面
        if (!radio.equals("")) {
            StringBuilder pickContent = new StringBuilder();
            String separator = "<div class='separator'>%s</div>";
            String radioDiv = "<div class='p5 tl cursor%s' id='d_%s' onclick=\"pickCheck('%s','true');\">"
                    + "<input id='%s' type='radio' %s disabled name='cid' value='%s'> "
                    + "&nbsp;&nbsp; <span class='cidName'>%s</span></div>";
            String checkBoxDiv = "<div class='p5 tl cursor%s' id='d_%s' onclick=\"pickCheck('%s');\">"
                    + "<input id='%s' type='checkbox' %s disabled name='cid' value='%s'>"
                    + "&nbsp;&nbsp; <span class='cidName'>%s</span><br></div>";

            for (PickDto p : picks) {
                if (p.getValue().equals(IConst.SEPARATOR)) {
                    pickContent.append(String.format(separator, p.getName()));
                } else {
                    if (radio.equals("true")) {
                        boolean isCheck = (def != null && def.equals(p.getValue()));
                        pickContent.append(String.format(radioDiv, isCheck ? " pickActive main-color" : "",
                                p.getId(), p.getId(), p.getId(), isCheck ? "checked" : "",
                                p.getValue(), p.getName()));
                    } else {
                        boolean isCheck = (def != null && ("," + def).indexOf("," + p.getValue() + ",") >= 0);
                        pickContent.append(String.format(checkBoxDiv,
                                isCheck ? " pickActive main-color" : "", p.getId(),
                                p.getId(), p.getId(),
                                isCheck ? "checked" : "", p.getValue(),
                                p.getName()));
                    }
                }
            }
            return pickContent.toString();
        }
        return "";
    }
}
