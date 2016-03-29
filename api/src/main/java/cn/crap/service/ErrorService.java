package cn.crap.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.crap.framework.base.GenericDao;
import cn.crap.framework.base.GenericServiceImpl;
import cn.crap.inter.IErrorService;
import cn.crap.model.Error;

@Service
public class ErrorService extends GenericServiceImpl<Error, String>
		implements IErrorService {

	@Resource(name="errorDao")
	public void setDao(GenericDao<Error, String> dao) {
		super.setDao(dao);
	}
}
