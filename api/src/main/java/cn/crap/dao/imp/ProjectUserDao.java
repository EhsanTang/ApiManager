package cn.crap.dao.imp;

import cn.crap.dao.IProjectUserDao;
import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.model.ProjectUser;

@Repository("projectUserDao")
public class ProjectUserDao extends BaseDao<ProjectUser> implements IProjectUserDao {
}