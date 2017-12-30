package cn.crap.dao.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author nico 2017-07-28
 */
@Service
public class CustomArticleDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;


    public List<String> queryArticleCategoryByUserId(String userId){
		return jdbcTemplate.queryForList("select distinct category from article where moduleId in( select id from Module where userId=?", new Object[]{userId},  String.class);
	}

    public List<String> queryArticleCategoryByWeb(){
		return jdbcTemplate.queryForList("select distinct category from article where moduleId='web'",  String.class);

	}

    public List<String> queryTop20Category(String moduleId, String type){
		return jdbcTemplate.queryForList("select distinct category from article where moduleId=? and type=? limit 0,20", new Object[]{moduleId, type}, String.class);
	}

    public void updateClickById(String id){
		jdbcTemplate.update("update article set click=click+1 where id=?", id);
	}
}