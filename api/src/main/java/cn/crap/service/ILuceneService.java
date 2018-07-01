package cn.crap.service;

import cn.crap.dto.SearchDto;
import cn.crap.framework.MyException;

import java.util.List;

public interface ILuceneService{
	public List<SearchDto> getAll();
	public List<SearchDto> getAllByProjectId(String projectId);
	public String getLuceneType();
}
