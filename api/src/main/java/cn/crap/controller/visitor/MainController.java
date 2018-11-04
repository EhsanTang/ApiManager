package cn.crap.controller.visitor;

import cn.crap.adapter.ArticleAdapter;
import cn.crap.beans.Config;
import cn.crap.dto.*;
import cn.crap.enu.*;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.model.HotSearch;
import cn.crap.query.ArticleQuery;
import cn.crap.schedule.OpenSourceInfoTask;
import cn.crap.schedule.TaskUtil;
import cn.crap.service.*;
import cn.crap.service.tool.LuceneSearchService;
import cn.crap.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("fontMainController")
public class MainController extends BaseController{
	@Autowired
	private ISearchService luceneService;
	@Autowired
	private MenuService customMenuService;
	@Autowired
	private HotSearchService hotSearchService;
	@Autowired
    private HotSearchService customHotSearchService;
	@Autowired
    private ProjectService projectService;
	@Autowired
    private ArticleService articleService;

	private static final String TOTAL_USER = "totalUser";
    private static final String TOTAL_PROJECT = "totalProject";
    private static final String TOTAL_INTERFACE = "totalInterface";
    private static final String TOTAL_ARTICLE = "totalArticle";
    private static final String PROJECT_LIST = "projectList";
    private static final String ARTICLE_LIST = "articleList";
    private static final String FORK_NUM = "forkNum";
    private static final String STAR_NUM = "starNum";


    /**
	 * 默认页面，重定向web.do，不直接进入web.do是因为进入默认地址，浏览器中的href不会改变， 会导致用户第一点击闪屏
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/home.do")
	public String home(HttpServletResponse response, ModelMap modelMap) throws Exception {
		SettingDto indexUrl = settingCache.get(S_INDEX_PAGE);
		if (indexUrl != null && !MyString.isEmpty(indexUrl.getValue())
                && !indexUrl.getValue().equals(IndexPageUrl.INDEX_HTML.getValue())){
			response.sendRedirect(indexUrl.getValue());
			return null;
		}else{
			return dashboard(modelMap);
		}
	}
	
	/**
	 * 前端主页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/index.do","/web.do"})
	public String index() throws Exception {
		return "resources/html/visitor/indexNew.html";
	}

	@RequestMapping(value = "dashboard.htm")
	public String dashboard(ModelMap modelMap) throws MyException{

		LoginInfoDto loginInfoDto = LoginUserHelper.tryGetUser();
		modelMap.addAttribute("login", loginInfoDto != null);
		modelMap.addAttribute("avatarUrl", loginInfoDto != null ? loginInfoDto.getAvatarUrl() : "resources/images/logo_new.png");
        modelMap.addAttribute("title", settingCache.get(S_TITLE).getValue());
        modelMap.addAttribute("keywords", settingCache.get(S_KEYWORDS).getValue());
        modelMap.addAttribute("description", settingCache.get(S_DESCRIPTION).getValue());
        modelMap.addAttribute("icon", settingCache.get(S_ICON).getValue());
        modelMap.addAttribute("logo", settingCache.get(S_LOGO).getValue());

        List<ArticleDto> articleList = (List<ArticleDto>) objectCache.get(ARTICLE_LIST);
        if (CollectionUtils.isEmpty(articleList)){
        	ArticleQuery query = new ArticleQuery().setStatus(ArticleStatus.RECOMMEND.getStatus()).setType(ArticleType.ARTICLE.name())
					.setPageSize(5);
            articleList = ArticleAdapter.getDto(articleService.query(query), null, null);
            objectCache.add(ARTICLE_LIST, articleList);
        }
        modelMap.addAttribute("articleList", articleList);

        // 从缓存中获取菜单
        List<MenuWithSubMenuDto> menuList = (List<MenuWithSubMenuDto>)objectCache.get(C_CACHE_MENU);
        if(menuList == null){
            menuList = customMenuService.getMenu();
            objectCache.add(C_CACHE_MENU, menuList);
        }
        modelMap.addAttribute("menuList", menuList);

        // fork & star 数量
		TaskUtil.execute(new OpenSourceInfoTask());
		modelMap.addAttribute(FORK_NUM, OpenSourceInfoTask.forNumStr);
		modelMap.addAttribute(STAR_NUM, OpenSourceInfoTask.starNumStr);

        return "WEB-INF/views/dashboard.jsp";
    }


    /**
	 * 公共
	 * @param result
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 */
	@RequestMapping("/result.do")
	public String validateEmail(HttpServletRequest request, String result) throws UnsupportedEncodingException, MessagingException {
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
		return "resources/html/visitor/projectIndex.html";
	}
	
