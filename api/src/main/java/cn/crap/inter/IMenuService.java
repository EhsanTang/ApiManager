package cn.crap.inter;

import java.util.List;

import cn.crap.framework.Pick;
import cn.crap.framework.base.GenericService;
import cn.crap.model.Menu;

public interface IMenuService extends GenericService<Menu, String>{


	void pick(List<Pick> picks, String radio, String code, String key);

}
