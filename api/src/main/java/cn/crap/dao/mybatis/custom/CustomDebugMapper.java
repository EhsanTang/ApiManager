package cn.crap.dao.mybatis.custom;

import cn.crap.model.mybatis.Article;
import cn.crap.utils.Page;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author nico 2017-07-28
 */
public interface CustomDebugMapper {

	@Delete("delete from Debug where moduleId=#{moduleId}")
	void deleteByModuleId(@Param("moduleId") String moduleId);

}