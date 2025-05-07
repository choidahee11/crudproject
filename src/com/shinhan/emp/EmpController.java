package com.shinhan.emp;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.shinhan.common.CommonControllerInterface;


//MVC2 모델 
//FrontController->Controller선택->service->DAO->DB
public class EmpController implements CommonControllerInterface{

	static Scanner sc = new Scanner(System.in);
	static EmpService empservice = new EmpService();

	public  void execute () {
		boolean isStop = false;
		while (!isStop) {
			menuDisplay();
			int job = sc.nextInt();
			switch (job) {
			case 1 -> {
				f_selectAll();
			}
			case 2 -> {
				f_selectByid();
			}
			case 3 -> {
				f_selectByDept();
			}
			case 4 -> {
				f_selectByJob();
			}
			case 5 -> {
				f_selectByJobAndDept();
			}
			case 6 -> {
				f_selectByCondition();
			}
			case 7 -> {
				f_deleteById();
			}
			case 8 -> {
				f_Insert();
			}
			case 9 -> {
				f_updateEmp();
			}
			case 10 -> {
				f_sp_call();
			}
			case 99 -> {
				isStop = true;
			}
			default -> {
				System.out.println("잘못된 선택입니다.");
			}
			}
		}
		System.out.println("good bye");
	}

	private static void f_sp_call() {
		System.out.println("조회할 직원 id");
		int employee_id = sc.nextInt();
		EmpDTO emp = empservice.execute_sp(employee_id);
		String message = "해당직원은 존재하지 않습니다.";
		if (emp != null) {
			message = emp.getEmail() + "---" + emp.getSalary();
		}
		EmpView.display(message);

	}

	private static void f_updateEmp() {
		System.out.println("수정할직원id>>");
		int employee_id = sc.nextInt();
		EmpDTO exist_emp = empservice.selectByid(employee_id);
		if (exist_emp == null) {
			EmpView.display("존재하지 않는 직원");
			return;
		}
		EmpView.display("==존재는 직원정보입니다==");
		EmpView.display(exist_emp);
		int result = empservice.empUpdate(makeemp(employee_id));
		EmpView.display(result + "건수정");

	}

	private static void f_Insert() {
		System.out.println("신규직원id>>");
		int employee_id = sc.nextInt();

		int result = empservice.empUpdate(makeemp2(employee_id));
		EmpView.display(result + "건입력");
	}

	static EmpDTO makeemp(int employee_id) {
		System.out.print("first_name>>");
		String first_name = sc.next();

		System.out.print("last_name>>");
		String last_name = sc.next();

		System.out.print("email>>");
		String email = sc.next();

		System.out.print("phone_number>>");
		String phone_number = sc.next();

		System.out.print("hdate(yyyy-MM-dd)>>");
		String hdate = sc.next();
		Date hire_date = null;
		if (!hdate.equals("0"))
			hire_date = DateUtil.convertToSQLDate(DateUtil.convertToDate(hdate));

		System.out.print("job_id(FK:IT_PROG)>>");
		String job_id = sc.next();

		System.out.print("salary>>");
		Double salary = sc.nextDouble();

		System.out.print("commission_pct(0.2)>>");
		Double commission_pct = sc.nextDouble();
		System.out.print("manager_id(FK:100)>>");
		Integer manager_id = sc.nextInt();
		System.out.print("department_id(FK:60,90)>>");
		Integer department_id = sc.nextInt();

		if (first_name.equals("0"))
			first_name = null;
		if (last_name.equals("0"))
			last_name = null;
		if (email.equals("0"))
			email = null;
		if (phone_number.equals("0"))
			phone_number = null;
		if (job_id.equals("0"))
			job_id = null;
		if (salary == 0)
			salary = null;
		if (commission_pct == 0)
			commission_pct = null;
		if (manager_id == 0)
			manager_id = null;
		if (department_id == 0)
			department_id = null;

		EmpDTO emp = EmpDTO.builder().first_name(first_name).last_name(last_name).commission_pct(commission_pct)
				.email(email).phone_number(phone_number).hire_date(hire_date).job_id(job_id).salary(salary)
				.commission_pct(commission_pct).manager_id(manager_id).department_id(department_id).build();
		int result = empservice.empInsert(emp);
		System.out.println("건 삽입");

		return emp;
	}

