package cn.crap.controller.user;

import cn.crap.adapter.ErrorAdapter;
import cn.crap.adapter.InterfaceAdapter;
import cn.crap.beans.Config;
import cn.crap.dto.CategoryDto;
import cn.crap.dto.DictionaryDto;
import cn.crap.dto.InterfacePDFDto;
import cn.crap.dto.SettingDto;
import cn.crap.enumer.ArticleType;
import cn.crap.enumer.MyError;
import cn.crap.enumer.ProjectType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.model.*;
import cn.crap.model.Error;
import cn.crap.query.ArticleQuery;
import cn.crap.query.ErrorQuery;
import cn.crap.query.InterfaceQuery;
import cn.crap.query.ModuleQuery;
import cn.crap.service.ArticleService;
import cn.crap.service.ErrorService;
import cn.crap.service.InterfaceService;
import cn.crap.service.ModuleService;
import cn.crap.utils.*;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user/staticize")
public class StaticizeController extends BaseController{
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private Config config;
	@Autowired
	private InterfaceService interfaceService;
	@Autowired
	private ErrorService errorService;
	@Autowired
	private ArticleService articleService;

	/**
	 * 静态化错误码列表
	 */
	@RequestMapping("/errorList.do")
	public ModelAndView staticizeError(HttpServletRequest req, @RequestParam String projectId,@RequestParam int currentPage,
			String needStaticizes, @RequestParam String secretKey) throws MyException {
		// 验证是否是非法请求
		if( !settingCache.get(S_SECRETKEY).getValue().equals(secretKey) ){
			throw new MyException(MyError.E000056);
		}
		Project project = projectCache.get(projectId);
		String path = Tools.getStaticPath(project);
		if(project.getType() != ProjectType.PUBLIC.getType()){
			Tools.deleteFile(path);
			// 删除旧的静态化文件
			throw new MyException(MyError.E000044);
		}
		
		Map<String, Object> returnMap = getProjectModuleInfor(null, project, "-错误码");
		

		ErrorQuery errorQuery = new ErrorQuery().setProjectId(projectId);
		errorQuery.setCurrentPage(currentPage);
		Page page = new Page(errorQuery);
		List<Error> errorModels = errorService.query(errorQuery);
		page.setAllRow(errorService.count(errorQuery));

		returnMap.put("page", page);
		returnMap.put("errorList", ErrorAdapter.getDto(errorModels));
		returnMap.put("activePage","errorList");
		returnMap.put("url", "errorList");
		returnMap.put("needStaticizes", needStaticizes);
		return new ModelAndView("WEB-INF/views/staticize/default/errorList.jsp",returnMap);
	}

	

