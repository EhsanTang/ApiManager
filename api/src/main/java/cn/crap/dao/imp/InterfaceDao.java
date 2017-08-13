package cn.crap.dao.imp;

import cn.crap.dao.IInterfaceDao;
import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.model.Interface;

@Repository("interfaceDao")
public class InterfaceDao extends BaseDao<Interface>
		implements IInterfaceDao {

}