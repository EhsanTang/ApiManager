package cn.crap.inter;

import java.util.List;

import cn.crap.framework.Pick;
import cn.crap.framework.base.GenericService;
import cn.crap.model.Module;

public interface IModuleService extends GenericService<Module, String>{


	void getModulePick(List<Pick> picks, String idPre, String parentId,
			String deep, String value);

	void getModulePick(List<Pick> picks, String idPre, String parentId,
			String deep, String value, String suffix);
}
