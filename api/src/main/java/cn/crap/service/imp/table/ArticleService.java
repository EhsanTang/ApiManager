package cn.crap.service.imp.table;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.crap.framework.base.BaseService;
import cn.crap.framework.base.IBaseDao;
import cn.crap.dao.IArticleDao;
import cn.crap.dao.IModuleDao;
import cn.crap.service.IArticleService;
import cn.crap.service.ILuceneService;
import cn.crap.model.Article;
import cn.crap.utils.Tools;

@Service
public class ArticleService extends BaseService<Article>
		implements IArticleService,ILuceneService<Article> {
	@Resource(name="articleDao")
	IArticleDao webPageDao;

	@Autowired
	private IModuleDao moduleDao;
	
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

	@Override
	public String getLuceneType() {
		return "文章&数据字典";
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Article> getAllByProjectId(String projectId) {
		return (List<Article>) webPageDao.queryByHql("from Article where moduleId in (select id  from Module where projectId=:projectId)", Tools.getMap("projectId", projectId));
	}
	
	
	
}
