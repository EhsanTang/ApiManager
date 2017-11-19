package genCode.utils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class GenMain {

	public static String[] colnames; // 列名数组
	public static String[] tablecolnames; // 列名类型数组
	public static String[] colTypes; // 列名类型数组
	//public static int[] colSizes; // 列名大小数组

	public GenMain(String tableName) {
		Connection conn = GenSqlUtil.openConnection(); // 得到数据库连接
		PreparedStatement pstmt = null;
		String strsql = "select * from " + tableName;
		try {
			pstmt = conn.prepareStatement(strsql);
			ResultSetMetaData rsmd = pstmt.getMetaData();
			int size = rsmd.getColumnCount(); // 共有多少列
			colnames = new String[size];
			colTypes = new String[size];
			tablecolnames = new String[size];
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				tablecolnames[i] = rsmd.getColumnName(i + 1);
				colnames[i] = CenUtil.getCamelStr(rsmd.getColumnName(i + 1));
				colTypes[i] = rsmd.getColumnTypeName(i + 1);
			}
			try {
				String basePath = "/Users/apple/ijworkspace/ApiManager/api/src/test/java/genCode/genResult/";
				
				// Service
				File file = new File(basePath);
				if (!file.exists()) {
					file.mkdirs();
				}
				String fileName = basePath + "/Mybatis" + CenUtil.initcap(tableName) + "Service.java";
				file = new File(fileName);
				if (file.exists()) {
					file.delete();
				}
				CenUtil.writeStringToFile(new File(fileName), CenUtil.processServiceImp(tableName));
				
				// 生成Dto
				fileName = basePath + "/" + CenUtil.initcap(tableName) + "Dto.java";
				file = new File(fileName);
				if (file.exists()) {
					file.delete();
				}
				CenUtil.writeStringToFile(new File(fileName), CenUtil.processDto(tableName));

				// 生成Adapter
				fileName = basePath + "/" + CenUtil.initcap(tableName) + "Adapter.java";
				file = new File(fileName);
				if (file.exists()) {
					file.delete();
				}
				CenUtil.writeStringToFile(new File(fileName), CenUtil.processAdapter(tableName));

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			GenSqlUtil.closeDatabase(conn, pstmt);
		}
	}




	public static void main(String[] args) {
		String tableName = "source";
		new GenMain(tableName);
		
	}

}