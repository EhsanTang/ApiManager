package cn.crap.dao.imp;

import cn.crap.framework.base.IBaseDao;
import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.model.Project;

@Repository("projectDao")
public class ProjectDao extends BaseDao<Project>
		implements IBaseDao<Project> {

}