package cn.crap.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.crap.framework.base.IBaseDao;
import cn.crap.framework.base.BaseService;
import cn.crap.inter.service.ISettingService;
import cn.crap.model.Setting;

@Service
public class SettingService extends BaseService<Setting>
		implements ISettingService {

	@Resource(name="settingDao")
	public void setDao(IBaseDao<Setting> dao) {
		super.setDao(dao, new Setting());
	}
}
