package cn.crap.controller.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.enumeration.ArticleType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.table.IArticleService;
import cn.crap.inter.service.table.ICommentService;
import cn.crap.inter.service.table.IMenuService;
import cn.crap.inter.service.tool.ICacheService;
import cn.crap.model.Comment;
import cn.crap.model.Module;
import cn.crap.springbeans.Config;
import cn.crap.model.Article;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;

/**
 * 项目主页
 * @author Ehsan
 *
 */
@Controller
public class FrontWebPageController extends BaseController<Article> {
	@Autowired
	IMenuService menuService;
	@Autowired
	private ICacheService cacheService;
	@Autowired
	private IArticleService webPageService;
	@Autowired
	private ICommentService commentService;
	@Autowired
	private Config config;
	
	
	@RequestMapping("/front/webPage/diclist.do")
	@ResponseBody
	public JsonResult list(@RequestParam String moduleId, String name, @RequestParam(defaultValue="1") Integer currentPage) throws MyException{
		Page page= new Page(15);
		page.setCurrentPage(currentPage);
		Map<String,Object> map = Tools.getMap("moduleId",moduleId, "type", ArticleType.DICTIONARY.name(), "name|like", name);
		return new JsonResult(1,   webPageService.findByMap(map, " new Article(id, type, name, click, category, createTime, key, moduleId, brief, sequence) ", page, null)  , page,
				Tools.getMap("crumbs", Tools.getCrumbs( ArticleType.DICTIONARY.getName() +"-" + cacheService.getModuleName(moduleId), "void")) );
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping("/front/webPage/list.do")
	@ResponseBody
	public JsonResult list(@RequestParam(defaultValue="1") Integer currentPage, String moduleId, @RequestParam String type,@RequestParam String category){
		Page page= new Page(15);
		page.setCurrentPage(currentPage);
		/**
		 * 模块不能为空，为空则查询WebPage中module=' '的数据
		 */
		Map<String,Object> map;
		if(MyString.isEmpty(moduleId)){
			map = Tools.getMap("moduleId", Const.WEB_MODULE, "type", type, "category", category);
		}else{
			map = Tools.getMap("moduleId", moduleId, "type", type, "category", category);
		}
		
		// 选择分类，最多显示前20个
		List<String> categorys = (List<String>) cacheService.getObj(Const.CACHE_ARTICLE_CATEGORY);
		if( categorys == null){
			categorys = (List<String>) webPageService.queryByHql("select distinct category from Article where type ='ARTICLE'", null, new Page(20));
			cacheService.setObj(Const.CACHE_ARTICLE_CATEGORY, categorys, config.getCacheTime());
		}
		// 
		
		List<Article> webPages = webPageService.findByMap(map, " new Article(id, type, name, click, category, createTime, key, moduleId, brief, sequence) ", page,null);
		return new JsonResult(1, webPages, page,
				Tools.getMap("type", ArticleType.valueOf(type).getName(), "category", category, 
						"crumbs", Tools.getCrumbs(category, "void"), "categorys", categorys));
	}
	
	@RequestMapping("/front/webPage/detail.do")
	@ResponseBody
	public JsonResult webDetail(@ModelAttribute Article webPage,String password,String visitCode, @RequestParam(defaultValue="1") Integer currentPage) throws MyException{
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Article model = (Article) cacheService.getObj( Const.CACHE_WEBPAGE + webPage.getId() );
		Map<String,Object> map;
		if(model == null){
			// 根据key查询webPage
			if(webPage.getId().length()<21){
				map = Tools.getMap("key", webPage.getId());
				List<Article>models=webPageService.findByMap(map, null, null);
				if(models.size()>0)
					model = models.get(0);
			}
			// 根据key没有查到，则根据id查
			if(model==null){
				model= webPageService.get(webPage.getId());
			}
			if(model == null)
				throw new MyException("000020");
			cacheService.setObj( Const.CACHE_WEBPAGE + webPage.getId(), model, config.getCacheTime());
		}
		
		
		// 文章访问密码
		if(model.getType().equals(ArticleType.ARTICLE.name())){
			returnMap.put("crumbs", Tools.getCrumbs(model.getCategory(),"#/"+webPage.getModuleId()+"/webPage/list/ARTICLE/"+model.getCategory(), model.getName(), "void"));
			Tools.canVisitModule(model.getPassword(), password, visitCode, request);
		}
		
		// 数据字典密码访问由模块决定
		else if(model.getType().equals(ArticleType.DICTIONARY.name())){
			// 需要改造，项目id放在#/后面
			//returnMap.put("crumbs", Tools.getCrumbs("数据字典列表", "#/webPage/list/DICTIONARY/null", model.getName(), "void"));
			Module module = cacheService.getModule(model.getModuleId());
			Tools.canVisitModule(module.getPassword(), password, visitCode, request);
		}
		
		else{
			returnMap.put("crumbs", Tools.getCrumbs(model.getName(), "void"));
		}
		
		if(!model.getType().equals(ArticleType.DICTIONARY.name())){
			Object categorys = null;
			// 选择分类，最多显示前20个
			categorys = cacheService.getObj(Const.CACHE_ARTICLE_CATEGORY);
			if( categorys == null){
				categorys = webPageService.queryByHql("select distinct category from Article where type ='ARTICLE'", null, new Page(20));
				cacheService.setObj(Const.CACHE_ARTICLE_CATEGORY, categorys, config.getCacheTime());
			}
			returnMap.put("categorys", categorys);
			returnMap.put("category", model.getCategory());
		}
		
		returnMap.put("comment", new Comment(model.getId()));
		map = Tools.getMap("webpageId", model.getId());
		
		Page page= (Page) cacheService.getObj(Const.CACHE_COMMENT_PAGE + model.getId(), currentPage + "");
		@SuppressWarnings("unchecked")
		List<Comment> comments = (List<Comment>) cacheService.getObj(Const.CACHE_COMMENTLIST + model.getId(), currentPage+"");
		if( comments == null || page == null){
			page = new Page(10);
			page.setCurrentPage(currentPage);
			comments = commentService.findByMap(map, page, "createTime desc");
			cacheService.setObj(Const.CACHE_COMMENTLIST + model.getId() , currentPage + "", comments, config.getCacheTime());
			cacheService.setObj(Const.CACHE_COMMENT_PAGE + model.getId() , currentPage + "", page, config.getCacheTime());
		}
		
				
		returnMap.put("comments", comments);
		returnMap.put("commentCode", cacheService.getSetting(Const.SETTING_COMMENTCODE).getValue());
		// 更新点击量
		webPageService.update("update Article set click=click+1 where id=:id", Tools.getMap("id", model.getId()));
		return new JsonResult(1, model, page, returnMap);
	}
	
}
