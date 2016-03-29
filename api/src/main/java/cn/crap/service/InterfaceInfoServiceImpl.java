package cn.crap.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.crap.framework.base.GenericDao;
import cn.crap.framework.base.GenericServiceImpl;
import cn.crap.inter.InterfaceInfoService;
import cn.crap.model.InterfaceInfoModel;

@Service
public class InterfaceInfoServiceImpl extends GenericServiceImpl<InterfaceInfoModel, String>
		implements InterfaceInfoService {

	@Resource(name="interfaceInfoDao")
	public void setDao(GenericDao<InterfaceInfoModel, String> dao) {
		super.setDao(dao);
	}


}
