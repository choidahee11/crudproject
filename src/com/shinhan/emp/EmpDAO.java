package com.shinhan.emp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.sql.Types;
//import javax.lang.model.util.Types;

import com.shinhan.day15.DBUtil;

//DAO(Data Access Object):DB에 CRUD작업(select, insert, update, delete)
//Statement 는 sql 문을 보내는 통로...바인딩 변수 지우너하지 않음
//PreparedStatement:Statement 상속받은, 자인딩 변수 지원 , 바인딩 변수 쓸떄 사용
//Statement:바인딩 변수 안쓸때 사용 
//CallableStatement:sp호출 지원 



public class EmpDAO {

	// Stored Procedure를 실행하기 (직원번호를 받아서 이메일과 급여를 return )
	public EmpDTO execute_sp(int empid) {
		EmpDTO emp = null;
		Connection conn = DBUtil.getConnection();
		CallableStatement st = null;
		String sql = "{call sp_empinfo(?,?,?)}";

		try {
			conn.prepareCall(sql);
			st.setInt(1, empid);
			st.registerOutParameter(2, Types.VARCHAR);
			st.registerOutParameter(3, Types.INTEGER);

			boolean result =st.execute();

			emp = new EmpDTO();
			String email = st.getString(2);
			Double salary = st.getDouble(3);
			emp.setEmail(email);
			emp.setSalary(salary);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return emp;
	}

	
	public int empUpdate(EmpDTO emp) {
		int result = 0;
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		Map<String, Object> dynamicSQL = new HashMap<>();

		if (emp.getFirst_name() != null)
			dynamicSQL.put("First_name", emp.getFirst_name());
		if (emp.getLast_name() != null)
			dynamicSQL.put("LAST_NAME", emp.getLast_name());
		if (emp.getEmail() != null)
			dynamicSQL.put("Email", emp.getEmail());
		if (emp.getPhone_number() != null)
			dynamicSQL.put("Phone_number", emp.getPhone_number());
		if (emp.getHire_date() != null)
			dynamicSQL.put("Hire_date", emp.getHire_date());
		if (emp.getJob_id() != null)
			dynamicSQL.put("Job_id", emp.getJob_id());
		if (emp.getSalary() != null)
			dynamicSQL.put("Salary", emp.getSalary());
		if (emp.getCommission_pct() != null)
			dynamicSQL.put("Commission_pct", emp.getCommission_pct());
		if (emp.getManager_id() != null)
			dynamicSQL.put("Manager_id", emp.getManager_id());
		if (emp.getDepartment_id() != null)
			dynamicSQL.put("Department_id", emp.getDepartment_id());
		if (emp.getEmployees_id() != null)
			dynamicSQL.put("Employees_id", emp.getEmployees_id());

		String sql = " update employees set";
		String sql2 = " where EMPLOYEES_ID";

		for (String key : dynamicSQL.keySet()) { 
			sql += key + "=" + "?";
		}
		sql = sql.substring(0, sql.length() - 1);
		sql += sql2;
		System.out.println(sql);

		try {
			st = conn.prepareStatement(sql);
			int i = 1;
			for (String key : dynamicSQL.keySet()) {
				st.setObject(i++, dynamicSQL.get(key));
			}
			st.setInt(i, emp.getEmployees_id());
			result = st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// 수정(update)
	public int empUpdate2(EmpDTO emp) {
		int result = 0;
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		String sql = """
				insert into employees
					FIRST_NAME=?,
					LAST_NAME=?,
					EMAIL=?,
					PHONE_NUMBER=?,
					HIRE_DATE=?,
					JOB_ID=?,
					SALARY=?,
					COMMISSION_PCT=?,
					MANAGER_ID=?,
					DEPARTMENT_ID=?,
				where EMPLOYEE_ID=?
									""";
		try {
			st = conn.prepareStatement(sql);
			st.setInt(11, emp.getEmployees_id());
			st.setString(1, emp.getFirst_name());
			st.setString(2, emp.getLast_name());
			st.setString(3, emp.getEmail());
			st.setString(4, emp.getPhone_number());
			st.setDate(5, emp.getHire_date());
			st.setString(6, emp.getJob_id());
			st.setDouble(7, emp.getSalary());
			st.setDouble(8, emp.getCommission_pct());
			st.setInt(9, emp.getManager_id());
			st.setInt(10, emp.getDepartment_id());
			result = st.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return result;
	}

	// 입력해라(Insert)
	public int empInsert(EmpDTO emp) {
		int result = 0;
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		String sql = """
				insert into employees(
					EMPLOYEE_ID,
					FIRST_NAME,
					LAST_NAME,
					EMAIL,
					PHONE_NUMBER,
					HIRE_DATE,
					JOB_ID,
					SALARY,
					COMMISSION_PCT,
					MANAGER_ID,
					DEPARTMENT_ID )
					values(?,?,?,?,?,?,?,?,?,?,?)
									""";
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, emp.getEmployees_id());
			st.setString(2, emp.getFirst_name());
			st.setString(3, emp.getLast_name());
			st.setString(4, emp.getEmail());
			st.setString(5, emp.getPhone_number());
			st.setDate(6, emp.getHire_date());
			st.setString(7, emp.getJob_id());
			st.setDouble(8, emp.getSalary());
			st.setDouble(9, emp.getCommission_pct());
			st.setInt(10, emp.getManager_id());
			st.setInt(11, emp.getDepartment_id());

			result = st.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return result;
	}

	// 특정 직원을 삭제하기
	public int empDeleteById(int empid) {
		int result = 0;
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		String sql = "delete from employees where employee_id=?";
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, empid);
			result = st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public List<EmpDTO> selectByCondition(int deptid, String job, int salary, String hdate) {
		List<EmpDTO> emplist = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		// String
		// deptStr=Arrays.stream(arr).map(id->"?").collect(Collectors.joining(","));

		String sql = "select*from employees " + "where jod_id like= ? " + "and department_id= ? " // 10,20,30
				+ "and salary >= ?" + "and hire_date >= ?";

		try {
			st = conn.prepareStatement(sql); // sql 문을 준비한다
			st.setString(1, "%" + job + "%"); // 1번째 ?에 값을 setting 한다.
			st.setInt(2, deptid);
			st.setInt(3, salary);

			Date d = DateUtil.convertToSQLDate(DateUtil.convertToDate(hdate));
			st.setDate(4, d);

			/*
			 * int col=4; for(int i=0;i<arr length;i++) { st.setInt(cal++, arr[i]); }
			 */

			rs = st.executeQuery();
			while (rs.next()) { // 한번 돌아서 if
				EmpDTO emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// DBUtill.dbDiconnent
		}
		return null;
	}

	public List<EmpDTO> selectByJobAndDept(String job, int dept) {
		List<EmpDTO> emplist = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "select*from employees where employees_id= ? and department_id= ? ";

		try {
			st = conn.prepareStatement(sql); // sql문을 준비한다
			st.setString(1, job); // 1번째 ?에 값을 setting 한다.
			st.setInt(2, dept); // 2번째 ?에 값을 setting 한다.
			rs = st.executeQuery();
			while (rs.next()) { // 한번 돌아서 if
				EmpDTO emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// DBUtill.dbDiconnent
		}
		return emplist;
	}

	// job 으로 직원 조회
	public List<EmpDTO> selectByJob(String job) {
		List<EmpDTO> emplist = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		String sql = "select*from employees where employees_id=?";

		try {
			st = conn.prepareStatement(sql); // sql 문을 준비한다
			st.setString(1, job); // 1번째 ?에 값을 setting 한다.
			rs = st.executeQuery();
			while (rs.next()) { // 한번 돌아서 if
				EmpDTO emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// DBUtill.dbDiconnent
		}
		return emplist;
	}

	// 부서의 직원을 조회
	public List<EmpDTO> selectByDept(int deptid) {
		List<EmpDTO> emplist = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		Statement st = null;
		ResultSet rs = null;
		String sql = "select*from employees where department_id=" + deptid;

		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				EmpDTO emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return emplist;
	}

	// 직원번호로 직원 정보를 상세보기
	// 직원 한명만 보니까 list 없어도됨
	public EmpDTO selectByid(int empid) {
		EmpDTO emp = null;
		Connection conn = DBUtil.getConnection();
		Statement st = null;
		ResultSet rs = null;
		String sql = "select*from employees where employees_id=" + empid;

		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) { // 한번 돌아서 if
				emp = makeEmp(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
		return emp;
	}

	// 모든 직원 조회
	// 모든 직원을 조회하고 싶으니까 list 작성
	public List<EmpDTO> selectAll() {
		List<EmpDTO> emplist = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		Statement st = null;
		ResultSet rs = null;
		String sql = "select*from employees";

		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				EmpDTO emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return emplist;
	}

	private EmpDTO makeEmp(ResultSet rs) throws SQLException {
		EmpDTO emp = EmpDTO.builder().commission_pct(rs.getDouble("commission_pct"))
				.department_id(rs.getInt("department_id")).email(rs.getString("email"))
				.employees_id(rs.getInt("employee_id")).first_name(rs.getString("first_name"))
				.last_name(rs.getString("last_name")).phone_number(rs.getString("phone_number"))
				.hire_date(rs.getDate("hire_date")).job_id(rs.getString("job_id")).salary(rs.getDouble("salary"))
				.build();
		return emp;
	}

}
