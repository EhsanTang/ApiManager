package cn.crap.controller.user;

import cn.crap.adapter.CommentAdapter;
import cn.crap.dto.CommentDTO;
import cn.crap.enu.MyError;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.CommentPO;
import cn.crap.model.User;
import cn.crap.query.CommentQuery;
import cn.crap.service.ArticleService;
import cn.crap.service.CommentService;
import cn.crap.service.UserService;
import cn.crap.service.tool.EmailService;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
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
	// TODO articleId 修改为 targetId

	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(@ModelAttribute CommentQuery query) throws MyException {
		Assert.notNull(query.getTargetId());
		Assert.notNull(query.getType());

		// TODO 权限校验
		//checkPermission(articleService.getById(query.getTargetId()).getProjectId() , READ);
		query.setPageSize(10);
		query.setSort(TableField.SORT.SEQUENCE_DESC);

		Page page = new Page(query);
		page.setAllRow(commentService.count(query));
		List<CommentPO> commentList = commentService.select(query, page);
		return new JsonResult(1, CommentAdapter.getDto(commentList), page);
	}

	@RequestMapping("/detail.do")
	@ResponseBody
	@AuthPassport
	public JsonResult detail(String id) throws MyException {
		CommentPO dbComment = commentService.get(id);

		// TODO 权限校验
        // ArticleWithBLOBs article = articleService.getById(dbComment.getTargetId());
        // checkPermission(article.getProjectId(), READ);
		return new JsonResult().data(CommentAdapter.getDto(dbComment));
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport
	public JsonResult addOrUpdate(@ModelAttribute CommentDTO commentDto) throws MyException {
//        ArticleWithBLOBs article = articleService.getById(commentDto.getArticleId());
//        checkPermission(article.getProjectId(), MOD_ARTICLE);
		CommentPO comment = CommentAdapter.getModel(commentDto);
		commentService.update(comment);

		// 发送邮件通知
		CommentPO dbComment = commentService.get(commentDto.getId());
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
			CommentPO comment = commentService.get(tempId);
//            ArticleWithBLOBs article = articleService.getById(comment.getTargetId());
//            checkPermission(article.getProjectId(), DEL_ARTICLE);
			commentService.delete(tempId);
		}
		return new JsonResult(1, null);
	}
}
