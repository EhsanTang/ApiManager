package cn.crap.dto;

import cn.crap.service.ICacheService;

public interface ILuceneDto {
	public SearchDto toSearchDto(ICacheService cacheService);
}
