package cn.crap.inter.service;

import cn.crap.enumeration.DataType;
import cn.crap.framework.base.IBaseService;
import cn.crap.model.Role;

public interface IRoleService extends IBaseService<Role>{

	void getAuthFromAuth(StringBuilder sb, String pauth);

	void getSubAuth(DataType dataType, StringBuilder sb, String parentId);
}
