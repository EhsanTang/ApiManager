package cn.crap.service.imp.table;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.crap.framework.base.BaseService;
import cn.crap.framework.base.IBaseDao;
import cn.crap.dao.ISettingDao;
import cn.crap.service.ISettingService;
import cn.crap.model.Setting;

@Service
public class SettingService extends BaseService<Setting>
		implements ISettingService {

	@Autowired
	private ISettingDao settingDao;

	@Resource(name="settingDao")
	public void setDao(IBaseDao<Setting> dao) {
		super.setDao(dao);
	}
	
	@Override
	@Transactional
	public Setting get(String id){
		Setting model = settingDao.get(id);
		if(model == null)
			 return new Setting();
		return model;
	}
}
