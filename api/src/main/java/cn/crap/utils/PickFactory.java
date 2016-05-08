package cn.crap.utils;

import java.util.List;
import cn.crap.dto.PickDto;
import cn.crap.inter.service.IErrorService;
import cn.crap.inter.service.IMenuService;
import cn.crap.inter.service.IModuleService;
import cn.crap.inter.service.IRoleService;
import cn.crap.inter.service.IWebPageService;
import cn.crap.model.Error;
import cn.crap.model.Menu;
import cn.crap.model.Module;
import cn.crap.model.Role;
import cn.crap.model.WebPage;

public class PickFactory {

	/**
	 * 
	 * @param picks
	 * @param code
	 *            需要选着的pick代码
	 * @param key
	 *            pick二级关键字（如类型、父节点等）
	 * @return
	 */

	public static void getPickList(List<PickDto> picks, String code, String key, 
			IMenuService menuService, IModuleService moduleService, IErrorService errorService, IRoleService roleService, IWebPageService webPageService) {
		PickDto pick = null;
		String preUrl = "";
		switch (code) {

		// 一级菜单
		case "MENU":
			for (Menu m : menuService.findByMap(Tools.getMap("parentId", "0"), null, null)) {
				pick = new PickDto(m.getMenuId(), m.getMenuName());
				picks.add(pick);
			}
			break;
			
		// 权限
		case "AUTH":
			pick = new PickDto(DataType.MODULE.name() + "_0", "项目管理");
			picks.add(pick);
			// 分割线
			pick = new PickDto(Const.SEPARATOR, "模块管理");
			picks.add(pick);
			moduleService.getModulePick(picks, "m_", "0", "", DataType.MODULE.name() + "_moduleId", "--【模块】");
			// 分割线
			pick = new PickDto(Const.SEPARATOR, "接口管理");
			picks.add(pick);
			moduleService.getModulePick(picks, "i_", "0", "", DataType.INTERFACE.name() + "_moduleId", "--【接口】");
			// 分割线
			pick = new PickDto(Const.SEPARATOR, "错误码管理");
			picks.add(pick);
			for (Module m : moduleService.findByMap(Tools.getMap("parentId", "0"), null, null)) {
				pick = new PickDto(m.getModuleId(), DataType.ERROR.name() + "_" + m.getModuleId(),
						m.getModuleName() + "--【错误码】");
				picks.add(pick);
			}
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
			// 分割线
			pick = new PickDto(Const.SEPARATOR, "数据字典");
			picks.add(pick);
			for (Module m : moduleService.findByMap(Tools.getMap("parentId", "0"), null, null)) {
				pick = new PickDto("w_d_" + m.getModuleId(), DataType.DICTIONARY.name() + "_" + m.getModuleId(),
						m.getModuleName());
				picks.add(pick);
			}
			// 分割线
			pick = new PickDto(Const.SEPARATOR, "网站页面&文章管理");
			picks.add(pick);
			for (WebPageType w : WebPageType.values()) {
				if (w.equals(WebPageType.DICTIONARY))
					continue;
				pick = new PickDto("w_w_" + w.name(), w.name(), w.getName());
				picks.add(pick);
			}

			// 分割线
			pick = new PickDto(Const.SEPARATOR, "我的菜单");
			picks.add(pick);
			for (Menu m : menuService.findByMap(Tools.getMap("parentId", "0", "type", MenuType.BACK.name()), null,
					null)) {
				pick = new PickDto(m.getMenuId(), m.getMenuName() + "--【菜单】");
				picks.add(pick);
			}
			break;
		
		// 角色
		case "ROLE":
			pick = new PickDto(Const.SUPER, "超级管理员");
			picks.add(pick);
			for (Role r : roleService.findByMap(null, null, null)) {
				pick = new PickDto(r.getRoleId(), r.getRoleName());
				picks.add(pick);
			}
			break;
		
		// 顶级模块
		case "TOPMODULE":
			for (Module m : moduleService.findByMap(Tools.getMap("parentId", "0"), null, null)) {
				pick = new PickDto(m.getModuleId(), m.getModuleName());
				picks.add(pick);
			}
			break;
		
		// 枚举 接口状态
		case "INTERFACESTATUS":
			for (InterfaceStatus status : InterfaceStatus.values()) {
				pick = new PickDto(status.getName(), status.name());
				picks.add(pick);
			}
			break;
			
		// 枚举 webPage
		case "WEBPAGETYPE":
			for (WebPageType type : WebPageType.values()) {
				pick = new PickDto(type.name(), type.getName());
				picks.add(pick);
			}
			break;

		// 请求参数类型
		case "PARAMETERTYPE":
			for (ParameterType param : ParameterType.values()) {
				pick = new PickDto(param.name(), param.getName());
				picks.add(pick);
			}
			break;
		
		// 枚举 菜单类型
		case "MENUTYPE":
			for (MenuType type : MenuType.values()) {
				pick = new PickDto(type.name(), type.getName());
				picks.add(pick);
			}
			break;
		
		// 枚举 设置类型
		case "SETTINGTYPE":
			for (SettingType type : SettingType.values()) {
				pick = new PickDto(type.name(), type.getName());
				picks.add(pick);
			}
			break;
		case "DATATYPE":// 枚举 数据类型
			for (DataType status : DataType.values()) {
				pick = new PickDto(status.name(), status.getName());
				picks.add(pick);
			}
			break;
		case "REQUESTMETHOD": // 枚举 请求方式 post get
			for (RequestMethod status : RequestMethod.values()) {
				pick = new PickDto(status.name(), status.getName(), status.getName());
				picks.add(pick);
			}
			break;
		case "TRUEORFALSE":// 枚举true or false
			for (TrueOrFalse status : TrueOrFalse.values()) {
				pick = new PickDto(status.getName(), status.name());
				picks.add(pick);
			}
			break;
			
		case "ERRORCODE":// 错误码
			Module module = moduleService.get(key);
			while (module != null && !module.getParentId().equals("0")) {
				module = moduleService.get(module.getParentId());
			}
			for (Error error : errorService.findByMap(
					Tools.getMap("moduleId", module == null ? "" : module.getModuleId()), null, "errorCode asc")) {
				pick = new PickDto(error.getErrorCode(), error.getErrorCode() + "--" + error.getErrorMsg());
				picks.add(pick);
			}
			break;
		case "CATEGORY":
			int i = 0;
			@SuppressWarnings("unchecked")
			List<String> categorys = (List<String>) webPageService.queryByHql("select distinct category from WebPage", null);
			for (String w : categorys) {
				if (w == null)
					continue;
				i++;
				pick = new PickDto("cat_" + i, w, w);
				picks.add(pick);
			}
			break;
		
		case "MENURUL":
			
			// 后台菜单url
			if(key.equals(MenuType.BACK.name())){
				pick = new PickDto(Const.SEPARATOR, "后台");
				picks.add(pick);
				// 后端错误码管理
				pick = new PickDto("h_e_0", "index.do#/error/list", "错误码列表");
				picks.add(pick);
				// 后端用户管理
				pick = new PickDto("h_u_0", "index.do#/user/list", "用户列表");
				picks.add(pick);
				// 后端角色管理
				pick = new PickDto("h_r_0", "index.do#/role/list", "角色列表");
				picks.add(pick);
				// 后端系统设置
				pick = new PickDto("h_s_0", "index.do#/setting/list/null", "系统设置列表");
				picks.add(pick);
				// 分割线
				pick = new PickDto(Const.SEPARATOR, "后台菜单列表");
				picks.add(pick);
				for (MenuType type : MenuType.values()) {
					pick = new PickDto("h_m_" + type.name(), "index.do#/menu/list/0/" + type.name() + "/一级菜单",
							type.getName());
					picks.add(pick);
				}
				pick = new PickDto(Const.SEPARATOR, "后台数据字典&页面&文章管理");
				picks.add(pick);
				preUrl = "index.do#/webPage/list/";
				
				for (WebPageType webPage : WebPageType.values()) {
					pick = new PickDto("h_" + webPage.name(), preUrl + webPage.name(), webPage.getName());
					picks.add(pick);
				}
				// 分割线
				pick = new PickDto(Const.SEPARATOR, "后台模块");
				picks.add(pick);
				// 后端接口&模块管理
				preUrl = "index.do#/interface/list/";
				pick = new PickDto("h_0", preUrl + "0/无", "顶级模块");
				picks.add(pick);
				moduleService.getModulePick(picks, "h_", "0", "- - - ", preUrl + "moduleId/moduleName");
				break;
			}
			
			// 前端菜单url
			else{
				pick = new PickDto(Const.SEPARATOR, "前端错误码");
				picks.add(pick);
				preUrl = "web.do#/webError/list/";
				for (Module m : moduleService.findByMap(Tools.getMap("parentId", "0"), null, null)) {
					pick = new PickDto("e_" + m.getModuleId(), preUrl + m.getModuleId(), m.getModuleName());
					picks.add(pick);
				}
				// 分割线
				pick = new PickDto(Const.SEPARATOR, "前端模块");
				picks.add(pick);
				preUrl = "web.do#/webInterface/list/";
				moduleService.getModulePick(picks, "w_", "0", "", preUrl + "moduleId/moduleName");
				pick = new PickDto(Const.SEPARATOR, "前端数据字典列表");
				picks.add(pick);
				preUrl = "web.do#/webWebPage/list/";
				pick = new PickDto("DICTIONARY", preUrl + "DICTIONARY/null", "数据字典列表");
				picks.add(pick);
				// 分割线
				pick = new PickDto(Const.SEPARATOR, "前端文章类目列表");
				picks.add(pick);
				preUrl = "web.do#/webWebPage/list/ARTICLE/";
				int j = 0;
				@SuppressWarnings("unchecked")
				List<String> categorys2 = (List<String>) webPageService.queryByHql("select distinct category from WebPage", null);
				for (String w : categorys2) {
					if (w == null)
						continue;
					j++;
					pick = new PickDto("cat_" + j, preUrl + w, w);
					picks.add(pick);
				}
				// 分割线
				pick = new PickDto(Const.SEPARATOR, "前端页面");
				picks.add(pick);
				preUrl = "web.do#/webWebPage/detail/PAGE/";
				for (WebPage w : webPageService
						.findByMap(Tools.getMap("key|" + Const.NOT_NULL, Const.NOT_NULL, "type", "PAGE"), null, null)) {
					pick = new PickDto("wp_" + w.getKey(), preUrl + w.getKey(), w.getName());
					picks.add(pick);
				}
				// 分割线
				break;
			}
						
		case "ALLMODULE":// 所有模块
			moduleService.getModulePick(picks, "", "0", "", null);
			break;
		case "LEAFMODULE":// 查询叶子模块
			@SuppressWarnings("unchecked")
			List<Module> modules = (List<Module>) moduleService
					.queryByHql("from Module m where m.moduleId not in (select m2.parentId from Module m2)",null);
			for (Module m : modules) {
				pick = new PickDto(m.getModuleId(), m.getModuleName());
				picks.add(pick);
			}
			break;
		}
	}

}
