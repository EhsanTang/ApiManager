package cn.crap.framework.base;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.orm.hibernate4.HibernateTemplate;

import cn.crap.utils.DateFormartUtil;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;

@SuppressWarnings("unchecked")
public class BaseDao<T extends BaseModel> implements IBaseDao<T> {
	@Resource
	protected HibernateTemplate hibernateTemplate;

	public IBaseDao<T> genericDao;
	
	Class<T> entity;

	String entityName;

	public BaseDao() {
		this.entity = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		this.entityName = entity.getName();
	}

	@Override
	public T save(T t) {
		if(MyString.isEmpty(t.getCreateTime()))
			t.setCreateTime(DateFormartUtil.getDateByFormat(DateFormartUtil.YYYY_MM_DD_HH_mm_ss));
		hibernateTemplate.save(entityName, t);
		return t;
	}


	@Override
	public void delete(T t) {
		hibernateTemplate.delete(entityName, t);
	}

	@Override
	public T get(String m) {
		return (T) hibernateTemplate.get(entity, m);
	}

	@Override
	public List<T> findByExample(T t) {
		return hibernateTemplate.findByExample(entityName, t);
	}

	@Override
	public List<T> loadAll(T t) {
		return hibernateTemplate.loadAll(entity);
	}

	@Override
	public void update(T t) {
		 hibernateTemplate.merge(t);
	}
	
	@Override
	public int update(String hql, Map<String, Object> map) {
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql);  
		Tools.setQuery(map, query);
		return query.executeUpdate();  
	}
	
	@Override
	public List<T> queryByHql(String hql, Map<String, Object> map) {
		return queryByHql(hql, map, null);
	}
	
	@Override
	public List<T> queryByHql(String hql, Map<String, Object> map, Page pageBean) {
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession()
				.createQuery(hql);
		if(pageBean!=null){
			pageBean.setAllRow(  getCount(map, hql.toUpperCase().indexOf(" WHERE ")>0? hql.substring(  hql.toUpperCase().indexOf(" WHERE")  ) : "")  );
			if(pageBean.getCurrentPage()>pageBean.getTotalPage())
				pageBean.setCurrentPage(pageBean.getTotalPage());
		}
		Tools.setPage(query, pageBean);
		Tools.setQuery(map, query);
		return query.list();
	}
	
	@Override
	public int getCount(Map<String, Object> map, String conditions) {
		String hql = "select count(*) from " + entity.getSimpleName() + conditions;
		
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession()
				.createQuery(hql);
		Tools.setQuery(map, query);
		return Integer.parseInt(query.uniqueResult().toString());
	}

	/**
	 * @param constructed: 构造函数 如 new A(aa,bb)
	 */
	@Override
	public List<T> findByMap(String construct,Map<String, Object> map,
			Page pageBean, String order) {
		String conditions = Tools.getHql(map);
		String hql = "select " + construct +" from "+entity.getSimpleName() + conditions + (MyString.isEmpty(order) ? " order by sequence desc, createTime desc" : " order by " + order);
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession()
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
	
	@Override
	public List<T> findByMap(Map<String, Object> map,
			Page pageBean, String order) {
		String conditions = Tools.getHql(map);
		String hql = "from "+entity.getSimpleName() + conditions + (MyString.isEmpty(order) ? " order by sequence desc, createTime desc" : " order by " + order);
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession()
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
	
	@Override
	public HibernateTemplate gethibernateTemplate(){
		return hibernateTemplate;
	}
}
