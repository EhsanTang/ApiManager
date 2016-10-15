package cn.crap.service.tool;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.crap.framework.base.IBaseDao;
import cn.crap.inter.service.table.ICommentService;
import cn.crap.framework.base.BaseService;
import cn.crap.model.Comment;

@Service
public class CommentService extends BaseService<Comment>
		implements ICommentService {

	@Resource(name="commentDao")
	public void setDao(IBaseDao<Comment> dao) {
		super.setDao(dao, new Comment());
	}
}
