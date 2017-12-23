package cn.crap.dao.mybatis.custom;

import cn.crap.dao.mybatis.custom.SqlHelper;
import cn.crap.dao.mybatis.custom.SqlProvider;
import cn.crap.model.mybatis.Article;
import cn.crap.model.mybatis.Project;
import cn.crap.utils.Page;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author nico 2017-07-28
 */
public interface CustomProjectMapper {

	@Select("select id from project where status>0 and userId=#{userId} or id in (select projectId from projectUser where userId=#{userId})")
	List<String> queryProjectIdByUserId(@Param("userId") String userId);

	@Select("select id from project where type=#{type}")
	List<String> queryMyProjectIdByType(@Param("type") int type);


	@SelectProvider(type=SqlProvider.class, method="queryProjectSqlByUserIdName")
	@Results(
			{
					@Result(id = true, column = "id", property = "id"),
					@Result(column = "name", property = "name"),
					@Result(column = "type", property = "type"),
					@Result(column = "remark", property = "remark"),
					@Result(column = "userId", property = "userId"),
					@Result(column = "createTime", property = "createTime"),
					@Result(column = "cover", property = "cover")
			})
	List<Project> queryProjectByUserId(String userId, String name, Page page);

	@SelectProvider(type=SqlHelper.class, method = "countProjectSqlByUserIdName")
	int countProjectByUserId(String userId, String name);


}