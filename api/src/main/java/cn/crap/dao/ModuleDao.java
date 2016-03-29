package cn.crap.dao;

import org.springframework.stereotype.Repository;

import cn.crap.framework.base.GenericDaoImpl;
import cn.crap.inter.IModuleDao;
import cn.crap.model.Module;

@Repository("moduleDao")
public class ModuleDao extends GenericDaoImpl<Module, String>
		implements IModuleDao {

}