	/**
	 * 静态化接口列表
	 */
	@RequestMapping("/interfaceList.do")
	public ModelAndView interfaceList(HttpServletRequest req, @RequestParam String moduleId, @RequestParam int currentPage,
			String needStaticizes, @RequestParam String secretKey) throws MyException {
		// 验证是否是非法请求
		if( !settingCache.get(S_SECRETKEY).getValue().equals(secretKey) ){
			throw new MyException(MyError.E000056);
		}
		
		Module module = moduleCache.get(moduleId);
		Project project = projectCache.get(module.getProjectId());
		String path = Tools.getStaticPath(project);

		if(project.getType() != ProjectType.PUBLIC.getType()){
			Tools.deleteFile(path);
			// 删除旧的静态化文件
			throw new MyException(MyError.E000044);
		}
		
		Map<String, Object> returnMap = getProjectModuleInfor(module, project, "-接口");
		
		Map<String, Object> map = Tools.getMap("moduleId", moduleId);

        InterfaceQuery interfaceQuery = new InterfaceQuery().setCurrentPage(currentPage).setModuleId(moduleId);
        Page page = new Page(interfaceQuery);
        page.setAllRow(interfaceService.count(interfaceQuery));
		returnMap.put("page", page);
		returnMap.put("interfaceList", InterfaceAdapter.getDto(interfaceService.query(interfaceQuery), module, null));
		returnMap.put("activePage",moduleId+"_interface");
		returnMap.put("url", module.getId() + "-interfaceList");
		returnMap.put("needStaticizes", needStaticizes);
		return new ModelAndView("WEB-INF/views/staticize/default/interfaceList.jsp",returnMap);
	}

	
	/**
	 * 静态化模块文章列表
	 */
	@RequestMapping("/articleList.do")
	public ModelAndView staticizeModule(HttpServletRequest req, @RequestParam String moduleId,@RequestParam String category,@RequestParam int currentPage,
			String type, String needStaticizes, @RequestParam String secretKey) throws MyException {
		// 验证是否是非法请求
		if( !settingCache.get(S_SECRETKEY).getValue().equals(secretKey) ){
			throw new MyException(MyError.E000056);
		}
		Module module = moduleCache.get(moduleId);
		Project project = projectCache.get(module.getProjectId());
		String path = Tools.getStaticPath(project);

		if(project.getType() != ProjectType.PUBLIC.getType()){
			Tools.deleteFile(path);
			// 删除旧的静态化文件
			throw new MyException(MyError.E000044);
		}
		
		Map<String, Object> returnMap = getProjectModuleInfor(module, project, "-文章");
		
		// 当前类目
		if( category.equals(IConst.ALL) ){
			category = "";
			returnMap.put("md5Category", "");
		}else{
			returnMap.put("md5Category", MD5.encrytMD5(category, "").substring(0, 10));
		}
		
		if(MyString.isEmpty(type)){
			type = "ARTICLE";
		}
		
		if(type.equals("ARTICLE")){
			// 获取所有类目
			// 静态化模块文章
			List<String> categorys = moduleService.queryCategoryByModuleId(module.getId());
			List<CategoryDto> categoryDtos = new ArrayList<CategoryDto>();
			// 文章分类，按类目静态化
			for(String c: categorys){
				if(MyString.isEmpty(c)){
					continue;
				}
				CategoryDto categoryDto = new CategoryDto();
				categoryDto.setMd5Category(MD5.encrytMD5(c, "").substring(0, 10));
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

        ArticleQuery articleQuery = new ArticleQuery().setCurrentPage(currentPage).setModuleId(moduleId).setType(type).setCategory(category);
        Page page = new Page(articleQuery);
        page.setAllRow(articleService.count(articleQuery));
		List<Article> articleList = articleService.query(articleQuery);
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
	public ModelAndView staticizeArticle(HttpServletRequest req, @RequestParam String articleId, 
			String needStaticizes, @RequestParam String secretKey) throws MyException {
		// 验证是否是非法请求
		if( !settingCache.get(S_SECRETKEY).getValue().equals(secretKey) ){
			throw new MyException(MyError.E000056);
		}		
		
		ArticleWithBLOBs article = articleService.getById(articleId);
		Module module = moduleCache.get(article.getModuleId());
		Project project = projectCache.get(module.getProjectId());
		String path = Tools.getStaticPath(project);

		if(project.getType() != ProjectType.PUBLIC.getType()){
			Tools.deleteFile(path);
			// 删除旧的静态化文件
			throw new MyException(MyError.E000044);
		}
		if(article.getType().equals(ArticleType.ARTICLE.name())){
			Map<String, Object> returnMap = getProjectModuleInfor(module, project, "-文章详情");
			returnMap.put("article", article);
			// 项目备注将静态化成网站的description
			returnMap.put("description", article.getBrief());
			// 模块名称将静态化成网站标题
			returnMap.put("title", article.getName());

			returnMap.put("activePage",module.getId()+"_article");
			returnMap.put("needStaticizes", needStaticizes);
			return new ModelAndView("WEB-INF/views/staticize/default/articleDetail.jsp",returnMap);
		}else{
			Map<String, Object> returnMap = getProjectModuleInfor(module, project, "-数据库表详情");
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
	 * @return
	 * @throws MyException
	 */
	@RequestMapping("/interfaceDetail.do")
	public ModelAndView interfaceDetail(HttpServletRequest req, @RequestParam String interfaceId,
			String needStaticizes, @RequestParam String secretKey) throws MyException {
		// 验证是否是非法请求
		if( !settingCache.get(S_SECRETKEY).getValue().equals(secretKey) ){
			throw new MyException(MyError.E000056);
		}		
				
		InterfaceWithBLOBs interFace = interfaceService.getById(interfaceId);
		Module module = moduleCache.get(interFace.getModuleId());
		Project project = projectCache.get(module.getProjectId());
		String path = Tools.getStaticPath(project);

		if(project.getType() != ProjectType.PUBLIC.getType()){
			Tools.deleteFile(path);
			// 删除旧的静态化文件
			throw new MyException(MyError.E000044);
		}
		Map<String, Object> returnMap = getProjectModuleInfor(module, project, "-接口详情");
		List<InterfacePDFDto> interfaces = new ArrayList<InterfacePDFDto>();
		interfaces.add(interfaceService.getInterDto(interFace, module, false));

		returnMap.put("interfaces", interfaces);
		returnMap.put("activePage",module.getId()+"_interface");
		returnMap.put("needStaticizes", needStaticizes);
		return new ModelAndView("WEB-INF/views/staticize/default/interfaceDetail.jsp",returnMap);
	}
	

	/**
	 * 删除静态化
	 * @throws Exception 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/delStaticize.do")
	@ResponseBody
	public JsonResult delStaticize(HttpServletRequest req, @RequestParam String projectId, String needStaticizes) throws UnsupportedEncodingException, Exception {
		Project project = projectCache.get(projectId);
		checkUserPermissionByProject(project);
		String path = Tools.getStaticPath(project);
		Tools.deleteFile(path);
		return new JsonResult(1, null );
	}
	
	/**
	 * 下载静态化文件
	 * @throws Exception 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/downloadStaticize.do")
	@ResponseBody
	public JsonResult downloadStaticize(HttpServletRequest req, @RequestParam String projectId, String needStaticizes) throws UnsupportedEncodingException, Exception {
		Project project = projectCache.get(projectId);
		checkUserPermissionByProject(project);
		String path = Tools.getStaticPath(project);
		File file = new File(path);
    	if( !file.exists()){
    		throw new MyException(MyError.E000057);
    	}
    	
    	String webBasePath = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort() + req.getContextPath() +"/";
    	Tools.createFile(path + "/downLoad/");
    	
    	
        //获取html，提取url，替换线上文件路径，准本下载文件夹
        String[] childFilePaths = file.list();  
        List<String> filePaths = new ArrayList<>();
        for(String childFilePath : childFilePaths){  
           if( !childFilePath.endsWith(".html") ){
        	   continue;
           }
           String html = Tools.readFile(file.getAbsolutePath() +"/"+ childFilePath);
           Tools.getHrefFromText(html, filePaths);
           html = html.replaceAll(webBasePath, "");
           Tools.staticize(html, path + "/downLoad/" + new File(childFilePath).getName());
        }  
        
        // 拷贝文件文件
        for(String sourcePath:filePaths){
        	if(sourcePath.startsWith(webBasePath) && !sourcePath.endsWith(".do")){
        		sourcePath = sourcePath.replace(webBasePath, "").split("\"")[0].split("\\?")[0].trim();
        		if(sourcePath.endsWith(".do")){
        			continue;
        		}
        		// 创建文件目录
        		String sourcePathFile = sourcePath.substring(0, sourcePath.lastIndexOf("/"));
        		Tools.createFile(path + "/downLoad/"+ sourcePathFile.replace(Tools.getServicePath(), ""));
        		
        		Tools.copyFile(Tools.getServicePath() + sourcePath , path + "/downLoad/"+ sourcePath );
        	}
        }
    	
        //压缩
        Tools.createZip(path + "/downLoad/", path + "/" + projectId + ".zip");
        // 返回下载页面
		return new JsonResult(1, webBasePath + "static/"+project.getId() + "/" + projectId + ".zip" );
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
		
		String secretKey = settingCache.get(S_SECRETKEY).getValue();
		Project project = projectCache.get(projectId);
		
		checkUserPermissionByProject(project);
		
		String path = Tools.getStaticPath(project);
		Tools.createFile(path);
		if(project.getType() != ProjectType.PUBLIC.getType()){
			Tools.deleteFile(path);
			// 删除旧的静态化文件
			throw new MyException(MyError.E000044);
		}
		// 静态化错误码// 查询页码
		int pageSize = 15;
		int totalPage = 0;
		if(needStaticizes.indexOf(",error,") >= 0){
			int errorSize = errorService.count(new ErrorQuery().setProjectId(projectId));
			// 计算总页数
			totalPage = (errorSize+pageSize-1)/pageSize;
			if(totalPage == 0){
				totalPage = 1;
			}
			for(int i=1 ; i<= totalPage; i++){
				String html = HttpPostGet.get(config.getDomain()+ "/user/staticize/errorList.do?projectId="+projectId
						+"&currentPage="+i + "&needStaticizes=" + needStaticizes + "&secretKey=" + secretKey, null, null, 10 * 1000);
				// list-类目摘要-页码
				Tools.staticize(html, path + "/errorList-" + i + ".html");
			}
		}
		
		Map<String, Object> map = new HashMap<>();

		for(Module module : moduleService.query(new ModuleQuery().setProjectId(projectId))){
			if(needStaticizes.indexOf(",article,") >= 0){
				// 静态化模块文章，分类
				List<String> categorys = moduleService.queryCategoryByModuleId(module.getId());
				// 文章分类，按类目静态化
				for(String category: categorys){
					if( MyString.isEmpty( category )){
						continue; // 空类目不静态化
					}
					// 查询页码
                    ArticleQuery articleQuery = new ArticleQuery();
					articleQuery.setModuleId(module.getId()).setType(ArticleType.ARTICLE.name()).setCategory(category);
					int articleSize = articleService.count(articleQuery);
					// 计算总页数
					totalPage = (articleSize+pageSize-1)/pageSize;
					if(totalPage == 0){
						totalPage = 1;
					}
					for(int i=1 ; i<= totalPage; i++){
						String html = HttpPostGet.get(config.getDomain()+ "/user/staticize/articleList.do?moduleId="+ module.getId()+"&category="+
								category+"&currentPage="+i + "&needStaticizes="+needStaticizes + "&secretKey=" + secretKey, null, null, 10 * 1000);
						// list-类目摘要-页码
						Tools.staticize(html, path + "/" + module.getId() +"-articleList-"+ MD5.encrytMD5(category, "").substring(0, 10) + "-" + i + ".html");
					}
				}
				
				// 文章分类，不分类
                ArticleQuery articleQuery = new ArticleQuery();
                articleQuery.setModuleId(module.getId()).setType(ArticleType.ARTICLE.name());
				int articleSize = articleService.count(articleQuery);
				// 计算总页数
				totalPage = (articleSize+pageSize-1)/pageSize;
				if(totalPage == 0){
					totalPage = 1;
				}
				for(int i=1 ; i<= totalPage; i++){
					String html = HttpPostGet.get(config.getDomain()+ "/user/staticize/articleList.do?moduleId="+ module.getId()+
							"&category="+ IConst.ALL+"&currentPage="+i + "&needStaticizes="+needStaticizes+ "&secretKey=" + secretKey, null, null, 10 * 1000);
					// list-类目摘要-页码
					Tools.staticize(html, path + "/" +  module.getId() +"-articleList--" + i + ".html");
				}
				
				
				// 静态化文章
                articleQuery.setPageSize(ALL_PAGE_SIZE);
                for(Article article: articleService.query(articleQuery)){
					String html = HttpPostGet.get(config.getDomain()+ "/user/staticize/articleDetail.do?articleId="+ article.getId() + 
							"&needStaticizes="+needStaticizes+ "&secretKey=" + secretKey, null, null, 10 * 1000);
					Tools.staticize(html, path + "/" + article.getId()+".html");
				}
				
				
			}
			
			if(needStaticizes.indexOf(",dictionary,") >= 0){
				// 数据库表列表
                ArticleQuery articleQuery = new ArticleQuery().setModuleId(module.getId()).setType(ArticleType.DICTIONARY.name());
                int articleSize = articleService.count(articleQuery);
				// 计算总页数
				totalPage = (articleSize+pageSize-1)/pageSize;
				if(totalPage == 0){
					totalPage = 1;
				}
				for(int i=1 ; i<= totalPage; i++){
					String html = HttpPostGet.get(config.getDomain()+ "/user/staticize/articleList.do?moduleId="+ module.getId()+
							"&category="+ IConst.ALL+"&currentPage="+i+"&type=DICTIONARY" + "&needStaticizes="+needStaticizes+ "&secretKey=" + secretKey, null, null, 10 * 1000);
					// list-类目摘要-页码
					Tools.staticize(html, path + "/" +  module.getId() +"-dictionaryList-" + i + ".html");
				}
				
				// 静态化数据库表详情
                articleQuery.setPageSize(ALL_PAGE_SIZE);
				for(Article article: articleService.query(articleQuery)){
					String html = HttpPostGet.get(config.getDomain()+ "/user/staticize/articleDetail.do?articleId="+ article.getId() +
							"&needStaticizes="+needStaticizes+ "&secretKey=" + secretKey, null, null, 10 * 1000);
					Tools.staticize(html, path + "/" + article.getId()+".html");
				}
			}
			
			if(needStaticizes.indexOf("interface") >= 0){
				// 接口列表
				// 计算总页数
                InterfaceQuery interfaceQuery = new InterfaceQuery().setModuleId(module.getId());
                totalPage = (interfaceService.count(interfaceQuery)+pageSize-1)/pageSize;
				if(totalPage == 0){
					totalPage = 1;
				}
				for(int i=1 ; i<= totalPage; i++){
					String html = HttpPostGet.get(config.getDomain()+ "/user/staticize/interfaceList.do?moduleId="+ module.getId()+"&currentPage="+i +
							"&needStaticizes="+needStaticizes+ "&secretKey=" + secretKey, null, null, 10 * 1000);
					// list-类目摘要-页码
					Tools.staticize(html, path + "/" +  module.getId() +"-interfaceList-" + i + ".html");
				}
				
				
				// 静态化接口详情
                interfaceQuery.setPageSize(ALL_PAGE_SIZE);
				for(Interface inter: interfaceService.query(interfaceQuery)){
					String html = HttpPostGet.get(config.getDomain()+ "/user/staticize/interfaceDetail.do?interfaceId="+ inter.getId() + 
							"&needStaticizes="+needStaticizes+ "&secretKey=" + secretKey, null, null, 10 * 1000);
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
	
	
	private Map<String, Object> getProjectModuleInfor(Module module, Project project, String typeName) throws MyException{
		// 静态化
		Map<String, String> settingMap = new HashMap<>();
		for (SettingDto setting : settingCache.getAll()) {
			settingMap.put(setting.getKey(), setting.getValue());
		}
		if(!MyString.isEmpty(project.getCover())){
			if(!project.getCover().startsWith("http:") &&  !project.getCover().startsWith("https:") ){
				project.setCover(config.getDomain() +"/"+ project.getCover());
			}
		}
		
		settingMap.put(IConst.DOMAIN, config.getDomain());
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("settings", settingMap);
		returnMap.put("project", project);
		returnMap.put("module", module);
		// 将选中的模块放到第一位
		List<Module> moduleList = moduleService.query(new ModuleQuery().setProjectId(project.getId()));

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
