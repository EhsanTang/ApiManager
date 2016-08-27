package cn.crap.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.PickDto;
import cn.crap.enumeration.DataCeneterType;
import cn.crap.enumeration.DataType;
import cn.crap.enumeration.FontFamilyType;
import cn.crap.enumeration.InterfaceStatus;
import cn.crap.enumeration.MenuType;
import cn.crap.enumeration.ModuleStatus;
import cn.crap.enumeration.RequestMethod;
import cn.crap.enumeration.SettingType;
import cn.crap.enumeration.TrueOrFalse;
import cn.crap.enumeration.UserType;
import cn.crap.enumeration.WebPageType;
import cn.crap.framework.MyException;
import cn.crap.inter.service.IDataCenterService;
import cn.crap.inter.service.IErrorService;
import cn.crap.inter.service.IMenuService;
import cn.crap.inter.service.IRoleService;
import cn.crap.inter.service.IWebPageService;
import cn.crap.model.DataCenter;
import cn.crap.model.Error;
import cn.crap.model.Menu;
import cn.crap.model.Role;
import cn.crap.model.WebPage;

public class PickFactory {

	/**
	 * 
	 * @param picks 前端选项（非敏感信息）
	 * @param code 需要选着的pick代码
	 * @param key pick二级关键字（如类型、父节点等）
	 * @return
	 * @throws MyException 
	 */

	public static void getFrontPickList(List<PickDto> picks, String code, String key, 
			IMenuService menuService, IDataCenterService dataCenter, IErrorService errorService, IRoleService roleService, IWebPageService webPageService,IDataCenterService dataCenterService) throws MyException {
		
			PickDto pick = null;
			switch (code) {
				case "OPENPROJECT": // 所有管理员目录下的非私有的的项目（）
					List<Byte> statuss = new ArrayList<Byte>();
					statuss.add(Byte.valueOf("1"));
					statuss.add(Byte.valueOf("3"));
					
					List<String> modules = new ArrayList<String>();
					modules.add(Const.TOP_MODULE);
					modules.add(Const.PRIVATE_MODULE);
					for (DataCenter m : dataCenter.findByMap(Tools.getMap("parentId|modules", modules, "type", "MODULE", "status|in", statuss ), null, null)) {
						pick = new PickDto(m.getId(), m.getName());
						picks.add(pick);
					}
					return;
				case "REQUESTMETHOD": // 枚举 请求方式 post get
					for (RequestMethod status : RequestMethod.values()) {
						pick = new PickDto(status.name(), status.getName(), status.getName());
						picks.add(pick);
					}
					return;
					// 枚举 接口状态
				case "INTERFACESTATUS":
					for (InterfaceStatus status : InterfaceStatus.values()) {
						pick = new PickDto(status.getName(), status.name());
						picks.add(pick);
					}
					return;
				case "TRUEORFALSE":// 枚举true or false
					for (TrueOrFalse status : TrueOrFalse.values()) {
						pick = new PickDto(status.getName(), status.name());
						picks.add(pick);
					}
					return;
			}
			// 如果前端pick没有，则查询登陆用户的pick
			if(getUserPickList(picks, code, key, menuService, dataCenter, errorService, roleService, webPageService, dataCenterService)){
				return;
			}
			
			// 如果前端选项、登陆用户的pick没有，则查询后端选项
			getBackPickList(picks, code, key, menuService, dataCenter, errorService, roleService, webPageService);
	}
	
