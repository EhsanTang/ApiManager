package cn.crap.dao.imp;

import cn.crap.dao.ICommentDao;
import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.model.Comment;

@Repository("commentDao")
public class CommentDao extends BaseDao<Comment> implements ICommentDao {

}