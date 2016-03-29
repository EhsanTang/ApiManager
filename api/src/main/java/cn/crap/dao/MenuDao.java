package cn.crap.dao;

import org.springframework.stereotype.Repository;

import cn.crap.framework.base.GenericDaoImpl;
import cn.crap.inter.IMenuDao;
import cn.crap.model.Menu;

@Repository("menuDao")
public class MenuDao extends GenericDaoImpl<Menu, String>
		implements IMenuDao {

}