package cn.crap.dao.custom;

import org.apache.ibatis.annotations.*;

/**
 * @author nico 2017-07-28
 */
public interface CustomDebugMapper {

	@Delete("delete from Debug where moduleId=#{moduleId}")
	void deleteByModuleId(@Param("moduleId") String moduleId);

}