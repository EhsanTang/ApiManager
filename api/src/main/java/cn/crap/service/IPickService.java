package cn.crap.service;

import java.util.List;

import cn.crap.dto.LoginInfoDto;
import cn.crap.dto.PickDto;
import cn.crap.framework.MyException;

public interface IPickService {

	/**
	 * 
	 * @param code 需要选着的pick代码
	 * @param key pick二级关键字参数（如类型、父节点等）
	 * @return
	 * @throws MyException 
	 */
	List<PickDto> getPickList(String code, String key) throws MyException;

}