	/**
	 * 普通用户pick
	 * @param picks
	 * @param code
	 * @param key
	 * @param menuService
	 * @param dataCenter
	 * @param errorService
	 * @param roleService
	 * @param webPageService
	 * @throws MyException
	 */
	private static boolean getUserPickList(List<PickDto> picks, String code, String key, 
			IMenuService menuService, IDataCenterService dataCenter, IErrorService errorService, IRoleService roleService, IWebPageService webPageService,
			IDataCenterService dataCenterService) throws MyException {
		// 需要登陆才能
		LoginInfoDto user = Tools.getUser();
		if(user == null ){
			throw new MyException("000003");
		}
		// 管理员通过getBackPickList方法查询
		if(user.getType() != 1){
			return false;
		}
		PickDto pick = null;
		List<String> moduleIds = null;
		switch (code) {
			case "TOPMODULE":// 所有顶级模块
				// 如果用户为普通用户，则只能查看自己的模块
				moduleIds = dataCenterService.getList(  null, DataCeneterType.MODULE.name(), Tools.getUser().getId() );
				moduleIds.add("NULL");
				
				for (DataCenter m : dataCenter.findByMap(Tools.getMap("id|in", moduleIds, "parentId", Const.PRIVATE_MODULE, "type", "MODULE"), null, null)) {
					pick = new PickDto(m.getId(), m.getName());
					picks.add(pick);
				}
				return true;
			case "DATACENTER":// 所有数据
				if(user.getType() == Byte.valueOf(UserType.普通用户.getName())){
					// 如果用户为普通用户，则只能查看自己的模块
					moduleIds = dataCenterService.getList(  null, DataCeneterType.MODULE.name(), Tools.getUser().getId() );
					moduleIds.add("NULL");
					dataCenter.getDataCenterPick(picks, dataCenterService.findByMap(Tools.getMap("id|in", moduleIds, "type", key), null, null) , "", Const.PRIVATE_MODULE ,  Const.LEVEL_PRE , "", "", new HashSet<String>());
					picks.add(0,new PickDto(Const.PRIVATE_MODULE, "根目录（用户）"));
					return true;
				}
			case "INTERFACEMODULE":// 即可模块
				if(user.getType() == Byte.valueOf(UserType.普通用户.getName())){
					// 如果用户为普通用户，则只能查看自己的模块
					moduleIds = dataCenterService.getList(  null, DataCeneterType.MODULE.name(), Tools.getUser().getId() );
					moduleIds.add("NULL");
					dataCenter.getDataCenterPick(picks, dataCenterService.findByMap(Tools.getMap("id|in", moduleIds, "type", key), null, null) , "", Const.PRIVATE_MODULE ,  Const.LEVEL_PRE , "", "", new HashSet<String>());
					return true;
				}
				// 枚举 模块类型（公开、私有）
			case "MODULESTATUS":
				for (ModuleStatus status : ModuleStatus.values()) {
					// 用户不能设置模块为推荐状态
					if(status.getName().equals("3"))
						continue;
					pick = new PickDto(status.getName(), status.name());
					picks.add(pick);
				}
				return true;
			case "ERRORCODE":// 错误码
				DataCenter module = dataCenter.get(key);
				while (module != null &&  !module.getParentId().equals(Const.PRIVATE_MODULE)) {
					module = dataCenter.get(module.getParentId());
				}
				for (Error error : errorService.findByMap(
						Tools.getMap("moduleId", module == null ? "" : module.getId()), null, "errorCode asc")) {
					pick = new PickDto(error.getErrorCode(), error.getErrorCode() + "--" + error.getErrorMsg());
					picks.add(pick);
				}
				return true;
		}
		
		return false;
	}
	/**
	 * 
	 * @param picks 后台选项
	 * @param code 需要选着的pick代码
	 * @param key pick二级关键字（如类型、父节点等）
	 * @return
	 * @throws MyException 
	 */

