package cn.crap.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.crap.framework.base.IBaseDao;
import cn.crap.framework.base.BaseService;
import cn.crap.inter.service.ILogService;
import cn.crap.model.Log;

@Service
public class LogService extends BaseService<Log>
		implements ILogService {

	@Resource(name="logDao")
	public void setDao(IBaseDao<Log> dao) {
		super.setDao(dao, new Log());
	}
}
