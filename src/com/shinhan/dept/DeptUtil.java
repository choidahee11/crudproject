package com.shinhan.dept;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DeptUtil {

	public static Connection getConnection() {
		Connection conn = null;
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String userid = "hr", uesrpass = "hr";
		
		try {
			conn=DriverManager.getConnection(url, userid, uesrpass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;

	}
	public static void dbDisconnect(Connection conn, Statement st, ResultSet rs) {
			try {
				if(rs!=null)rs.close();
				if(st!=null)rs.close();
				if(conn!=null)rs.close();
				rs.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
	}
}
