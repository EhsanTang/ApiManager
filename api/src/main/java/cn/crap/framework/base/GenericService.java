package cn.crap.framework.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.crap.utils.Page;
/**
 * @author lizhiyong
 * 
 */
public interface GenericService<T extends BaseModel, M extends Serializable> {

	/**
	 * 保存对象
	 * */
	public T save(T model);

	public void update(T model);
	/**
	 * 批量保存对象
	 * */
	public List<T> saveAll(List<T> list);

	/**
	 * 根据主键删除
	 * */
	public void deleteByPK(M id);

	/**
	 * 根据示例对象删除
	 * */
	public void delete(T model);

	/**
	 * 根据示例对象集合删除
	 * */
	public void deleteAll(List<T> list);

	/**
	 * 根据主键获取对象
	 * */
	public T get(M id);

	/**
	 * 根据示例对象获取对象列表
	 * */
	public List<T> findByExample(T model);

	/**
	 * 根据示例对象获取对象列表
	 * */
	public List<T> loadAll(T model);

	public List<T> findByMap(Map<String, Object> map,
			Page pageBean, String order);
	
	public int getCount(Map<String, Object> map);

	public List<T> findByHql(String Hql);

	

}
