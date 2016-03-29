package cn.crap.dao;

import org.springframework.stereotype.Repository;

import cn.crap.framework.base.GenericDaoImpl;
import cn.crap.inter.ModuleInfoDao;
import cn.crap.model.ModuleInfoModel;

@Repository("moduleInfoDao")
public class ModuleInfoDaoImpl extends GenericDaoImpl<ModuleInfoModel, String>
		implements ModuleInfoDao {

}