package cn.crap.inter.service;

import java.util.List;

import cn.crap.framework.base.IBaseService;
import cn.crap.model.WebPage;

public interface IWebPageService extends IBaseService<WebPage>{

	List<String> findCategory();

}
