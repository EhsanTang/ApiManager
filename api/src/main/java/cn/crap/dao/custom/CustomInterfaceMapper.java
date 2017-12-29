package cn.crap.dao.custom;

import org.apache.ibatis.annotations.*;

/**
 * @author nico 2017-07-28
 */
public interface CustomInterfaceMapper {

	@Update("update Interface set fullUrl=CONCAT(#{moduleUrl},url) where moduleId=#{moduleId}")
	void updateFullUrlByModuleId(@Param("moduleUrl") String moduleUrl, @Param("moduleId") String moduleId);

	@Update("update Interface set isTemplate=0 where moduleId =#{moduleId}")
	void deleteTemplateByModuleId(@Param("moduleId") String moduleId);


}