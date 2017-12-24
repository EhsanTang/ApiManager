package cn.crap.controller.user;

import cn.crap.adapter.CommentAdapter;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.mybatis.Comment;
import cn.crap.model.mybatis.CommentCriteria;
import cn.crap.service.mybatis.imp.MybatisArticleService;
import cn.crap.service.mybatis.imp.MybatisCommentService;
import cn.crap.utils.Const;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user/comment")
public class CommentController extends BaseController {
	@Autowired
	private MybatisCommentService commentService;
	@Autowired
	private MybatisArticleService articleService;
	
	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(String articleId, @RequestParam(defaultValue = "1") Integer currentPage) throws MyException {
		
		hasPermission( projectCache.get(  articleService.selectByPrimaryKey(articleId).getProjectId() ), view);
		Page page= new Page(15, currentPage);

		CommentCriteria example = new CommentCriteria();
		example.createCriteria().andArticleIdEqualTo(articleId);
		example.setOrderByClause(TableField.SORT.CREATE_TIME_DES);
		example.setLimitStart(page.getStart());
		example.setMaxResults(page.getSize());

		page.setAllRow(commentService.countByExample(example));

		List<Comment> commentList = commentService.selectByExample(example);

		return new JsonResult(1, CommentAdapter.getDto(commentList), page);
	}

	@RequestMapping("/detail.do")
	@ResponseBody
	@AuthPassport
	public JsonResult detail(@ModelAttribute Comment comment) throws MyException {
		Comment model;
		if (!comment.getId().equals(Const.NULL_ID)) {
			model = commentService.selectByPrimaryKey(comment.getId());
			hasPermission( projectCache.get(  articleService.selectByPrimaryKey( model.getArticleId() ).getProjectId() ), view);
		} else {
			model = new Comment();
		}
		return new JsonResult(1, model);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport
	public JsonResult addOrUpdate(@ModelAttribute Comment comment) throws MyException {
		hasPermission( projectCache.get(  articleService.selectByPrimaryKey(  comment.getArticleId()  ).getProjectId() ) , modArticle);
		comment.setUpdateTime(new Date());
		commentService.update(comment);
		return new JsonResult(1, null);
	}

	@RequestMapping("/delete.do")
	@ResponseBody
	@AuthPassport
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
			Comment comment = commentService.selectByPrimaryKey(tempId);
			hasPermission( projectCache.get(  articleService.selectByPrimaryKey(  comment.getArticleId()  ).getProjectId() ), delArticle);
			comment = commentService.selectByPrimaryKey(comment.getId());
			commentService.delete(tempId);
		}
		return new JsonResult(1, null);
	}
}
