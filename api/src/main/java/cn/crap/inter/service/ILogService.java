package cn.crap.inter.service;

import cn.crap.framework.base.IBaseService;
import cn.crap.model.Log;

public interface ILogService extends IBaseService<Log>{

	void recover(Log log);

}
