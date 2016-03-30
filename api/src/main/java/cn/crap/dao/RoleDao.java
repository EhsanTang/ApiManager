package cn.crap.dao;

import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.inter.dao.IRoleDao;
import cn.crap.model.Role;

@Repository("roleDao")
public class RoleDao extends BaseDao<Role>
		implements IRoleDao {

}