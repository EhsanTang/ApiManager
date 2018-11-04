package cn.crap.controller.visitor;

import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.SettingDto;
import cn.crap.enu.MyError;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.model.Comment;
import cn.crap.service.CommentService;
import cn.crap.utils.LoginUserHelper;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller("visitorCommentController")
@RequestMapping("/visitor/comment")
public class CommentController extends BaseController {
	@Autowired
	private CommentService commentService;

	@RequestMapping("/add.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute Comment comment) throws MyException {
		Assert.notNull(comment.getArticleId(), "articleId 不能为空");
		
		if (settingCache.get(S_COMMENTCODE).getValue().equals("true")) {
			if (!comment.getId().equals(Tools.getImgCode())) {
				throw new MyException(MyError.E000010);
			}
		}

		LoginInfoDto user = LoginUserHelper.tryGetUser();
		SettingDto anonymousComment = settingCache.get(S_ANONYMOUS_COMMENT);
		if (anonymousComment != null && !C_TRUE.equals(anonymousComment.getValue())){
			if (user == null){
				throw new MyException(MyError.E000060);
			}
		}
		
		comment.setUserName("匿名");
		comment.setAvatarUrl(Tools.getAvatar());
		if(user != null){
			comment.setUserId(user.getId());
			if (!MyString.isEmpty(user.getAvatarUrl())){
				comment.setAvatarUrl(user.getAvatarUrl());
			}
			comment.setUserName(user.getUserName());
		}
		
		comment.setId(null);
		comment.setUpdateTime(new Date());
		commentService.insert(comment);
		return new JsonResult(1, null);
	}
}
