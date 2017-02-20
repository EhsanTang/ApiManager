package cn.crap.controller.front;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.MenuDto;
import cn.crap.dto.SearchDto;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.table.IMenuService;
import cn.crap.inter.service.tool.ICacheService;
import cn.crap.inter.service.tool.ISearchService;
import cn.crap.model.Setting;
import cn.crap.model.User;
import cn.crap.service.tool.LuceneSearchService;
import cn.crap.springbeans.Config;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;

@Controller("fontMainController")
public class MainController extends BaseController<User> {
	@Autowired
	IMenuService menuService;
	@Autowired
	private ICacheService cacheService;
	@Autowired
	private ISearchService luceneService;
	@Autowired
	private Config config;
	/**
	 * 默认页面，重定向web.do，不直接进入web.do是因为进入默认地址，浏览器中的href不会改变， 会导致用户第一点击闪屏
	 * 
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/home.do")
	public void home() throws Exception {
		response.sendRedirect("index.do");
	}
	
	/**
	 * 前端主页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/index.do","/web.do"})
	public String index() throws Exception {
		return "resources/html/frontHtml/index.html";
	}
	/**
	 * 公共
	 * @param result
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 */
	@RequestMapping("/result.do")
	public String validateEmail(String result) throws UnsupportedEncodingException, MessagingException {
		request.setAttribute("result", result);
		return "WEB-INF/views/result.jsp";
	}
	
	/**
	 * 前端项目主页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/project.do")
	public String project() throws Exception {
		return "resources/html/frontHtml/projectIndex.html";
	}
	
	@RequestMapping("/searchList.do")
	@ResponseBody
	public void searchList(HttpServletResponse response) throws Exception {
		// 只显示前10个
		StringBuilder sb = new StringBuilder("<div class='tl'>");
		@SuppressWarnings("unchecked")
		ArrayList<String> searchWords = (ArrayList<String>) cacheService.getObj(Const.CACHE_SEARCH_WORDS);
		if(searchWords != null){
			int i = 0;
			String itemClass = "";
			for(String searchWord: searchWords){
				i = i+1;
				if(i > 10) break;
				if(i == 1) itemClass = " text-danger ";
				else if(i == 2) itemClass = " text-info ";
				else if(i == 3) itemClass = " text-warning ";
				else itemClass = " C555 ";
				
				
				String showText = searchWord.substring(0, searchWord.length()>20?20:searchWord.length());
				if(searchWord.length()>20){
					showText = showText + "...";
				}
				searchWord = LuceneSearchService.handleHref(searchWord);
				sb.append( "<a onclick=\"iClose('lookUp');\" class='p3 pl10 dis "+ itemClass +"' href='#/frontSearch/"+searchWord+"'>"+showText+"</a>");
			}
			
		}
		sb.append("</div>");
		printMsg(sb.toString());
		
	}

	/**
	 * 初始化前端页面
	 * 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/front/init.do")
	@ResponseBody
	public JsonResult frontInit(HttpServletRequest request) throws Exception {
		Map<String, String> settingMap = new HashMap<String, String>();
		for (Setting setting : cacheService.getSetting()) {
			settingMap.put(setting.getKey(), setting.getValue());
		}
		settingMap.put(Const.DOMAIN, config.getDomain());
		settingMap.put(Const.SETTING_OPEN_REGISTER, config.isOpenRegister()+"");
		settingMap.put(Const.SETTING_GITHUB_ID, MyString.isEmpty( config.getClientID() )? "false":"true");
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("settingMap", settingMap);
		
		// 从缓存中获取菜单
		Object objMenus = cacheService.getObj("cache:leftMenu");
		List<MenuDto> menus = null;
		if(objMenus == null){
			synchronized (MainController.class) {
				objMenus = cacheService.getObj("cache:leftMenu");
				if(objMenus == null){
					menus = menuService.getLeftMenu(null);
					cacheService.setObj("cache:leftMenu", menus, config.getCacheTime());//缓存10分钟
				}else{
					menus = (List<MenuDto>) objMenus;
				}
			}
			
		}else{
			menus = (List<MenuDto>) objMenus;
		}
		
		returnMap.put("menuList", menus);
		LoginInfoDto user = (LoginInfoDto) Tools.getUser();

		returnMap.put("sessionAdminName", user == null? "": user.getUserName());
		return new JsonResult(1, returnMap);
	}
	

	@RequestMapping("/frontSearch.do")
	@ResponseBody
	public JsonResult frontSearch(@RequestParam(defaultValue="") String keyword, @RequestParam(defaultValue = "1") Integer currentPage) throws Exception{
		if(config.isLuceneSearchNeedLogin()){
			LoginInfoDto user = Tools.getUser();
			if(user == null){
				throw new MyException("000043");
			}
		}
		keyword = keyword.trim();
		Page page= new Page(15);
		page.setCurrentPage(currentPage);
		page.setSize(10);
		List<SearchDto> searchResults = luceneService.search(keyword, page);
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("searchResults", searchResults);
		
		// 将搜索的内容记入内存
		if(!MyString.isEmpty(keyword)){
			@SuppressWarnings("unchecked")
			ArrayList<String> searchWords = (ArrayList<String>) cacheService.getObj(Const.CACHE_SEARCH_WORDS);
			if(searchWords == null){
				searchWords = new ArrayList<String>();
			}
			// 如果已经存在，则将排序+1
			if(searchWords.contains(keyword)){
				int index = searchWords.indexOf(keyword);
				if(index>0){
					searchWords.remove(keyword);
					searchWords.add(index-1, keyword);
				}
			}else{
				// 最多存200个，超过200个，则移除最后一个，并将新搜索的词放在100
				if(searchWords.size() >= 200){
					searchWords.remove(199);
					searchWords.add(100, keyword);
				}else{
					searchWords.add(keyword);
				}
			}
			cacheService.setObj(Const.CACHE_SEARCH_WORDS, searchWords, -1);
		}
		
		return new JsonResult(1, returnMap, page, 
				Tools.getMap("crumbs", Tools.getCrumbs("搜索关键词:"+keyword,"void")));
	}
}
