package cn.crap.dao;

import org.springframework.stereotype.Repository;

import cn.crap.framework.base.BaseDao;
import cn.crap.inter.dao.IWebPageDao;
import cn.crap.model.WebPage;

@Repository("webPageDao")
public class WebPageDao extends BaseDao<WebPage> implements IWebPageDao {
}