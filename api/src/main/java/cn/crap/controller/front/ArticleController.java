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
import cn.crap.model.Article;
import cn.crap.model.Comment;
import cn.crap.model.Module;
import cn.crap.model.Project;
import cn.crap.springbeans.Config;
import cn.crap.utils.Const;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;

/**
 * 项目主页
 * @author Ehsan
 *
 */
@Controller("frontArticleController")
public class ArticleController extends BaseController<Article> {
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
	
	
	@RequestMapping("/front/article/diclist.do")
	@ResponseBody
	public JsonResult list(@RequestParam String moduleId, String name, @RequestParam(defaultValue="1") Integer currentPage, String password, String visitCode) throws MyException{
		
		Module module = cacheService.getModule(moduleId);
		Project project = cacheService.getProject(module.getProjectId());
		
		// 如果是私有项目，必须登录才能访问，公开项目需要查看是否需要密码
		isPrivateProject(password, visitCode, project);
		
		Page page= new Page(15);
		page.setCurrentPage(currentPage);
		Map<String,Object> map = Tools.getMap("moduleId",moduleId, "type", ArticleType.DICTIONARY.name(), "name|like", name);
		return new JsonResult(1, webPageService.findByMap(map, " new Article(id, type, name, click, category, createTime, key, moduleId, brief, sequence) ", page, null)  , page,
				Tools.getMap("crumbs", Tools.getCrumbs( ArticleType.DICTIONARY.getName() +"-" + module.getName(), "void")) );
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping("/front/article/list.do")
	@ResponseBody
	public JsonResult list(@RequestParam(defaultValue="1") Integer currentPage,@RequestParam(defaultValue=Const.WEB_MODULE) String moduleId, @RequestParam String type,@RequestParam String category
			,String password, String visitCode) throws MyException{
		
		Module module = cacheService.getModule(moduleId);
		Project project = cacheService.getProject(module.getProjectId());
		
		// 如果是私有项目，必须登录才能访问，公开项目需要查看是否需要密码
		isPrivateProject(password, visitCode, project);
		
		Page page= new Page(15);
		page.setCurrentPage(currentPage);
		
		Map<String,Object> map = Tools.getMap("moduleId", moduleId, "type", type.equals("PAGE")? "ARTICLE":type, "category", category);
		
		// 选择分类，最多显示前20个
		List<String> categorys = (List<String>) cacheService.getObj(Const.CACHE_ARTICLE_CATEGORY + module.getId());
		if( categorys == null){
			categorys = (List<String>) webPageService.queryByHql("select distinct category from Article where type = 'ARTICLE' and moduleId = '"+module.getId()+"'", null, new Page(20));
			cacheService.setObj(Const.CACHE_ARTICLE_CATEGORY + module.getId(), categorys, config.getCacheTime());
		}

		List<Article> webPages = webPageService.findByMap(map, " new Article(id, type, name, click, category, createTime, key, moduleId, brief, sequence) ", page,null);
		return new JsonResult(1, webPages, page,
				Tools.getMap("type", ArticleType.valueOf(type).getName(), "category", category, "categorys", categorys, 
						"crumbs", 
						Tools.getCrumbs( 
								project.getName(), "#/"+project.getId()+"module/list",
								module.getName()+":文章列表", "void")));
	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping("/front/article/detail.do")
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
		
		Module module = cacheService.getModule(model.getModuleId());
		Project project = cacheService.getProject(module.getProjectId());
		// 如果是私有项目，必须登录才能访问，公开项目需要查看是否需要密码
		isPrivateProject(password, visitCode, project);
		
		Page page = null;
		// 选择分类，最多显示前20个
		if( !model.getType().equals(ArticleType.DICTIONARY.name()) ){
			List<String> categorys = (List<String>) cacheService.getObj(Const.CACHE_ARTICLE_CATEGORY + model.getModuleId());
			if( categorys == null){
				categorys = (List<String>) webPageService.queryByHql("select distinct category from Article where type = 'ARTICLE' and moduleId = '"+model.getModuleId()+"'", null, new Page(20));
				cacheService.setObj(Const.CACHE_ARTICLE_CATEGORY + model.getModuleId(), categorys, config.getCacheTime());
			}
			returnMap.put("categorys", categorys);
			returnMap.put("category", model.getCategory());
			
			// 初始化前端js评论对象
			returnMap.put("comment", new Comment(model.getId()));
			map = Tools.getMap("articleId", model.getId());
			
			// 评论
			page= (Page) cacheService.getObj(Const.CACHE_COMMENT_PAGE + model.getId(), currentPage + "");
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
		}
		
		// 更新点击量
		webPageService.update("update Article set click=click+1 where id=:id", Tools.getMap("id", model.getId()));
		return new JsonResult(1, model, page, returnMap);
	}
}
