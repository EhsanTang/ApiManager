package cn.crap.inter.service;

import java.util.Map;

import cn.crap.framework.JsonResult;
import cn.crap.framework.base.IBaseService;
import cn.crap.model.Interface;
import cn.crap.utils.Page;

public interface IInterfaceService extends IBaseService<Interface>{

	JsonResult getInterfaceList(Page page, Map<String, Object> map, Interface interFace, Integer currentPage);
}
