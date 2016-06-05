package cn.crap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.auth.AuthPassport;
import cn.crap.framework.base.BaseController;
import cn.crap.inter.service.ICommentService;
import cn.crap.model.Comment;
import cn.crap.utils.Cache;
import cn.crap.utils.Const;
import cn.crap.utils.Tools;

@Scope("prototype")
@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController<Comment> {

	@Autowired
	private ICommentService commentService;

	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(@ModelAttribute Comment comment, @RequestParam(defaultValue = "1") Integer currentPage) {
		page.setCurrentPage(currentPage);
		return new JsonResult(1, commentService.findByMap(map, page, null), page);
	}

	@RequestMapping("/detail.do")
	@ResponseBody
	public JsonResult detail(@ModelAttribute Comment comment) {
		if (!comment.getId().equals(Const.NULL_ID)) {
			model = commentService.get(comment.getId());
		} else {
			model = new Comment();
		}
		return new JsonResult(1, model);
	}

	@RequestMapping("/add.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute Comment comment) throws MyException {
		if (Cache.getSetting(Const.SETTING_COMMENTCODE).getValue().equals("true")) {
			if (!comment.getId().equals(Tools.getImgCode(request))) {
				throw new MyException("000010");
			}
		}
		comment.setId(null);
		commentService.save(comment);
		return new JsonResult(1, null);
	}

	@RequestMapping("/delete.do")
	@ResponseBody
	@AuthPassport(authority = Const.AUTH_COMMENT)
	public JsonResult delete(@ModelAttribute Comment comment) throws MyException {
		comment = commentService.get(comment.getId());
		commentService.delete(comment);
		return new JsonResult(1, null);
	}

	@Override
	public JsonResult changeSequence(String id, String changeId) {
		return null;
	}

}
