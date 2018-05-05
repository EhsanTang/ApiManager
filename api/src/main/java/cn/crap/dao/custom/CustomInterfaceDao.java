package cn.crap.dao.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author nico 2017-07-28
 */
@Service
public class CustomInterfaceDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void updateFullUrlByModuleId(String moduleUrl, String moduleId){
		jdbcTemplate.update("update interface set fullUrl=CONCAT(?,url) where moduleId=?", moduleUrl, moduleId);
	}

	public void deleteTemplateByModuleId(String moduleId){
		Assert.notNull(moduleId);
		jdbcTemplate.update("update interface set isTemplate=0 where moduleId =?", moduleId);
	}


}