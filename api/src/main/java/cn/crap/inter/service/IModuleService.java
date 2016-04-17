package cn.crap.inter.service;

import java.util.List;

import cn.crap.dto.PickDto;
import cn.crap.framework.base.IBaseService;
import cn.crap.model.Module;

public interface IModuleService extends IBaseService<Module>{


	void getModulePick(List<PickDto> picks, String idPre, String parentId,
			String deep, String value);

	void getModulePick(List<PickDto> picks, String idPre, String parentId,
			String deep, String value, String suffix);
}
