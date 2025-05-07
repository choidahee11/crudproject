package com.shinhan.day15;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CRUDTest2 {
	public static void main(String[] args) throws SQLException {
		//모두 성공하면 commit, 하나라도 실패하면 rollback
		//insert..
		//update..
		Connection conn = null; //DB 연결 한번만 해도됨 
		Statement st1 = null;
		Statement st2 = null;
		String sql1="""
				INSERT INTO EMP1(EMPLOYEE_ID,FIRST_NAME,LAST_NAME,HIRE_DATE,JOB_ID,EMAIL) 
				VALUES(2,'AA','BB',SYSDATE,'TT','ZZILRE')
				""";
		String sql2="UPDATE EMP1 SET SALARY=2000 WHERE EMPLOYEE_ID=198";
		
		conn=DBUtil.getConnection();
		conn.setAutoCommit(false);
		st1=conn.createStatement();
		int result1=st1.executeUpdate(sql1); 
		st2=conn.createStatement();
		int result2=st2.executeUpdate(sql2);
		
		
		if(result1>=1&&result2>=2) {
			conn.commit();
			} else {
				conn.rollback();
			}
		
	}

	public static void f_4(String[] args) throws SQLException {
		Connection conn = null;
		Statement st = null;
		int result = 0;
		String sql = """
				delete from EMP1
				WHERE EMPLOYEE_ID<100
				""";
		conn = DBUtil.getConnection();
		st = conn.createStatement();
		result = st.executeUpdate(sql); // insert 문장 업데이트해서 저장됨
		System.out.println(result >= 1 ? "건 delete success" : "delete fail");
	}

	public static void f_3(String[] args) throws SQLException {
		Connection conn = null;
		Statement st = null;
		int result = 0;
		String sql = """
								UPDATE EMP1
				SET DEPARTMENT_ID=( SELECT DEPARTMENT_ID
				                    FROM EMPLOYEES
				                    WHERE EMPLOYEE_ID=100),

				          SALARY=(SELECT SALARY
				                    FROM EMPLOYEES
				                    WHERE EMPLOYEE_ID=101)
				WHERE EMPLOYEE_ID=100

								""";
		conn = DBUtil.getConnection();
		st = conn.createStatement();
		result = st.executeUpdate(sql); // insert 문장 업데이트해서 저장됨
		System.out.println(result >= 1 ? "update success" : "update fail");
	}

	public static void f_2(String[] args) throws SQLException {
		Connection conn = null;
		Statement st = null;
		int result = 0;
		String sql = """
				INSERT INTO EMP1 VALUES(4,'정', '진','wlw@naver.com','폰',SYSDATE,'JOB',100,0.2,100,'20')
				""";
		conn = DBUtil.getConnection();
		st = conn.createStatement();
		result = st.executeUpdate(sql); // insert 문장 업데이트해서 저장됨
		System.out.println(result >= 1 ? "insert success" : "insert fail");
	}

	public static void f_1(String[] args) throws SQLException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = """
				SELECT ENAME,SAL,MGR
				FROM EMP
				WHERE MGR=(
				SELECT EMPNO FROM EMP WHERE ENAME='KING')
				""";
		conn = DBUtil.getConnection();
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		while (rs.next()) {
			String a = rs.getNString(1);
			int b = rs.getInt(2);
			int c = rs.getInt(3);
			System.out.println((a + "\t" + b + "\t" + c));
		}
	}

}
