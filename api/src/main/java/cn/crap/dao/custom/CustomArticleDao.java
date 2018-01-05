package cn.crap.dao.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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
}