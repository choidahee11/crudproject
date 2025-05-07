package com.shinhan.day15;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//CRUD(Create Read Update Delete)
//Read(select)
public class CRUDTest {

	public static void main(String[] args) {
		Connection conn =null;
		Statement st=null;
		ResultSet rs=null;
		String sql="SELECT DEPARTMENT_ID,MAX(SALARY),MIN(SALARY)\r\n"
				+ "FROM EMPLOYEES\r\n"
				+ "GROUP BY DEPARTMENT_ID\r\n"
				+ "HAVING MAX(SALARY)<>MIN(SALARY)";
		
		conn = DBUtil.getConnection();
		try {
			st=conn.createStatement();
			rs=st.executeQuery(sql);
			while(rs.next()) {
				int a=rs.getInt(1);
				int b=rs.getInt(2);
				int c=rs.getInt(3);
				System.out.println(a+"\t"+b+"\t"+c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public static void f_2(String[] args) {
		String url="jdbc:oracle:thi:@localhost:1521:xe";
		String userid="hr",uesrpass="hr";
		Connection conn=null;
		Statement st=null;
		ResultSet rs=null;
		String sql="SELECT DEPARTMENT_ID,COUNT(*)\r\n"
				+ "FROM EMPLOYEES\r\n"
				+ "GROUP BY DEPARTMENT_ID\r\n"
				+ "HAVING COUNT(*)>=5"
				+ "order by 2 desc";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn =DriverManager.getConnection(url, userid, uesrpass);
		    st=conn.createStatement();
		    rs=st.executeQuery(sql);
		    while(rs.next()) {
		    	int deptis =rs.getInt(1);
		    	int cut =rs.getInt(2);
		    	System.out.println("부서코드:"+deptis+"\t인원수"+uesrpass);
		    }
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null)rs.close();
				if(st!=null)st.close();
				if(conn!=null)conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
//sql 에서 파일을 찾고 자바에서 불러오는 연습 
	public static void f_1(String[] args) throws ClassNotFoundException, SQLException {
		//1.JDBC Driver 준비(class path추가)
		//1.JDBC Driver load
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("2.JDBC Driver load");
		//3.Connection
		String url="jdbc:oracle:thin:@localhost:1521:xe";
		String userid="hr",userpass="hr";
		Connection conn =DriverManager.getConnection(url, userid, userpass);
		System.out.println("3.Connectiont성공");
		//4. SQL문 보낼 통로 얻기 
		Statement st=conn.createStatement();
		System.out.println("4. SQL문 보낼 통로 얻기 성공");
		//5.SQL문 생성
		String sql="SELECT *\r\n"
				+ "FROM EMPLOYEES\r\n"
				+ "WHERE DEPARTMENT_ID=80";
		ResultSet rs=st.executeQuery(sql);
		while(rs.next()) {
			int empid =rs.getInt("employee_id");
			String fname =rs.getString("first_name");
			Date hdate= rs.getDate("hire_date");
			double comm=rs.getDouble("COMMISSION_PCT");
			System.out.printf("직원번호:%d 이름:%s hdate:%s comm:%3.1f",empid,fname,hdate,comm);
		}
		rs.close();
		st.close();
		conn.close();
	}

}
