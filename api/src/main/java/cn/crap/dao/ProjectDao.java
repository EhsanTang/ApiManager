package cn.crap.dao;

import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.inter.dao.IProjectDao;
import cn.crap.model.Project;

@Repository("projectDao")
public class ProjectDao extends BaseDao<Project>
		implements IProjectDao {

}