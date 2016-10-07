package cn.crap.inter.service;

import java.util.List;

import cn.crap.framework.base.IBaseService;
import cn.crap.model.Project;

public interface IProjectService extends IBaseService<Project>{

	List<String> getProjectIdByUid(String userId);

	List<String> getProjectIdByType(int type);
	
}
