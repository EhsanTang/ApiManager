package cn.crap.dao;

import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.inter.dao.IInterfaceDao;
import cn.crap.model.Interface;

@Repository("interfaceDao")
public class InterfaceDao extends BaseDao<Interface>
		implements IInterfaceDao {

}