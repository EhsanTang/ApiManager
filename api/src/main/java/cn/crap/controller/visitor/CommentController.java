package cn.crap.controller.visitor;

import cn.crap.adapter.CommentAdapter;
import cn.crap.dto.CommentDTO;
import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.SettingDto;
import cn.crap.enu.MyError;
import cn.crap.enu.ProjectPermissionEnum;
import cn.crap.framework.JsonResult;
import cn.crap.framework.MyException;
import cn.crap.framework.base.BaseController;
import cn.crap.model.BugPO;
import cn.crap.model.CommentPO;
import cn.crap.query.CommentQuery;
import cn.crap.service.BugService;
import cn.crap.service.CommentService;
import cn.crap.utils.LoginUserHelper;
import cn.crap.utils.Page;
import cn.crap.utils.TableField;
import cn.crap.utils.Tools;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller("visitorCommentController")
@RequestMapping("/comment")
public class CommentController extends BaseController {
	@Autowired
	private CommentService commentService;
	@Autowired
    private BugService bugService;

    // TODO 删除bug需要删除所有评论，bug暂不允许删除
	@RequestMapping("/add.do")
	@ResponseBody
	public JsonResult addOrUpdate(@ModelAttribute CommentDTO commentDTO) throws Exception {
		Assert.notNull(commentDTO.getTargetId(), "targetId 不能为空");
        Assert.notNull(commentDTO.getType(), "type 不能为空");
        Assert.notNull(commentDTO.getContent(), "评论不能为空");
        Assert.isTrue(commentDTO.getContent().length() > 5, "评论长度必须大于5");

		LoginInfoDto user = LoginUserHelper.tryGetUser();
		// 图形验证码校验，防止恶意评论
		if (user == null && settingCache.get(S_COMMENTCODE).getValue().equals("true")) {
			if (commentDTO.getImgCode() == null || !commentDTO.getImgCode().equals(Tools.getImgCode())) {
				throw new MyException(MyError.E000010);
			}
		}

		SettingDto anonymousComment = settingCache.get(S_ANONYMOUS_COMMENT);
		if (anonymousComment != null && !C_TRUE.equals(anonymousComment.getValue())){
            Optional.ofNullable(user).orElseThrow(()-> new MyException(MyError.E000060));
		}

		// bug管理系统
		if (commentDTO.getType().equals(C_BUG)){
            BugPO bugPO = bugService.get(commentDTO.getTargetId());
            checkPermission(bugPO.getProjectId(), ProjectPermissionEnum.ADD_BUG);
        }

		CommentPO commentPO = CommentAdapter.getModel(commentDTO);
		commentService.insert(commentPO);
		return new JsonResult(1, null);
	}

    @RequestMapping("/preAdd.do")
    @ResponseBody
    public JsonResult detail(@ModelAttribute CommentDTO commentDTO) throws Exception {
        Assert.notNull(commentDTO.getType(), "type 不能为空");
        SettingDto anonymousComment = settingCache.get(S_ANONYMOUS_COMMENT);
        commentDTO.setNeedImgCode(false);
        if (commentDTO.getType().equals(C_BUG)){
            commentDTO.setNeedImgCode(false);
        }

        if (anonymousComment != null && !C_TRUE.equals(anonymousComment.getValue())){
            commentDTO.setNeedImgCode(true);
        }

        if (settingCache.get(S_COMMENTCODE).getValue().equals("true")) {
            commentDTO.setNeedImgCode(true);
        }

        LoginInfoDto user = LoginUserHelper.tryGetUser();
        if (user != null) {
            commentDTO.setNeedImgCode(false);
        }

        return new JsonResult(1, commentDTO);
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public JsonResult list(@ModelAttribute CommentQuery query) throws Exception {
        if (query.getTargetId() == null){
            return new JsonResult().data(Lists.newArrayList()).page(new Page(query));
        }
        Assert.notNull(query.getType(), "type 不能为空");
        query.setPageSize(10);
        query.setSort(TableField.SORT.CREATE_TIME_DES);

        List<CommentPO> commentPOList = commentService.select(query);
        Page page = new Page(query);
        page.setAllRow(commentService.count(query));
        return new JsonResult().success().data(CommentAdapter.getDto(commentPOList)).page(page);
    }
}
