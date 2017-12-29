package cn.crap.controller.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crap.adapter.ArticleAdapter;
import cn.crap.adapter.CommentAdapter;
import cn.crap.dto.ArticleDto;
import cn.crap.model.mybatis.*;
import cn.crap.service.custom.CustomArticleService;
import cn.crap.service.custom.CustomCommentService;
import cn.crap.service.imp.MybatisArticleService;
import cn.crap.service.imp.MybatisCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.enumer.ArticleType;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.model.mybatis.Project;
import cn.crap.springbeans.Config;
import cn.crap.utils.Const;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;

/**
 * front article page
 * @author Ehsan
 */
@Controller("frontArticleController")
public class ArticleController extends BaseController {
    @Autowired
    private MybatisCommentService commentService;
    @Autowired
    private Config config;
    @Autowired
    private CustomArticleService customArticleService;
    @Autowired
    private MybatisArticleService articleService;
    @Autowired
    private CustomCommentService customCommentService;


    @RequestMapping("/front/article/diclist.do")
    @ResponseBody
    public JsonResult list(@RequestParam String moduleId,
                           String name,
                           @RequestParam(defaultValue = "1") Integer currentPage,
                           String password,
                           String visitCode) throws MyException {

        String type = ArticleType.DICTIONARY.name();
        Module module = moduleCache.get(moduleId);
        Project project = projectCache.get(module.getProjectId());

        // private project need login, public project need check password
        isPrivateProject(password, visitCode, project);

        Page page = new Page(15, currentPage);
        List<Article> articles = customArticleService.queryArticle(moduleId, name, type, null, page);
        page.setAllRow(customArticleService.countByProjectId(moduleId, name, type, null));

        Map<String, Object> others = Tools.getMap("crumbs", Tools.getCrumbs(type + "-" + module.getName(), "void"));

        return new JsonResult().success().data(articles).page(page).others(others);
    }


    @SuppressWarnings("unchecked")
    @RequestMapping("/front/article/list.do")
    @ResponseBody
    public JsonResult list(@RequestParam(defaultValue = "1") Integer currentPage,
                           @RequestParam(defaultValue = Const.WEB_MODULE) String moduleId,
                           @RequestParam String type,
                           @RequestParam String category,
                           String password,
                           String visitCode) throws MyException {

        Page page = new Page(15, currentPage);
        if ( ArticleType.ARTICLE.name().equals(type)){
            type = ArticleType.ARTICLE.name();
        }
        Module module = moduleCache.get(moduleId);
        Project project = projectCache.get(module.getProjectId());

        // 如果是私有项目，必须登录才能访问，公开项目需要查看是否需要密码
        isPrivateProject(password, visitCode, project);

        List<String> categories = customArticleService.queryTop20Category(module.getId(), type);
        List<Article> articles = customArticleService.queryArticle(moduleId, null,  type, category, page);
        List<ArticleDto> articleDtos = ArticleAdapter.getDto(articles);

        // TODO others 结构需要改变
        Map<String, Object> others = Tools.getMap("type", ArticleType.valueOf(type).getName(), "category", category, "categorys", categories,
                "crumbs", Tools.getCrumbs(project.getName(), "#/" + project.getId() + "module/list", module.getName() + ":文章列表", "void"));

        return new JsonResult().success().data(articleDtos).page(page).others(others);
    }


    @SuppressWarnings("unchecked")
    @RequestMapping("/front/article/detail.do")
    @ResponseBody
    public JsonResult webDetail(@RequestParam String id,
                                String password, String visitCode,
                                @RequestParam(defaultValue = "1") Integer currentPage) throws MyException {
        Map<String, Object> returnMap = new HashMap<>();
        ArticleWithBLOBs article = null;

        article = articleService.selectByPrimaryKey(id);
        if (article == null) {
            article = customArticleService.selectByKey(id);
        }

        if (article == null) {
            throw new MyException("000020");
        }
        id = article.getId();


        Module module = moduleCache.get(article.getModuleId());
        Project project = projectCache.get(module.getProjectId());

        // 如果是私有项目，必须登录才能访问，公开项目需要查看是否需要密码
        isPrivateProject(password, visitCode, project);

        if (article.getType().equals(ArticleType.DICTIONARY.name())) {
            return new JsonResult().success().data(article).others(returnMap);
        }

        List<String> categories = customArticleService.queryTop20Category(article.getModuleId(), "ARTICLE");
        returnMap.put("categories", categories);
        returnMap.put("category", article.getCategory());

        // 初始化前端js评论对象
        Comment comment = new Comment();
        comment.setArticleId(id);
        returnMap.put("comment", comment);

        // 评论
        Page page = new Page(10, currentPage);
        List<Comment> comments = customCommentService.selectByArticelId(id, null, page);
        page.setAllRow(customCommentService.countByArticleId(id));
        returnMap.put("comments", CommentAdapter.getDto(comments));
        returnMap.put("commentCode", settingCache.get(Const.SETTING_COMMENTCODE).getValue());

        // 更新点击量
        customArticleService.updateClickById(id);
        return new JsonResult(1, article, page, returnMap);
    }
}
