package cn.crap.framework.base;
import java.util.List;
import java.util.Map;

import cn.crap.utils.Page;

public interface IBaseDao<T extends BaseModel> {
	public abstract T save(T t);
	public abstract List<T> saveAll(List<T> list);
	public abstract void deleteByPK(String id);
	public abstract void delete(T t);
	public abstract void deleteAll(List<T> list);
	public abstract T get(String id);
	public abstract List<T> findByExample(T t);
	public abstract List<T> loadAll(T t);
	public abstract void update(T t);
	public List<T> findByMap(Map<String, Object> map,
			Page pageBean, String order);
	public int getCount(Map<String, Object> map, String conditions);
	public abstract List<T> findByHql(String hql);
}
