package cn.crap.dto;

import cn.crap.inter.service.ICacheService;

public interface ILuceneDto {
	public SearchDto toSearchDto(ICacheService cacheService);
}
