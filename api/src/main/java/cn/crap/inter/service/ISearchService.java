package cn.crap.inter.service;

import java.util.List;

import cn.crap.dto.SearchDto;
import cn.crap.utils.Page;

public interface ISearchService{

	/**
	 * 根据关键词、分页信息查询
	 * @param keyword
	 * @param page
	 * @return
	 */
	List<SearchDto> search(String keyword, Page page);

	/**
	 * 删除索引文件
	 * @param searchDto
	 * @return
	 */
	boolean delete(SearchDto searchDto);

	/**
	 * 更新索引文件
	 * @param searchDto
	 * @return
	 */
	boolean update(SearchDto searchDto);

	/**
	 * 添加索引文件
	 * @param searchDto
	 * @return
	 */
	boolean add(SearchDto searchDto);

}
