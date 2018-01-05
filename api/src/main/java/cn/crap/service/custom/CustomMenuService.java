package cn.crap.service.custom;

import cn.crap.dao.mybatis.MenuDao;
import cn.crap.dao.custom.CustomArticleDao;
import cn.crap.dto.MenuWithSubMenuDto;
import cn.crap.dto.PickDto;
import cn.crap.enumer.MenuType;
import cn.crap.enumer.ProjectStatus;
import cn.crap.framework.MyException;
import cn.crap.model.mybatis.Project;
import cn.crap.model.mybatis.Menu;
import cn.crap.model.mybatis.MenuCriteria;
import cn.crap.beans.Config;
import cn.crap.service.IPickService;
import cn.crap.utils.IConst;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CustomMenuService {
    @Autowired
    private MenuDao dao;
    @Autowired
    private CustomProjectService customProjectService;
    @Autowired
    private Config config;
    @Autowired
    private CustomArticleDao customArticleMapper;
    @Autowired
    private CustomModuleService customModuleService;
    @Resource(name="pickService")
    private IPickService pickService;

    public List<Menu> queryByParentId(String parentId){
        Assert.notNull(parentId, "parentId can't be null");
        MenuCriteria example = new MenuCriteria();
        example.createCriteria().andParentIdEqualTo(parentId);
        return  dao.selectByExample(example);
    }

    public List<Menu> queryByParentIds(List<String> parentIds){
        Assert.notNull(parentIds, "parentIds can't be null");
        if (parentIds.size() == 0){
            return Collections.emptyList();
        }
        MenuCriteria example = new MenuCriteria();
        example.createCriteria().andParentIdIn(parentIds);
        return  dao.selectByExample(example);
    }



    public List<MenuWithSubMenuDto> getLeftMenu() {
        List<Menu> menus = queryByParentId("0");
        List<String> menuIds = new ArrayList<String>();
        for (Menu menu : menus) {
            menuIds.add(menu.getId());
        }

        List<Menu> subMenus = queryByParentIds(menuIds);
        List<MenuWithSubMenuDto> menuVOs = new ArrayList<MenuWithSubMenuDto>();

        Page page = new Page(config.getSubMenuSize(),1);
        // 加载默认推荐项目菜单
        if(config.isShowRecommendProject()){
            MenuWithSubMenuDto menuVO = new MenuWithSubMenuDto();
            Menu menu = new Menu();
            menu.setIconRemark("<i class=\"iconfont\">&#xe636;</i>");
            menu.setId("recommendProjectId");
            menu.setMenuName(config.getRecommendProjectMenuName());
            menu.setParentId("0");
            menu.setType(MenuType.FRONT.name());
            menuVO.setMenu(menu);

            menuVO.setSubMenu(new ArrayList<Menu>());
            for (Project project : customProjectService.pageProjectByStatusName(ProjectStatus.RECOMMEND.getStatus(), null, page)) {
                Menu subMenu = new Menu();
                subMenu.setId("recPro_"+project.getId());
                subMenu.setMenuName(project.getName());
                subMenu.setParentId("recommendProjectId");
                subMenu.setType(MenuType.FRONT.name());
                subMenu.setMenuUrl(String.format(IConst.FRONT_PROJECT_URL, project.getId()));
                menuVO.getSubMenu().add(subMenu);
            }
            // 添加更多按钮
            if(menuVO.getSubMenu().size() == page.getSize()){
                Menu subMenu = new Menu();
                subMenu.setId("recPro_more");
                subMenu.setMenuName("更多项目...");
                subMenu.setParentId("recommendProjectId");
                subMenu.setType(MenuType.FRONT.name());
                subMenu.setMenuUrl("#/project/list/false/NULL");
                menuVO.getSubMenu().add(subMenu);
            }
            menuVOs.add(menuVO);
        }

        // 加载默认推荐文章
        if(config.isShowArticle()){
            MenuWithSubMenuDto menuVO = new MenuWithSubMenuDto();
            Menu menu = new Menu();
            menu.setIconRemark("<i class=\"iconfont\">&#xe637;</i>");
            menu.setId("articleListId");
            menu.setMenuName(config.getArticleMenuName());
            menu.setParentId("0");
            menu.setType(MenuType.FRONT.name());
            menuVO.setMenu(menu);

            menuVO.setSubMenu(new ArrayList<Menu>());
            List<String> categorys = customModuleService.queryCategoryByModuleId(IConst.C_WEB_MODULE);
            int i = 0;
            for (String category : categorys) {
                if (MyString.isEmpty(category))
                    continue;
                i ++ ;
                Menu subMenu = new Menu();
                subMenu.setId("arcList_cate_"+i);
                subMenu.setMenuName(category);
                subMenu.setParentId("articleListId");
                subMenu.setType(MenuType.FRONT.name());
                subMenu.setMenuUrl(String.format(IConst.FRONT_ARTICLE_URL, IConst.C_WEB_MODULE, IConst.C_WEB_MODULE, category));
                menuVO.getSubMenu().add(subMenu);
            }
            menuVOs.add(menuVO);
        }

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

    public String pick(String radio, String code, String key, String def, String notNull) throws MyException{

        List<PickDto> picks =  pickService.getPickList(code, key);

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
                        pickContent.append(String.format(radioDiv, isCheck ? " pickActive" : "",
                                p.getId(), p.getId(), p.getId(), isCheck ? "checked" : "",
                                p.getValue(), p.getName()));
                    } else {
                        boolean isCheck = (def != null && ("," + def).indexOf("," + p.getValue() + ",") >= 0);
                        pickContent.append(String.format(checkBoxDiv,
                                isCheck ? " pickActive" : "", p.getId(),
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
