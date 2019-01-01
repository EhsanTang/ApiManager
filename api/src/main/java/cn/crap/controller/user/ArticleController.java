package cn.crap.controller.user;

import cn.crap.adapter.ArticleAdapter;
import cn.crap.dto.ArticleDto;
import cn.crap.dto.SearchDto;
import cn.crap.enu.*;
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
		Project project = getProject(query);
		Module module = getModule(query);
        checkPermission(project, READ);

		Page page = new Page(query);
		page.setAllRow(articleService.count(query));
		List<Article> models = articleService.query(query);
		List<ArticleDto> dtos = ArticleAdapter.getDto(models, module, project);

		return new JsonResult().success().data(dtos).page(page)
                .others(Tools.getMap("type", ArticleType.getByEnumName(query.getType()), "category", query.getCategory()));
	}
	
	@RequestMapping("/detail.do")
	@ResponseBody
	@AuthPassport
	public JsonResult detail(String id, @ModelAttribute ArticleQuery query) throws MyException{
		Project project = getProject(query);
		Module module = getModule(query);
		ArticleWithBLOBs article = new ArticleWithBLOBs();
		if (id != null){
			article =  articleService.getById(id);
			project = projectCache.get(article.getProjectId());
			module = moduleCache.get(article.getModuleId());
		}else {
			article.setType(query.getType());
			article.setModuleId(module == null ? null : module.getId());
			article.setStatus(ArticleStatus.COMMON.getStatus());
			article.setCanDelete(CanDeleteEnum.CAN.getCanDelete());
			article.setCanComment(CommonEnum.TRUE.getByteValue());
			article.setProjectId(project.getId());
		}

		checkPermission(project, READ);
		return new JsonResult(1, ArticleAdapter.getDtoWithBLOBs(article, module, project));
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute ArticleDto dto) throws Exception{
	    Assert.notNull(dto.getProjectId(), "projectId can't be null");
        if (ArticleStatus.PAGE.getStatus().equals(dto.getStatus()) && MyString.isEmpty(dto.getMkey())){
            throw new MyException(MyError.E000066);
        }

        String id = dto.getId();
		String newProjectId = getProjectId(dto.getProjectId(), dto.getModuleId());
        Project newProject = projectCache.get(newProjectId);
		dto.setProjectId(newProjectId);

        ArticleWithBLOBs article = ArticleAdapter.getModel(dto);
        // key、status 只有最高管理员 以及 拥有ARTICLE权限的管理员才能修改
        if (!LoginUserHelper.checkAuthPassport(DataType.ARTICLE.name())){
            article.setMkey(null);
            article.setStatus(null);
        }

        // 修改
		if(id != null){
            String oldProjectId = articleService.getById(article.getId()).getProjectId();
            checkPermission(newProjectId, article.getType().equals(ArticleType.ARTICLE.name())? MOD_ARTICLE : MOD_DICT);
            checkPermission(oldProjectId, article.getType().equals(ArticleType.ARTICLE.name())? MOD_ARTICLE : MOD_DICT);

            // 只有项目拥有者可以修改是否可以删除属性
            if (!LoginUserHelper.isAdminOrProjectOwner(newProject)){
                article.setCanDelete(null);
            }

			articleService.update(article, ArticleType.getByEnumName(article.getType()), "");
		} else{
            // 新增
            checkPermission(newProject, article.getType().equals(ArticleType.ARTICLE.name())? ADD_ARTICLE : ADD_DICT);
            articleService.insert(article);
            id = article.getId();
        }

		/**
		 * 更新标：
		 * mark_down：文本编辑器内容
		 */
        if (ArticleType.ARTICLE.name().equals(dto.getType())){
			if (dto.getUseMarkdown() != null && dto.getUseMarkdown()){
				articleService.updateAttribute(id, IAttributeConst.MARK_DOWN, IAttributeConst.TRUE);
			} else {
				articleService.deleteAttribute(id, IAttributeConst.MARK_DOWN);
			}
		}

		luceneService.add(ArticleAdapter.getSearchDto(articleService.getById(id)));
		return new JsonResult(1, article);

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
			checkPermission(project , model.getType().equals(ArticleType.ARTICLE.name())? DEL_ARTICLE : DEL_DICT);

			if(model.getCanDelete().equals(CanDeleteEnum.CAN_NOT.getCanDelete()) && !LoginUserHelper.isAdminOrProjectOwner(project)){
				throw new MyException(MyError.E000009);
			}

			if (commentService.count(new CommentQuery().setArticleId(model.getId())) > 0){
				throw new MyException(MyError.E000037);
			}

			// 非管理员不能删除PAGE
			if (ArticleStatus.PAGE.getStatus().equals(model.getStatus()) && !LoginUserHelper.isSuperAdmin()){
                throw new MyException(MyError.E000009);
            }

			luceneService.delete(new SearchDto(model.getId()));
			articleService.delete(tempId, ArticleType.getByEnumName(model.getType()) , "");
		}
		return new JsonResult(1,null);
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
        checkPermission(projectCache.get(module.getProjectId()), READ);

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
