package cn.crap.service.table;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.crap.framework.base.BaseService;
import cn.crap.framework.base.IBaseDao;
import cn.crap.inter.dao.IArticleDao;
import cn.crap.inter.service.table.IArticleService;
import cn.crap.inter.service.tool.ILuceneService;
import cn.crap.model.Article;

@Service
public class ArticleService extends BaseService<Article>
		implements IArticleService,ILuceneService<Article> {
	@Resource(name="articleDao")
	IArticleDao webPageDao;

	@Resource(name="articleDao")
	public void setDao(IBaseDao<Article> dao) {
		super.setDao(dao);
	}
	
	@Override
	@Transactional
	public Article get(String id){
		Article model = dao.get(id);
		if(model == null)
			 return new Article();
		return model;
	}

	@Override
	@Transactional
	public List<Article> getAll() {
		return webPageDao.findByMap(null, null, null);
	}
	
	
	
}
