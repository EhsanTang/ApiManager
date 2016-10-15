package cn.crap.inter.service.table;

import java.util.List;

import cn.crap.dto.PickDto;
import cn.crap.framework.base.IBaseService;
import cn.crap.model.Module;

public interface IModuleService extends IBaseService<Module>{
	/**
	 * 
	 * @param picks
	 * @param idPre html id前缀
	 * @param parentIds 父id
	 * @param type 数据类型
	 * @param value 选中后的值：为空则为datacenter.name ，不为空则使用datacenter.id,datacenter.name 替换value中的moduleId，moduleName
	 * @param suffix
	 */
	void getDataCenterPick(List<PickDto> picks, List<String> parentIds, String idPre, String value, String suffix);
	/**
	 * 根据状态获、类型、用户ID 获取DataCenterID
	 * @return
	 */
	List<String> getList(Byte status, String type, String userId);
	List<String> getListByStatuss(List<Byte> statuss, String type, String userId);
	
	
	
	
}
