package cn.crap.dao.mybatis.custom;

import cn.crap.model.mybatis.Article;
import cn.crap.utils.Page;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author nico 2017-07-28
 */
@Service
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


}