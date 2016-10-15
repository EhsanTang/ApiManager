package cn.crap.inter.service.table;

import java.util.List;

import cn.crap.framework.JsonResult;
import cn.crap.framework.base.IBaseService;
import cn.crap.model.Interface;
import cn.crap.utils.Page;

public interface IInterfaceService extends IBaseService<Interface>{
	/**
	 * 根据模块id，url，接口名等分页查询接口列表
	 * 管理员能查看所有接口、普通用户只能查看自己创建的模块下的接口
	 * @param page 分页信息
	 * @param moduleIds 
	 * @param interFace 接口类，自动封装的接口查询信息
	 * @param currentPage 当前页码
	 * @return
	 */
	public JsonResult getInterfaceList(Page page, List<String> moduleIds, Interface interFace, Integer currentPage);
	
	/**
	 * 根据接口名，参数，请求头组装请求示例
	 * @param interFaceInfo
	 * @return
	 */
	public void getInterFaceRequestExam(Interface interFaceInfo);
}
