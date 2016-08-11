package cn.crap.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.crap.dto.MenuDto;
import cn.crap.dto.PickDto;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseService;
import cn.crap.framework.base.IBaseDao;
import cn.crap.inter.service.IErrorService;
import cn.crap.inter.service.IMenuService;
import cn.crap.inter.service.IDataCenterService;
import cn.crap.inter.service.IRoleService;
import cn.crap.inter.service.IWebPageService;
import cn.crap.model.Menu;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.PickFactory;
import cn.crap.utils.Tools;

@Service
public class MenuService extends BaseService<Menu> implements IMenuService {

	@Autowired
	private IDataCenterService dataCenter;
	@Autowired
	private IErrorService errorService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IWebPageService webPageService;
	@Autowired
	private IDataCenterService dataCenterService;
	
	@Resource(name = "menuDao")
	public void setDao(IBaseDao<Menu> dao) {
		super.setDao(dao, new Menu());
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
		PickFactory.getFrontPickList(picks, code, key, this, dataCenter, errorService, roleService, webPageService, dataCenterService);
		
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
