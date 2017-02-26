package cn.crap.inter.service.table;

import cn.crap.framework.MyException;
import cn.crap.framework.base.IBaseService;
import cn.crap.model.Log;

public interface ILogService extends IBaseService<Log>{

	void recover(Log log) throws MyException;

}
