package cn.crap.framework.base;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.crap.utils.Page;

/**
 * @author lizhiyong
 * 
 */
public interface GenericDao<T extends BaseModel, M extends Serializable> {
	
	public abstract T save(T t);
	public abstract List<T> saveAll(List<T> list);
	public abstract void deleteByPK(M id);
	public abstract void delete(T t);
	public abstract void deleteAll(List<T> list);
	public abstract T get(M m);
	public abstract List<T> findByExample(T t);
	public abstract List<T> loadAll(T t);
	public abstract void update(T t);
	public List<T> findByMap(Map<String, Object> map,
			Page pageBean, String order);
	public int getCount(Map<String, Object> map, String conditions);
	public abstract List<T> findByHql(String hql);
}
