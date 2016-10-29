package cn.crap.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.table.IArticleService;
import cn.crap.inter.service.table.ICommentService;
import cn.crap.model.Comment;
import cn.crap.utils.Const;
import cn.crap.utils.DateFormartUtil;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;

@Controller
@RequestMapping("/user/comment")
public class CommentController extends BaseController<Comment> {
	@Autowired
	private ICommentService commentService;
	@Autowired
	private IArticleService articleService;
	
	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(@ModelAttribute Comment comment, @RequestParam(defaultValue = "1") Integer currentPage) throws MyException {
		
		hasPermission( cacheService.getProject(  articleService.get(  comment.getArticleId()  ).getProjectId() ), view);
		Page page= new Page(15);
		page.setCurrentPage(currentPage);
		return new JsonResult(1, commentService.findByMap(Tools.getMap("articleId", comment.getArticleId()), page, " createTime desc"), page);
	}

	@RequestMapping("/detail.do")
	@ResponseBody
	@AuthPassport
	public JsonResult detail(@ModelAttribute Comment comment) throws MyException {
		Comment model;
		if (!comment.getId().equals(Const.NULL_ID)) {
			model = commentService.get(comment.getId());
			hasPermission( cacheService.getProject(  articleService.get( model.getArticleId() ).getProjectId() ), view);
		} else {
			model = new Comment();
		}
		return new JsonResult(1, model);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport
	public JsonResult addOrUpdate(@ModelAttribute Comment comment) throws MyException {
		hasPermission( cacheService.getProject(  articleService.get(  comment.getArticleId()  ).getProjectId() ) , modArticle);
		comment.setUpdateTime(DateFormartUtil.getDateByFormat(DateFormartUtil.YYYY_MM_DD_HH_mm_ss));
		commentService.update(comment);
		return new JsonResult(1, null);
	}

	@RequestMapping("/delete.do")
	@ResponseBody
	@AuthPassport
	public JsonResult delete(@ModelAttribute Comment comment) throws MyException {
		comment = commentService.get(comment.getId());
		hasPermission( cacheService.getProject(  articleService.get(  comment.getArticleId()  ).getProjectId() ), delArticle);
		comment = commentService.get(comment.getId());
		commentService.delete(comment);
		return new JsonResult(1, null);
	}
}
