package cn.crap.controller.user;

import cn.crap.adapter.ArticleAdapter;
import cn.crap.dto.ArticleDto;
import cn.crap.dto.SearchDto;
import cn.crap.enumeration.ArticleType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.mybatis.Article;
import cn.crap.service.ISearchService;
import cn.crap.service.mybatis.custom.CustomArticleService;
import cn.crap.service.mybatis.custom.CustomCommentService;
import cn.crap.service.mybatis.imp.MybatisArticleService;
import cn.crap.service.mybatis.imp.MybatisCommentService;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

// TODO 版本升级提供在线接口，调用接口直接检查数据库并升级
// TODO setting 中记录版本ID（MD5（version））
@Controller
@RequestMapping("/user/article")
public class ArticleController extends BaseController{
	@Autowired
	private MybatisArticleService articleService;
	@Autowired
	private CustomArticleService customArticleService;
	@Autowired
	private ISearchService luceneService;
	@Autowired
	private MybatisCommentService commentService;
	@Autowired
	private CustomCommentService customCommentService;

	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(String projectId, String moduleId, String name, String type, String category,@RequestParam(defaultValue="1") Integer currentPage) throws MyException{
		Assert.notNull(moduleId);
		Assert.notNull(projectId);
		hasPermission( projectCache.get(projectId) , view);
		
		Page page= new Page(15, currentPage);
		page.setAllRow(customArticleService.countByProjectId(moduleId, name, type, category));
		List<Article> models = customArticleService.queryArticle(moduleId, name, type, category, page);
		List<ArticleDto> dtos = ArticleAdapter.getDto(models);

		return new JsonResult(1, dtos, page).others(Tools.getMap("type", ArticleType.valueOf(type).getName(), "category", category));
	}
	
	@RequestMapping("/detail.do")
	@ResponseBody
	@AuthPassport
	public JsonResult detail(String id, String type, String moduleId) throws MyException{
		Article model;
		if(id != null){
			model= articleService.selectByPrimaryKey(id);
			String projectId = customArticleService.getProjectId(model.getModuleId());
			hasPermission( projectCache.get(projectId), view);
			return new JsonResult(1,model);
		}

		model=new Article();
		model.setType(type);
		model.setModuleId(moduleId);
		return new JsonResult(1,model);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute ArticleDto dto) throws MyException, IOException{

		// 如果模块为空，表示为管理员，将模块设置为系统模块
		if(MyString.isEmpty(dto.getModuleId())){
			dto.setModuleId(Const.WEB_MODULE);
		}

		if(MyString.isEmpty(dto.getMkey())){
			dto.setMkey(null);
		}

		dto.setCanDelete(Byte.valueOf("1"));
		if(!MyString.isEmpty(dto.getId())){
			/**
			 * 判断是否为系统数据，系统数据不允许修改mkey和canDelete字段
			 */
			Article dbArticle = articleService.selectByPrimaryKey(dto.getId());
			if(dbArticle.getCanDelete()!=1){
				dto.setMkey(null);
				dto.setCanDelete(null);
			}
			// 修改模块
			if(!dto.getModuleId().equals(dbArticle.getModuleId())){
				String dbProjectId = customArticleService.getProjectId(dbArticle.getModuleId());
				hasPermission( projectCache.get(dbProjectId) , dbArticle.getType().equals(ArticleType.ARTICLE.name())? modArticle:modDict);
			}	
			
			hasPermission( projectCache.get(dto.getProjectId()) , dto.getType().equals(ArticleType.ARTICLE.name())? modArticle:modDict);

			customArticleService.update(ArticleAdapter.getModel(dto), ArticleType.getByEnumName(dto.getType()), "");
			luceneService.update(ArticleAdapter.getSearchDto(ArticleAdapter.getModel(dto)));
		}else{
			hasPermission( projectCache.get(dto.getProjectId()) , dto.getType().equals(ArticleType.ARTICLE.name())? addArticle:addDict);
			articleService.insert(ArticleAdapter.getModel(dto));
			luceneService.add(ArticleAdapter.getSearchDto(ArticleAdapter.getModel(dto)));
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
			Article model = articleService.selectByPrimaryKey(tempId);
			hasPermission( projectCache.get(customArticleService.getProjectId(model.getModuleId())) , model.getType().equals(ArticleType.ARTICLE.name())? delArticle:delDict);
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
	public JsonResult changeSequence(@RequestParam String id,@RequestParam String changeId) throws MyException {
		Article change = articleService.selectByPrimaryKey(changeId);
		Article model = articleService.selectByPrimaryKey(id);
		// TODO
		//hasPermission( projectCache.get(change.getProjectId()), change.getType().equals(ArticleType.ARTICLE.name())? modArticle:modDict);
		//hasPermission( projectCache.get(model.getProjectId()), model.getType().equals(ArticleType.ARTICLE.name())? modArticle:modDict);
		
		int modelSequence = model.getSequence();
		
		model.setSequence(change.getSequence());
		change.setSequence(modelSequence);

		// TODO
		//articleService.update(model);
		//articleService.update(change);
		return new JsonResult(1, null);
	}
	
	@RequestMapping("/dictionary/importFromSql.do")
	@ResponseBody
	@AuthPassport
	public JsonResult importFromSql(@RequestParam String sql, @RequestParam(defaultValue="") String brief, @RequestParam String moduleId, String name,
			@RequestParam(defaultValue="") boolean isMysql) throws MyException {
//		Article article = null;
//		if(isMysql){
//			article = SqlToDictionaryUtil.mysqlToDictionary(sql, brief, moduleId, name);
//		}else{
//			article = SqlToDictionaryUtil.sqlserviceToDictionary(sql, brief, moduleId, name);
//		}
//		articleService.save(article);
		return new JsonResult(1, new Article());
	}

	@RequestMapping("/markdown.do")
	public String markdown(@ModelAttribute Article webPage) throws Exception {
		Article model;
//		if(!webPage.getId().equals(Const.NULL_ID)){
//			model= articleService.get(webPage.getId());
//		}else{
//			model=new Article();
//			model.setType(webPage.getType());
//			model.setModuleId(webPage.getModuleId());
//		}
//		request.setAttribute("markdownPreview", model.getContent());
//		request.setAttribute("markdownText", model.getMarkdown());
		return "/WEB-INF/views/markdown.jsp";
	}
}
