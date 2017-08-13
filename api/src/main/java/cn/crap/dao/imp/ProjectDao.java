package cn.crap.dao.imp;

import cn.crap.dao.IProjectDao;
import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.model.Project;

@Repository("projectDao")
public class ProjectDao extends BaseDao<Project>
		implements IProjectDao {

}