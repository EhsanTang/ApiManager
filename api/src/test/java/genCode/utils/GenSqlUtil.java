package genCode.utils;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class GenSqlUtil {

	public static Connection openConnection() {
		try {
			// 加载MySql的驱动类
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("找不到驱动程序类 ，加载驱动失败！");
			e.printStackTrace();
		}
		String url = "jdbc:mysql://localhost:3306/apidev?useUnicode=true&characterEncoding=utf-8";
		String username = "apidev";
		String password = "apidev";
		try {
			Connection con = DriverManager.getConnection(url, username, password);
			return con;
		} catch (SQLException se) {
			System.out.println("数据库连接失败！");
			se.printStackTrace();
		}
		return null;

	}
	public static void closeDatabase(Connection conn, PreparedStatement pstmt) {
		if( conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if( pstmt != null){
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
}