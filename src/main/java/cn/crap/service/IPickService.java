package cn.crap.service;

import cn.crap.dto.PickDto;
import cn.crap.framework.MyException;

import java.util.List;

public interface IPickService {

    /**
     * @param code 需要选着的pick代码
     * @param key  pick二级关键字参数（如类型、父节点等）
     * @return
     * @throws MyException
     */
    List<PickDto> getPickList(String code, String key) throws MyException;

}