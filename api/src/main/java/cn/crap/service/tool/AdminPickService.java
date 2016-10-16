package cn.crap.service.tool;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.PickDto;
import cn.crap.enumeration.ArticleType;
import cn.crap.enumeration.DataType;
import cn.crap.enumeration.FontFamilyType;
import cn.crap.enumeration.MenuType;
import cn.crap.enumeration.ModuleStatus;
import cn.crap.enumeration.ProjectType;
import cn.crap.enumeration.SettingType;
import cn.crap.enumeration.UserType;
import cn.crap.framework.MyException;
import cn.crap.inter.dao.ICacheDao;
import cn.crap.inter.dao.IModuleDao;
import cn.crap.inter.dao.IProjectDao;
import cn.crap.inter.dao.ISettingDao;
import cn.crap.inter.service.table.IArticleService;
import cn.crap.inter.service.table.IErrorService;
import cn.crap.inter.service.table.IMenuService;
import cn.crap.inter.service.table.IModuleService;
import cn.crap.inter.service.table.IProjectService;
import cn.crap.inter.service.table.IRoleService;
import cn.crap.inter.service.table.IUserService;
import cn.crap.inter.service.tool.ICacheService;
import cn.crap.inter.service.tool.IPickService;
import cn.crap.model.Article;
import cn.crap.model.Menu;
import cn.crap.model.Module;
import cn.crap.model.Project;
import cn.crap.model.Role;
import cn.crap.model.Setting;
import cn.crap.springbeans.Config;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

/**
 * 下拉选着
 * @author Ehsan
 *
 */
@Service("adminPickService")
public class AdminPickService implements IPickService{
	@Autowired
	IMenuService menuService;
	@Autowired
	private ICacheService cacheService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IProjectService projectService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IModuleService moduleService;
	@Autowired
	private IArticleService articleService;

