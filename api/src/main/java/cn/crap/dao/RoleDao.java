package cn.crap.dao;

import org.springframework.stereotype.Repository;

import cn.crap.framework.base.GenericDaoImpl;
import cn.crap.inter.IRoleDao;
import cn.crap.model.Role;

@Repository("roleDao")
public class RoleDao extends GenericDaoImpl<Role, String>
		implements IRoleDao {

}