package cn.crap.dao.custom;

import cn.crap.enu.ArticleStatus;
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

    public void updateClickById(String id){
		jdbcTemplate.update("update article set click=click+1 where id=?", id);
	}

	public void updateTypeToNullById(String id){
		jdbcTemplate.update("update article set mkey=null where id=? and status!=" + ArticleStatus.PAGE.getStatus(), id);
	}

	public List<String> queryTop10RecommendCategory(){
		return jdbcTemplate.queryForList("select distinct category from article where category is not null and category !='' " +
				"and status=" + ArticleStatus.RECOMMEND.getStatus() + " limit 10", String.class);
	}
}