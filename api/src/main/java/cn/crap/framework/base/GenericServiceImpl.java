package cn.crap.framework.base;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.crap.utils.Page;
import cn.crap.utils.Tools;

/**
 * @author lizhiyong
 * 
 */
public class GenericServiceImpl<T extends BaseModel, M extends Serializable> implements GenericService<T, M> {
	private GenericDao<T,M> dao;

	@Resource
	public void setDao(GenericDao<T,M> dao) {
		this.dao = dao;
	}

	/**
	 * 保存对象
	 * */
	public T save(T model){
		dao.save(model);
		return model;
	}

	public void update(T model) {
		dao.update(model);
	}
	/**
	 * 批量保存对象
	 * */
	public List<T> saveAll(List<T> list){
		dao.saveAll(list);
		return list;
	}

	/**
	 * 根据主键删除
	 * */
	public void deleteByPK(M id){
		dao.deleteByPK(id);
	}

	/**
	 * 根据示例对象删除
	 * */
	public void delete(T model){
		dao.delete(model);
	}

	/**
	 * 根据示例对象集合删除
	 * */
	public void deleteAll(List<T> list){
		dao.deleteAll(list);
	}

	/**
	 * 根据主键获取对象
	 * */
	public T get(M id){
		return dao.get(id);
	}

	/**
	 * 根据示例对象获取对象列表
	 * */
	public List<T> findByExample(T model){
		return dao.findByExample(model);
	}

	/**
	 * 根据示例对象获取对象列表
	 * */
	public List<T> loadAll(T model){
		return dao.loadAll(model);
	}

	public List<T> findByMap(Map<String, Object> map,
			Page pageBean, String order) {
		return dao.findByMap(map, pageBean, order);
	}
	
	public List<T> findByHql(String Hql) {
		return dao.findByHql(Hql);
	}
	
	public int getCount(Map<String, Object> map) {
		String conditions = Tools.getHql(map);
		return dao.getCount(map,conditions);
	}

}
