package cn.crap.dao.imp;

import cn.crap.dao.IMenuDao;
import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.model.Menu;

@Repository("menuDao")
public class MenuDao extends BaseDao<Menu>
		implements IMenuDao {

}