package cn.crap.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crap.dto.SearchDto;
import cn.crap.inter.service.IInterfaceService;
import cn.crap.inter.service.ISearchService;
import cn.crap.model.Interface;
import cn.crap.utils.DataType;
import cn.crap.utils.Page;
import cn.crap.utils.Tools;

@Service("dataBaseSearch")
public class DateBaseSearchService implements ISearchService{
	@Autowired
	private IInterfaceService interfaceService;
	
	/**
	 * 搜索
	 */
	@Override
	public List<SearchDto> search(String keyword, Page page){
		List<SearchDto> searchDtos = new ArrayList<SearchDto>();
		Map<String, Object> map = Tools.getMap("interfaceName|like", keyword);
		List<Interface> interfaces = interfaceService.findByMap(map, page, null);
		for(Interface inter:interfaces){
			SearchDto searchDto = new SearchDto(inter.getId(), inter.getInterfaceName(), DataType.INTERFACE.name(), 
					"web.do#/webInterfaceDetail/"+inter.getId(), inter.getRemark(), inter.getVersion(), inter.getModuleName(), inter.getCreateTime());
			searchDtos.add(searchDto);
		}
		return searchDtos;
	}
	
	@Override
	public boolean delete(SearchDto searchDto){
		return true;
	}
	
	@Override
	public boolean update(SearchDto searchDto){
		return true;
	}
	
	@Override
	public boolean add(SearchDto searchDto){
		return true;
	}
}
