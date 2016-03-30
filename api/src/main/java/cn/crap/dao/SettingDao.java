package cn.crap.dao;

import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.inter.dao.ISettingDao;
import cn.crap.model.Setting;

@Repository("settingDao")
public class SettingDao extends BaseDao<Setting>
		implements ISettingDao {

}