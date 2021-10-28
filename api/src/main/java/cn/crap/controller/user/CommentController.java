package cn.crap.controller.user;

import cn.crap.adapter.CommentAdapter;
import cn.crap.dto.CommentDTO;
import cn.crap.enu.CommentType;
import cn.crap.enu.MyError;
import cn.crap.enu.ProjectPermissionEnum;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.framework.interceptor.AuthPassport;
import cn.crap.model.CommentPO;
import cn.crap.model.UserPO;
import cn.crap.query.CommentQuery;
import cn.crap.service.ArticleService;
import cn.crap.service.BugService;
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
	@Autowired
    private BugService bugService;
	// TODO articleId 修改为 targetId

	@RequestMapping("/list.do")
	@ResponseBody
	@AuthPassport
	public JsonResult list(@ModelAttribute CommentQuery query) throws MyException {
		Assert.notNull(query.getTargetId());
		Assert.notNull(query.getType());

        String projectId;
        if (query.getType().equals(CommentType.ARTICLE.getType())){
            projectId = articleService.getById(query.getTargetId()).getProjectId();
        } else {
            projectId = bugService.get(query.getTargetId()).getProjectId();
        }

		checkPermission(projectId, ProjectPermissionEnum.READ);
		query.setPageSize(10);
		query.setSort(TableField.SORT.SEQUENCE_DESC);


		List<CommentPO> commentList = commentService.select(query);
		Page page = new Page(query);
		page.setAllRow(commentService.count(query));

		return new JsonResult(1, CommentAdapter.getDto(commentList), page);
	}

	@RequestMapping("/detail.do")
	@ResponseBody
	@AuthPassport
	public JsonResult detail(String id) throws MyException {
		CommentPO dbComment = commentService.get(id);

        String projectId;
        if (dbComment.getType().equals(CommentType.ARTICLE.getType())){
            projectId = articleService.getById(dbComment.getTargetId()).getProjectId();
        } else {
            projectId = bugService.get(dbComment.getTargetId()).getProjectId();
        }
        checkPermission(projectId, ProjectPermissionEnum.READ);
		return new JsonResult().data(CommentAdapter.getDto(dbComment));
	}
	
	@RequestMapping("/addOrUpdate.do")
	@ResponseBody
	@AuthPassport
	public JsonResult addOrUpdate(@ModelAttribute CommentDTO commentDto) throws MyException {
        String projectId;
        if (commentDto.getType().equals(CommentType.ARTICLE.getType())){
            projectId = articleService.getById(commentDto.getTargetId()).getProjectId();
        } else {
            projectId = bugService.get(commentDto.getTargetId()).getProjectId();
        }
        checkPermission(projectId, ProjectPermissionEnum.READ);

		CommentPO comment = CommentAdapter.getModel(commentDto);
		commentService.update(comment);

		// 发送邮件通知
		CommentPO dbComment = commentService.get(commentDto.getId());
		if (MyString.isNotEmpty(dbComment.getUserId())){
			UserPO user = userService.get(dbComment.getUserId());
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
	public JsonResult delete(String id, String ids) throws MyException{
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
            String projectId;
            if (comment.getType().equals(CommentType.ARTICLE.getType())){
                projectId = articleService.getById(comment.getTargetId()).getProjectId();
            } else {
                projectId = bugService.get(comment.getTargetId()).getProjectId();
            }
            checkPermission(projectId, ProjectPermissionEnum.READ);
			commentService.delete(tempId);
		}
		return new JsonResult(1, null);
	}
}
