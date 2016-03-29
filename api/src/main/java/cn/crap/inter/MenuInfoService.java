package cn.crap.inter;

import java.util.List;

import cn.crap.framework.Pick;
import cn.crap.framework.base.GenericService;
import cn.crap.model.MenuInfoModel;

public interface MenuInfoService extends GenericService<MenuInfoModel, String>{


	void pick(List<Pick> picks, String radio, String code, String key);

}
