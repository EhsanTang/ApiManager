package cn.crap.framework.base;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate4.HibernateTemplate;

import cn.crap.utils.Page;

public interface IBaseDao<T extends BaseModel> {
	public abstract T save(T t);
	public abstract void delete(T t);
	public abstract T get(String id);
	public abstract List<T> findByExample(T t);
	public abstract List<T> loadAll(T t);
	public abstract void update(T t);
	public List<T> findByMap(Map<String, Object> map,
			Page pageBean, String order);
	public int getCount(Map<String, Object> map, String conditions);
	List<?> queryByHql(String hql, Map<String, Object> map);
	int update(String hql, Map<String, Object> map);
	HibernateTemplate gethibernateTemplate();
	List<T> findByMap(String construct, Map<String, Object> map, Page pageBean, String order);
	List<?> queryByHql(String hql, Map<String, Object> map, Page pageBean);
}
