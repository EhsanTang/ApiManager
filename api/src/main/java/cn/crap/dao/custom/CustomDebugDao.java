package cn.crap.dao.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author nico 2017-07-28
 */
@Service
public class CustomDebugDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;


	public void deleteByModuleId(String moduleId){
        Assert.notNull(moduleId);
        jdbcTemplate.update("delete from debug where moduleId=?", moduleId);
	}

}