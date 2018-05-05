package unitTest;

import cn.crap.service.ISearchService;
import genCode.utils.GenSqlUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// v7.9升级v8.0
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:springMVC.xml"})
public class UpdateProjectId {
    private Map<String, String> moduleProjectMap = new HashMap<String, String>();

	@Test
    public void addProjectIdForArticle(){
        getModuleProjectMap();
        Connection conn = GenSqlUtil.openConnection(); // 得到数据库连接
        PreparedStatement pstmt = null;
        String sqls[] = {"update article set projectId='%s' where moduleId='%s'",
                "update interface set projectId='%s' where moduleId='%s'",
                "update source set projectId='%s' where moduleId='%s'"};
        try {
            Iterator<Map.Entry<String, String>> entries = moduleProjectMap.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, String> entry = entries.next();
                for (String sql: sqls) {
                    pstmt = conn.prepareStatement(String.format(sql, entry.getValue(), entry.getKey()));
                    pstmt.execute();
                    System.out.println(String.format(sql, entry.getValue(), entry.getKey()));
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            GenSqlUtil.closeDatabase(conn, pstmt);
        }
    }


	public void getModuleProjectMap(){
		Connection conn = GenSqlUtil.openConnection(); // 得到数据库连接
		PreparedStatement pstmt = null;
        String strsql = "select id,projectId from module";
        try {
			pstmt = conn.prepareStatement(strsql);
            ResultSet rs = pstmt.executeQuery();
            while (null != rs && rs.next()) {
                String moduleId = rs.getString(1);
                String projectId = rs.getString(2);
                moduleProjectMap.put(moduleId, projectId);
            }
        } catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GenSqlUtil.closeDatabase(conn, pstmt);
		}
	}
}
