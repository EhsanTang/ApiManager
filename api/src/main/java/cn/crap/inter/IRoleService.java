package cn.crap.inter;

import cn.crap.framework.base.GenericService;
import cn.crap.model.Role;

public interface IRoleService extends GenericService<Role, String>{
	void getAuthFromRole(StringBuilder sb, Role role);
}
