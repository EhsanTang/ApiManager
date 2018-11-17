package cn.crap.controller.user;

import cn.crap.adapter.CommentAdapter;
import cn.crap.dto.CommentDto;
import cn.crap.enu.MyError;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.ArticleWithBLOBs;
import cn.crap.model.Comment;
import cn.crap.model.User;
import cn.crap.query.CommentQuery;
import cn.crap.service.ArticleService;
import cn.crap.service.CommentService;
import cn.crap.service.UserService;
import cn.crap.service.tool.EmailService;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user/comment")
public class CommentController extends BaseController {
	@Autowired
	private CommentService commentService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private UserService userService;
	@Autowired
	private EmailService emailService;


	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(@ModelAttribute CommentQuery query) throws MyException {
		checkPermission(articleService.getById(query.getArticleId()).getProjectId() , READ);

		Page page= new Page(query);
		page.setAllRow(commentService.count(query));

		List<Comment> commentList = commentService.query(query);
		return new JsonResult(1, CommentAdapter.getDto(commentList), page);
	}

	@RequestMapping("/detail.do")
	@ResponseBody
	@AuthPassport
	public JsonResult detail(String id) throws MyException {
		Comment dbComment = commentService.getById(id);
        ArticleWithBLOBs article = articleService.getById(dbComment.getArticleId());

        checkPermission(article.getProjectId(), READ);
		return new JsonResult().data(dbComment);
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport
	public JsonResult addOrUpdate(@ModelAttribute CommentDto commentDto) throws MyException {
        ArticleWithBLOBs article = articleService.getById(commentDto.getArticleId());
        checkPermission(article.getProjectId(), MOD_ARTICLE);
		Comment comment = CommentAdapter.getModel(commentDto);
		comment.setUpdateTime(new Date());
		commentService.update(comment);

		// 发送邮件通知
		Comment dbComment = commentService.getById(commentDto.getId());
		if (MyString.isNotEmpty(dbComment.getUserId())){
			User user = userService.getById(dbComment.getUserId());
			if (MyString.isNotEmpty(user.getEmail())){
				String context = "问题【" + dbComment.getContent() + "】收到回复，【" + comment.getReply() + "】";
				emailService.sendMail(dbComment.getContent(), user.getEmail(), context);
			}
		}
		return new JsonResult(1, null);
	}

	@RequestMapping("/delete.do")
	@ResponseBody
	@AuthPassport
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
			Comment comment = commentService.getById(tempId);
            ArticleWithBLOBs article = articleService.getById(comment.getArticleId());
            checkPermission(article.getProjectId(), DEL_ARTICLE);
			commentService.delete(tempId);
		}
		return new JsonResult(1, null);
	}
}
