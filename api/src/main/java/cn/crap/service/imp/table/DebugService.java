package cn.crap.service.imp.table;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.crap.framework.base.BaseService;
import cn.crap.framework.base.IBaseDao;
import cn.crap.dao.IDebugDao;
import cn.crap.service.IDebugService;
import cn.crap.model.Debug;

@Service
public class DebugService extends BaseService<Debug>
		implements IDebugService {
	@Resource(name="debugDao")
	IDebugDao debugDao;

	@Resource(name="debugDao")
	public void setDao(IBaseDao<Debug> dao) {
		super.setDao(dao);
	}
	
	
}
