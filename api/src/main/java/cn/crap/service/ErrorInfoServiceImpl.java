package cn.crap.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.crap.framework.base.GenericDao;
import cn.crap.framework.base.GenericServiceImpl;
import cn.crap.inter.ErrorInfoService;
import cn.crap.model.ErrorInfoModel;

@Service
public class ErrorInfoServiceImpl extends GenericServiceImpl<ErrorInfoModel, String>
		implements ErrorInfoService {

	@Resource(name="errorInfoDao")
	public void setDao(GenericDao<ErrorInfoModel, String> dao) {
		super.setDao(dao);
	}
}
