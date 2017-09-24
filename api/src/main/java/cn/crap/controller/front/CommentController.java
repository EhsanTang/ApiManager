package cn.crap.controller.front;

import java.util.Date;
import java.util.Random;

import cn.crap.dto.SettingDto;
import cn.crap.service.mybatis.imp.MybatisCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.dto.LoginInfoDto;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.service.ICacheService;
import cn.crap.model.mybatis.Comment;
import cn.crap.model.Setting;
import cn.crap.utils.Const;
import cn.crap.utils.DateFormartUtil;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Controller("frontCommentController")
@RequestMapping("/front/comment")
public class CommentController extends BaseController<cn.crap.model.Comment> {
	@Autowired
	private ICacheService cacheService;

	@Autowired
	private MybatisCommentService commentService;

	@RequestMapping("/add.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute Comment comment) throws MyException {
		if (cacheService.getSetting(Const.SETTING_COMMENTCODE).getValue().equals("true")) {
			if (!comment.getId().equals(Tools.getImgCode(request))) {
				throw new MyException("000010");
			}
		}
		LoginInfoDto user = Tools.getUser();
		SettingDto anonymousComment = cacheService.getSetting(Const.SETTING_ANONYMOUS_COMMENT);
		if (anonymousComment != null && !"true".equals(anonymousComment.getValue())){
			if (user == null){
				throw new MyException("000060");
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
		
		cacheService.delObj( Const.CACHE_COMMENTLIST + comment.getArticleId());
		cacheService.delObj( Const.CACHE_COMMENT_PAGE + comment.getArticleId());
		
		return new JsonResult(1, null);
	}
}
