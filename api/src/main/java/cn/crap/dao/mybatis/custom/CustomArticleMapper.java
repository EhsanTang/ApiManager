package cn.crap.dao.mybatis.custom;

import cn.crap.model.mybatis.Article;
import cn.crap.utils.Page;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;

/**
 * @author nico 2017-07-28
 */
public interface CustomArticleMapper {

	@SelectProvider(type=SqlProvider.class, method="queryArticleSql")
	@Results(
			{
					@Result(id = true, column = "id", property = "id"),
					@Result(column = "type", property = "type"),
					@Result(column = "name", property = "name"),
					@Result(column = "category", property = "category"),
					@Result(column = "createTime", property = "createTime"),
					@Result(column = "key", property = "key"),
					@Result(column = "moduleId", property = "moduleId"),
					@Result(column = "brief", property = "brief"),
					@Result(column = "sequence", property = "sequence"),
					@Result(column = "click", property = "click")
			})
	List<Article> queryArticle(String moduleId, String name, String type, String category, Page page);

	@Select("select distinct category from article where moduleId in( select id from Module where userId=#{userId}")
	List<String> queryArticleCatetoryByUserId(@Param("userId") String userId);

	@Select("select distinct category from article where moduleId='web'")
	List<String> queryArticleCatetoryByWeb();

	@Select("select distinct category from article where moduleId=#{moduleId} and type=#{type}")
	List<String> queryArticleCatetoryByModuleIdAndType(@Param("moduleId") String moduleId, @Param("type") String type);

	@Update("update article set click=click+1 where id=#{id}")
	void updateClickById(@Param("id") String id);
}