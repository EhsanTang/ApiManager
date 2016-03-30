package cn.crap.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.crap.framework.base.IBaseDao;
import cn.crap.framework.base.BaseService;
import cn.crap.inter.service.IInterfaceService;
import cn.crap.model.Interface;

@Service
public class InterfaceService extends BaseService<Interface>
		implements IInterfaceService {

	@Resource(name="interfaceDao")
	public void setDao(IBaseDao<Interface> dao) {
		super.setDao(dao);
	}


}
