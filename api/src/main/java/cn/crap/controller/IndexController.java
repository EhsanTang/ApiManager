package cn.crap.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.dto.MenuDto;
import cn.crap.dto.PickDto;
import cn.crap.dto.SearchDto;
import cn.crap.framework.JsonResult;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.ICacheService;
import cn.crap.inter.service.IMenuService;
import cn.crap.model.Setting;
import cn.crap.model.User;
import cn.crap.utils.Config;
import cn.crap.utils.Const;
import cn.crap.utils.GetBeanBySetting;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;
import cn.crap.utils.ValidateCodeService;

@Scope("prototype")
@Controller
public class IndexController extends BaseController<User> {
	@Autowired
	IMenuService menuService;
	@Autowired
	private ICacheService cacheService;
	/**
	 * 默认页面，重定向web.do，不直接进入web.do是因为进入默认地址，浏览器中的href不会改变， 会导致用户第一点击闪屏
	 * 
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/home.do")
	public void home(HttpServletResponse response) throws Exception {
		response.sendRedirect("web.do");
	}

	/**
	 * 跳转至前段主页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/web.do")
	public String web() throws Exception {
		return "resources/html/frontHtml/index.html";
	}

	/**
	 * 初始化前端页面
	 * 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/frontInit.do")
	@ResponseBody
	public JsonResult frontInit() throws Exception {
		Map<String, String> settingMap = new HashMap<String, String>();
		for (Setting setting : cacheService.getSetting()) {
			settingMap.put(setting.getKey(), setting.getValue());
		}
		returnMap.put("settingMap", settingMap);
		
		// 从缓存中获取菜单
		Object objMenus = cacheService.getObj("cache:leftMenu");
		List<MenuDto> menus = null;
		if(objMenus == null){
			synchronized (IndexController.class) {
				objMenus = cacheService.getObj("cache:leftMenu");
				if(objMenus == null){
					menus = menuService.getLeftMenu(map);
					cacheService.setObj("cache:leftMenu", menus, Config.getCacheTime());//缓存10分钟
				}else{
					menus = (List<MenuDto>) objMenus;
				}
			}
			
		}else{
			menus = (List<MenuDto>) objMenus;
		}
		
		returnMap.put("menuList", menus);
		returnMap.put("sessionAdminName", request.getSession().getAttribute(Const.SESSION_ADMIN));
		return new JsonResult(1, returnMap);
	}
	
	/**
	 * 
	 * @param code
	 *            需要显示的pick code
	 * @param key
	 *            可选参数：根据具体情况定义，如当为模块是，key代表父id
	 * @param radio
	 *            是否为单选
	 * @param def
	 *            默认值
	 * @param tag
	 *            保存选中结果的id
	 * @param tagName
	 *            显示名称的输入框id
	 * @param notNull
	 *            是否可以为空：当为单选，且notNull=false是，则可以选着为空
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "pick.do")
	public String pickOut(String code,@RequestParam(defaultValue = "")  String key, @RequestParam(defaultValue = "true") String radio, String def,
			String tag, String tagName, String notNull) throws Exception {
		if (MyString.isEmpty(radio)) {
			radio = "true";
		}
		List<PickDto> picks = new ArrayList<PickDto>();
		String pickContent = menuService.pick(picks, radio, code, key, def, notNull);
		request.setAttribute("radio", radio);
		request.setAttribute("picks", picks);
		request.setAttribute("tag", tag);
		request.setAttribute("def", def);
		request.setAttribute("iCallBack", getParam("iCallBack", "voidFunction"));
		request.setAttribute("iCallBackParam", getParam("iCallBackParam", ""));
		request.setAttribute("tagName", tagName);
		request.setAttribute("pickContent", pickContent);
		return "WEB-INF/views/pick.jsp";
	}

	@RequestMapping("getImgCode.do")
	@ResponseBody
	public void getImgvcode() throws IOException {
		// 设置response，输出图片客户端不缓存
		response.setDateHeader("Expires", 0);
		response.addHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
		response.setContentType("image/jpeg");
		ServletOutputStream out = response.getOutputStream();
		ValidateCodeService vservice = new ValidateCodeService();
		request.getSession().setAttribute(Const.SESSION_IMG_CODE, vservice.getCode());
		request.getSession().setAttribute(Const.SESSION_IMGCODE_TIMES, "0");
		try {
			vservice.write(out);
			out.flush();
		} finally {
			out.close();
		}
	}
	

	@RequestMapping("/frontSearch.do")
	@ResponseBody
	public JsonResult frontSearch(@RequestParam(defaultValue="") String keyword, @RequestParam(defaultValue = "1") Integer currentPage) throws Exception{
		page.setCurrentPage(currentPage);
		page.setSize(10);
		List<SearchDto> searchResults = GetBeanBySetting.getSearchService().search(keyword, page);
		returnMap.put("searchResults", searchResults);
		return new JsonResult(1, returnMap, page, 
				Tools.getMap("crumbs", Tools.getCrumbs("搜索关键词:"+keyword,"void")));
	}
	
	

	/**
	 * 
	 * @param 跳转至指定页面
	 * @return
	 */
	@RequestMapping("go.do")
	public String go(@RequestParam String p) {
		return p;
	}

	@Override
	public JsonResult detail(User model) {
		return null;
	}

	@Override
	public JsonResult changeSequence(String id, String changeId) {
		return null;
	}
}
