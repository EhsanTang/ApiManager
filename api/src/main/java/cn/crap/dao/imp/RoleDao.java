package cn.crap.dao.imp;

import cn.crap.dao.IRoleDao;
import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.model.Role;

@Repository("roleDao")
public class RoleDao extends BaseDao<Role>
		implements IRoleDao {

}