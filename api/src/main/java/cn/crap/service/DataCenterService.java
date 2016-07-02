package cn.crap.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.crap.framework.base.IBaseDao;
import cn.crap.dto.PickDto;
import cn.crap.framework.base.BaseService;
import cn.crap.inter.service.IDataCenterService;
import cn.crap.model.DataCenter;
import cn.crap.utils.MyString;
import cn.crap.utils.Tools;

@Service
public class DataCenterService extends BaseService<DataCenter>
		implements IDataCenterService {

	@Resource(name="dataCenterDao")
	public void setDao(IBaseDao<DataCenter> dao ) {
		super.setDao(dao, new DataCenter());
	}
	@Override
	@Transactional
	public void getDataCenterPick(List<PickDto> picks,String idPre,String parentId,String type, String deep,String value,String suffix){
		if(MyString.isEmpty(type)){
			type = "MODULE";
		}
		PickDto pick = null;
		for (DataCenter m : findByMap(Tools.getMap("parentId",parentId,"type", type), null,null)) {
			if(MyString.isEmpty(value))
				pick = new PickDto(idPre+m.getId(), deep+m.getName());
			else
				pick = new PickDto(idPre+m.getId(), value.replace("moduleId", m.getId()).replace("moduleName", m.getName()),deep+m.getName()+suffix);
			picks.add(pick);
			getDataCenterPick(picks, idPre,m.getId(), type, deep+"- - ",value,suffix);
		}
	}
}
