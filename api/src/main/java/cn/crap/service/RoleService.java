package cn.crap.service;


import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crap.framework.base.GenericDao;
import cn.crap.framework.base.GenericServiceImpl;
import cn.crap.inter.IModuleService;
import cn.crap.inter.IRoleService;
import cn.crap.model.Module;
import cn.crap.model.Role;
import cn.crap.utils.DataType;
import cn.crap.utils.Tools;

@Service
public class RoleService extends GenericServiceImpl<Role, String>
		implements IRoleService {
	@Autowired
	private IModuleService moduleService;
	@Resource(name="roleDao")
	public void setDao(GenericDao<Role, String> dao) {
		super.setDao(dao);
	}
	@Override
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
	private void getSubAuth(DataType dataType,StringBuilder sb,String parentId){
		for(Module module :moduleService.findByMap(Tools.getMap("parentId",parentId), null, null)){
			sb.append(dataType.name()+"_"+module.getModuleId()+",");
			getSubAuth(dataType,sb,module.getModuleId());
		}
	}
}
