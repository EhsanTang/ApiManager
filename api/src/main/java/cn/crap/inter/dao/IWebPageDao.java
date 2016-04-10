package cn.crap.inter.dao;

import java.util.List;

import cn.crap.framework.base.IBaseDao;
import cn.crap.model.WebPage;

public interface IWebPageDao extends IBaseDao<WebPage>{
	public List<String> getCategory();
}