	static EmpDTO makeemp2(int employee_id) {
		// Integer employees_idsc = sc.nextInt();
		System.out.println("직원id>>");

		String first_name = sc.next();
		System.out.println("first_name>>");

		String last_name = sc.next();
		System.out.println("last_name>>");

		String email = sc.next();
		System.out.println("email>>");

		String phone_number = sc.next();
		System.out.println("phone_number>>");

		String hdate = sc.next();
		Date hire_date = DateUtil.convertToSQLDate(DateUtil.convertToDate(hdate));
		System.out.println("hire_date>>");

		String job_id = sc.next();
		System.out.println("job_id>>(IT_PROG,60)");

		Double salary = sc.nextDouble();
		System.out.println("salary>>");

		Double commission_pct = sc.nextDouble();
		System.out.println("commission_pct>>");

		int manager_id = sc.nextInt();
		System.out.println("manager_id>>");

		int department_id = sc.nextInt();
		System.out.println("department_id>>");

		EmpDTO emp = EmpDTO.builder().first_name(first_name).last_name(last_name).commission_pct(commission_pct)
				.email(email).phone_number(phone_number).hire_date(hire_date).job_id(job_id).salary(salary)
				.commission_pct(commission_pct).manager_id(manager_id).department_id(department_id).build();
		int result = empservice.empInsert(emp);
		System.out.println("건 삽입");

		return emp;
	}

	private static void f_deleteById() {
		System.out.println("삭제할 직원 id>>");
		int empid = sc.nextInt();
		int result = empservice.empDeleteById(empid);
		EmpView.display(result + "삭제되었다");

	}

	private static void f_selectByCondition() {
		// =부서,like 직책, >=급여, >=입사일
		System.out.println("조회할 부서>>");
		int deptid = sc.nextInt();
		System.out.println("조회할 직책");
		String job = sc.toString();
		System.out.println("조회할 salary(이상)>>");
		int salary = sc.nextInt();
		System.out.println("조회할 입사일(yy-mm-dd)");
		String hdate = sc.toString();
		List<EmpDTO> emplist = empservice.selectByCondition(deptid, job, salary, hdate);
		EmpView.display(emplist);
	}

	private static void f_selectByJobAndDept() {
		System.out.println("조회할 직책id,부서id>>"); // IT_PROG,60
		String data = sc.next();
		String[] arr = data.split(",");
		String jobid = arr[0];
		int deptid = Integer.parseInt(arr[1]);
		List<EmpDTO> emplist = empservice.selectByJobAndDept(jobid, deptid);
		EmpView.display(emplist);

	}

	private static void f_selectByJob() {
		System.out.println("조회할 직책");
		String job = sc.toString();
		List<EmpDTO> emplist = empservice.selectByJob(job);
		EmpView.display(emplist);

	}

	private static void f_selectByDept() {
		System.out.println("조회할 부서id");
		int deptid = sc.nextInt();
		List<EmpDTO> emplist = empservice.selectByDept(deptid);
		EmpView.display(emplist);

	}

	private static void f_selectByid() {
		System.out.println("조회할 id");
		int empid = sc.nextInt();
		EmpDTO emp = empservice.selectByid(empid);
		EmpView.display(emp);

	}

	private static void f_selectAll() {
		List<EmpDTO> emplist = empservice.selectAll();
		EmpView.display(emplist);
	}

	private static void menuDisplay() {
		System.out.println("==================================================================");
		System.out.println("1.모두조회 2.조회(직원번호) 3.조회(부서) 4.조회(직책) 5.조회(부서와직책) 6.조건조회 ");
		System.out.println("7.삭제하기(delete) 8.추가(insert) 9.수정(update) 10.sp호출 99.끝");
		System.out.println("==================================================================");
		System.out.print("작업선택> ");
	}
	public static void main(String[] args) {
		new EmpController().execute();
	}
}
