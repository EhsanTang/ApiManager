package cn.crap.dao.imp;

import cn.crap.dao.IModuleDao;
import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.model.Module;

@Repository("dataCenterDao")
public class ModuleDao extends BaseDao<Module>
		implements IModuleDao {

}