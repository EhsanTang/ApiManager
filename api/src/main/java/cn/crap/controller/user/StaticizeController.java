package cn.crap.controller.user;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.crap.dto.CategoryDto;
import cn.crap.dto.DictionaryDto;
import cn.crap.dto.InterfacePDFDto;
import cn.crap.dto.ParamDto;
import cn.crap.enumeration.ArticleType;
import cn.crap.enumeration.ProjectType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.table.IArticleService;
import cn.crap.inter.service.table.IErrorService;
import cn.crap.inter.service.table.IInterfaceService;
import cn.crap.inter.service.table.IMenuService;
import cn.crap.inter.service.table.IModuleService;
import cn.crap.inter.service.table.IProjectService;
import cn.crap.inter.service.table.IProjectUserService;
import cn.crap.inter.service.table.IRoleService;
import cn.crap.inter.service.table.IUserService;
import cn.crap.inter.service.tool.ICacheService;
import cn.crap.model.Article;
import cn.crap.model.Interface;
import cn.crap.model.Module;
import cn.crap.model.Project;
import cn.crap.model.Setting;
import cn.crap.springbeans.Config;
import cn.crap.utils.Const;
import cn.crap.utils.HttpPostGet;
import cn.crap.utils.MD5;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;
import net.sf.json.JSONArray;

@Controller
@RequestMapping("/user/staticize")
public class StaticizeController extends BaseController<Project> {
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
	private IErrorService errorService;
	@Autowired
	private IProjectUserService projectUserService;
	@Autowired
	private Config config;
	@Autowired
	private IArticleService articleService;
	@Autowired
	private IInterfaceService interfaceService;
	
	/**
	 * 静态化错误码列表
	 */
	@RequestMapping("/errorList.do")
	public ModelAndView staticizeError(HttpServletRequest req, @RequestParam String projectId,@RequestParam int currentPage, String needStaticizes) throws MyException {
		Project project = cacheService.getProject(projectId);
		String path = Tools.getServicePath(req) + "resources/html/staticize/"+project.getId(); 

		if(project.getType() != ProjectType.PUBLIC.getType()){
			Tools.deleteFile(path);
			// 删除旧的静态化文件
			throw new MyException("000044");
		}
		
		Map<String, Object> returnMap = getProjectModuleInfor(null, project, "-错误码");
		
		Map<String, Object> map = Tools.getMap("projectId", projectId);
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setSize(15);
		returnMap.put("page", page);
		returnMap.put("errorList",  errorService.findByMap(map, page, null));
		returnMap.put("activePage","errorList");
		returnMap.put("url", "errorList");
		returnMap.put("needStaticizes", needStaticizes);
		return new ModelAndView("WEB-INF/views/staticize/default/errorList.jsp",returnMap);
	}
	
