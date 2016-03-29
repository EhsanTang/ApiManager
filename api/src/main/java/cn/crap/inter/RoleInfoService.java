package cn.crap.inter;

import cn.crap.framework.base.GenericService;
import cn.crap.model.RoleInfoModel;

public interface RoleInfoService extends GenericService<RoleInfoModel, String>{

	void getSubAuth(StringBuilder sb, RoleInfoModel role);
}
