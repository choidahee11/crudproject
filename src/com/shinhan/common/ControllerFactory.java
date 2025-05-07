package com.shinhan.common;

import com.shinhan.dept.DeptController;
import com.shinhan.emp.EmpController;
import com.shinhan.emp.JobController;

public class ControllerFactory {

	public static CommonControllerInterface make(String business) {
		CommonControllerInterface conteoller =null;
		
		switch (business) {
		case "emp"->{conteoller =new EmpController();}
		case"dept"->{conteoller =new DeptController();}
		case"job"->{conteoller =new JobController();}
		}
		return conteoller;
	}

}
