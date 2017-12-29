package cn.crap.service.tool;

import java.util.List;

import cn.crap.dao.custom.CustomArticleMapper;
import cn.crap.model.mybatis.*;
import cn.crap.service.custom.CustomMenuService;
import cn.crap.service.mybatis.ArticleService;
import cn.crap.service.mybatis.ProjectService;
import cn.crap.service.mybatis.RoleService;
import cn.crap.utils.IConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.PickDto;
import cn.crap.enumer.ArticleType;
import cn.crap.enumer.DataType;
import cn.crap.enumer.FontFamilyType;
import cn.crap.enumer.IndexPageUrl;
import cn.crap.enumer.MenuType;
import cn.crap.enumer.ProjectStatus;
import cn.crap.enumer.SettingType;
import cn.crap.enumer.UserType;
import cn.crap.framework.MyException;
import cn.crap.service.IPickService;
import cn.crap.utils.MyString;

/**
 * 下拉选着
 * @author Ehsan
 *
 */
@Service("adminPickService")
public class AdminPickService implements IPickService{
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private CustomMenuService customMenuService;
	@Autowired
	private CustomArticleMapper customArticleMapper;
	@Autowired
	private RoleService roleService;

	@Override
	public void getPickList(List<PickDto> picks, String code, String key, LoginInfoDto user) throws MyException {
		PickDto pick = null;
		String preUrl = "";
		switch (code) {
		case "CATEGORY":
			int i = 0;
			List<String> categorys =customArticleMapper.queryArticleCategoryByUserId(user.getId());
			for (String w : categorys) {
				if (w == null)
					continue;
				i++;
				pick = new PickDto("cat_" + i, w, w);
				picks.add(pick);
			}
			return;
			
//		case "MYPROJECT":
//			for (Project p : projectService.findByMap(null, null, null)) {
//				pick = new PickDto(p.getId(), p.getName());
//				picks.add(pick);
//			}
//			return;
		case "PROJECT":
			ProjectCriteria example = new ProjectCriteria();
			ProjectCriteria.Criteria criteria = example.createCriteria().andStatusGreaterThan(Byte.valueOf("0"));
			for (Project p : projectService.selectByExample(example)) {
				pick = new PickDto(p.getId(), p.getName());
				picks.add(pick);
			}
			return;
		case "PROJECTSTATUE":// 管理员项目、推荐项目...
			for (ProjectStatus ps : ProjectStatus.values()) {
				pick = new PickDto(ps.name(), ps.getStatus()+"", ps.getName());
				picks.add(pick);
			}
			return;
		// 一级菜单
		case "MENU":
			for (Menu m : customMenuService.queryByParentId("0")) {
				pick = new PickDto(m.getId(), m.getMenuName());
				picks.add(pick);
			}
			return;
			
		// 权限
		case "AUTH":
			// 分割线
			pick = new PickDto(IConst.SEPARATOR, "用户、菜单、角色、系统设置管理");
			picks.add(pick);
			pick = new PickDto(DataType.USER.name(), "用户管理");
			picks.add(pick);
			pick = new PickDto(DataType.ROLE.name(), "角色管理");
			picks.add(pick);
			pick = new PickDto(DataType.MENU.name(), "菜单管理");
			picks.add(pick);
			pick = new PickDto(DataType.SETTING.name(), "系统设置管理");
			picks.add(pick);
			pick = new PickDto(ArticleType.ARTICLE.name(), "站点文章管理");
			picks.add(pick);
			pick = new PickDto(ArticleType.PAGE.name(), "站点页面管理");
			picks.add(pick);
			pick = new PickDto(DataType.LOG.name(), "操作日志管理");
			picks.add(pick);
			
			return;
		
		// 角色
		case "ROLE":
			pick = new PickDto(IConst.C_SUPER, "超级管理员");
			picks.add(pick);
			for (Role r : roleService.selectByExample(new RoleCriteria())) {
				pick = new PickDto(r.getId(), r.getRoleName());
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
			// TODO
//			List<String> modelNames = (List<String>) articleService.queryByHql("select distinct modelName from Log", null);
//			for (String w : modelNames) {
//				if (w == null)
//					continue;
//				i++;
//				pick = new PickDto("modelName_" + i, w, w);
//				picks.add(pick);
//			}
			return;
		
		case "MENURUL":
				// 分割线
				pick = new PickDto(IConst.SEPARATOR, "项目列表");
				picks.add(pick);
				pick = new PickDto("m_myproject", "#/project/list/true/NULL", "我的项目列表");
				picks.add(pick);
				pick = new PickDto("m_myproject", "#/project/list/false/NULL", "推荐项目列表");
				picks.add(pick);
				
				pick = new PickDto(IConst.SEPARATOR, "项目主页【推荐项目】");
				picks.add(pick);

				ProjectCriteria projectCriteria  = new ProjectCriteria();
				ProjectCriteria.Criteria projectCriteriaCriteria = projectCriteria.createCriteria().andStatusEqualTo(ProjectStatus.RECOMMEND.getStatus());
				for (Project project : projectService.selectByExample(projectCriteria)) {
					pick = new PickDto(project.getId() , String.format(IConst.FRONT_PROJECT_URL, project.getId()) , project.getName());
					picks.add(pick);
				}
				
				// 分割线
				pick = new PickDto(IConst.SEPARATOR, "文章列表【站点文章】");
				picks.add(pick);
				int j = 0;

				List<String> categorys2 = customArticleMapper.queryArticleCategoryByWeb();
				for (String article : categorys2) {
					if (MyString.isEmpty(article))
						continue;
					j++;
					pick = new PickDto("cat_" + j, String.format(IConst.FRONT_ARTICLE_URL, IConst.WEB_MODULE,  IConst.WEB_MODULE, article) , article);
					picks.add(pick);
				}
				
				// 分割线
				pick = new PickDto(IConst.SEPARATOR, "页面【站点页面】");
				picks.add(pick);
				preUrl = "#/web/article/detail/web/PAGE/";
				ArticleCriteria example2= new ArticleCriteria();
				example2.createCriteria().andTypeEqualTo("PAGE").andMkeyIsNotNull();
				for (Article w : articleService.selectByExample(example2)) {
					pick = new PickDto("wp_" + w.getMkey(), preUrl + w.getMkey(), w.getName());
					picks.add(pick);
				}
				// 分割线
				return;
				
		case "FONTFAMILY":// 字体
			for (FontFamilyType font : FontFamilyType.values()) {
				pick = new PickDto(font.name(), font.getValue(), font.getName());
				picks.add(pick);
			}
			return;
		case "INDEXPAGE":// 首页
			for (IndexPageUrl indexPage : IndexPageUrl.values()) {
				pick = new PickDto(indexPage.name(), indexPage.getValue(), indexPage.getName());
				picks.add(pick);
			}
			return;
		case "USERTYPE": // 用户类型
			for (UserType type : UserType.values()) {
				pick = new PickDto("user-type"+type.getType(), type.getType()+"", type.getName());
				picks.add(pick);
			}
			return;
		}
	}

}
