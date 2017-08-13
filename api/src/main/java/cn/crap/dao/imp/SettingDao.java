package cn.crap.dao.imp;

import cn.crap.dao.ISettingDao;
import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.model.Setting;

@Repository("settingDao")
public class SettingDao extends BaseDao<Setting>
		implements ISettingDao {

}