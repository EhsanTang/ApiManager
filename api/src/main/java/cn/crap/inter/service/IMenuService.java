package cn.crap.inter.service;

import java.util.List;

import cn.crap.framework.Pick;
import cn.crap.framework.base.IBaseService;
import cn.crap.model.Menu;

public interface IMenuService extends IBaseService<Menu>{



	String pick(List<Pick> picks, String radio, String code, String key,
			String def);

}
