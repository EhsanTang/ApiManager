package cn.crap.controller.visitor;

import cn.crap.adapter.ArticleAdapter;
import cn.crap.adapter.CommentAdapter;
import cn.crap.dto.ArticleDto;
import cn.crap.dto.CrumbDto;
import cn.crap.enumer.ArticleStatus;
import cn.crap.enumer.ArticleType;
import cn.crap.enumer.MyError;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.model.*;
import cn.crap.query.ArticleQuery;
import cn.crap.query.CommentQuery;
import cn.crap.service.ArticleService;
import cn.crap.service.CommentService;
import cn.crap.service.ModuleService;
import cn.crap.utils.MyCrumbDtoList;
import cn.crap.utils.MyHashMap;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * visitor article page
 * @author Ehsan
 */
@Controller("visitorArticleController")
public class ArticleController extends BaseController {
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;


    @RequestMapping("/visitor/article/diclist.do")
    @ResponseBody
    public JsonResult list(@ModelAttribute ArticleQuery query,
                           String password,
                           String visitCode) throws MyException {

        query.setType(ArticleType.DICTIONARY.name());
        Module module = moduleCache.get(query.getModuleId());
        Project project = projectCache.get(module.getProjectId());

        checkFrontPermission(password, visitCode, project);

        Page page = new Page(query);
        List<Article> articles = articleService.query(query);
        page.setAllRow(articleService.count(query));

        Map<String, Object> others = Tools.getMap("crumbs", Tools.getCrumbs(query.getType() + "-" + module.getName(), "void"));

        return new JsonResult().success().data(ArticleAdapter.getDto(articles, module, null)).page(page).others(others);
    }


    @RequestMapping("/visitor/article/list.do")
    @ResponseBody
    public JsonResult articleList(@ModelAttribute ArticleQuery query,
                           String password,
                           String visitCode) throws MyException {
        Page page = new Page(query);
        if (query.getStatus() == null || !query.getStatus().equals(ArticleStatus.RECOMMEND.getStatus())){
            Module module = moduleCache.get(query.getModuleId());
            Project project = projectCache.get(module.getProjectId());

            // 如果是私有项目，必须登录才能访问，公开项目需要查看是否需要密码
            checkFrontPermission(password, visitCode, project);

            List<String> categories = moduleService.queryCategoryByModuleId(module.getId());

            List<Article> articles = articleService.query(query);
            page.setAllRow(articleService.count(query));
            List<ArticleDto> articleDtos = ArticleAdapter.getDto(articles, module, null);

            Map<String, Object> others = MyHashMap.getMap("type", ArticleType.valueOf(query.getType()).getName())
                    .put("category", query.getCategory())
                    .put("categorys", categories)
                    .put("crumbs", Tools.getCrumbs("模块:" + project.getName(), "#/module/list?projectId=" + project.getId(), "文章:" + module.getName(), "void"))
                    .getMap();
            return new JsonResult().success().data(articleDtos).page(page).others(others);
        }

        // 推荐的文章
        List<String> categories = articleService.queryTop10RecommendCategory();
        query.setModuleId(null).setName(null).setProjectId(null);
        List<Article> articles = articleService.query(query);
        List<ArticleDto> articleDtos = ArticleAdapter.getDto(articles, null, null);

        page.setAllRow(articleService.count(query));
        Map<String, Object> others = MyHashMap.getMap("type", ArticleType.valueOf(query.getType()).getName())
                .put("category", query.getCategory())
                .put("categorys", categories)
                .put("crumbs", Tools.getCrumbs( "推荐文章列表", "void"))
                .getMap();

        return new JsonResult().success().data(articleDtos).page(page).others(others);
    }


    @RequestMapping("/visitor/article/detail.do")
    @ResponseBody
    public JsonResult articleDetail(@RequestParam String id,
                                String password, String visitCode,
                                 Integer currentPage) throws MyException {
        Map<String, Object> returnMap = new HashMap<>();
        ArticleWithBLOBs article = null;

        article = articleService.getById(id);
        if (article == null) {
            List<Article> tempArticle = articleService.query(new ArticleQuery().setKey(id).setPageSize(1));
            if (!CollectionUtils.isEmpty(tempArticle)){
                article = articleService.getById(tempArticle.get(0).getId());
            }
        }

        if (article == null) {
            throw new MyException(MyError.E000020);
        }
        id = article.getId();

        Module module = moduleCache.get(article.getModuleId());
        Project project = projectCache.get(module.getProjectId());

        // 如果是私有项目，必须登录才能访问，公开项目需要查看是否需要密码
        checkFrontPermission(password, visitCode, project);

        if (article.getType().equals(ArticleType.DICTIONARY.name())) {
            return new JsonResult().success().data(article).others(returnMap);
        }

        // 初始化前端js评论对象
        Comment comment = new Comment();
        comment.setArticleId(id);
        returnMap.put("comment", comment);

        // 评论
        CommentQuery commentQuery = new CommentQuery().setArticleId(id).setPageSize(10).setCurrentPage(currentPage);
        Page page = new Page(commentQuery);
        List<Comment> comments = commentService.query(commentQuery);
        page.setAllRow(commentService.count(commentQuery));
        returnMap.put("comments", CommentAdapter.getDto(comments));
        returnMap.put("commentCode", settingCache.get(S_COMMENTCODE).getValue());

        // 更新点击量
        articleService.updateClickById(id);

        List<CrumbDto> crumbDtos = MyCrumbDtoList.getList("模块:" + project.getName(), "#/module/list?projectId=" + project.getId())
                .add("文章:" + module.getName(), "#/article/list?projectId=" + project.getId() +"&moduleId=" + module.getId() + "&type=ARTICLE")
                .add(article.getName(), "void")
                .getList();
        returnMap.put("crumbs", crumbDtos);
        return new JsonResult(1, article, page, returnMap);
    }
}