	/**
	 * 静态化接口列表
	 */
	@RequestMapping("/interfaceList.do")
	public ModelAndView interfaceList(HttpServletRequest req, @RequestParam String moduleId, @RequestParam int currentPage, String needStaticizes) throws MyException {
		Module module = cacheService.getModule(moduleId);
		Project project = cacheService.getProject(module.getProjectId());
		String path = Tools.getServicePath(req) + "resources/html/staticize/"+project.getId(); 

		if(project.getType() != ProjectType.PUBLIC.getType()){
			Tools.deleteFile(path);
			// 删除旧的静态化文件
			throw new MyException("000044");
		}
		
		Map<String, Object> returnMap = getProjectModuleInfor(module, project, "-接口");
		
		Map<String, Object> map = Tools.getMap("moduleId", moduleId);
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setSize(15);
		returnMap.put("page", page);
		returnMap.put("interfaceList",  interfaceService.findByMap(map, page, null));
		returnMap.put("activePage",moduleId+"_interface");
		returnMap.put("url", module.getId() + "-interfaceList");
		returnMap.put("needStaticizes", needStaticizes);
		return new ModelAndView("WEB-INF/views/staticize/default/interfaceList.jsp",returnMap);
	}

	
	/**
	 * 静态化模块文章列表
	 */
	@RequestMapping("/articleList.do")
	public ModelAndView staticizeModule(HttpServletRequest req, @RequestParam String moduleId,@RequestParam String category,@RequestParam int currentPage, String type, String needStaticizes) throws MyException {
		Module module = cacheService.getModule(moduleId);
		Project project = cacheService.getProject(module.getProjectId());
		String path = Tools.getServicePath(req) + "resources/html/staticize/"+project.getId(); 

		if(project.getType() != ProjectType.PUBLIC.getType()){
			Tools.deleteFile(path);
			// 删除旧的静态化文件
			throw new MyException("000044");
		}
		
		Map<String, Object> returnMap = getProjectModuleInfor(module, project, "-文章");
		
		// 当前类目
		if( category.equals(Const.ALL) ){
			category = "";
			returnMap.put("md5Category", "");
		}else{
			returnMap.put("md5Category", MD5.encrytMD5(category).substring(0, 10));
		}
		
		if(MyString.isEmpty(type)){
			type = "ARTICLE";
		}
		
		if(type.equals("ARTICLE")){
			// 获取所有类目
			// 静态化模块文章
			@SuppressWarnings("unchecked")
			List<String> categorys =  (List<String>) articleService.queryByHql("select distinct category from Article where type = 'ARTICLE' and moduleId = '"+module.getId()+"'", null, null);
			List<CategoryDto> categoryDtos = new ArrayList<CategoryDto>();
			// 文章分类，按类目静态化
			for(String c: categorys){
				if(MyString.isEmpty(c)){
					continue;
				}
				CategoryDto categoryDto = new CategoryDto();
				categoryDto.setMd5Category(MD5.encrytMD5(c).substring(0, 10)); 
				categoryDto.setCategory(c);
				categoryDtos.add( categoryDto );
			}
			returnMap.put("categoryDtos", categoryDtos);
			returnMap.put("activePage",module.getId()+"_article");
			returnMap.put("url", module.getId() + "-articleList-"+returnMap.get("md5Category"));
		}else{
			returnMap.put("activePage",module.getId()+"_dictionary");
			returnMap.put("url", module.getId() + "-dictionary");
		}
		
		Map<String, Object> map = Tools.getMap("moduleId", moduleId, "type", type, "category", category);
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setSize(15);
		List<Article> articleList = articleService.findByMap(map, " new Article(id, type, name, click, category, createTime, key, moduleId, brief, sequence) ",
				page, null);
		returnMap.put("page", page);
		returnMap.put("articleList", articleList);
		returnMap.put("needStaticizes", needStaticizes);
		return new ModelAndView("WEB-INF/views/staticize/default/articleList.jsp",returnMap);
	}


	/**
	 * 静态化文章
	 * @param req
	 * @param articleId
	 * @return
	 * @throws MyException
	 */
	@RequestMapping("/articleDetail.do")
	public ModelAndView staticizeArticle(HttpServletRequest req, @RequestParam String articleId, String needStaticizes) throws MyException {
		Article article = articleService.get(articleId);
		Module module = cacheService.getModule(article.getModuleId());
		Project project = cacheService.getProject(module.getProjectId());
		String path = Tools.getServicePath(req) + "resources/html/staticize/"+project.getId(); 

		if(project.getType() != ProjectType.PUBLIC.getType()){
			Tools.deleteFile(path);
			// 删除旧的静态化文件
			throw new MyException("000044");
		}
		if(article.getType().equals(ArticleType.ARTICLE.name())){
			Map<String, Object> returnMap = getProjectModuleInfor(module, project, "-文章详情");
			returnMap.put("article", article);
			returnMap.put("activePage",module.getId()+"_article");
			returnMap.put("needStaticizes", needStaticizes);
			return new ModelAndView("WEB-INF/views/staticize/default/articleDetail.jsp",returnMap);
		}else{
			Map<String, Object> returnMap = getProjectModuleInfor(module, project, "-数据字典详情");
			returnMap.put("article", article);
			returnMap.put("activePage",module.getId()+"_dictionary");
			returnMap.put("dictionaryFields", JSONArray.toArray(JSONArray.fromObject(article.getContent()), DictionaryDto.class));
			returnMap.put("needStaticizes", needStaticizes);
			return new ModelAndView("WEB-INF/views/staticize/default/dictionaryDetail.jsp",returnMap);
		}
	}
	
	/**
	 * 静态化接口详情
	 * @param req
	 * @param articleId
	 * @return
	 * @throws MyException
	 */
	@RequestMapping("/interfaceDetail.do")
	public ModelAndView interfaceDetail(HttpServletRequest req, @RequestParam String interfaceId, String needStaticizes) throws MyException {
		Interface interFace = interfaceService.get(interfaceId);
		Module module = cacheService.getModule(interFace.getModuleId());
		Project project = cacheService.getProject(module.getProjectId());
		String path = Tools.getServicePath(req) + "resources/html/staticize/"+project.getId(); 

		if(project.getType() != ProjectType.PUBLIC.getType()){
			Tools.deleteFile(path);
			// 删除旧的静态化文件
			throw new MyException("000044");
		}
		Map<String, Object> returnMap = getProjectModuleInfor(module, project, "-接口详情");
		List<InterfacePDFDto> interfaces = new ArrayList<InterfacePDFDto>();
		InterfacePDFDto interDto = null;
		interDto= new InterfacePDFDto();
		interfaceService.getInterDto(config, interfaces, interFace, interDto);

		returnMap.put("interfaces", interfaces);
		returnMap.put("activePage",module.getId()+"_interface");
		returnMap.put("needStaticizes", needStaticizes);
		return new ModelAndView("WEB-INF/views/staticize/default/interfaceDetail.jsp",returnMap);
	}
	
	
	
