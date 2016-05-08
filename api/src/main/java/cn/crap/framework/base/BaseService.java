package cn.crap.framework.base;
import java.util.List;
import java.util.Map;
import cn.crap.utils.DateFormartUtil;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;

public class BaseService<T extends BaseModel> implements IBaseService<T> {
	protected IBaseDao<T> dao;
	//空对象，避免空指针异常（当没有找到数据时，返回一个新的对象，该对象在类实例化时由子类调用setDao注入）
	private T model = null;
	
	
	public void setDao(IBaseDao<T> dao, T model) {
		this.dao = dao;
		this.model = model;
	}

	/**
	 * 保存对象
	 * */
	@Override
	public T save(T model){
		model.setCreateTime(DateFormartUtil.getDateByFormat(DateFormartUtil.YYYY_MM_DD_HH_mm_ss));
		dao.save(model);
		return model;
	}

	@Override
	public void update(T model) {
		dao.update(model);
	}
	
	@Override
	public void update(String hql, Map<String, Object> map) {
		dao.update(hql, map);
	}
	
	/**
	 * 批量保存对象
	 * */
	@Override
	public List<T> saveAll(List<T> list){
		dao.saveAll(list);
		return list;
	}

	/**
	 * 根据示例对象删除
	 * */
	@Override
	public void delete(T model){
		dao.delete(model);
	}

	/**
	 * 根据示例对象集合删除
	 * */
	@Override
	public void deleteAll(List<T> list){
		dao.deleteAll(list);
	}

	/**
	 * 根据主键获取对象
	 * */
	@Override
	public T get(String id){
		T temp = dao.get(id);
		if(temp != null)
			return temp;
		else
			return model;
	}

	/**
	 * 根据示例对象获取对象列表
	 * */
	@Override
	public List<T> findByExample(T model){
		return dao.findByExample(model);
	}

	/**
	 * 根据示例对象获取对象列表
	 * */
	@Override
	public List<T> loadAll(T model){
		return dao.loadAll(model);
	}

	@Override
	public List<T> findByMap(Map<String, Object> map,
			Page page, String order) {
		return dao.findByMap(map, page, order);
	}
	
	@Override
	public int getCount(Map<String, Object> map) {
		String conditions = Tools.getHql(map);
		return dao.getCount(map,conditions);
	}
	
	@Override
	public List<?> queryByHql(String hql, Map<String, Object> map){
		return  dao.queryByHql(hql, map);
	}
}
