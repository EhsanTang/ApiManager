package cn.crap.service.table;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.crap.framework.base.BaseService;
import cn.crap.framework.base.IBaseDao;
import cn.crap.inter.dao.IErrorDao;
import cn.crap.inter.service.table.IErrorService;
import cn.crap.model.Error;

@Service
public class ErrorService extends BaseService<Error>
		implements IErrorService {
	@Resource(name="errorDao")
	IErrorDao errorDao;
	
	@Resource(name="errorDao")
	public void setDao(IBaseDao<Error> dao) {
		super.setDao(dao);
	}
	
	@Override
	@Transactional
	public Error get(String id){
		Error model = errorDao.get(id);
		if(model == null)
			 return new Error();
		return model;
	}
}
