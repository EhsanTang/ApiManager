package cn.crap.inter.service.tool;

import java.util.List;

import cn.crap.dto.ILuceneDto;

public interface ILuceneService<T extends ILuceneDto>{
	public List<T> getAll();
	public List<T> getAllByProjectId(String projectId);
	public String getLuceneType();
}
