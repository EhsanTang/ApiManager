package cn.crap.controller.back;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.dto.LoginInfoDto;
import cn.crap.framework.JsonResult;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.ICacheService;
import cn.crap.inter.service.IMenuService;
import cn.crap.model.Setting;
import cn.crap.model.User;
import cn.crap.utils.Const;
import cn.crap.utils.MyCookie;

@Scope("prototype")
@Controller
public class BackController extends BaseController<User> {
	@Autowired
	IMenuService menuService;
	@Autowired
	private ICacheService cacheService;
	
	/**
	 * 后台管理主页面
	 */
	@RequestMapping("/admin.do")
	@AuthPassport
	public String showHomePage() throws Exception {
		return "resources/html/backHtml/index.html";
	}
	
	@RequestMapping("/loginOrRegister.do")
	public String loginOrRegister() throws Exception {
		return "resources/html/backHtml/loginOrRegister.html";
	}
	
	
	/**
	 * 删除错误提示
	 */
	@RequestMapping("/back/closeErrorTips.do")
	@ResponseBody
	@AuthPassport(authority = Const.AUTH_ADMIN)
	public JsonResult closeErrorTips() throws Exception {
		cacheService.delStr(Const.CACHE_ERROR_TIP);
		return new JsonResult(1, null);
	}
	
	/**
	 * 后台页面初始化
	 */
	@RequestMapping("/back/init.do")
	@ResponseBody
	@AuthPassport
	public JsonResult init() throws Exception {
		Map<String, String> settingMap = new HashMap<String, String>();
		for (Setting setting : cacheService.getSetting()) {
			settingMap.put(setting.getKey(), setting.getValue());
		}
		String token = MyCookie.getCookie(Const.COOKIE_TOKEN, false, request);
		returnMap.put("settingMap", settingMap);
		returnMap.put("menuList", menuService.getLeftMenu(map));
		LoginInfoDto user = (LoginInfoDto) cacheService.getObj(Const.CACHE_USER + token);
		returnMap.put("sessionAdminName", user.getUserName());
		returnMap.put("sessionAdminAuthor", user.getAuthStr());
		returnMap.put("sessionAdminRoleIds", user.getRoleId());
		returnMap.put("sessionAdminId", user.getId());
		returnMap.put("errorTips", cacheService.getStr(Const.CACHE_ERROR_TIP));
		return new JsonResult(1, returnMap);
	}
}
