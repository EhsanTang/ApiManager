package cn.crap.controller.user;

import cn.crap.adapter.ArticleAdapter;
import cn.crap.dto.ArticleDto;
import cn.crap.dto.SearchDto;
import cn.crap.enumer.ArticleType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.mybatis.Article;
import cn.crap.model.mybatis.ArticleWithBLOBs;
import cn.crap.model.mybatis.Module;
import cn.crap.model.mybatis.Project;
import cn.crap.service.ISearchService;
import cn.crap.service.custom.CustomArticleService;
import cn.crap.service.custom.CustomCommentService;
import cn.crap.service.mybatis.ArticleService;
import cn.crap.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

// TODO 版本升级提供在线接口，调用接口直接检查数据库并升级
// TODO setting 中记录版本ID（MD5（version））
@Controller
@RequestMapping("/user/article")
public class ArticleController extends BaseController{
	@Autowired
	private ArticleService articleService;
	@Autowired
	private CustomArticleService customArticleService;
	@Autowired
	private ISearchService luceneService;
	@Autowired
	private CustomCommentService customCommentService;

	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(String moduleId, String name, String type, String category, Integer currentPage) throws MyException{
		Assert.notNull(moduleId);
		checkUserPermissionByModuleId(moduleId, VIEW);
		
		Page page= new Page(currentPage);

		page.setAllRow(customArticleService.countByProjectId(moduleId, name, type, category));
		List<Article> models = customArticleService.queryArticle(moduleId, name, type, category, page);
		List<ArticleDto> dtos = ArticleAdapter.getDto(models, null);

		return new JsonResult().success().data(dtos).page(page)
                .others(Tools.getMap("type", ArticleType.valueOf(type).getName(), "category", category));
	}
	
	@RequestMapping("/detail.do")
	@ResponseBody
	@AuthPassport
	public JsonResult detail(String id, String type, String moduleId) throws MyException{
		if(id != null){
            ArticleWithBLOBs article =  articleService.getById(id);
            checkUserPermissionByProject(article.getProjectId(), VIEW);
            Module module = moduleCache.get(article.getModuleId());
			return new JsonResult(1, ArticleAdapter.getDtoWithBLOBs(article, module));
		}

		Module module = new Module();
		if (moduleId != null) {
			module = moduleCache.get(moduleId);
			checkUserPermissionByProject(module.getProjectId(), VIEW);
		}

        ArticleWithBLOBs model = new ArticleWithBLOBs();
		model.setType(type);
		model.setModuleId(moduleId);
		model.setProjectId(module.getProjectId());
		return new JsonResult(1, ArticleAdapter.getDtoWithBLOBs(model, module));
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute ArticleDto dto) throws Exception{
	    Assert.notNull(dto.getModuleId());
        Project project = projectCache.get(moduleCache.get(dto.getModuleId()).getProjectId());
        checkUserPermissionByProject(project, dto.getType().equals(ArticleType.ARTICLE.name())? ADD_ARTICLE : ADD_DICT);

        dto.setCanDelete(Byte.valueOf("1"));
		if(!MyString.isEmpty(dto.getId())){
			/**
			 * 判断是否为系统数据，系统数据不允许修改mkey和canDelete字段
			 */
			ArticleWithBLOBs dbArticle = articleService.getById(dto.getId());
			if(dbArticle.getCanDelete()!=1){
				dto.setMkey(null);
				dto.setCanDelete(null);
			}

            ArticleWithBLOBs article = ArticleAdapter.getModel(dto);
			customArticleService.update(article, ArticleType.getByEnumName(dto.getType()), "");

            dbArticle = articleService.getById(dto.getId());
			luceneService.update(ArticleAdapter.getSearchDto(dbArticle));
		}else{
            ArticleWithBLOBs article = ArticleAdapter.getModel(dto);
            article.setProjectId(project.getId());
            article.setMarkdown("");
			articleService.insert(article);

			luceneService.add(ArticleAdapter.getSearchDto(article));
		}
		return new JsonResult(1,dto);
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
			Article model = articleService.getById(tempId);
			checkUserPermissionByProject(model.getProjectId() , model.getType().equals(ArticleType.ARTICLE.name())? DEL_ARTICLE : DEL_DICT);
			if(model.getCanDelete()!=1){
				throw new MyException("000009");
			}

			if (customCommentService.countByArticleId(model.getId()) > 0){
				throw new MyException("000037");
			}

			customArticleService.delete(tempId, ArticleType.getByEnumName(model.getType()) , "");

			luceneService.delete(new SearchDto(model.getId()));
		}
		return new JsonResult(1,null);
	}
	
	@RequestMapping("/changeSequence.do")
	@ResponseBody
	@AuthPassport
	public JsonResult changeSequence(@RequestParam String id, @RequestParam String changeId) throws MyException {
		ArticleWithBLOBs change = articleService.getById(changeId);
        ArticleWithBLOBs model = articleService.getById(id);

		checkUserPermissionByProject(change.getProjectId(), change.getType().equals(ArticleType.ARTICLE.name())? MOD_ARTICLE:MOD_DICT);
		checkUserPermissionByProject(model.getProjectId(), model.getType().equals(ArticleType.ARTICLE.name())? MOD_ARTICLE:MOD_DICT);
		
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
		ArticleWithBLOBs article = null;
		if(isMysql){
			article = SqlToDictionaryUtil.mysqlToDictionary(sql, brief, moduleId, name);
		}else{
			article = SqlToDictionaryUtil.sqlserviceToDictionary(sql, brief, moduleId, name);
		}
		articleService.insert(article);
		return new JsonResult(1, new Article());
	}

	@RequestMapping("/markdown.do")
	public String markdown(@ModelAttribute Article article, HttpServletRequest request) throws Exception {
		ArticleWithBLOBs model;
		if(article.getId() != null){
			model= articleService.getById(article.getId());
		}else{
			model= new ArticleWithBLOBs();
			model.setType(article.getType());
			model.setModuleId(article.getModuleId());
		}
		request.setAttribute("markdownPreview", model.getContent());
		request.setAttribute("markdownText", model.getMarkdown());
		return "/WEB-INF/views/markdown.jsp";
	}
}