	/**
	 * 静态化
	 * @throws Exception 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/staticize.do")
	@ResponseBody
	public JsonResult staticize(HttpServletRequest req, @RequestParam String projectId, String needStaticizes) throws UnsupportedEncodingException, Exception {
		
		if(MyString.isEmpty(needStaticizes)){
			needStaticizes = ",article,";
		}else{
			needStaticizes = ",article," + needStaticizes + ",";
		}
		Project project = cacheService.getProject(projectId);
		String path = Tools.getServicePath(req) + "resources/html/staticize/"+project.getId(); 
		Tools.createFile(path);
		if(project.getType() != ProjectType.PUBLIC.getType()){
			Tools.deleteFile(path);
			// 删除旧的静态化文件
			throw new MyException("000044");
		}
		// 静态化错误码// 查询页码
		int pageSize = 15;
		int totalPage = 0;
		Map<String, Object> map = Tools.getMap("projectId", projectId);
		if(needStaticizes.indexOf(",error,") >= 0){
			int errorSize = errorService.getCount(map);
			// 计算总页数
			totalPage = (errorSize+pageSize-1)/pageSize;
			if(totalPage == 0){
				totalPage = 1;
			}
			for(int i=1 ; i<= totalPage; i++){
				String html = HttpPostGet.get(config.getDomain()+ "/user/staticize/errorList.do?projectId="+projectId+"&currentPage="+i + "&needStaticizes="+needStaticizes, null, null, 10 * 1000);
				// list-类目摘要-页码
				Tools.staticize(html, path + "/errorList-" + i + ".html");
			}
		}
		
		
		
		for(Module module : moduleService.findByMap(Tools.getMap("projectId", projectId), null, null)){
			if(needStaticizes.indexOf(",article,") >= 0){
				// 静态化模块文章，分类
				@SuppressWarnings("unchecked")
				List<String> categorys =  (List<String>) articleService.queryByHql("select distinct category from Article where type = 'ARTICLE' and moduleId = '"+module.getId()+"'", null, null);
				// 文章分类，按类目静态化
				for(String category: categorys){
					if( MyString.isEmpty( category )){
						continue; // 空类目不静态化
					}
					// 查询页码
					map = Tools.getMap("moduleId", module.getId(), "type", "ARTICLE", "category", category);
					int articleSize = articleService.getCount(map);
					// 计算总页数
					totalPage = (articleSize+pageSize-1)/pageSize;
					if(totalPage == 0){
						totalPage = 1;
					}
					for(int i=1 ; i<= totalPage; i++){
						String html = HttpPostGet.get(config.getDomain()+ "/user/staticize/articleList.do?moduleId="+ module.getId()+"&category="+category+"&currentPage="+i + "&needStaticizes="+needStaticizes, null, null, 10 * 1000);
						// list-类目摘要-页码
						Tools.staticize(html, path + "/" + module.getId() +"-articleList-"+ MD5.encrytMD5(category).substring(0, 10) + "-" + i + ".html");
					}
				}
				
				// 文章分类，不分类
				map = Tools.getMap("moduleId", module.getId(), "type", "ARTICLE");
				int articleSize = articleService.getCount(map);
				// 计算总页数
				totalPage = (articleSize+pageSize-1)/pageSize;
				if(totalPage == 0){
					totalPage = 1;
				}
				for(int i=1 ; i<= totalPage; i++){
					String html = HttpPostGet.get(config.getDomain()+ "/user/staticize/articleList.do?moduleId="+ module.getId()+"&category="+Const.ALL+"&currentPage="+i + "&needStaticizes="+needStaticizes, null, null, 10 * 1000);
					// list-类目摘要-页码
					Tools.staticize(html, path + "/" +  module.getId() +"-articleList--" + i + ".html");
				}
				
				
				// 静态化文章
				for(Article article: articleService.findByMap(Tools.getMap("moduleId", module.getId(), "type", "ARTICLE"), null, null)){
					String html = HttpPostGet.get(config.getDomain()+ "/user/staticize/articleDetail.do?articleId="+ article.getId() + "&needStaticizes="+needStaticizes, null, null, 10 * 1000);
					Tools.staticize(html, path + "/" + article.getId()+".html");
				}
				
				
			}
			
			if(needStaticizes.indexOf(",dictionary,") >= 0){
				// 数据字典列表
				map = Tools.getMap("moduleId", module.getId(), "type", "DICTIONARY");
				int articleSize = articleService.getCount(map);
				// 计算总页数
				totalPage = (articleSize+pageSize-1)/pageSize;
				if(totalPage == 0){
					totalPage = 1;
				}
				for(int i=1 ; i<= totalPage; i++){
					String html = HttpPostGet.get(config.getDomain()+ "/user/staticize/articleList.do?moduleId="+ module.getId()+"&category="+Const.ALL+"&currentPage="+i+"&type=DICTIONARY" + "&needStaticizes="+needStaticizes, null, null, 10 * 1000);
					// list-类目摘要-页码
					Tools.staticize(html, path + "/" +  module.getId() +"-dictionaryList-" + i + ".html");
				}
				
				// 静态化数据字典详情
				for(Article article: articleService.findByMap(Tools.getMap("moduleId", module.getId(), "type", "DICTIONARY"), null, null)){
					String html = HttpPostGet.get(config.getDomain()+ "/user/staticize/articleDetail.do?articleId="+ article.getId() + "&needStaticizes="+needStaticizes, null, null, 10 * 1000);
					Tools.staticize(html, path + "/" + article.getId()+".html");
				}
			}
			
			if(needStaticizes.indexOf("interface") >= 0){
				// 接口列表
				map = Tools.getMap("moduleId", module.getId());
				// 计算总页数
				totalPage = (interfaceService.getCount(map)+pageSize-1)/pageSize;
				if(totalPage == 0){
					totalPage = 1;
				}
				for(int i=1 ; i<= totalPage; i++){
					String html = HttpPostGet.get(config.getDomain()+ "/user/staticize/interfaceList.do?moduleId="+ module.getId()+"&currentPage="+i + "&needStaticizes="+needStaticizes, null, null, 10 * 1000);
					// list-类目摘要-页码
					Tools.staticize(html, path + "/" +  module.getId() +"-interfaceList-" + i + ".html");
				}
				
				
				// 静态化接口详情
				for(Interface inter: interfaceService.findByMap(Tools.getMap("moduleId", module.getId()), null, null)){
					String html = HttpPostGet.get(config.getDomain()+ "/user/staticize/interfaceDetail.do?interfaceId="+ inter.getId() + "&needStaticizes="+needStaticizes, null, null, 10 * 1000);
					Tools.staticize(html, path + "/" + inter.getId()+".html");
				}
			}
			// 推送给百度
//			try{
//				if( !config.getBaidu().equals("") )
//					HttpPostGet.postBody(config.getBaidu(), config.getDomain()+"/resources/html/staticize/"+project.getId()+"/"+module.getId()+"/list.html", null);
//			}catch(Exception e){
//				e.printStackTrace();
//			}
		}
		return new JsonResult(1, null );
	}
	
	
	private Map<String, Object> getProjectModuleInfor(Module module, Project project, String typeName) {
		// 静态化
		Map<String, String> settingMap = new HashMap<String, String>();
		for (Setting setting : cacheService.getSetting()) {
			settingMap.put(setting.getKey(), setting.getValue());
		}
		if(!MyString.isEmpty(project.getCover())){
			if(!project.getCover().startsWith("http:")){
				project.setCover(config.getDomain() +"/"+ project.getCover());
			}
		}
		
		settingMap.put(Const.DOMAIN, config.getDomain());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Map<String,Object> map = Tools.getMap("projectId",project.getId());
		returnMap.put("settings", settingMap);
		returnMap.put("project", project);
		returnMap.put("module", module);
		// 将选中的模块放到第一位
		List<Module> moduleList = moduleService.findByMap(map, null, null);
		if(module != null){
			for(Module m:moduleList){
				if(m.getId().equals(module.getId())){
					moduleList.remove(m);
					break;
				}
			}
			moduleList.add(0, module);
		}
		returnMap.put("moduleList", moduleList);
		//returnMap.put("menuList", menuService.getLeftMenu(null));
		// 模块将静态化成网站的keywords
		returnMap.put("keywords", module!=null ? module.getRemark():project.getRemark());
		// 项目备注将静态化成网站的description
		returnMap.put("description", project.getRemark());
		// 模块名称将静态化成网站标题
		returnMap.put("title", module!=null ? module.getName() + typeName: project.getName() + typeName);
		return returnMap;
	}
}
