package cn.crap.dao;

import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.inter.dao.ICommentDao;
import cn.crap.model.Comment;

@Repository("commentDao")
public class CommentDao extends BaseDao<Comment> implements ICommentDao {

}