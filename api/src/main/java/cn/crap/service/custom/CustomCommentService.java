package cn.crap.service.custom;

import cn.crap.dao.mybatis.CommentDao;
import cn.crap.model.mybatis.Comment;
import cn.crap.model.mybatis.CommentCriteria;
import cn.crap.utils.Page;
import cn.crap.utils.TableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;


/**
 * @Auth crap.cn
 */
@Service
public class CustomCommentService {
    @Autowired
    private CommentDao mapper;

    /**
     * @param articleId must not be null
     * @param orderBy
     * @param page
     * @return
     */
    public List<Comment> selectByArticelId(String articleId, String orderBy, Page page) {
        Assert.notNull(articleId);

        CommentCriteria example = new CommentCriteria();
        example.createCriteria().andArticleIdEqualTo(articleId);

        if(page != null){
            example.setLimitStart(page.getStart());
            example.setMaxResults(page.getSize());
        }

        if (orderBy != null){
            example.setOrderByClause(orderBy);
        }else{
            example.setOrderByClause(TableField.SORT.CREATE_TIME_DES);
        }

        return mapper.selectByExample(example);
    }

    /**
     * @param articleId must not be null
     * @return
     */
    public int countByArticleId(String articleId) {
        Assert.notNull(articleId);

        CommentCriteria example = new CommentCriteria();
        example.createCriteria().andArticleIdEqualTo(articleId);
        return mapper.countByExample(example);
    }



}
