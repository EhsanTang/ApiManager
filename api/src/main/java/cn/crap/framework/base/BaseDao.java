package cn.crap.framework.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;





import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;

/**
 * @author lizhiyong
 * 
 */
@SuppressWarnings("unchecked")
public class BaseDao<T extends BaseModel> extends HibernateDaoSupport implements IBaseDao<T> {
	
	public final HibernateTemplate getHibernateTemplateSuper(){
		return super.getHibernateTemplate();
	}
	@Resource
	public final void setSessionFactorySuper(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	public IBaseDao<T> genericDao;
	
	Class<T> entity;

	String entityName;

	public BaseDao() {
		this.entity = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		this.entityName = entity.getName();
	}

	public T save(T t) {
		getHibernateTemplateSuper().merge(entityName, t);
		return t;
	}

	public List<T> saveAll(List<T> list) {
		for (T t : list) {
			getHibernateTemplateSuper().merge(entityName, t);
		}
		return list;
	}

	public void deleteByPK(String id) {
		getHibernateTemplateSuper().delete(get(id));
	}

	public void delete(T t) {
		getHibernateTemplateSuper().delete(entityName, t);
	}

	public void deleteAll(List<T> list) {
		getHibernateTemplateSuper().deleteAll(list);
	}

	public T get(String m) {
		return (T) getHibernateTemplateSuper().get(entity, m);
	}

	public List<T> findByExample(T t) {
		return getHibernateTemplateSuper().findByExample(entityName, t);
	}

	public List<T> loadAll(T t) {
		return getHibernateTemplateSuper().loadAll(entity);
	}

	public void update(T t) {
		 getHibernateTemplateSuper().update(t);
	}
	
	public int getCount(Map<String, Object> map, String conditions) {
		String hql = "select count(*) from " + entity.getSimpleName() + conditions;
		
		Query query = getHibernateTemplateSuper().getSessionFactory().getCurrentSession()
				.createQuery(hql);
		Tools.setQuery(map, query);
		return Integer.parseInt(query.uniqueResult().toString());
	}

	public List<T> findByMap(Map<String, Object> map,
			Page pageBean, String order) {
		String conditions = Tools.getHql(map);
		String hql = "from "+entity.getSimpleName() + conditions + (MyString.isEmpty(order) ? " order by createTime desc" : " order by " + order);
		Query query = getHibernateTemplateSuper().getSessionFactory().getCurrentSession()
				.createQuery(hql);
		if(pageBean!=null){
			pageBean.setAllRow(getCount(map, conditions));
			if(pageBean.getCurrentPage()>pageBean.getTotalPage())
				pageBean.setCurrentPage(pageBean.getTotalPage());
		}
		Tools.setPage(query, pageBean);
		Tools.setQuery(map, query);
		return query.list();
	}
	public List<T> findByHql(String hql) {
		Query query = getHibernateTemplateSuper().getSessionFactory().getCurrentSession()
				.createQuery(hql);
		return query.list();
	}
}
