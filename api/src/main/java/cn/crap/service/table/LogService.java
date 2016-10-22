package cn.crap.service.table;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.crap.framework.base.BaseService;
import cn.crap.framework.base.IBaseDao;
import cn.crap.inter.dao.IInterfaceDao;
import cn.crap.inter.dao.ILogDao;
import cn.crap.inter.service.table.ILogService;
import cn.crap.model.Interface;
import cn.crap.model.Log;
import net.sf.json.JSONObject;

@Service
public class LogService extends BaseService<Log>
		implements ILogService {
	@Autowired
	private IInterfaceDao interfaceDao;
	@Autowired
	private ILogDao logDao;
	
	@Resource(name="logDao")
	public void setDao(IBaseDao<Log> dao) {
		super.setDao(dao);
	}

	@Override
	@Transactional
	public Log get(String id){
		Log model = logDao.get(id);
		if(model == null)
			 return new Log();
		return model;
	}
	
	
	@Override
	@Transactional
	public void recover(Log log){
		log = get(log.getId());
		if(log.getModelClass().equals("Interface")){//恢复接口
			JSONObject json = JSONObject.fromObject(log.getContent());
			Interface inter = (Interface) JSONObject.toBean(json,Interface.class);
			// 删除旧数据，在插入新数据
			Interface oldInter = interfaceDao.get(inter.getId());
			if( oldInter!= null){
				interfaceDao.delete(oldInter);
				interfaceDao.gethibernateTemplate().getSessionFactory().getCurrentSession().flush();
			}
			interfaceDao.save(inter);
		}
	}
}
