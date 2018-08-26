package cn.crap.dao.custom;

import cn.crap.model.Project;
import cn.crap.utils.Page;
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


	public List<Project> queryProjectByUserId(String userId, boolean onlyJoin, String name, final Page page){
		Assert.notNull(userId);
		Assert.notNull(page);

		List <Object> params = new ArrayList<>();
        params.add(userId);
        params.add(userId);
		StringBuilder sb = new StringBuilder("select id, name, type, remark, userId, createTime, cover, sequence, status from project where");
		if (onlyJoin){
			sb.append(" userId !=? and id in (select projectId from project_user where userId=?)");
		}else {
			sb.append(" (userId= ? or id in (select projectId from project_user where userId=?))");
		}

		if (name != null){
			sb.append(" and name like ? ");
			params.add("%" + name + "%");
		}

		sb.append(" order by sequence desc");
		sb.append(" limit " + page.getStart() + "," + page.getSize());

		return jdbcTemplate.query(sb.toString(), params.toArray(), new RowMapper<Project>() {
			@Override
			public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
				Project project = new Project();
				project.setId(rs.getString(1));
				project.setName(rs.getString(2));
				project.setType(rs.getByte(3));
				project.setRemark(rs.getString(4));
				project.setUserId(rs.getString(5));
				project.setCreateTime(rs.getTimestamp(6));
				project.setCover(rs.getString(7));
				project.setSequence(rs.getInt(8));
				project.setStatus(rs.getByte(9));
				return project;
			}
		});
	}

	public int countProjectByUserId(String userId, boolean onlyJoin, String name){
		Assert.notNull(userId);

		List <Object> params = new ArrayList<>();
        params.add(userId);
        params.add(userId);
        StringBuilder sb = new StringBuilder("select count(0) from project where ");
        if (onlyJoin){
            sb.append(" userId != ? and id in (select projectId from project_user where userId=?)");
        }else {
            sb.append(" (userId= ? or id in (select projectId from project_user where userId=?))");
        }

        if (name != null){
            sb.append(" and name like ?");
            params.add("%" + name + "%");
        }

		return jdbcTemplate.queryForObject(sb.toString(), params.toArray(), Integer.class);
	}
}