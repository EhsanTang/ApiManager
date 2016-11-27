package cn.crap.framework.base;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.crap.enumeration.LogType;
import cn.crap.inter.dao.ILogDao;
import cn.crap.model.Log;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;
import net.sf.json.JSONObject;

public class BaseService<T extends BaseModel> implements IBaseService<T> {
	protected IBaseDao<T> dao;
	@Autowired
	private ILogDao logDao;
	
	
	public void setDao(IBaseDao<T> dao) {
		this.dao = dao;
	}
	
	/**
	 * 保存对象
	 * */
	@Override
	@Transactional
	public T save(T model){
		model.setId(null);
		dao.save(model);
		return model;
	}
	
	@Override
	@Transactional
	public void update(T model) {
		dao.update(model);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(T model, String modelName, String remark) {
		T oldModel = dao.get(model.getId());
		if(MyString.isEmpty(remark))
			remark = "修改："+oldModel.getLogRemark();
		Log log = new Log(modelName, remark, LogType.UPDATE.name(), JSONObject.fromObject(oldModel).toString(),
				model.getClass().getSimpleName(), model.getId());
		logDao.save(log);
		dao.update(model);
	}
	
	
	
	@Override
	@Transactional
	public void update(String hql, Map<String, Object> map) {
		dao.update(hql, map);
	}
	

	/**
	 * 删除前，先将对象数据存入log对象
	 * */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(T model, String modelName, String remark){
		model = get(model.getId());
		if(MyString.isEmpty(remark))
			remark = "删除："+model.getLogRemark();
		Log log = new Log(modelName, remark, LogType.DELTET.name(), JSONObject.fromObject(model).toString(),
				model.getClass().getSimpleName(), model.getId());
		logDao.save(log);
		dao.delete(model);
	}
	
	@Override
	@Transactional
	public void delete(T model){
		dao.delete(model);
	}


	/**
	 * 根据主键获取对象
	 * */
	@Override
	@Transactional
	public T get(String id){
		return dao.get(id);
	}

	/**
	 * 根据示例对象获取对象列表
	 * */
	@Override
	@Transactional
	public List<T> findByExample(T model){
		return dao.findByExample(model);
	}

	/**
	 * 根据示例对象获取对象列表
	 * */
	@Override
	@Transactional
	public List<T> loadAll(T model){
		return dao.loadAll(model);
	}

	@Override
	@Transactional
	public List<T> findByMap(Map<String, Object> map,
			Page page, String order) {
		return dao.findByMap(map, page, order);
	}
	
	@Override
	@Transactional
	public List<T> findByMap(Map<String, Object> map, String construct,
			Page page, String order) {
		return dao.findByMap(construct, map, page, order);
	}
	
	@Override
	@Transactional
	public int getCount(Map<String, Object> map) {
		String conditions = Tools.getHql(map);
		return dao.getCount(map,conditions);
	}
	
	@Override
	@Transactional
	public List<T> queryByHql(String hql, Map<String, Object> map){
		return  queryByHql(hql, map, null);
	}
	
	@Override
	@Transactional
	public List<T> queryByHql(String hql, Map<String, Object> map, Page page){
		return  dao.queryByHql(hql, map, page);
	}
	
}
