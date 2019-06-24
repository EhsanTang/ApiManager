package cn.crap.service;

import cn.crap.dto.SearchDto;

import java.util.List;

public interface ILuceneService {
    List<SearchDto> getAll();

    List<SearchDto> getAllByProjectId(String projectId);

    String getLuceneType();
}
