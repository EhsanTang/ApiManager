package cn.crap.controller.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.crap.dto.LoginInfoDto;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.table.ICommentService;
import cn.crap.inter.service.tool.ICacheService;
import cn.crap.model.Comment;
import cn.crap.utils.Const;
import cn.crap.utils.DateFormartUtil;
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
		if(user != null)
			comment.setUserId(user.getId());
		
		comment.setId(null);
		comment.setUpdateTime(DateFormartUtil.getDateByFormat(DateFormartUtil.YYYY_MM_DD_HH_mm_ss));
		commentService.save(comment);
		
		cacheService.delObj( Const.CACHE_COMMENTLIST + comment.getArticleId());
		cacheService.delObj( Const.CACHE_COMMENT_PAGE + comment.getArticleId());
		
		return new JsonResult(1, null);
	}
}