	@RequestMapping("/searchList.do")
	@ResponseBody
	public void searchList(HttpServletResponse response) throws Exception {
		// 只显示前10个
		StringBuilder sb = new StringBuilder("<div class='tl'>");
		List<String> searchWords = customHotSearchService.queryTop10();
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
				sb.append( "<a onclick=\"iClose('lookUp');\" class='p3 pl10 dis "+ itemClass +"' href='#/visitorSearch?keyword="+searchWord+"'>"+showText+"</a>");
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
	@RequestMapping("/visitor/init.do")
	@ResponseBody
	public JsonResult visitorInit(HttpServletRequest request) throws Exception {
		Map<String, String> settingMap = new HashMap<String, String>();
		for (SettingDto setting : settingCache.getAll()) {
			if (SettingStatus.COMMON.getStatus().equals(setting.getStatus())) {
				settingMap.put(setting.getKey(), setting.getValue());
			}
			settingMap.put(setting.getKey(), setting.getValue());
		}
		settingMap.put(IConst.DOMAIN, Config.domain);
		settingMap.put(IConst.SETTING_OPEN_REGISTER, Config.openRegister+"");
		settingMap.put(IConst.SETTING_GITHUB_ID, MyString.isEmpty( Config.clientID )? "false":"true");

		// 新增加且没有写入数据库的配置
		for (SettingEnum settingEnum : SettingEnum.values()){
			if (!settingMap.containsKey(settingEnum.getKey())){
				settingMap.put(settingEnum.getKey(), settingEnum.getValue());
			}
		}
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("settingMap", settingMap);

		// 从缓存中获取菜单
		List<MenuWithSubMenuDto> menus = (List<MenuWithSubMenuDto>)objectCache.get(C_CACHE_MENU);
		if(menus == null){
			menus = customMenuService.getMenu();
			objectCache.add(C_CACHE_MENU, menus);
		}
		
		returnMap.put("menuList", menus);
		LoginInfoDto user = LoginUserHelper.tryGetUser();

		returnMap.put("sessionAdminName", user == null? "": user.getUserName());
		return new JsonResult(1, returnMap);
	}
	

	@RequestMapping("/visitorSearch.do")
	@ResponseBody
	public JsonResult visitorSearch(@RequestParam(defaultValue="") String keyword, Integer currentPage) throws Exception{
		if(Config.luceneSearchNeedLogin){
			LoginInfoDto user = LoginUserHelper.getUser(MyError.E000043);
		}
		keyword = keyword.trim();
        if (keyword.length() > 200){
            keyword = keyword.substring(0, 200);
        }

		Page page= new Page(currentPage);
		List<SearchDto> searchResults = luceneService.search(keyword, page);
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("searchResults", searchResults);


		// 将搜索的内容记入数据库
		if(MyString.isNotEmpty(keyword)){
            HotSearch hotSearch = customHotSearchService.tryGetByKeyWord(keyword);
            if (hotSearch == null){
                hotSearch = new HotSearch();
                hotSearch.setCreateTime(new Date());
                hotSearch.setUpdateTime(new Date());
                hotSearch.setTimes(1);
                hotSearch.setKeyword(keyword);
                try {
					hotSearchService.insert(hotSearch);
				}catch (Exception e){
					hotSearch = customHotSearchService.tryGetByKeyWord(keyword);
                	if (hotSearch == null){
                		e.printStackTrace();
					}
				}
            }else{
                hotSearch.setTimes(hotSearch.getTimes() + 1);
                hotSearchService.update(hotSearch);
            }
        }
		
		return new JsonResult(1, returnMap, page, 
				Tools.getMap("crumbs", Tools.getCrumbs("搜索关键词:"+keyword,"void")));
	}
}
