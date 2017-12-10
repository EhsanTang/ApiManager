package cn.crap.dao.mybatis.custom;

import cn.crap.model.mybatis.Project;
import cn.crap.utils.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author nico 2017-07-28
 */
public interface CustomInterfaceMapper {

	@Update("update Interface set fullUrl=CONCAT(#{moduleUrl},url) where moduleId=#{moduleId}")
	void updateFullUrlByModuleId(@Param("moduleUrl") String moduleUrl, @Param("moduleId") String moduleId);

	@Update("update Interface set isTemplate=0 where moduleId =#{moduleId}")
	void deleteTemplateByModuleId(@Param("moduleId") String moduleId);


}