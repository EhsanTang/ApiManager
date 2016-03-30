package cn.crap.dao;

import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.inter.dao.IMenuDao;
import cn.crap.model.Menu;

@Repository("menuDao")
public class MenuDao extends BaseDao<Menu>
		implements IMenuDao {

}