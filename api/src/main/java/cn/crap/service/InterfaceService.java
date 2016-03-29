package cn.crap.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.crap.framework.base.GenericDao;
import cn.crap.framework.base.GenericServiceImpl;
import cn.crap.inter.IInterfaceService;
import cn.crap.model.Interface;

@Service
public class InterfaceService extends GenericServiceImpl<Interface, String>
		implements IInterfaceService {

	@Resource(name="interfaceDao")
	public void setDao(GenericDao<Interface, String> dao) {
		super.setDao(dao);
	}


}
