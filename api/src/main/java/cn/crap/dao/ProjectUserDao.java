package cn.crap.dao;

import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.inter.dao.IProjectUserDao;
import cn.crap.model.ProjectUser;

@Repository("projectUserDao")
public class ProjectUserDao extends BaseDao<ProjectUser> implements IProjectUserDao {
}