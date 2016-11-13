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
			article.setModuleId(old.getModuleId());
			article.setType(old.getType());
			
			hasPermission( cacheService.getProject(article.getProjectId()) , article.getType().equals(ArticleType.ARTICLE.name())? modArticle:modDict);
			articleService.update(article);
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
	public JsonResult delete(@ModelAttribute Article article) throws MyException, IOException{
		Article model = articleService.get(article.getId());
		hasPermission( cacheService.getProject(model.getProjectId()) , model.getType().equals(ArticleType.ARTICLE.name())? delArticle:delDict);
		if(model.getCanDelete()!=1){
			throw new MyException("000009");
		}
		
		if( commentService.getCount(Tools.getMap("articleId", model.getId()))>0){
			throw new MyException("000037");
		}
		articleService.delete(article);
		cacheService.delObj(Const.CACHE_WEBPAGE + article.getId());
		cacheService.delObj(Const.CACHE_WEBPAGE + article.getKey());
		
		luceneService.delete(new SearchDto(article.getId()));
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
	public JsonResult importFromSql(@RequestParam String sql) throws MyException {
		sql = sql.toLowerCase();
		
		/**
		 * mysql
		 */
		
		
		
		return new JsonResult(1, null);
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

	public static void main(String args[]){
		String sql ="CREATE TABLE `article` ( `id` varchar(50) NOT NULL, `name` varchar(100) NOT NULL,   `brief` varchar(200) DEFAULT NULL,"+
				  "`content` longtext NOT NULL,"+
				  "`click` int(11) NOT NULL DEFAULT '0',"+
				 " `type` varchar(20) NOT NULL DEFAULT 'PAGE',"+
				 " `status` tinyint(4) NOT NULL DEFAULT '1',"+
				 " `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,"+
				 " `moduleId` varchar(50) NOT NULL DEFAULT 'top',"+
				"  `mkey` varchar(20) DEFAULT NULL COMMENT 'key，唯一键，页面唯一标识',"+
				"  `canDelete` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否可删除，可修key，默认可以',"+
				"  `category` varchar(50) DEFAULT NULL,"+
				"  `canComment` tinyint(4) NOT NULL DEFAULT '1',"+
				"  `commentCount` int(11) NOT NULL DEFAULT '0',"+
				"  `sequence` int(11) NOT NULL DEFAULT '0' COMMENT '排序，越大越靠前',"+
				"  `markdown` text NOT NULL,"+
				"  PRIMARY KEY (`id`),"+
				"  UNIQUE KEY `mkey_UNIQUE` (`mkey`)"+
				") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		sql = sql.toLowerCase();
		String tableName = sql.substring(sql.indexOf("table"), sql.indexOf("(")).replace("table", "").replaceAll("`", "").replace("'", "").trim();
		
		System.out.println(tableName);
	}
}
