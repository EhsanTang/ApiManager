package cn.crap.dao;

import org.springframework.stereotype.Repository;

import cn.crap.framework.base.GenericDaoImpl;
import cn.crap.inter.InterfaceInfoDao;
import cn.crap.model.InterfaceInfoModel;

@Repository("interfaceInfoDao")
public class InterfaceInfoDaoImpl extends GenericDaoImpl<InterfaceInfoModel, String>
		implements InterfaceInfoDao {

}