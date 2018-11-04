package cn.crap.service;

import cn.crap.dao.mybatis.CommentDao;
import cn.crap.enu.TableId;
import cn.crap.framework.MyException;
import cn.crap.model.Comment;
import cn.crap.model.CommentCriteria;
import cn.crap.query.CommentQuery;
import cn.crap.utils.Page;
import cn.crap.utils.TableField;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;


/**
 * @Auth crap.cn
 */
@Service
public class CommentService extends BaseService<Comment, CommentDao> {
    private CommentDao commentDao;

    @Resource
    public void CommentDao(CommentDao commentDao) {
        this.commentDao = commentDao;
        super.setBaseDao(commentDao, TableId.COMMENT);
    }

    @Override
    public boolean insert(Comment model) throws MyException{
        if (model == null) {
            return false;
        }
        model.setSequence(0);
        return super.insert(model);
    }

    /**
     * 查询评论
     * @param query
     * @return
     * @throws MyException
     */
    public List<Comment> query(CommentQuery query) throws MyException {
        Assert.notNull(query);

        Page page = new Page(query);
        CommentCriteria example = getCommentCriteria(query);
        example.setLimitStart(page.getStart());
        example.setMaxResults(page.getSize());
        example.setOrderByClause(query.getSort() == null ? TableField.SORT.CREATE_TIME_DES : query.getSort());

        return commentDao.selectByExample(example);
    }

    /**
     * 查询评论数量
     * @param query
     * @return
     * @throws MyException
     */
    public int count(CommentQuery query) throws MyException {
        Assert.notNull(query);

        CommentCriteria example = getCommentCriteria(query);
        return commentDao.countByExample(example);
    }

    private CommentCriteria getCommentCriteria(CommentQuery query) throws MyException {
        CommentCriteria example = new CommentCriteria();
        CommentCriteria.Criteria criteria = example.createCriteria();
        if (query.getArticleId() != null) {
            criteria.andArticleIdEqualTo(query.getArticleId());
        }
        return example;
    }

}
