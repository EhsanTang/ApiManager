package cn.crap.service.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.crap.dto.MenuDto;
import cn.crap.dto.PickDto;
import cn.crap.enumeration.MenuType;
import cn.crap.enumeration.ProjectStatus;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseService;
import cn.crap.framework.base.IBaseDao;
import cn.crap.inter.dao.IMenuDao;
import cn.crap.inter.service.table.IArticleService;
import cn.crap.inter.service.table.IMenuService;
import cn.crap.inter.service.table.IProjectService;
import cn.crap.model.Menu;
import cn.crap.model.Project;
import cn.crap.springbeans.Config;
import cn.crap.springbeans.PickFactory;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;

@Service
public class MenuService extends BaseService<Menu> implements IMenuService {

	@Autowired
	private IProjectService projectService;
	@Autowired
	private IArticleService articleService;
	@Autowired
	private PickFactory pickFactory;
	@Autowired
	private Config config;
	@Autowired
	private IMenuDao menuDao;
	
	@Resource(name = "menuDao")
	public void setDao(IBaseDao<Menu> dao) {
		super.setDao(dao);
	}

	@Override
	@Transactional
	public Menu get(String id){
		Menu model = menuDao.get(id);
		if(model == null)
			 return new Menu();
		return model;
	}
	
	@Override
	@Transactional
	public List<MenuDto> getLeftMenu(Map<String,Object> map) {
		map = Tools.getMap("parentId", "0");
		List<Menu> menus = findByMap(map, null, null);
		map.clear();
		List<String> menuIds = new ArrayList<String>();
		for (Menu menu : menus) {
			menuIds.add(menu.getId());
		}
		map.put("parentId|in", menuIds);
		List<Menu> subMenus = findByMap(map, null, null);
		List<MenuDto> menuVOs = new ArrayList<MenuDto>();
		
		Page page = new Page();
		page.setSize(config.getSubMenuSize());
		// 加载默认推荐项目菜单
		if(config.isShowRecommendProject()){
			MenuDto menuVO = new MenuDto();
			Menu menu = new Menu();
			menu.setIconRemark("<i class=\"iconfont\">&#xe636;</i>");
			menu.setId("recommendProjectId");
			menu.setMenuName(config.getRecommendProjectMenuName());
			menu.setParentId("0");
			menu.setType(MenuType.FRONT.name());
			menuVO.setMenu(menu);
			
			menuVO.setSubMenu(new ArrayList<Menu>());
			for (Project project : projectService.findByMap(Tools.getMap("status", ProjectStatus.RECOMMEND.getStatus()), page, null)) {
				Menu subMenu = new Menu();
				subMenu.setId("recPro_"+project.getId());
				subMenu.setMenuName(project.getName());
				subMenu.setParentId("recommendProjectId");
				subMenu.setType(MenuType.FRONT.name());
				subMenu.setMenuUrl(String.format(Const.FRONT_PROJECT_URL, project.getId()));
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
			MenuDto menuVO = new MenuDto();
			Menu menu = new Menu();
			menu.setIconRemark("<i class=\"iconfont\">&#xe637;</i>");
			menu.setId("articleListId");
			menu.setMenuName(config.getArticleMenuName());
			menu.setParentId("0");
			menu.setType(MenuType.FRONT.name());
			menuVO.setMenu(menu);
			
			menuVO.setSubMenu(new ArrayList<Menu>());
			@SuppressWarnings("unchecked")
			List<String> categorys = (List<String>) articleService.queryByHql("select distinct category from Article where moduleId='web'", null, page);
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
				subMenu.setMenuUrl(String.format(Const.FRONT_ARTICLE_URL, Const.WEB_MODULE, Const.WEB_MODULE, category));
				menuVO.getSubMenu().add(subMenu);
			}
			menuVOs.add(menuVO);
		}
		
		for (Menu menu : menus) {
			MenuDto menuVO = new MenuDto();
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
	@Override
	@Transactional
	public String pick(List<PickDto> picks, String radio, String code, String key, String def, String notNull) throws MyException {
		PickDto pick = null;
		
		// 单选是否可以为空
		if (radio.equals("true") && !MyString.isEmpty(notNull) && notNull.equals("false")) {
			pick = new PickDto("pick_null", "", "");
			picks.add(pick);
		}
		
		// 根据code，key加载pick列表
		pickFactory.getPickList(picks, code, key);
		
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
				if (p.getValue().equals(Const.SEPARATOR)) {
					pickContent.append(String.format(separator, p.getName()));
				} else {
					if (radio.equals("true")) {
						pickContent.append(String.format(radioDiv, def.equals(p.getValue()) ? " pickActive" : "",
								p.getId(), p.getId(), p.getId(), def.equals(p.getValue()) ? "checked" : "",
								p.getValue(), p.getName()));
					} else {
						pickContent.append(String.format(checkBoxDiv,
								("," + def).indexOf("," + p.getValue() + ",") >= 0 ? " pickActive" : "", p.getId(),
								p.getId(), p.getId(),
								("," + def).indexOf("," + p.getValue() + ",") >= 0 ? "checked" : "", p.getValue(),
								p.getName()));
					}
				}
			}
			return pickContent.toString();
		}
		return "";
	}

}
