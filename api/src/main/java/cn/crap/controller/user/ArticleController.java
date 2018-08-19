package cn.crap.controller.user;

import cn.crap.adapter.ArticleAdapter;
import cn.crap.dto.ArticleDto;
import cn.crap.dto.SearchDto;
import cn.crap.enumer.*;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.Article;
import cn.crap.model.ArticleWithBLOBs;
import cn.crap.model.Module;
import cn.crap.model.Project;
import cn.crap.query.ArticleQuery;
import cn.crap.query.CommentQuery;
import cn.crap.service.ArticleService;
import cn.crap.service.CommentService;
import cn.crap.service.ISearchService;
import cn.crap.service.tool.ModuleCache;
import cn.crap.service.tool.ProjectCache;
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
// TODO 最高管理员可以将文章置顶，将文章修改为站点页面
@Controller
@RequestMapping("/user/article")
public class ArticleController extends BaseController{
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ISearchService luceneService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private ProjectCache projectCache;
	@Autowired
	private ModuleCache moduleCache;

	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(@ModelAttribute ArticleQuery query) throws MyException{
		Assert.isTrue(MyString.isNotEmpty(query.getModuleId()) || MyString.isNotEmpty(query.getProjectId()), "项目ID & 模块ID不能同时为空");

        Module module = moduleCache.get(query.getModuleId());
        Project project = projectCache.get(module.getProjectId());
        checkUserPermissionByProject(project, VIEW);

		Page page= new Page(query);

		page.setAllRow(articleService.count(query));
		List<Article> models = articleService.query(query);
		List<ArticleDto> dtos = ArticleAdapter.getDto(models, module, project);

		return new JsonResult().success().data(dtos).page(page)
                .others(Tools.getMap("type", ArticleType.getByEnumName(query.getType()), "category", query.getCategory()));
	}
	
	@RequestMapping("/detail.do")
	@ResponseBody
	@AuthPassport
	public JsonResult detail(String id, String type, String moduleId, String projectId) throws MyException{
		Module module = new Module();
		Project project = new Project();
		if (moduleId != null) {
			module = moduleCache.get(moduleId);
			project = projectCache.get(module.getProjectId());
			checkUserPermissionByProject(project, VIEW);
		}else {
            project = projectCache.get(projectId);
        }

		if(id != null){
            ArticleWithBLOBs article =  articleService.getById(id);
            checkUserPermissionByProject(article.getProjectId(), VIEW);
			module = moduleCache.get(article.getModuleId());
			return new JsonResult(1, ArticleAdapter.getDtoWithBLOBs(article, module, project));
		}

        ArticleWithBLOBs model = new ArticleWithBLOBs();
		model.setType(type);
		model.setModuleId(moduleId);
		model.setProjectId(module.getProjectId());
        model.setStatus(ArticleStatus.COMMON.getStatus());
        model.setCanDelete(CanDeleteEnum.CAN.getCanDelete());
		model.setCanComment(CommonEnum.TRUE.getByteValue());
        model.setProjectId(projectId);
		return new JsonResult(1, ArticleAdapter.getDtoWithBLOBs(model, module, project));
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute ArticleDto dto) throws Exception{
	    Assert.notNull(dto.getModuleId());
	    if (ArticleStatus.PAGE.getStatus().equals(dto.getStatus()) && MyString.isEmpty(dto.getMkey())){
            throw new MyException(MyError.E000066);
        }

        Project project = projectCache.get(moduleCache.get(dto.getModuleId()).getProjectId());
        checkUserPermissionByProject(project, dto.getType().equals(ArticleType.ARTICLE.name())? ADD_ARTICLE : ADD_DICT);

		if(!MyString.isEmpty(dto.getId())){
            ArticleWithBLOBs article = ArticleAdapter.getModel(dto);

            // key、status 只有最高管理员 以及 拥有ARTICLE权限的管理员才能修改
            if (!LoginUserHelper.checkAuthPassport(DataType.ARTICLE.name())){
                article.setMkey(null);
				article.setStatus(null);
            }

            // 只有项目拥有者可以修改是否可以删除属性
            if (!LoginUserHelper.isAdminOrProjectOwner(project)){
                article.setCanDelete(null);
            }

			articleService.update(article, ArticleType.getByEnumName(dto.getType()), "");

			ArticleWithBLOBs dbArticle = articleService.getById(dto.getId());
			luceneService.update(ArticleAdapter.getSearchDto(dbArticle));
            return new JsonResult(1,dto);
		}

        ArticleWithBLOBs article = ArticleAdapter.getModel(dto);
        article.setProjectId(project.getId());

		// key、status 只有最高管理员 以及 拥有ARTICLE权限的管理员才能修改
		if (!LoginUserHelper.checkAuthPassport(DataType.ARTICLE.name())){
			article.setMkey(null);
			article.setStatus(null);
		}

        articleService.insert(article);

        luceneService.add(ArticleAdapter.getSearchDto(article));
        return new JsonResult(1, dto);

    }
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(String id, String ids) throws MyException, IOException{
		if( MyString.isEmpty(id) && MyString.isEmpty(ids)){
			throw new MyException(MyError.E000029);
		}
		if( MyString.isEmpty(ids) ){
			ids = id;
		}
		
		for(String tempId : ids.split(",")){
			if(MyString.isEmpty(tempId)){
				continue;
			}
			Article model = articleService.getById(tempId);
			Project project = projectCache.get(model.getProjectId());
			checkUserPermissionByProject(project , model.getType().equals(ArticleType.ARTICLE.name())? DEL_ARTICLE : DEL_DICT);

			if(model.getCanDelete().equals(CanDeleteEnum.CAN_NOT.getCanDelete()) && !LoginUserHelper.isAdminOrProjectOwner(project)){
				throw new MyException(MyError.E000009);
			}

			if (commentService.count(new CommentQuery().setArticleId(model.getId())) > 0){
				throw new MyException(MyError.E000037);
			}

			// 非管理员不能删除PAGE
			if (ArticleStatus.PAGE.getStatus().equals(model.getStatus()) && !LoginUserHelper.isAdmin()){
                throw new MyException(MyError.E000009);
            }

			articleService.delete(tempId, ArticleType.getByEnumName(model.getType()) , "");

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
		Module module = moduleCache.get(moduleId);
		article.setProjectId(module.getProjectId());
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