	@SuppressWarnings("unchecked")
	private static void getBackPickList(List<PickDto> picks, String code, String key, 
			IMenuService menuService, IDataCenterService dataCenter, IErrorService errorService, IRoleService roleService, 
			IWebPageService webPageService) throws MyException {
		PickDto pick = null;
		String preUrl = "";
		// 后端选项需要具有数据查看功能才能查看
		Tools.hasAuth(Const.AUTH_ADMIN,"");
		switch (code) {
		// 一级菜单
		case "MENU":
			for (Menu m : menuService.findByMap(Tools.getMap("parentId", "0"), null, null)) {
				pick = new PickDto(m.getId(), m.getMenuName());
				picks.add(pick);
			}
			return;
			
		// 权限
		case "AUTH":
			List<DataCenter> modules = dataCenter.findByMap(Tools.getMap("type", "MODULE"), null, null);
			pick = new PickDto(Const.SEPARATOR, "项目管理");
			picks.add(pick);
			pick = new PickDto(DataType.MODULE.name() + "_0", "项目管理（系统项目）");
			picks.add(pick);
			pick = new PickDto(DataType.MODULE.name() + "_" + Const.PRIVATE_MODULE, "项目管理（注册用户项目）");
			picks.add(pick);
			// 分割线
			pick = new PickDto(Const.SEPARATOR, "模块管理");
			picks.add(pick);
			dataCenter.getDataCenterPick(picks, modules, "m_", "0", "", DataType.MODULE.name() + "_moduleId", "--【模块】", new HashSet<String>());
			// 分割线
			pick = new PickDto(Const.SEPARATOR, "接口管理");
			picks.add(pick);
			dataCenter.getDataCenterPick(picks, modules, "i_", "0", "", DataType.INTERFACE.name() + "_moduleId", "--【接口】", new HashSet<String>());
			// 分割线
			pick = new PickDto(Const.SEPARATOR, "错误码管理");
			picks.add(pick);
			for (DataCenter m : dataCenter.findByMap(Tools.getMap("parentId", "0",  "type", "MODULE"), null, null)) {
				pick = new PickDto(m.getId(), DataType.ERROR.name() + "_" + m.getId(),
						m.getName() + "--【错误码】");
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
			pick = new PickDto(DataType.LOG);
			picks.add(pick);
			pick = new PickDto(DataType.SOURCE);
			picks.add(pick);
			// 分割线
			pick = new PickDto(Const.SEPARATOR, "数据字典");
			picks.add(pick);
			for (DataCenter m : dataCenter.findByMap(Tools.getMap("parentId", "0",  "type", "MODULE"), null, null)) {
				pick = new PickDto("w_d_" + m.getId(), DataType.DICTIONARY.name() + "_" + m.getId(),
						m.getName());
				picks.add(pick);
			}
			// 分割线
			pick = new PickDto(Const.SEPARATOR, "网站页面&文章管理");
			picks.add(pick);
			for (WebPageType w : WebPageType.values()) {
				if (w.equals(WebPageType.DICTIONARY))
					continue;
				pick = new PickDto("w_w_" + w.name(), w.name()  + "_top", w.getName());
				picks.add(pick);
			}

			// 分割线
			pick = new PickDto(Const.SEPARATOR, "我的菜单");
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
		
		// 顶级模块
		case "TOPMODULE": 
			for (DataCenter m : dataCenter.findByMap(Tools.getMap("parentId", "0", "type", "MODULE"), null, null)) {
				pick = new PickDto(m.getId(), m.getName());
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
			for (WebPageType type : WebPageType.values()) {
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
			
		case "ERRORCODE":// 错误码
			DataCenter module = dataCenter.get(key);
			while (module != null &&  !module.getParentId().equals("0")) {
				module = dataCenter.get(module.getParentId());
			}
			for (Error error : errorService.findByMap(
					Tools.getMap("moduleId", module == null ? "" : module.getId()), null, "errorCode asc")) {
				pick = new PickDto(error.getErrorCode(), error.getErrorCode() + "--" + error.getErrorMsg());
				picks.add(pick);
			}
			return;
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
			return;
		case "MODELNAME":// 数据类型
			i = 0;
			@SuppressWarnings("unchecked")
			List<String> modelNames = (List<String>) webPageService.queryByHql("select distinct modelName from Log", null);
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
				// 后端错误码管理
				pick = new PickDto("h_e_0", "#/error/list", "错误码列表");
				picks.add(pick);
				// 后端用户管理
				pick = new PickDto("h_u_0", "#/user/list", "用户列表");
				picks.add(pick);
				// 后端角色管理
				pick = new PickDto("h_r_0", "#/role/list", "角色列表");
				picks.add(pick);
				// 后端PDF、DOC等文档管理
				pick = new PickDto("h_sorce_0", "#/back/source/list/0/根目录", "PDF、DOC等文档列表");
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
				pick = new PickDto(Const.SEPARATOR, "后台数据字典&页面&文章管理");
				picks.add(pick);
				preUrl = "#/webPage/list/";
				
				for (WebPageType webPage : WebPageType.values()) {
					pick = new PickDto("h_" + webPage.name(), preUrl + webPage.name(), webPage.getName());
					picks.add(pick);
				}
				// 分割线
				pick = new PickDto(Const.SEPARATOR, "后台模块");
				picks.add(pick);
				// 后端接口&模块管理
				preUrl = "#/back/interface/list/";
				dataCenter.getDataCenterPick(picks,  dataCenter.findByMap(Tools.getMap("type", "MODULE"), null, null), "h_", "top", Const.LEVEL_PRE, preUrl + "moduleId/moduleName","", new HashSet<String>());
				return;
			}
			
			// 前端菜单url
			else{
				/**
				 * 前端错误吗
				 */
				pick = new PickDto(Const.SEPARATOR, "前端错误码");
				picks.add(pick);
				
				List<Byte> statuss = new ArrayList<Byte>();
				statuss.add(Byte.valueOf("1"));
				statuss.add(Byte.valueOf("3"));
				
				List<String> parentIds = new ArrayList<String>();
				parentIds.add(Const.ADMIN_MODULE);
				parentIds.add(Const.PRIVATE_MODULE);
				
				for (DataCenter m : dataCenter.findByMap(Tools.getMap("parentId|in", parentIds, "type", "MODULE", "status|in", statuss), null, null)) {
					pick = new PickDto( "e_" + m.getId(), String.format(Const.FRONT_ERROR_URL, m.getId()), m.getName());
					picks.add(pick);
				}
				
				// 分割线
				pick = new PickDto(Const.SEPARATOR, "前端模块");
				picks.add(pick);
				
				
				preUrl = "#/projectId/interface/list/moduleId/moduleName";
				dataCenter.getDataCenterPick(picks, dataCenter.findByMap(Tools.getMap("type", "MODULE"), null, null), "w_", Const.ADMIN_MODULE , "", preUrl, "", new HashSet<String>());
				
				pick = new PickDto(Const.SEPARATOR, "前端文档");
				picks.add(pick);
				preUrl = "#/front/source/list/moduleId/moduleName";
				pick = new PickDto("source_0", preUrl + "0/根目录", "根目录");
				picks.add(pick);
				dataCenter.getDataCenterPick(picks, dataCenter.findByMap(Tools.getMap("type", Const.DIRECTORY), null, null), "source_", "0", "--", preUrl, "", new HashSet<String>());
				
				
				/**
				 * 前端数据字典
				 */
				pick = new PickDto(Const.SEPARATOR, "前端数据字典列表");
				picks.add(pick);
				
				statuss = new ArrayList<Byte>();
				statuss.add(Byte.valueOf("1"));
				statuss.add(Byte.valueOf("3"));
				
				parentIds = new ArrayList<String>();
				parentIds.add(Const.ADMIN_MODULE);
				parentIds.add(Const.PRIVATE_MODULE);
				
				for (DataCenter m : dataCenter.findByMap(Tools.getMap("parentId|in", parentIds, "type", "MODULE", "status|in", statuss), null, null)) {
					pick = new PickDto( "d_" + m.getId(), String.format(Const.FRONT_DICT_URL, m.getId()), m.getName());
					picks.add(pick);
				}
				
				// 分割线
				pick = new PickDto(Const.SEPARATOR, "前端文章类目列表");
				picks.add(pick);
				int j = 0;
				@SuppressWarnings("unchecked")
				List<String> categorys2 = (List<String>) webPageService.queryByHql("select distinct category from WebPage", null);
				for (String w : categorys2) {
					if (w == null)
						continue;
					j++;
					pick = new PickDto("cat_" + j, String.format(Const.FRONT_ARTICLE_URL, Const.TOP_MODULE, w) , w);
					picks.add(pick);
				}
				// 分割线
				pick = new PickDto(Const.SEPARATOR, "前端页面");
				picks.add(pick);
				preUrl = "#/%s/webPage/detail/PAGE/";
				for (WebPage w : webPageService
						.findByMap(Tools.getMap("key|" + Const.NOT_NULL, Const.NOT_NULL, "type", "PAGE"), null, null)) {
					pick = new PickDto("wp_" + w.getKey(), String.format( preUrl, w.getModuleId() ) + w.getKey(), w.getName());
					picks.add(pick);
				}
				// 分割线
				return;
			}
						
		case "DATACENTER":// 所有数据
			if(MyString.isEmpty(key)){
				key = Const.MODULE;
			}
			dataCenter.getDataCenterPick(picks,  dataCenter.findByMap(Tools.getMap("type", key), null, null) 
					, "", Const.ADMIN_MODULE,  Const.LEVEL_PRE , "",  "", new HashSet<String>());
			picks.add(0,new PickDto(Const.ADMIN_MODULE, "根目录（管理员）"));
			return;
		case "INTERFACEMODULE":// 接口模块
			dataCenter.getDataCenterPick(picks,  dataCenter.findByMap(Tools.getMap("type", "MODULE"), null, null) 
					, "", Const.ADMIN_MODULE, "" , "",  "", new HashSet<String>());
			return;
		case "LEAFMODULE":// 查询叶子模块
			for (DataCenter m : (List<DataCenter>) dataCenter
					.queryByHql("from Module m where type='MODULE' and m.id not in (select m2.parentId from Module m2)",null)) {
				pick = new PickDto(m.getId(), m.getName());
				picks.add(pick);
			}
			return;
		case "FONTFAMILY":// 字体
			for (FontFamilyType font : FontFamilyType.values()) {
				pick = new PickDto(font.name(), font.getValue(), font.getName());
				picks.add(pick);
			}
			return;
		case "USERTYPE": // 用户类型
			for (UserType type : UserType.values()) {
				pick = new PickDto("user-type"+type.getName(), type.getName(), type.name());
				picks.add(pick);
			}
			return;
		}
	}

}
