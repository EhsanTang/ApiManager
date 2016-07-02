package cn.crap.service;


import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.crap.framework.base.IBaseDao;
import cn.crap.framework.base.BaseService;
import cn.crap.inter.service.IDataCenterService;
import cn.crap.inter.service.IRoleService;
import cn.crap.model.DataCenter;
import cn.crap.model.Role;
import cn.crap.utils.DataType;
import cn.crap.utils.Tools;

@Service
public class RoleService extends BaseService<Role>
		implements IRoleService {
	@Autowired
	private IDataCenterService moduleService;
	@Resource(name="roleDao")
	public void setDao(IBaseDao<Role> dao) {
		super.setDao(dao, new Role());
	}
	@Override
	@Transactional
	public void getAuthFromRole(StringBuilder sb, Role role){
		sb.append(role.getAuth()+",");
		for(String auth:role.getAuth().split(",")){
			if(auth.startsWith(DataType.MODULE.name()+"_")){
				getSubAuth(DataType.MODULE,sb,auth.split("_")[1]);
			}else if(auth.startsWith(DataType.INTERFACE.name()+"_")){
				getSubAuth(DataType.INTERFACE,sb,auth.split("_")[1]);
			}
		}
	}
	@Transactional
	private void getSubAuth(DataType dataType,StringBuilder sb,String parentId){
		for(DataCenter module :moduleService.findByMap(Tools.getMap("parentId",parentId), null, null)){
			sb.append(dataType.name()+"_"+module.getId()+",");
			getSubAuth(dataType,sb,module.getId());
		}
	}
}
