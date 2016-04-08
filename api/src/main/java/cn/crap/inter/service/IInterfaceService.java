package cn.crap.inter.service;

import java.util.Map;

import cn.crap.framework.JsonResult;
import cn.crap.framework.base.IBaseService;
import cn.crap.model.Interface;
import cn.crap.utils.Page;

public interface IInterfaceService extends IBaseService<Interface>{
	/**
	 * 根据模块id，url，接口名等分页查询接口列表
	 * @param page 分页信息
	 * @param map 
	 * @param interFace 接口类，自动封装的接口查询信息
	 * @param currentPage 当前页码
	 * @return
	 */
	public JsonResult getInterfaceList(Page page, Map<String, Object> map, Interface interFace, Integer currentPage);
	
	/**
	 * 根据接口名，参数，请求头组装请求示例
	 * @param interFaceInfo
	 * @return
	 */
	public void getInterFaceRequestExam(Interface interFaceInfo);
}
