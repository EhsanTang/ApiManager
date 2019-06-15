package cn.crap.service;

import cn.crap.dto.SearchDto;

import java.util.List;

public interface ILuceneService{
	/**
	 * 根据ID分页查询所有数据
	 * @param projectId 可选
	 * @param id 可选
	 * @return
	 */
	List<SearchDto> selectOrderById(String projectId, String id, int pageSize);
}
