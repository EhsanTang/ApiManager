package cn.crap.controller.front;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.enumeration.WebPageType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.ICacheService;
import cn.crap.inter.service.ICommentService;
import cn.crap.inter.service.IDataCenterService;
import cn.crap.inter.service.IInterfaceService;
import cn.crap.inter.service.IMenuService;
import cn.crap.inter.service.IWebPageService;
import cn.crap.model.Comment;
import cn.crap.model.DataCenter;
import cn.crap.model.Interface;
import cn.crap.model.User;
import cn.crap.model.WebPage;
import cn.crap.utils.Config;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;

/**
 * 项目主页
 * @author Ehsan
 *
 */
@Scope("prototype")
@Controller
public class FrontWebPageController extends BaseController<WebPage> {
	@Autowired
	IMenuService menuService;
	@Autowired
	private IDataCenterService dataCenterService;
	@Autowired
	private IInterfaceService interfaceService;
	@Autowired
	private ICacheService cacheService;
	@Autowired
	private IWebPageService webPageService;
	@Autowired
	private ICommentService commentService;
	
	
	@RequestMapping("/front/webPage/diclist.do")
	@ResponseBody
	public JsonResult list(@RequestParam String moduleId, String name, @RequestParam(defaultValue="1") Integer currentPage) throws MyException{
		if( !Tools.moduleIdIsLegal(moduleId) ){
			throw new MyException("000020");
		}
		page.setCurrentPage(currentPage);
		map = Tools.getMap("moduleId",moduleId, "type", WebPageType.DICTIONARY.name(), "name|like", name);
		return new JsonResult(1,   webPageService.findByMap(map, " new WebPage(id, type, name, click, category, createTime, key, moduleId, brief, sequence) ", page, null)  , page,
				Tools.getMap("crumbs", Tools.getCrumbs( WebPageType.DICTIONARY.getName() +"-" + cacheService.getModuleName(moduleId), "void")) );
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping("/front/webPage/list.do")
	@ResponseBody
	public JsonResult list(@RequestParam(defaultValue="1") Integer currentPage, String moduleId, @RequestParam String type,@RequestParam String category){
		page.setCurrentPage(currentPage);
		/**
		 * 模块不能为空，为空则查询WebPage中module=' '的数据
		 */
		if(MyString.isEmpty(moduleId)){
			map = Tools.getMap("moduleId", Const.TOP_MODULE, "type", type, "category", category);
		}else{
			map = Tools.getMap("moduleId", moduleId, "type", type, "category", category);
		}
		
		// 选择分类，最多显示前20个
		List<String> categorys = (List<String>) cacheService.getObj(Const.CACHE_ARTICLE_CATEGORY);
		if( categorys == null){
			categorys = (List<String>) webPageService.queryByHql("select distinct category from WebPage where type ='ARTICLE'", null, new Page(20));
			cacheService.setObj(Const.CACHE_ARTICLE_CATEGORY, categorys, Config.getCacheTime());
		}
		// 
		
		List<WebPage> webPages = webPageService.findByMap(map, " new WebPage(id, type, name, click, category, createTime, key, moduleId, brief, sequence) ", page,null);
		return new JsonResult(1, webPages, page,
				Tools.getMap("type", WebPageType.valueOf(type).getName(), "category", category, 
						"crumbs", Tools.getCrumbs(category, "void"), "categorys", categorys));
	}
	
	@RequestMapping("/front/webPage/detail.do")
	@ResponseBody
	public JsonResult webDetail(@ModelAttribute WebPage webPage,String password,String visitCode) throws MyException{
		model = (WebPage) cacheService.getObj( Const.CACHE_WEBPAGE + webPage.getId() );
		if(model == null){
			// 根据key查询webPage
			if(webPage.getId().length()<21){
				map = Tools.getMap("key", webPage.getId());
				List<WebPage>models=webPageService.findByMap(map, null, null);
				if(models.size()>0)
					model = models.get(0);
			}
			// 根据key没有查到，则根据id查
			if(model==null){
				model= webPageService.get(webPage.getId());
			}
			if(model == null)
				throw new MyException("000020");
			cacheService.setObj( Const.CACHE_WEBPAGE + webPage.getId(), model, Config.getCacheTime());
		}
		
		
		// 文章访问密码
		if(model.getType().equals(WebPageType.ARTICLE.name())){
			returnMap.put("crumbs", Tools.getCrumbs(model.getCategory(),"#/"+webPage.getModuleId()+"/webPage/list/ARTICLE/"+model.getCategory(), model.getName(), "void"));
			Tools.canVisitModule(model.getPassword(), password, visitCode, request);
		}
		
		// 数据字典密码访问由模块决定
		else if(model.getType().equals(WebPageType.DICTIONARY.name())){
			// 需要改造，项目id放在#/后面
			//returnMap.put("crumbs", Tools.getCrumbs("数据字典列表", "#/webPage/list/DICTIONARY/null", model.getName(), "void"));
			DataCenter module = cacheService.getModule(model.getModuleId());
			Tools.canVisitModule(module.getPassword(), password, visitCode, request);
		}
		
		else{
			returnMap.put("crumbs", Tools.getCrumbs(model.getName(), "void"));
		}
		
		if(!model.getType().equals(WebPageType.DICTIONARY.name())){
			Object categorys = null;
			// 选择分类，最多显示前20个
			categorys = cacheService.getObj(Const.CACHE_ARTICLE_CATEGORY);
			if( categorys == null){
				categorys = webPageService.queryByHql("select distinct category from WebPage where type ='ARTICLE'", null, new Page(20));
				cacheService.setObj(Const.CACHE_ARTICLE_CATEGORY, categorys, Config.getCacheTime());
			}
			returnMap.put("categorys", categorys);
			returnMap.put("category", model.getCategory());
		}
		
		returnMap.put("comment", new Comment(model.getId()));
		map = Tools.getMap("webpageId", model.getId());
		
		@SuppressWarnings("unchecked")
		List<Comment> comments = (List<Comment>) cacheService.getObj(Const.CACHE_COMMENTLIST + model.getId());
		if( comments == null){
			comments = commentService.findByMap(map, null, null);
			cacheService.setObj(Const.CACHE_COMMENTLIST + model.getId(), comments, Config.getCacheTime());
		}
				
		returnMap.put("comments", comments);
		returnMap.put("commentCode", cacheService.getSetting(Const.SETTING_COMMENTCODE).getValue());
		// 更新点击量
		webPageService.update("update WebPage set click=click+1 where id=:id", Tools.getMap("id", model.getId()));
		return new JsonResult(1, model, null, returnMap);
	}
	
}
