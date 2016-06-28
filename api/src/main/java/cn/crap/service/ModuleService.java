package cn.crap.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.crap.framework.base.IBaseDao;
import cn.crap.dto.PickDto;
import cn.crap.framework.base.BaseService;
import cn.crap.inter.service.IModuleService;
import cn.crap.model.Module;
import cn.crap.utils.Tools;

@Service
public class ModuleService extends BaseService<Module>
		implements IModuleService {

	@Resource(name="moduleDao")
	public void setDao(IBaseDao<Module> dao ) {
		super.setDao(dao, new Module());
	}
	@Override
	@Transactional
	public void getModulePick(List<PickDto> picks,String idPre,String parentId,String deep,String value,String suffix){
		PickDto pick = null;
		for (Module m : findByMap(Tools.getMap("parentId",parentId), null,null)) {
			if(value==null)
				pick = new PickDto(idPre+m.getId(), deep+m.getModuleName());
			else
				pick = new PickDto(idPre+m.getId(),
						value.replace("moduleId", m.getId()).replace("moduleName", m.getModuleName()),deep+m.getModuleName()+suffix);
			picks.add(pick);
			getModulePick(picks,idPre,m.getId(),deep+"- - - ",value,suffix);
		}
	}
	@Override
	@Transactional
	public void getModulePick(List<PickDto> picks,String idPre,String parentId,String deep,String value){
		getModulePick(picks,idPre,parentId,deep,value,"");
	}
}
