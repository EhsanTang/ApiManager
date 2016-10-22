package cn.crap.service.table;


import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.crap.framework.base.BaseService;
import cn.crap.framework.base.IBaseDao;
import cn.crap.inter.dao.IRoleDao;
import cn.crap.inter.service.table.IRoleService;
import cn.crap.model.Role;

@Service
public class RoleService extends BaseService<Role>
		implements IRoleService {
	
	@Autowired
	private IRoleDao roleDao;
	
	@Resource(name="roleDao")
	public void setDao(IBaseDao<Role> dao) {
		super.setDao(dao);
	}
	
	
	@Override
	@Transactional
	public Role get(String id){
		Role model = roleDao.get(id);
		if(model == null)
			 return new Role();
		return model;
	}
}
