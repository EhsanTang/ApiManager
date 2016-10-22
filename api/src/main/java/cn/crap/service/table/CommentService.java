package cn.crap.service.table;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.crap.framework.base.BaseService;
import cn.crap.framework.base.IBaseDao;
import cn.crap.inter.dao.ICommentDao;
import cn.crap.inter.service.table.ICommentService;
import cn.crap.model.Comment;

@Service
public class CommentService extends BaseService<Comment>
		implements ICommentService {
	@Resource(name="commentDao")
	ICommentDao commentDao;
	
	@Resource(name="commentDao")
	public void setDao(IBaseDao<Comment> dao) {
		super.setDao(dao);
	}
	
	@Override
	@Transactional
	public Comment get(String id){
		Comment model = commentDao.get(id);
		if(model == null)
			 return new Comment();
		return model;
	}
}
