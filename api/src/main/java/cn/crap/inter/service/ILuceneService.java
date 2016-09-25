package cn.crap.inter.service;

import java.util.List;

import cn.crap.dto.ILuceneDto;

public interface ILuceneService<T extends ILuceneDto>{
	public List<T> getAll();
}
