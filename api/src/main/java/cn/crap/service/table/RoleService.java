package cn.crap.service.table;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.crap.framework.base.BaseService;
import cn.crap.framework.base.IBaseDao;
import cn.crap.inter.service.table.IRoleService;
import cn.crap.model.Role;

@Service
public class RoleService extends BaseService<Role>
		implements IRoleService {
	@Resource(name="roleDao")
	public void setDao(IBaseDao<Role> dao) {
		super.setDao(dao, new Role());
	}
}
