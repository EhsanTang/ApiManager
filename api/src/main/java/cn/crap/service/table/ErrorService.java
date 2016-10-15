package cn.crap.service.table;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.crap.framework.base.IBaseDao;
import cn.crap.inter.service.table.IErrorService;
import cn.crap.framework.base.BaseService;
import cn.crap.model.Error;

@Service
public class ErrorService extends BaseService<Error>
		implements IErrorService {

	@Resource(name="errorDao")
	public void setDao(IBaseDao<Error> dao) {
		super.setDao(dao, new Error());
	}
}
