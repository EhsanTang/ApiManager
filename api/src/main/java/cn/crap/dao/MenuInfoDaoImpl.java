package cn.crap.dao;

import org.springframework.stereotype.Repository;

import cn.crap.framework.base.GenericDaoImpl;
import cn.crap.inter.MenuInfoDao;
import cn.crap.model.MenuInfoModel;

@Repository("menuInfoDao")
public class MenuInfoDaoImpl extends GenericDaoImpl<MenuInfoModel, String>
		implements MenuInfoDao {

}