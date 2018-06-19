package cn.crap.controller.front;

import cn.crap.adapter.ArticleAdapter;
import cn.crap.beans.Config;
import cn.crap.dto.*;
import cn.crap.enumer.ArticleStatus;
import cn.crap.enumer.ArticleType;
import cn.crap.enumer.MyError;
import cn.crap.enumer.ProjectStatus;
import cn.crap.framework.JsonResult;
import cn.crap.framework.ThreadContext;
import cn.crap.framework.base.BaseController;
import cn.crap.model.mybatis.*;
import cn.crap.service.ISearchService;
import cn.crap.service.custom.CustomArticleService;
import cn.crap.service.custom.CustomHotSearchService;
import cn.crap.service.custom.CustomMenuService;
import cn.crap.service.custom.CustomProjectService;
import cn.crap.service.mybatis.*;
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
	private CustomMenuService customMenuService;
	@Autowired
	private HotSearchService hotSearchService;
	@Autowired
    private CustomHotSearchService customHotSearchService;
	@Autowired
    private UserService userService;
	@Autowired
    private ArticleService articleService;
	@Autowired
    private ProjectService projectService;
	@Autowired
    private InterfaceService interfaceService;
	@Autowired
    private CustomProjectService customProjectService;
	@Autowired
    private CustomArticleService customArticleService;
	@Autowired
	private Config config;

	private static final String TOTAL_USER = "totalUser";
    private static final String TOTAL_PROJECT = "totalProject";
    private static final String TOTAL_INTERFACE = "totalInterface";
    private static final String TOTAL_ARTICLE = "totalArticle";
    private static final String PROJECT_LIST = "projectList";
    private static final String ARTICLE_LIST = "articleList";

    /**
	 * 默认页面，重定向web.do，不直接进入web.do是因为进入默认地址，浏览器中的href不会改变， 会导致用户第一点击闪屏
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/home.do")
	public void home(HttpServletResponse response) throws Exception {
		SettingDto indexUrl = settingCache.get(S_INDEX_PAGE);
		if (indexUrl != null && !MyString.isEmpty(indexUrl.getValue())){
			response.sendRedirect(indexUrl.getValue());
		}else{
			response.sendRedirect("index.do");
		}
	}
	
	/**
	 * 前端主页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({"/index.do","/web.do"})
	public String index() throws Exception {
		return "resources/html/frontHtml/indexNew.html";
	}

	@RequestMapping(value = "dashboard.htm")
	public String dashboard(ModelMap modelMap) {

		LoginInfoDto loginInfoDto = LoginUserHelper.tryGetUser();
		modelMap.addAttribute("login", loginInfoDto != null);
        modelMap.addAttribute("title", settingCache.get(S_TITLE).getValue());
        modelMap.addAttribute("keywords", settingCache.get(S_KEYWORDS).getValue());
        modelMap.addAttribute("description", settingCache.get(S_DESCRIPTION).getValue());
        modelMap.addAttribute("icon", settingCache.get(S_ICON).getValue());
        modelMap.addAttribute("logo", settingCache.get(S_LOGO).getValue());

        String totalUser = stringCache.get(TOTAL_USER);
        if (MyString.isEmpty(totalUser)){
            totalUser = userService.countByExample(new UserCriteria()) + "";
            stringCache.add(TOTAL_USER, totalUser);
        }
        String totalProject = stringCache.get(TOTAL_PROJECT);
        if (MyString.isEmpty(totalProject)){
            totalProject = projectService.countByExample(new ProjectCriteria()) + "";
            stringCache.add(TOTAL_PROJECT, totalProject);
        }
        String totalInterface = stringCache.get(TOTAL_INTERFACE);
        if (MyString.isEmpty(totalInterface)){
            totalInterface = interfaceService.countByExample(new InterfaceCriteria()) + "";
            stringCache.add(TOTAL_INTERFACE, totalInterface);
        }
        String totalArticle = stringCache.get(TOTAL_ARTICLE);
        if (MyString.isEmpty(totalArticle)){
            totalArticle = articleService.countByExample(new ArticleCriteria()) + "";
            stringCache.add(TOTAL_ARTICLE, totalArticle);
        }

        modelMap.addAttribute("totalUser", totalUser);
        modelMap.addAttribute("totalProject", totalProject);
        modelMap.addAttribute("totalInterface", totalInterface);
        modelMap.addAttribute("totalArticle", totalArticle);

        List<Project> projectList =(List<Project>) objectCache.get(PROJECT_LIST);
        Page page = new Page(12, 1);
        if (CollectionUtils.isEmpty(projectList)) {
            projectList = customProjectService.pageProjectByStatusName(ProjectStatus.RECOMMEND.getStatus(), null, page);
            objectCache.add(PROJECT_LIST, projectList);
        }
        modelMap.addAttribute("projectList", projectList);


        page = new Page(5, 1);
        List<ArticleDto> articleList = (List<ArticleDto>) objectCache.get(ARTICLE_LIST);
        if (CollectionUtils.isEmpty(articleList)){
            articleList = ArticleAdapter.getDto(customArticleService.queryArticle(null, null, ArticleType.ARTICLE.name(),
                    null, ArticleStatus.RECOMMEND.getStatus(), page), null);
            objectCache.add(ARTICLE_LIST, articleList);
        }
        modelMap.addAttribute("articleList", articleList);

        // 从缓存中获取菜单
        List<MenuWithSubMenuDto> menuList = (List<MenuWithSubMenuDto>)objectCache.get(C_CACHE_LEFT_MENU);
        if(menuList == null){
            menuList = customMenuService.getLeftMenu();
            objectCache.add(C_CACHE_LEFT_MENU, menuList);
        }
        modelMap.addAttribute("menuList", menuList);

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
	public String validateEmail(String result) throws UnsupportedEncodingException, MessagingException {
		ThreadContext.request().setAttribute("result", result);
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
		for (SettingDto setting : settingCache.getAll()) {
			if(S_SECRETKEY.equals(setting.getKey())){
				continue;
			}
			settingMap.put(setting.getKey(), setting.getValue());
		}
		settingMap.put(IConst.DOMAIN, config.getDomain());
		settingMap.put(IConst.SETTING_OPEN_REGISTER, config.isOpenRegister()+"");
		settingMap.put(IConst.SETTING_GITHUB_ID, MyString.isEmpty( config.getClientID() )? "false":"true");
		
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("settingMap", settingMap);

		// 从缓存中获取菜单
		List<MenuWithSubMenuDto> menus = (List<MenuWithSubMenuDto>)objectCache.get(C_CACHE_LEFT_MENU);
		if(menus == null){
			menus = customMenuService.getLeftMenu();
			objectCache.add(C_CACHE_LEFT_MENU, menus);
		}
		
		returnMap.put("menuList", menus);
		LoginInfoDto user = LoginUserHelper.tryGetUser();

		returnMap.put("sessionAdminName", user == null? "": user.getUserName());
		return new JsonResult(1, returnMap);
	}
	

	@RequestMapping("/frontSearch.do")
	@ResponseBody
	public JsonResult frontSearch(@RequestParam(defaultValue="") String keyword, Integer currentPage) throws Exception{
		if(config.isLuceneSearchNeedLogin()){
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
