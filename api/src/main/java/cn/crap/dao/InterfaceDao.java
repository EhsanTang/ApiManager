package cn.crap.dao;

import org.springframework.stereotype.Repository;

import cn.crap.framework.base.GenericDaoImpl;
import cn.crap.inter.IInterfaceDao;
import cn.crap.model.Interface;

@Repository("interfaceDao")
public class InterfaceDao extends GenericDaoImpl<Interface, String>
		implements IInterfaceDao {

}