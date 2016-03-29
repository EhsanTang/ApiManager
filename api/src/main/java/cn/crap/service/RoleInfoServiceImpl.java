package cn.crap.service;


import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crap.framework.base.GenericDao;
import cn.crap.framework.base.GenericServiceImpl;
import cn.crap.inter.ModuleInfoService;
import cn.crap.inter.RoleInfoService;
import cn.crap.model.ModuleInfoModel;
import cn.crap.model.RoleInfoModel;
import cn.crap.utils.DataType;
import cn.crap.utils.Tools;

@Service
public class RoleInfoServiceImpl extends GenericServiceImpl<RoleInfoModel, String>
		implements RoleInfoService {
	@Autowired
	private ModuleInfoService moduleInfoService;
	@Resource(name="roleInfoDao")
	public void setDao(GenericDao<RoleInfoModel, String> dao) {
		super.setDao(dao);
	}
	@Override
	public void getSubAuth(StringBuilder sb, RoleInfoModel role){
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
		for(ModuleInfoModel module :moduleInfoService.findByMap(Tools.getMap("parentId",parentId), null, null)){
			sb.append(dataType.name()+"_"+module.getModuleId()+",");
			getSubAuth(dataType,sb,module.getModuleId());
		}
	}
}
