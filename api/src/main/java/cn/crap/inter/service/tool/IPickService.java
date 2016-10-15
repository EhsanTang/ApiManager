package cn.crap.inter.service.tool;

import java.util.List;

import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.PickDto;
import cn.crap.framework.MyException;

public interface IPickService {

	/**
	 * 
	 * @param picks 后台选项
	 * @param code 需要选着的pick代码
	 * @param key pick二级关键字（如类型、父节点等）
	 * @return
	 * @throws MyException 
	 */

	void getPickList(List<PickDto> picks, String code, String key, LoginInfoDto user) throws MyException;

}