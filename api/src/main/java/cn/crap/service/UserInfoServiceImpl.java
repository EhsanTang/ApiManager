package cn.crap.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.crap.framework.base.GenericDao;
import cn.crap.framework.base.GenericServiceImpl;
import cn.crap.inter.UserInfoService;
import cn.crap.model.UserInfoModel;

@Service
public class UserInfoServiceImpl extends GenericServiceImpl<UserInfoModel, String>
		implements UserInfoService {

	@Resource(name="userInfoDao")
	public void setDao(GenericDao<UserInfoModel, String> dao) {
		super.setDao(dao);
	}
}
