package cn.crap.dao;

import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.inter.dao.IModuleDao;
import cn.crap.model.Module;

@Repository("dataCenterDao")
public class ModuleDao extends BaseDao<Module>
		implements IModuleDao {

}