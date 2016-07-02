package cn.crap.inter.service;

import java.util.List;

import cn.crap.dto.PickDto;
import cn.crap.framework.base.IBaseService;
import cn.crap.model.DataCenter;

public interface IDataCenterService extends IBaseService<DataCenter>{
	/**
	 * 
	 * @param picks
	 * @param idPre html id前缀
	 * @param parentId 父id
	 * @param type 数据类型
	 * @param deep 目录层次显示前缀
	 * @param value 选中后的值：为空则为datacenter.name ，不为空则使用datacenter.id,datacenter.name 替换value中的moduleId，moduleName
	 * @param suffix
	 */
	void getDataCenterPick(List<PickDto> picks, String idPre, String parentId, String type, String deep, String value,
			String suffix);

}