	@Override
	public void getPickList(List<PickDto> picks, String code, String key, LoginInfoDto user) throws MyException {
		PickDto pick = null;
		String preUrl = "";
		switch (code) {
		case "CATEGORY":
			int i = 0;
			List<String> categorys = (List<String>) articleService.queryByHql("select distinct category from Article where moduleId='web' "
					+ "or moduleId in( select id from Module where userId='" + user.getId()+"')", null);
			for (String w : categorys) {
				if (w == null)
					continue;
				i++;
				pick = new PickDto("cat_" + i, w, w);
				picks.add(pick);
			}
			return;
			
		case "MYPROJECT":
			for (Project p : projectService.findByMap(null, null, null)) {
				pick = new PickDto(p.getId(), p.getName());
				picks.add(pick);
			}
			return;
		case "PROJECT":
			for (Project p : projectService.findByMap(null, null, null)) {
				pick = new PickDto(p.getId(), p.getName());
				picks.add(pick);
			}
			return;
		// 一级菜单
		case "MENU":
			for (Menu m : menuService.findByMap(Tools.getMap("parentId", "0"), null, null)) {
				pick = new PickDto(m.getId(), m.getMenuName());
				picks.add(pick);
			}
			return;
			
		// 权限
		case "AUTH":
			pick = new PickDto(Const.SEPARATOR, "项目管理");
			picks.add(pick);
			pick = new PickDto(DataType.PROJECT.name(), "项目管理");
			picks.add(pick);
			
			// 分割线
			pick = new PickDto(Const.SEPARATOR, "用户、菜单、角色、系统设置管理");
			picks.add(pick);
			pick = new PickDto(DataType.USER.name(), "用户管理");
			picks.add(pick);
			pick = new PickDto(DataType.ROLE.name(), "角色管理");
			picks.add(pick);
			pick = new PickDto(DataType.MENU.name(), "菜单管理");
			picks.add(pick);
			pick = new PickDto(DataType.SETTING.name(), "系统设置管理");
			picks.add(pick);
			pick = new PickDto(DataType.LOG);
			picks.add(pick);

			// 分割线
			pick = new PickDto(Const.SEPARATOR, "后台菜单列表");
			picks.add(pick);
			for (Menu m : menuService.findByMap(Tools.getMap("parentId", "0", "type", MenuType.BACK.name()), null,
					null)) {
				pick = new PickDto(m.getId(), m.getMenuName() + "--【菜单】");
				picks.add(pick);
			}
			return;
		
		// 角色
		case "ROLE":
			pick = new PickDto(Const.SUPER, "超级管理员");
			picks.add(pick);
			for (Role r : roleService.findByMap(null, null, null)) {
				pick = new PickDto(r.getId(), r.getRoleName());
				picks.add(pick);
			}
			return;
		
		// 枚举 模块类型（公开、私有）
		case "MODULESTATUS":
			for (ModuleStatus status : ModuleStatus.values()) {
				pick = new PickDto(status.getName(), status.name());
				picks.add(pick);
			}
			return;
			
		// 枚举 webPage
		case "WEBPAGETYPE":
			for (ArticleType type : ArticleType.values()) {
				pick = new PickDto(type.name(), type.getName());
				picks.add(pick);
			}
			return;
		// 枚举 菜单类型
		case "MENUTYPE":
			for (MenuType type : MenuType.values()) {
				pick = new PickDto(type.name(), type.getName());
				picks.add(pick);
			}
			return;
		
		// 枚举 设置类型
		case "SETTINGTYPE":
			for (SettingType type : SettingType.values()) {
				pick = new PickDto(type.name(), type.getName());
				picks.add(pick);
			}
			return;
		case "DATATYPE":// 枚举 数据类型
			for (DataType status : DataType.values()) {
				pick = new PickDto(status.name(), status.getName());
				picks.add(pick);
			}
			return;
		case "MODELNAME":// 数据类型
			i = 0;
			List<String> modelNames = (List<String>) articleService.queryByHql("select distinct modelName from Log", null);
			for (String w : modelNames) {
				if (w == null)
					continue;
				i++;
				pick = new PickDto("modelName_" + i, w, w);
				picks.add(pick);
			}
			return;
		
		case "MENURUL":
			
			// 后台菜单url
			if(key.equals(MenuType.BACK.name())){
				pick = new PickDto(Const.SEPARATOR, "后台");
				picks.add(pick);
//				// 后端错误码管理
//				pick = new PickDto("h_e_0", "#/error/list", "错误码列表");
//				picks.add(pick);
				// 后端用户管理
				pick = new PickDto("h_u_0", "#/user/list", "用户列表");
				picks.add(pick);
				// 后端角色管理
				pick = new PickDto("h_r_0", "#/role/list", "角色列表");
				picks.add(pick);
				// 后端系统设置
				pick = new PickDto("h_s_0", "#/setting/list/null", "系统设置列表");
				picks.add(pick);
				pick = new PickDto("h_l_0", "#/log/list", "日志列表");
				picks.add(pick);
				// 分割线
				pick = new PickDto(Const.SEPARATOR, "后台菜单列表");
				picks.add(pick);
				for (MenuType type : MenuType.values()) {
					pick = new PickDto("h_m_" + type.name(), "#/menu/list/0/" + type.name() + "/一级菜单",
							type.getName());
					picks.add(pick);
				}
//				pick = new PickDto(Const.SEPARATOR, "后台数据字典&页面&文章管理");
//				picks.add(pick);
//				preUrl = "#/webPage/list/";
				
				for (ArticleType webPage : ArticleType.values()) {
					pick = new PickDto("h_" + webPage.name(), preUrl + webPage.name(), webPage.getName());
					picks.add(pick);
				}
				// 分割线
				pick = new PickDto(Const.SEPARATOR, "项目列表");
				picks.add(pick);
				// 后端项目
				pick = new PickDto("p_l_0", "#/user/project/list/NULL/NULL", "项目列表");
				picks.add(pick);
				return;
			}
			
			// 前端菜单url
			else{
				// 分割线
				pick = new PickDto(Const.SEPARATOR, "项目主页【推荐项目】");
				picks.add(pick);
				preUrl = "#/%s/module/list";
				for(Project project: projectService.findByMap(Tools.getMap("type", ProjectType.RECOMMEND.getType()), null, null)){
					pick = new PickDto(project.getId() , String.format(preUrl, project.getId()) , project.getName());
					picks.add(pick);
				}
				
				// 分割线
				pick = new PickDto(Const.SEPARATOR, "文章列表【站点文章】");
				picks.add(pick);
				int j = 0;
				@SuppressWarnings("unchecked")
				List<String> categorys2 = (List<String>) articleService.queryByHql("select distinct category from Article where moduleId='web'", null);
				for (String article : categorys2) {
					if (MyString.isEmpty(article))
						continue;
					j++;
					pick = new PickDto("cat_" + j, String.format(Const.FRONT_ARTICLE_URL, Const.WEB_MODULE,  Const.WEB_MODULE, article) , article);
					picks.add(pick);
				}
				
				// 分割线
				pick = new PickDto(Const.SEPARATOR, "页面【站点页面】");
				picks.add(pick);
				preUrl = "#/web/article/detail/web/PAGE/";
				for (Article w : articleService
						.findByMap(Tools.getMap("key|" + Const.NOT_NULL, Const.NOT_NULL, "type", "PAGE"), null, null)) {
					pick = new PickDto("wp_" + w.getKey(), preUrl + w.getKey(), w.getName());
					picks.add(pick);
				}
				// 分割线
				return;
			}
		case "FONTFAMILY":// 字体
			for (FontFamilyType font : FontFamilyType.values()) {
				pick = new PickDto(font.name(), font.getValue(), font.getName());
				picks.add(pick);
			}
			return;
		case "USERTYPE": // 用户类型
			for (UserType type : UserType.values()) {
				pick = new PickDto("user-type"+type.getType(), type.getType()+"", type.getName());
				picks.add(pick);
			}
			return;
		case "PROJECTTYPE": 
			for (ProjectType pt : ProjectType.values()) {
				pick = new PickDto(pt.getType()+"", pt.getName());
				picks.add(pick);
			}
			return;
		}
	}

}
