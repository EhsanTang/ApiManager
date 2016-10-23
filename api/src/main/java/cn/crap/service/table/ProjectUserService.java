package cn.crap.service.table;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.crap.framework.base.BaseService;
import cn.crap.framework.base.IBaseDao;
import cn.crap.inter.dao.IProjectUserDao;
import cn.crap.inter.service.table.IProjectUserService;
import cn.crap.model.ProjectUser;

@Service
public class ProjectUserService extends BaseService<ProjectUser>
		implements IProjectUserService{
	@Resource(name="projectUserDao")
	IProjectUserDao projectUserDao;

	@Resource(name="projectUserDao")
	public void setDao(IBaseDao<ProjectUser> projectUserDao) {
		super.setDao(projectUserDao);
	}
	
	@Override
	@Transactional
	public ProjectUser get(String id){
		ProjectUser model = projectUserDao.get(id);
		if(model == null)
			 return new ProjectUser();
		return model;
	}
	
}
