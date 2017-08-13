package cn.crap.controller.front;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.dto.LoginInfoDto;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.service.ICommentService;
import cn.crap.service.ICacheService;
import cn.crap.model.Comment;
import cn.crap.model.Setting;
import cn.crap.utils.Const;
import cn.crap.utils.DateFormartUtil;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Controller("frontCommentController")
@RequestMapping("/front/comment")
public class CommentController extends BaseController<Comment> {
	@Autowired
	private ICacheService cacheService;

	@Autowired
	private ICommentService commentService;

	@RequestMapping("/add.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute Comment comment) throws MyException {
		if (cacheService.getSetting(Const.SETTING_COMMENTCODE).getValue().equals("true")) {
			if (!comment.getId().equals(Tools.getImgCode(request))) {
				throw new MyException("000010");
			}
		}
		LoginInfoDto user = Tools.getUser();
		Setting anonymousComment = cacheService.getSetting(Const.SETTING_ANONYMOUS_COMMENT);
		if (anonymousComment != null && !"true".equals(anonymousComment.getValue())){
			if (user == null){
				throw new MyException("000060");
			}
		}
		
		comment.setUserName("匿名");
		Random random = new Random();
		comment.setAvatarUrl("resources/avatar/avatar" + random.nextInt(10) +".jpg");
		if(user != null){
			comment.setUserId(user.getId());
			if (!MyString.isEmpty(user.getAvatarUrl())){
				comment.setAvatarUrl(user.getAvatarUrl());
			}
			comment.setUserName(user.getUserName());
		}
		
		comment.setId(null);
		comment.setUpdateTime(DateFormartUtil.getDateByFormat(DateFormartUtil.YYYY_MM_DD_HH_mm_ss));
		commentService.save(comment);
		
		cacheService.delObj( Const.CACHE_COMMENTLIST + comment.getArticleId());
		cacheService.delObj( Const.CACHE_COMMENT_PAGE + comment.getArticleId());
		
		return new JsonResult(1, null);
	}
}
