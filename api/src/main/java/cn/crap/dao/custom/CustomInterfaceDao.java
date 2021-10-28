package cn.crap.dao.custom;

import cn.crap.utils.SafetyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

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

	public void deleteByModuleId(String moduleId){
		Assert.notNull(moduleId);
		jdbcTemplate.update("delete from interface where moduleId=?", moduleId);
	}

    public void deleteByModuleId(String moduleId, List<String> uniKeys) throws Exception{
        Assert.notNull(moduleId);
        if (CollectionUtils.isEmpty(uniKeys)){
        	return;
		}

		StringBuffer buf= new StringBuffer("delete from interface where moduleId=? and uniKey in (");

		for (int i=0; i< uniKeys.size(); i++) {
			SafetyUtil.checkSqlParam(uniKeys.get(i));

			if (i!=0) buf.append(",");

			buf.append("'" + uniKeys.get(i) + "'");
		}
		buf.append(")");

        jdbcTemplate.update(buf.toString(), moduleId);
    }

}