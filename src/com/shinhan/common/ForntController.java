package com.shinhan.common;

import java.util.Scanner;

//FrontController 패턴:Controller가 여러개인 경우 사용자의 요청과 응답은 출구가 여러개
//바람직하지 않음 
//하나로 통합(Frount)
import com.shinhan.dept.DeptController;
import com.shinhan.emp.EmpController;

public class ForntController {

	public static void main(String[] args) {
		// 사용자가 emp,dept 작업 할 것인지 결정

		Scanner sc = new Scanner(System.in);
		boolean isStop = false;
		CommonControllerInterface controller = null;
		while (!isStop) {
			System.out.println("emp,dept>>");
			String job = sc.next();
			switch (job) {
			case "emp" -> {
				controller = ControllerFactory.make("emp");
			}
			case "dept" -> {
				controller = ControllerFactory.make("dept");
			}
			case "job" -> {
				controller = ControllerFactory.make("job");
			}
			case "end" -> {
				isStop = true;
				continue;
			}
			default -> {
				continue;
			}

			}
			/*
			 * if (job.equals("emp")) { controller = ControllerFactory.make("emp"); } else
			 * if (job.equals("end")) { controller = ControllerFactory.make("dept"); isStop
			 * = true; }
			 */
			
			//전략패턴 
			controller.execute();// 작업이 달라져도 사용법은 같다. (전략패턴)
		}
		sc.close();
		System.out.println("====main end===");
	}
}
