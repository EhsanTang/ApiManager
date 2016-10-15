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
import cn.crap.inter.service.table.IProjectService;
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
	private IProjectService projectService;

	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(@ModelAttribute Article article,@RequestParam(defaultValue="1") Integer currentPage) throws MyException{
		
		hasPermission( projectService.get(article.getProjectId()) );
		
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
			hasPermission( projectService.get(model.getProjectId()) );
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
		hasPermission( projectService.get(article.getProjectId()) );
		
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
			Article model = articleService.get(article.getId());
			if(model.getCanDelete()!=1){
				article.setKey(model.getKey());
				article.setCanDelete(Byte.valueOf("0"));
			}
			
			hasPermission( projectService.get(model.getProjectId()) );
			articleService.update(article);
			luceneService.update(article.toSearchDto(null));
		}else{
			articleService.save(article);
			luceneService.add(article.toSearchDto(null));
		}
		cacheService.delObj(Const.CACHE_WEBPAGE + article.getId());
		cacheService.delObj(Const.CACHE_WEBPAGE + article.getKey());
		return new JsonResult(1,article);
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(@ModelAttribute Article article) throws MyException, IOException{
		Article model = articleService.get(article.getId());
		hasPermission( projectService.get(article.getProjectId()) );
		if(model.getCanDelete()!=1){
			throw new MyException("000009");
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
		
		hasPermission( projectService.get(change.getProjectId()) );
		hasPermission( projectService.get(model.getProjectId()) );
		
		int modelSequence = model.getSequence();
		
		model.setSequence(change.getSequence());
		change.setSequence(modelSequence);
		
		articleService.update(model);
		articleService.update(change);
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

}
