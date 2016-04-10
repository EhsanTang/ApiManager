package cn.crap.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.crap.framework.base.BaseDao;
import cn.crap.inter.dao.IWebPageDao;
import cn.crap.model.WebPage;

@Repository("webPageDao")
public class WebPageDao extends BaseDao<WebPage> implements IWebPageDao {
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<String> getCategory(){
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession()
		.createQuery("select distinct category from WebPage");
		return query.list();
	}
}