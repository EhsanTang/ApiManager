package cn.crap.dao;

import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.inter.dao.IDataCenterDao;
import cn.crap.model.DataCenter;

@Repository("dataCenterDao")
public class DataCenterDao extends BaseDao<DataCenter>
		implements IDataCenterDao {

}