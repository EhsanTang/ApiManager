package cn.crap.dao.custom;

import cn.crap.framework.MyException;
import cn.crap.model.ProjectPO;
import cn.crap.utils.MyString;
import cn.crap.utils.Page;
import cn.crap.utils.SafetyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nico 2017-07-28
 */
@Service
public class CustomProjectDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<String> queryProjectIdByUserId(String userId){
		Assert.notNull(userId);
		return jdbcTemplate.queryForList("select id from project where status>0 and userId=? or id in (select projectId from project_user where userId=?)",
				new String[]{userId, userId}, String.class);

	}

	public List<String> queryMyProjectIdByType(Integer type){
		Assert.notNull(type);
		return jdbcTemplate.queryForList("select id from project where type=?",
				new Integer[]{type}, String.class);
	}


	public List<ProjectPO> queryProjectByUserId(String userId, boolean onlyJoin, String name, final Page page) throws MyException {
		Assert.notNull(userId);
		Assert.notNull(page);
        SafetyUtil.checkSqlParam(name);

		List <Object> params = new ArrayList<>();
        params.add(userId);


		StringBuilder sb = new StringBuilder("select p.id, p.name, p.type, p.remark, p.userId, p.createTime, p.cover, p.sequence, p.status, p.uniKey from project p ");
        sb.append(" left join project_user u on p.id=u.projectId ");
        sb.append(" where u.userId=? ");
		if (onlyJoin){
			sb.append(" and u.type=2 ");
		}

		if (MyString.isNotEmpty(name)){
			sb.append(" and u.projectName like ? ");
			params.add("%" + name + "%");
		}

		sb.append(" order by u.sequence desc");
		sb.append(" limit " + page.getStart() + "," + page.getSize());

		return jdbcTemplate.query(sb.toString(), params.toArray(), new RowMapper<ProjectPO>() {
			@Override
			public ProjectPO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ProjectPO project = new ProjectPO();
				project.setId(rs.getString(1));
				project.setName(rs.getString(2));
				project.setType(rs.getByte(3));
				project.setRemark(rs.getString(4));
				project.setUserId(rs.getString(5));
				project.setCreateTime(rs.getTimestamp(6));
				project.setCover(rs.getString(7));
				project.setSequence(rs.getLong(8));
				project.setStatus(rs.getByte(9));
				project.setUniKey(rs.getString(10));
				return project;
			}
		});
	}

	public int countProjectByUserId(String userId, boolean onlyJoin, String name){
		Assert.notNull(userId);

		List <Object> params = new ArrayList<>();
        params.add(userId);
        StringBuilder sb = new StringBuilder("select count(0) from project_user where userId=? ");
        if (onlyJoin){
            sb.append(" and type=2 ");
        }

        if (name != null){
            sb.append(" and projectName like ?");
            params.add("%" + name + "%");
        }

		return jdbcTemplate.queryForObject(sb.toString(), params.toArray(), Integer.class);
	}
}