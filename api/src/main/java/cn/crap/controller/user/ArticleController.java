package cn.crap.controller.user;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.dto.SearchDto;
import cn.crap.enumeration.ArticleType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.table.IArticleService;
import cn.crap.inter.service.table.ICommentService;
import cn.crap.inter.service.tool.ICacheService;
import cn.crap.inter.service.tool.ISearchService;
import cn.crap.model.Article;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.SqlToDictionaryUtil;
import cn.crap.utils.Tools;

@Controller
@RequestMapping("/user/article")
public class ArticleController extends BaseController<Article>{
	@Autowired
	private IArticleService articleService;
	@Autowired
	private ICacheService cacheService;
	@Autowired
	private ISearchService luceneService;
	@Autowired
	private ICommentService commentService;

	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(@ModelAttribute Article article,@RequestParam(defaultValue="1") Integer currentPage) throws MyException{
		
		hasPermission( cacheService.getProject(article.getProjectId()) , view);
		
		Page page= new Page(15);
		page.setCurrentPage(currentPage);
		
		Map<String,Object> map = Tools.getMap("name|like",article.getName(),"moduleId",article.getModuleId(),"type", article.getType(),"category",article.getCategory());
		
		return new JsonResult(1,articleService.findByMap(map, " new Article(id, type, name, click, category, createTime, key, moduleId, brief, sequence) ", page,null), page,
				Tools.getMap("type", ArticleType.valueOf(article.getType()).getName(), "category", article.getCategory()));
	}
	
	@RequestMapping("/detail.do")
	@ResponseBody
	@AuthPassport
	public JsonResult detail(@ModelAttribute Article article) throws MyException{
		Article model;
		if(!article.getId().equals(Const.NULL_ID)){
			model= articleService.get(article.getId());
			hasPermission( cacheService.getProject(model.getProjectId()), view);
		}else{
			model=new Article();
			model.setType(article.getType());
			model.setModuleId(article.getModuleId());
		}
		return new JsonResult(1,model);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute Article article) throws MyException, IOException{
		
		// 如果模块为空，表示为管理员，将模块设置为系统模块
		if(MyString.isEmpty(article.getModuleId())){
			article.setModuleId(Const.WEB_MODULE);
		}
		if(MyString.isEmpty(article.getKey())){
			article.setKey(null);
		}
		
		article.setCanDelete(Byte.valueOf("1"));
		if(!MyString.isEmpty(article.getId())){
			/**
			 * 判断是否为系统数据，系统数据不允许修改可以和canDelete字段
			 */
			Article old = articleService.get(article.getId());
			if(old.getCanDelete()!=1){
				article.setKey(old.getKey());
				article.setCanDelete(Byte.valueOf("0"));
			}
			// 修改模块
			if(!article.getModuleId().equals(old.getModuleId())){
				hasPermission( cacheService.getProject(article.getProjectId()) , article.getType().equals(ArticleType.ARTICLE.name())? modArticle:modDict);
			}	
			
			hasPermission( cacheService.getProject(article.getProjectId()) , article.getType().equals(ArticleType.ARTICLE.name())? modArticle:modDict);
			articleService.update(article, ArticleType.getByEnumName(article.getType()), "");
			luceneService.update(article.toSearchDto(cacheService));
		}else{
			hasPermission( cacheService.getProject(article.getProjectId()) , article.getType().equals(ArticleType.ARTICLE.name())? addArticle:addDict);
			articleService.save(article);
			luceneService.add(article.toSearchDto(cacheService));
		}
		cacheService.delObj(Const.CACHE_WEBPAGE + article.getId());
		cacheService.delObj(Const.CACHE_WEBPAGE + article.getKey());
		return new JsonResult(1,article);
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(String id, String ids) throws MyException, IOException{
		if( MyString.isEmpty(id) && MyString.isEmpty(ids)){
			throw new MyException("000029");
		}
		if( MyString.isEmpty(ids) ){
			ids = id;
		}
		
		for(String tempId : ids.split(",")){
			if(MyString.isEmpty(tempId)){
				continue;
			}
			Article model = articleService.get(tempId);
			hasPermission( cacheService.getProject(model.getProjectId()) , model.getType().equals(ArticleType.ARTICLE.name())? delArticle:delDict);
			if(model.getCanDelete()!=1){
				throw new MyException("000009");
			}
			
			if( commentService.getCount(Tools.getMap("articleId", model.getId()))>0){
				throw new MyException("000037");
			}
			articleService.delete(model, ArticleType.getByEnumName(model.getType()) , "");
			cacheService.delObj(Const.CACHE_WEBPAGE + model.getId());
			cacheService.delObj(Const.CACHE_WEBPAGE + model.getKey());
			
			luceneService.delete(new SearchDto(model.getId()));
		}
		return new JsonResult(1,null);
	}
	
	@RequestMapping("/changeSequence.do")
	@ResponseBody
	@AuthPassport
	public JsonResult changeSequence(@RequestParam String id,@RequestParam String changeId) throws MyException {
		Article change = articleService.get(changeId);
		Article model = articleService.get(id);
		
		hasPermission( cacheService.getProject(change.getProjectId()), change.getType().equals(ArticleType.ARTICLE.name())? modArticle:modDict);
		hasPermission( cacheService.getProject(model.getProjectId()), model.getType().equals(ArticleType.ARTICLE.name())? modArticle:modDict);
		
		int modelSequence = model.getSequence();
		
		model.setSequence(change.getSequence());
		change.setSequence(modelSequence);
		
		articleService.update(model);
		articleService.update(change);
		return new JsonResult(1, null);
	}
	
	@RequestMapping("/dictionary/importFromSql.do")
	@ResponseBody
	@AuthPassport
	public JsonResult importFromSql(@RequestParam String sql, @RequestParam(defaultValue="") String brief, @RequestParam String moduleId, String name,
			@RequestParam(defaultValue="") boolean isMysql) throws MyException {
		Article article = null;
		if(isMysql){
			article = SqlToDictionaryUtil.mysqlToDictionary(sql, brief, moduleId, name);
		}else{
			article = SqlToDictionaryUtil.sqlserviceToDictionary(sql, brief, moduleId, name);
		}
		articleService.save(article);
		return new JsonResult(1, new Article());
	}

	@RequestMapping("/markdown.do")
	public String markdown(@ModelAttribute Article webPage) throws Exception {
		Article model;
		if(!webPage.getId().equals(Const.NULL_ID)){
			model= articleService.get(webPage.getId());
		}else{
			model=new Article();
			model.setType(webPage.getType());
			model.setModuleId(webPage.getModuleId());
		}
		request.setAttribute("markdownPreview", model.getContent());
		request.setAttribute("markdownText", model.getMarkdown());
		return "/WEB-INF/views/markdown.jsp";
	}
}
