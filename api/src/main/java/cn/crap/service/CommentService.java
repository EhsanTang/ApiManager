package cn.crap.service;

import cn.crap.dao.mybatis.CommentDao;
import cn.crap.dto.LoginInfoDto;
import cn.crap.enu.BugStatus;
import cn.crap.enu.TableId;
import cn.crap.framework.MyException;
import cn.crap.model.CommentPO;
import cn.crap.query.CommentQuery;
import cn.crap.utils.IConst;
import cn.crap.utils.LoginUserHelper;
import cn.crap.utils.Tools;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Optional;


/**
 * @Auth crap.cn
 */
@Service
public class CommentService extends NewBaseService<CommentPO, CommentQuery> implements IConst {
    private CommentDao commentDao;

    @Resource
    public void CommentDao(CommentDao commentDao) {
        this.commentDao = commentDao;
        super.setBaseDao(commentDao, TableId.COMMENT);
    }

    @Override
    public boolean insert(CommentPO commentPO) throws MyException {
        Assert.notNull(commentPO, "commentPO 不能为空");
        Assert.notNull(commentPO.getTargetId(), "targetId 不能为空");
        Assert.notNull(commentPO.getType(), "type 不能为空");

        commentPO.setId(null);
        commentPO.setUserName("匿名");
        commentPO.setAvatarUrl(Tools.getAvatar());
        LoginInfoDto user = LoginUserHelper.tryGetUser();
        Optional.ofNullable(user).ifPresent(u -> {
            commentPO.setUserId(u.getId());
            commentPO.setAvatarUrl(u.getAvatarUrl());
            // bug
            if (commentPO.getType().equals(C_BUG)){
                commentPO.setUserName(LoginUserHelper.getName(u));
            } else {
                commentPO.setUserName(LoginUserHelper.getSecretName(u));
            }
        });

        commentPO.setStatus(BugStatus.NEW.getByteValue());
        commentPO.setReply("");
        return super.insert(commentPO);
    }
}
