package cn.crap.dao.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author nico 2017-07-28
 */
@Service
public class CustomHotSearchDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

    public List<String> queryTop10(){
		return jdbcTemplate.queryForList("select keyword from hot_search order by times DESC limit 10", String.class);
	}
}