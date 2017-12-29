package cn.crap.dao.custom;

import cn.crap.dao.custom.SqlProvider;
import cn.crap.model.mybatis.Article;
import cn.crap.utils.Page;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author nico 2017-07-28
 */
@Service
public class CustomArticleMapper {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<Article> queryArticle(String moduleId, String name, String type, String category, Page page){
		StringBuilder sb = new StringBuilder("SELECT id,type,name,click,category,createTime,mkey,moduleId,brief,sequence FROM article ");
		sb.append(" where moduleId = '" + SqlHelper.checkParams(moduleId) + "'");
		if (name != null){
			sb.append(" and name like '%" + SqlHelper.checkParams(name) + "%'");
		}
		if (type != null){
			sb.append(" and type = '" + SqlHelper.checkParams(type) +"'");
		}
		if (category != null){
			sb.append(" and category = '" + SqlHelper.checkParams(category) +"'");
		}

		sb.append(" order by sequence asc");
		sb.append(" limit " + page.getStart() + "," + page.getSize());
		return jdbcTemplate.queryForList(sb.toString(), Article.class);
	}


    public List<String> queryArticleCategoryByUserId(String userId){
		return jdbcTemplate.queryForList("select distinct category from article where moduleId in( select id from Module where userId=?", new Object[]{userId},  String.class);
	}

    public List<String> queryArticleCategoryByWeb(){
		return jdbcTemplate.queryForList("select distinct category from article where moduleId='web'",  String.class);

	}

    public List<String> queryArticleCategoryByModuleIdAndType(String moduleId, String type){
		return jdbcTemplate.queryForList("select distinct category from article where moduleId=? and type=?", new Object[]{moduleId, type}, String.class);
	}

    public void updateClickById(String id){
		jdbcTemplate.update("update article set click=click+1 where id=?", id);
	}
}